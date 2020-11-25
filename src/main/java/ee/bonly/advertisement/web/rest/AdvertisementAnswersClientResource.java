package ee.bonly.advertisement.web.rest;

import ee.bonly.advertisement.service.AdvertisementAnswersService;
import ee.bonly.advertisement.service.dto.AdvertisementAnswersDTO;
import ee.bonly.advertisement.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link ee.bonly.advertisement.domain.AdvertisementAnswers}.
 */
@RestController
@RequestMapping("/api/client")
public class AdvertisementAnswersClientResource {

    private final Logger log = LoggerFactory.getLogger(AdvertisementAnswersClientResource.class);

    private static final String ENTITY_NAME = "advertisementAnswers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdvertisementAnswersService advertisementAnswersService;

    public AdvertisementAnswersClientResource(AdvertisementAnswersService advertisementAnswersService) {
        this.advertisementAnswersService = advertisementAnswersService;
    }

    /**
     * {@code POST  /advertisement-answers} : Create a new advertisementAnswers.
     *
     * @param advertisementAnswersDTO the advertisementAnswersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new advertisementAnswersDTO, or with status {@code 400 (Bad Request)} if the advertisementAnswers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/content-answers")
    public ResponseEntity<AdvertisementAnswersDTO> createAdvertisementAnswers(@RequestBody AdvertisementAnswersDTO advertisementAnswersDTO) throws URISyntaxException {
        log.debug("REST request to save AdvertisementAnswers : {}", advertisementAnswersDTO);
        if (advertisementAnswersDTO.getId() != null) {
            throw new BadRequestAlertException("A new advertisementAnswers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdvertisementAnswersDTO result = advertisementAnswersService.save(advertisementAnswersDTO);
        return ResponseEntity.created(new URI("/api/content-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /advertisement-answers} : Updates an existing advertisementAnswers.
     *
     * @param advertisementAnswersDTO the advertisementAnswersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated advertisementAnswersDTO,
     * or with status {@code 400 (Bad Request)} if the advertisementAnswersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the advertisementAnswersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/content-answers")
    public ResponseEntity<AdvertisementAnswersDTO> updateAdvertisementAnswers(@RequestBody AdvertisementAnswersDTO advertisementAnswersDTO) throws URISyntaxException {
        log.debug("REST request to update AdvertisementAnswers : {}", advertisementAnswersDTO);
        if (advertisementAnswersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdvertisementAnswersDTO result = advertisementAnswersService.save(advertisementAnswersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, advertisementAnswersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /advertisement-answers} : get all the advertisementAnswers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of advertisementAnswers in body.
     */
    @GetMapping("/content-answers")
    public List<AdvertisementAnswersDTO> getAllAdvertisementAnswers() {
        log.debug("REST request to get all AdvertisementAnswers");
        return advertisementAnswersService.findAll();
    }

    /**
     * {@code GET  /advertisement-answers/:id} : get the "id" advertisementAnswers.
     *
     * @param id the id of the advertisementAnswersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the advertisementAnswersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("content-answers/{id}")
    public ResponseEntity<AdvertisementAnswersDTO> getAdvertisementAnswers(@PathVariable Long id) {
        log.debug("REST request to get AdvertisementAnswers : {}", id);
        Optional<AdvertisementAnswersDTO> advertisementAnswersDTO = advertisementAnswersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(advertisementAnswersDTO);
    }

    /**
     * {@code DELETE  /advertisement-answers/:id} : delete the "id" advertisementAnswers.
     *
     * @param id the id of the advertisementAnswersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/content-answers/{id}")
    public ResponseEntity<Void> deleteAdvertisementAnswers(@PathVariable Long id) {
        log.debug("REST request to delete AdvertisementAnswers : {}", id);
        advertisementAnswersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
