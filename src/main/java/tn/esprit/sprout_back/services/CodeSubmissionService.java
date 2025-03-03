package tn.esprit.sprout_back.services;
import tn.esprit.sprout_back.models.CodeSubmission;
import tn.esprit.sprout_back.repositories.CodeSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CodeSubmissionService {

    @Autowired
    private CodeSubmissionRepository codeSubmissionRepository;

    public CodeSubmission saveCode(CodeSubmission submission) {
        submission.setStatus("In Progress");
        return codeSubmissionRepository.save(submission);
    }

    public CodeSubmission submitCode(CodeSubmission submission) {
        submission.setStatus("Submitted");
        submission.setSubmittedAt(LocalDateTime.now());
        return codeSubmissionRepository.save(submission);
    }

    public CodeSubmission reviewCode(String submissionId, String status) {
        CodeSubmission submission = codeSubmissionRepository.findById(submissionId).orElse(null);
        if (submission != null) {
            submission.setStatus(status); // Accepted or Rejected
            submission.setReviewedAt(LocalDateTime.now());
            return codeSubmissionRepository.save(submission);
        }
        return null;
    }
}