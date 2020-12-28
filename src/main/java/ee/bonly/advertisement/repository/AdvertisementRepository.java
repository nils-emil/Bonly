package ee.bonly.advertisement.repository;

import ee.bonly.advertisement.domain.Advertisement;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Advertisement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    @Query(value = "SELECT *\n" +
        "FROM advertisement\n" +
        "WHERE advertisement.id NOT IN (\n" +
        "    SELECT advertisement_id\n" +
        "    from user_advertisement_answers\n" +
        "    where user_advertisement_answers.user_id = ?1\n" +
        "    AND user_advertisement_answers.advertisement_id=advertisement.id\n" +
        "    ) " +
        "AND active_untill > now() AND active_from < now()" +
        "LIMIT 1;", nativeQuery = true)
    Advertisement findOneUnansweredAdvertisementByUserid(Long userId);
}
