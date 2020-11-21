package ee.bonly.advertisement.service;

import ee.bonly.advertisement.service.dto.UserAdvertisementAnswersDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ee.bonly.advertisement.domain.UserAdvertisementAnswers}.
 */
public interface UserAdvertisementAnswersService {

    /**
     * Save a userAdvertisementAnswers.
     *
     * @param userAdvertisementAnswersDTO the entity to save.
     * @return the persisted entity.
     */
    UserAdvertisementAnswersDTO save(UserAdvertisementAnswersDTO userAdvertisementAnswersDTO);

    /**
     * Get all the userAdvertisementAnswers.
     *
     * @return the list of entities.
     */
    List<UserAdvertisementAnswersDTO> findAll();


    /**
     * Get the "id" userAdvertisementAnswers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserAdvertisementAnswersDTO> findOne(Long id);

    /**
     * Delete the "id" userAdvertisementAnswers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
