package tn.esprit.sprout_back.payload.request;

import java.util.Set;
import java.util.List;

import jakarta.validation.constraints.*;

public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<String> roles;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private List<String> skills; // Add this field for skills

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<String> roles) { // Fix method name to setRoles
        this.roles = roles;
    }

    public List<String> getSkills() { // Getter for skills
        return skills;
    }

    public void setSkills(List<String> skills) { // Setter for skills
        this.skills = skills;
    }
}