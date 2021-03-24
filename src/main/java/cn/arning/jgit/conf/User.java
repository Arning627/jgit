package cn.arning.jgit.conf;


import org.springframework.stereotype.Component;

/**
 * @author arning
 */
@Component
public class User {

    private String username;

    private String password;

    public User() {
        this.username = System.getenv("GIT_USERNAME");
        this.password = System.getenv("GIT_PASSWORD");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
