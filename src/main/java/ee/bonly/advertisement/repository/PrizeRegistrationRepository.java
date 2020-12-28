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
    @Query(value = "SELECT count(*) FROM prize_registration WHERE user_id = ?1 and prize_id = ?2"
        , nativeQuery = true)
    Integer findCountByPrizeId(Long userId, Long prizeId);
}
