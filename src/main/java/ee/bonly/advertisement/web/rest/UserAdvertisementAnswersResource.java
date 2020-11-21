package ee.bonly.advertisement.web.rest;

import ee.bonly.advertisement.service.UserAdvertisementAnswersService;
import ee.bonly.advertisement.web.rest.errors.BadRequestAlertException;
import ee.bonly.advertisement.service.dto.UserAdvertisementAnswersDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ee.bonly.advertisement.domain.UserAdvertisementAnswers}.
 */
@RestController
@RequestMapping("/api")
public class UserAdvertisementAnswersResource {

    private final Logger log = LoggerFactory.getLogger(UserAdvertisementAnswersResource.class);

    private static final String ENTITY_NAME = "userAdvertisementAnswers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserAdvertisementAnswersService userAdvertisementAnswersService;

    public UserAdvertisementAnswersResource(UserAdvertisementAnswersService userAdvertisementAnswersService) {
        this.userAdvertisementAnswersService = userAdvertisementAnswersService;
    }

    /**
     * {@code POST  /user-advertisement-answers} : Create a new userAdvertisementAnswers.
     *
     * @param userAdvertisementAnswersDTO the userAdvertisementAnswersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userAdvertisementAnswersDTO, or with status {@code 400 (Bad Request)} if the userAdvertisementAnswers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-advertisement-answers")
    public ResponseEntity<UserAdvertisementAnswersDTO> createUserAdvertisementAnswers(@RequestBody UserAdvertisementAnswersDTO userAdvertisementAnswersDTO) throws URISyntaxException {
        log.debug("REST request to save UserAdvertisementAnswers : {}", userAdvertisementAnswersDTO);
        if (userAdvertisementAnswersDTO.getId() != null) {
            throw new BadRequestAlertException("A new userAdvertisementAnswers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserAdvertisementAnswersDTO result = userAdvertisementAnswersService.save(userAdvertisementAnswersDTO);
        return ResponseEntity.created(new URI("/api/user-advertisement-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-advertisement-answers} : Updates an existing userAdvertisementAnswers.
     *
     * @param userAdvertisementAnswersDTO the userAdvertisementAnswersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAdvertisementAnswersDTO,
     * or with status {@code 400 (Bad Request)} if the userAdvertisementAnswersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userAdvertisementAnswersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-advertisement-answers")
    public ResponseEntity<UserAdvertisementAnswersDTO> updateUserAdvertisementAnswers(@RequestBody UserAdvertisementAnswersDTO userAdvertisementAnswersDTO) throws URISyntaxException {
        log.debug("REST request to update UserAdvertisementAnswers : {}", userAdvertisementAnswersDTO);
        if (userAdvertisementAnswersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserAdvertisementAnswersDTO result = userAdvertisementAnswersService.save(userAdvertisementAnswersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userAdvertisementAnswersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-advertisement-answers} : get all the userAdvertisementAnswers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userAdvertisementAnswers in body.
     */
    @GetMapping("/user-advertisement-answers")
    public List<UserAdvertisementAnswersDTO> getAllUserAdvertisementAnswers() {
        log.debug("REST request to get all UserAdvertisementAnswers");
        return userAdvertisementAnswersService.findAll();
    }

    /**
     * {@code GET  /user-advertisement-answers/:id} : get the "id" userAdvertisementAnswers.
     *
     * @param id the id of the userAdvertisementAnswersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userAdvertisementAnswersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-advertisement-answers/{id}")
    public ResponseEntity<UserAdvertisementAnswersDTO> getUserAdvertisementAnswers(@PathVariable Long id) {
        log.debug("REST request to get UserAdvertisementAnswers : {}", id);
        Optional<UserAdvertisementAnswersDTO> userAdvertisementAnswersDTO = userAdvertisementAnswersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userAdvertisementAnswersDTO);
    }

    /**
     * {@code DELETE  /user-advertisement-answers/:id} : delete the "id" userAdvertisementAnswers.
     *
     * @param id the id of the userAdvertisementAnswersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-advertisement-answers/{id}")
    public ResponseEntity<Void> deleteUserAdvertisementAnswers(@PathVariable Long id) {
        log.debug("REST request to delete UserAdvertisementAnswers : {}", id);
        userAdvertisementAnswersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
