package tn.esprit.sprout_back.payload.response;

import java.util.List;

public class UserInfoResponse {
    private String id;
    private String username;
    private String email;
    private List<String> roles;
<<<<<<< HEAD

    public UserInfoResponse(String id, String username, String email, List<String> roles) {
=======
    private String token; // Ajout du token

    // Constructeur par défaut requis pour la désérialisation
    public UserInfoResponse() {
    }

    // Constructeur avec paramètres
    public UserInfoResponse(String id, String username, String email, List<String> roles, String token) {
>>>>>>> b979afa127bbd0ca73396ab069e703bcb0ea14d2
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
<<<<<<< HEAD
    }

=======
        this.token = token; // Assigner le token
    }

    // Getters et Setters
>>>>>>> b979afa127bbd0ca73396ab069e703bcb0ea14d2
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }
<<<<<<< HEAD
}

=======

    public void setRoles(List<String> roles) { // Ajout du setter manquant
        this.roles = roles;
    }

    public String getToken() { // Getter du token
        return token;
    }

    public void setToken(String token) { // Setter du token
        this.token = token;
    }
}
>>>>>>> b979afa127bbd0ca73396ab069e703bcb0ea14d2
