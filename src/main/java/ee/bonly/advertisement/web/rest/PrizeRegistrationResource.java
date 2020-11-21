package ee.bonly.advertisement.web.rest;

import ee.bonly.advertisement.service.PrizeRegistrationService;
import ee.bonly.advertisement.web.rest.errors.BadRequestAlertException;
import ee.bonly.advertisement.service.dto.PrizeRegistrationDTO;

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
 * REST controller for managing {@link ee.bonly.advertisement.domain.PrizeRegistration}.
 */
@RestController
@RequestMapping("/api")
public class PrizeRegistrationResource {

    private final Logger log = LoggerFactory.getLogger(PrizeRegistrationResource.class);

    private static final String ENTITY_NAME = "prizeRegistration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrizeRegistrationService prizeRegistrationService;

    public PrizeRegistrationResource(PrizeRegistrationService prizeRegistrationService) {
        this.prizeRegistrationService = prizeRegistrationService;
    }

    /**
     * {@code POST  /prize-registrations} : Create a new prizeRegistration.
     *
     * @param prizeRegistrationDTO the prizeRegistrationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prizeRegistrationDTO, or with status {@code 400 (Bad Request)} if the prizeRegistration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prize-registrations")
    public ResponseEntity<PrizeRegistrationDTO> createPrizeRegistration(@RequestBody PrizeRegistrationDTO prizeRegistrationDTO) throws URISyntaxException {
        log.debug("REST request to save PrizeRegistration : {}", prizeRegistrationDTO);
        if (prizeRegistrationDTO.getId() != null) {
            throw new BadRequestAlertException("A new prizeRegistration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrizeRegistrationDTO result = prizeRegistrationService.save(prizeRegistrationDTO);
        return ResponseEntity.created(new URI("/api/prize-registrations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prize-registrations} : Updates an existing prizeRegistration.
     *
     * @param prizeRegistrationDTO the prizeRegistrationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prizeRegistrationDTO,
     * or with status {@code 400 (Bad Request)} if the prizeRegistrationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prizeRegistrationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prize-registrations")
    public ResponseEntity<PrizeRegistrationDTO> updatePrizeRegistration(@RequestBody PrizeRegistrationDTO prizeRegistrationDTO) throws URISyntaxException {
        log.debug("REST request to update PrizeRegistration : {}", prizeRegistrationDTO);
        if (prizeRegistrationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrizeRegistrationDTO result = prizeRegistrationService.save(prizeRegistrationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prizeRegistrationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prize-registrations} : get all the prizeRegistrations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prizeRegistrations in body.
     */
    @GetMapping("/prize-registrations")
    public List<PrizeRegistrationDTO> getAllPrizeRegistrations() {
        log.debug("REST request to get all PrizeRegistrations");
        return prizeRegistrationService.findAll();
    }

    /**
     * {@code GET  /prize-registrations/:id} : get the "id" prizeRegistration.
     *
     * @param id the id of the prizeRegistrationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prizeRegistrationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prize-registrations/{id}")
    public ResponseEntity<PrizeRegistrationDTO> getPrizeRegistration(@PathVariable Long id) {
        log.debug("REST request to get PrizeRegistration : {}", id);
        Optional<PrizeRegistrationDTO> prizeRegistrationDTO = prizeRegistrationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prizeRegistrationDTO);
    }

    /**
     * {@code DELETE  /prize-registrations/:id} : delete the "id" prizeRegistration.
     *
     * @param id the id of the prizeRegistrationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prize-registrations/{id}")
    public ResponseEntity<Void> deletePrizeRegistration(@PathVariable Long id) {
        log.debug("REST request to delete PrizeRegistration : {}", id);
        prizeRegistrationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
