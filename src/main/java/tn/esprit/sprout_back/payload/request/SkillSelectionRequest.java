package tn.esprit.sprout_back.payload.request;

import java.util.List;

public class SkillSelectionRequest {
    private String userId; // ID de l'utilisateur
    private List<String> skills; // Liste des compétences sélectionnées

    // Getters et Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}