package tn.esprit.sprout_back.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.sprout_back.Model.Certification;
import tn.esprit.sprout_back.Repositories.CertificationRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class CertificationService {
    @Autowired
    private CertificationRepository certificationRepository;

    public Certification generateCertification(String courseId, String userId) {
        Certification certification = new Certification();
        certification.setCourseId(courseId);
        certification.setUserId(userId);
        certification.setDateAwarded(LocalDate.now());
        return certificationRepository.save(certification);
    }

    public List<Certification> getUserCertifications(String userId) {
        return certificationRepository.findByUserId(userId);
    }
}