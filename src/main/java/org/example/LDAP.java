package org.example;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.io.Console;
import java.util.Hashtable;

public class LDAP {

    public static void main(String[] args) {
        LDAP authentication = new LDAP();
        try {

            // Benutzer und Password
            Console console = System.console();

            String userID = console.readLine("User ID: ");
            String pwd = String.valueOf(console.readPassword("Password? "));


            // Parameter
            Hashtable<Object, Object> env = new Hashtable<Object, Object>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, "ldap://192.168.10.4:389");
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.REFERRAL, "follow");
            env.put(Context.SECURITY_PRINCIPAL, userID);
            env.put(Context.SECURITY_CREDENTIALS, pwd);

            // Authentifizieren
            DirContext ctx = new InitialDirContext(env);
            ctx.close();

            System.out.println("*** Konnte authentifiziert werden ***");

        } catch (Exception e) {
            // Bei Fehler sag problem
            e.printStackTrace();

            System.out.println("*** Konnte nicht authentifiziert werden ***");

        }
    }


}
