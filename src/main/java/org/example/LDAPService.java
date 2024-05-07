package org.example;

import org.jetbrains.annotations.NotNull;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.Hashtable;
import java.util.Optional;

public class DefaultLDAPService{

    private static final String LDAP_URL = "ldap://192.168.10.5:3268";
    private static final String BASE_DN = "dc=mahlo,dc=de";

    private final DirContext context;

    public DefaultLDAPService() {
        this.context = this.loginToUser("apache-auth@mahlo.de", "tester2010");
    }

    public @NotNull Optional<Object> login(@NotNull String userName, @NotNull String password) {
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
