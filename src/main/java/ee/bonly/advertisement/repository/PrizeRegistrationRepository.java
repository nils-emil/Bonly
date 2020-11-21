package ee.bonly.advertisement.repository;

import ee.bonly.advertisement.domain.PrizeRegistration;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PrizeRegistration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrizeRegistrationRepository extends JpaRepository<PrizeRegistration, Long> {
}
