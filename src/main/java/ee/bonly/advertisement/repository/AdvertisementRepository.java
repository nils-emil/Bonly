package ee.bonly.advertisement.repository;

import ee.bonly.advertisement.domain.Advertisement;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Advertisement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
}
