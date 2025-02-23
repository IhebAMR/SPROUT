package tn.esprit.sprout_back.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.sprout_back.Model.Application;
import tn.esprit.sprout_back.Repositories.ApplicationRepository;

import java.util.List;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;

    // Méthode pour récupérer les candidatures d'un projet par son ID
    public List<Application> getApplicationsByProjectId(String projectId) {
        return applicationRepository.findByProjectId(projectId);
    }

    public Application submitApplication(Application application) {
        return applicationRepository.save(application);
    }
}
