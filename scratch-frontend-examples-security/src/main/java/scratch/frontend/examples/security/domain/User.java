package scratch.frontend.examples.security.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "users")
public class User extends AbstractPersistable<Long> {

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    public User() {
        this(null, null);
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
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
