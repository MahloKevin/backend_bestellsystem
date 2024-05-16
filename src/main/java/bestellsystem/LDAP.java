package bestellsystem;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.Hashtable;

public class LDAP {

    static Configurations c = new Configurations();
    static String[] parts = c.LDAPConfig().split(";");
    private static final String LDAP_URL = parts[0];
    private static final String BASE_DN = parts[1];

    public boolean authenticateAndFetchUserInfo(String userName, String password) {
        boolean isAuthenticated = false;
        DirContext userContext = null;

        try {
            Hashtable<String, String> env = new Hashtable<>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, LDAP_URL);
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, userName);
            env.put(Context.SECURITY_CREDENTIALS, password);

            userContext = new InitialDirContext(env);

            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String filter = "(sAMAccountName=" + userName + ")";
            NamingEnumeration<SearchResult> results = userContext.search(BASE_DN, filter, controls);

            if(results != null) {
                isAuthenticated = true;
            }

            /*System.out.println("test");
            if (results.hasMore()) {
                System.out.println("test2");
                SearchResult searchResult = results.next();
                Attributes attributes = searchResult.getAttributes();
                String[] name = attributes.get("cn").get().toString().split(" ");
                LDAPAccountInfo ldapAccountInfo = new LDAPAccountInfo(userName, name[1], name[0], attributes.get("department").get().toString());

                isAuthenticated = true;
            }


             */
        } catch (AuthenticationException exception) {
            System.out.println("Unable to login to user: " + userName);
        } catch (NamingException exception) {
            exception.printStackTrace();
        } finally {
            if (userContext != null) {
                try {
                    userContext.close();
                } catch (NamingException exception) {
                    exception.printStackTrace();
                }
            }
        }

        return isAuthenticated;
    }
}
