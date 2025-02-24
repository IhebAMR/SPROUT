package tn.esprit.sprout_back.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "roles")
public class Role {
    @Id
    private String id;

    private ERole name; // Le nom du rôle (par exemple, ROLE_DEV, ROLE_ADMIN)

    private Set<String> permissions = new HashSet<>(); // Ajout du champ permissions

    // Constructeurs
    public Role() {
    }

    public Role(ERole name) {
        this.name = name;
    }

    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }

    // Getters et Setters pour permissions
    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
}