package tn.esprit.sprout_back.controllers;


import tn.esprit.sprout_back.models.CodeSubmission;
import tn.esprit.sprout_back.services.CodeSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manager")
public class ProjectManagerController {

    @Autowired
    private CodeSubmissionService codeSubmissionService;

    @PostMapping("/code/review/{submissionId}")
    public String reviewCode(@PathVariable String submissionId, @RequestParam String status) {
        CodeSubmission submission = codeSubmissionService.reviewCode(submissionId, status);
        return submission != null ? "Code reviewed successfully!" : "Submission not found.";
    }
}