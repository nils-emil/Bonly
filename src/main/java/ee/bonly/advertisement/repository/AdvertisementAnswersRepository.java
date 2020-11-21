package ee.bonly.advertisement.repository;

import ee.bonly.advertisement.domain.AdvertisementAnswers;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdvertisementAnswers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdvertisementAnswersRepository extends JpaRepository<AdvertisementAnswers, Long> {
}
