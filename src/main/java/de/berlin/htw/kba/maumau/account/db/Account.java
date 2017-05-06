package de.berlin.htw.kba.maumau.account.db;

// TODO: Auto-generated Javadoc
/**
 * The Class Account.
 */
public class Account {

    /** The name. */
    private String name;
    
    /** The user name. */
    private String userName;
    
    /** The e mail. */
    private String eMail;
    
    /** The password. */
    private String password;

    /**
     * Instantiates a new account.
     *
     * @param name the name
     * @param userName the user name
     * @param eMail the e mail
     * @param password the password
     */
    public Account(String name, String userName, String eMail, String password) {
        this.name = name;
        this.userName = userName;
        this.eMail = eMail;
        this.password = password;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user name.
     *
     * @param userName the new user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the e mail.
     *
     * @return the e mail
     */
    public String geteMail() {
        return eMail;
    }

    /**
     * Sets the e mail.
     *
     * @param eMail the new e mail
     */
    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
