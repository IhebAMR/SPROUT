package tn.esprit.sprout_back.models;

<<<<<<< HEAD
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

=======
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

>>>>>>> b979afa127bbd0ca73396ab069e703bcb0ea14d2
@Document(collection = "roles")
public class Role {
    @Id
    private String id;

<<<<<<< HEAD
    private ERole name;

=======
    private ERole name; // Le nom du rôle (par exemple, ROLE_DEV, ROLE_ADMIN)

    private Set<String> permissions = new HashSet<>(); // Ajout du champ permissions

    // Constructeurs
>>>>>>> b979afa127bbd0ca73396ab069e703bcb0ea14d2
    public Role() {
    }

    public Role(ERole name) {
        this.name = name;
    }

<<<<<<< HEAD
=======
    // Getters et Setters
>>>>>>> b979afa127bbd0ca73396ab069e703bcb0ea14d2
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
<<<<<<< HEAD
}
=======

    // Getters et Setters pour permissions
    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
}
>>>>>>> b979afa127bbd0ca73396ab069e703bcb0ea14d2
