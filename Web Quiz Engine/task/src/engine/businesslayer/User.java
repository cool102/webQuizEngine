package engine.businesslayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;
@Data

@Entity
@Table(name= "users")
public class User {
    @Id
    @NotBlank
    @NotNull
    @Email(regexp = ".*@.*\\.\\w{2,}")
    private String email;

    public User() {
    }

    @NotBlank
    @NotNull
    @Size(min=5)
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
