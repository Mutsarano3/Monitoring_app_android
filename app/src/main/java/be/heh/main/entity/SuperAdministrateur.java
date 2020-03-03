package be.heh.main.entity;

public class SuperAdministrateur {

    private int id;
    private String login;
    private String password;
    private String mail;
    private String role;

    public SuperAdministrateur() {}

    public SuperAdministrateur(String l, String p, String e, String role)
    {
        this.login = l;
        this.password = p;
        this.mail = e;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ID : "+ Integer.toString(getId()) + "\n"
        +" Login : " + getLogin() + "\n"
        + "Password : " + getPassword());
        return  sb.toString();
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
