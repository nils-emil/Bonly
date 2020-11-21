package ee.bonly.advertisement.service;

import ee.bonly.advertisement.service.dto.PrizeRegistrationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ee.bonly.advertisement.domain.PrizeRegistration}.
 */
public interface PrizeRegistrationService {

    /**
     * Save a prizeRegistration.
     *
     * @param prizeRegistrationDTO the entity to save.
     * @return the persisted entity.
     */
    PrizeRegistrationDTO save(PrizeRegistrationDTO prizeRegistrationDTO);

    /**
     * Get all the prizeRegistrations.
     *
     * @return the list of entities.
     */
    List<PrizeRegistrationDTO> findAll();


    /**
     * Get the "id" prizeRegistration.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PrizeRegistrationDTO> findOne(Long id);

    /**
     * Delete the "id" prizeRegistration.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
