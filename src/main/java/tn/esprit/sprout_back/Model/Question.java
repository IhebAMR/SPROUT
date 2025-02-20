package tn.esprit.sprout_back.Model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Document(collection = "questions")

public class Question {
    private String question;
    private List<String> options;
    private String answer;
    // Getters and setters
}
