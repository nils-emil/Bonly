package ee.bonly.advertisement.service;

import ee.bonly.advertisement.service.dto.AdvertisementAnswersDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ee.bonly.advertisement.domain.AdvertisementAnswers}.
 */
public interface AdvertisementAnswersService {

    /**
     * Save a advertisementAnswers.
     *
     * @param advertisementAnswersDTO the entity to save.
     * @return the persisted entity.
     */
    AdvertisementAnswersDTO save(AdvertisementAnswersDTO advertisementAnswersDTO);

    /**
     * Get all the advertisementAnswers.
     *
     * @return the list of entities.
     */
    List<AdvertisementAnswersDTO> findAll();


    /**
     * Get the "id" advertisementAnswers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdvertisementAnswersDTO> findOne(Long id);

    /**
     * Delete the "id" advertisementAnswers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
