package ee.bonly.advertisement.repository;

import ee.bonly.advertisement.domain.UserAdvertisementAnswers;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserAdvertisementAnswers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAdvertisementAnswersRepository extends JpaRepository<UserAdvertisementAnswers, Long> {
}
