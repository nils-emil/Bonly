package ee.bonly.advertisement.repository;

import ee.bonly.advertisement.domain.AllowedEmail;
import ee.bonly.advertisement.domain.Image;
import ee.bonly.advertisement.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AllowedEmailRepository extends JpaRepository<AllowedEmail, Long> {

    Optional<AllowedEmail> findOneByEmail(String login);

}
