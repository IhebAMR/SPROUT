package tn.esprit.sprout_back.payload.request;

import java.util.List;

public class ProfileUpdateRequest {
    private String bio;
    private String avatar;
    private String contactInfo;
    private List<String> skills; // Ajoutez les compétences ici

    // Getters et Setters
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
}
