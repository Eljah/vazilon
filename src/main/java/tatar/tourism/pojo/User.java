package tatar.tourism.pojo;

import org.apache.log4j.Logger;

/**
 * Created by Ilya Evlampiev on 14.10.2015.
 */
 public abstract class User {

    private static final long serialVersionUID = 1L;

    private int databaseId=-1;
    private String username;
    private String passwordHash;
    private String email;
    private String firstname;
    private String lastname;
    private String role;
    private String locale;
    private boolean confirmed;
    private boolean active;
    private Float vote;
    private Integer voteCount;

    static Logger log = Logger.getLogger(User.class);

    public User(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public User() {

    }



    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.passwordHash;
    }

    public String getEmail() {
        return this.email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) //throws UnsupportedEncodingException, NoSuchAlgorithmException
    {
        /*
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte byteData[] = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++)
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        this.passwordHash = sb.toString();
        */
        this.passwordHash=password;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }

    public int getDatabaseId()
    {
        return this.databaseId;
    }

    public void setDatabaseId(int id)
    {
         this.databaseId=id;
    }

    public boolean isYou(User sessionUser)
    {
        log.info(this.getUsername()+" is checked the session user "+sessionUser.getUsername()+" the same or not ");
        return this.getDatabaseId()==sessionUser.getDatabaseId();
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public abstract boolean isBusDriver();
    public abstract boolean isGuide();
    public abstract boolean isAdmin();

    public Float getVote() {
        return vote;
    }

    public void setVote(Float vote) {
        this.vote = vote;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }
}
