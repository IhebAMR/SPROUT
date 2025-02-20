package tn.esprit.sprout_back.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.sprout_back.Model.Certification;
import tn.esprit.sprout_back.Service.CertificationService;

import java.util.List;

@RestController
@RequestMapping("/api/certifications")
public class CertificationController {
    @Autowired
    private CertificationService certificationService;

    @PostMapping
    public Certification generateCertification(@RequestParam String courseId, @RequestParam String userId) {
        return certificationService.generateCertification(courseId, userId);
    }

    @GetMapping("/user/{userId}")
    public List<Certification> getUserCertifications(@PathVariable String userId) {
        return certificationService.getUserCertifications(userId);
    }
}