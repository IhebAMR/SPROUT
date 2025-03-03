package tn.esprit.sprout_back.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "profiles")
public class Profile {
    @Id
    private String id;

    private String bio; // Biographie de l'utilisateur
    private String avatar; // URL de l'avatar de l'utilisateur
    private String contactInfo; // Informations de contact
    private List<String> skills; // Ajoutez les compétences ici

    // Constructeurs
    public Profile() {
    }

    public Profile(String bio, String avatar, String contactInfo, List<String> skills) {
        this.bio = bio;
        this.avatar = avatar;
        this.contactInfo = contactInfo;
        this.skills = skills;
    }

    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public void setUsername(String username) {
    }
}