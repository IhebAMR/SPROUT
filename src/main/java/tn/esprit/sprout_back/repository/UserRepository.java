package tn.esprit.sprout_back.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.sprout_back.models.User;

<<<<<<< HEAD
=======
import java.util.List;
>>>>>>> b979afa127bbd0ca73396ab069e703bcb0ea14d2
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
<<<<<<< HEAD
    // Trouver un utilisateur par son email
    Optional<User> findByEmail(String email);

    // Trouver un utilisateur par son token de réinitialisation
    Optional<User> findByResetToken(String resetToken);
=======

    Optional<User> findByEmail(String email);


    Optional<User> findByResetToken(String resetToken);

>>>>>>> b979afa127bbd0ca73396ab069e703bcb0ea14d2
}