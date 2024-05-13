package bestellsystem;

import org.jetbrains.annotations.NotNull;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.Hashtable;
import java.util.Optional;


public class LDAPService{

    //Klasse um meine Infos aus der Config zu ziehen
    private static SQL s = new SQL();
    private static Configurations c = new Configurations();
    private static String ldap = c.LDAPConfig();
    private static String[] split = ldap.split(";");

    private static final String LDAP_URL = split[0];
    private static final String BASE_DN = split[1];

    private final DirContext context;

    public LDAPService() {
        this.context = this.loginToUser("apache-auth@mahlo.de", split[2]);
    }

    public @NotNull Optional<Object> login(Context context) {
        String parameter = String.valueOf(context);
        String[] parts = parameter.split(";");
        String userName = parts[0];
        String password = parts[1];
        if (this.loginToUser(userName + "@mahlo.de", password) == null) { // @mahlo.de must be applied
            return Optional.empty();
        }

        try {
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String filter = "(sAMAccountName=" + userName + ")";
            NamingEnumeration<SearchResult> results = this.context.search(BASE_DN, filter, controls);

            if (results.hasMore()) {
                SearchResult searchResult = results.next();
                Attributes attributes = searchResult.getAttributes();
                String[] name = attributes.get("cn").get().toString().split(" ");
                LDAPAccountInfo ldapAccountInfo = new LDAPAccountInfo(userName, name[1], name[0], attributes.get("department").get().toString());


                //s.newUser(name[1], name[0], attributes.get("department").get().toString());
                return Optional.of(ldapAccountInfo);
            }

        } catch (NamingException exception) {
            exception.printStackTrace();
        }

        return Optional.empty();
    }

    public void closeLDAPConnection() {
        try {
            this.context.close();
        } catch (NamingException exception) {
            exception.printStackTrace();
        }
    }

    private DirContext loginToUser(@NotNull String userName, @NotNull String password) {
        try {
            Hashtable<String, String> env = new Hashtable<>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, LDAP_URL);
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, userName);
            env.put(Context.SECURITY_CREDENTIALS, password);

            return new InitialDirContext(env);
        } catch (AuthenticationException exception) {
            System.err.println("Unable to login to user: " + userName);
        } catch (NamingException exception) {
            exception.printStackTrace();
        }

        return null;
    }

}
