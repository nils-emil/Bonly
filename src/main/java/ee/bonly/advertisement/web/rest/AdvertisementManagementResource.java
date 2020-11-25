package ee.bonly.advertisement.web.rest;

import ee.bonly.advertisement.service.AdvertisementService;
import ee.bonly.advertisement.web.rest.errors.BadRequestAlertException;
import ee.bonly.advertisement.service.dto.AdvertisementDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ee.bonly.advertisement.domain.Advertisement}.
 */
@RestController
@RequestMapping("/api")
public class AdvertisementManagementResource {

    private final Logger log = LoggerFactory.getLogger(AdvertisementManagementResource.class);

    private static final String ENTITY_NAME = "advertisement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdvertisementService advertisementService;

    public AdvertisementManagementResource(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    /**
     * {@code POST  /advertisements} : Create a new advertisement.
     *
     * @param advertisementDTO the advertisementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new advertisementDTO, or with status {@code 400 (Bad Request)} if the advertisement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/content")
    public ResponseEntity<AdvertisementDTO> createAdvertisement(@Valid @RequestBody AdvertisementDTO advertisementDTO) throws URISyntaxException {
        log.debug("REST request to save Advertisement : {}", advertisementDTO);
        if (advertisementDTO.getId() != null) {
            throw new BadRequestAlertException("A new advertisement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdvertisementDTO result = advertisementService.save(advertisementDTO);
        return ResponseEntity.created(new URI("/api/advertisements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /advertisements} : Updates an existing advertisement.
     *
     * @param advertisementDTO the advertisementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated advertisementDTO,
     * or with status {@code 400 (Bad Request)} if the advertisementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the advertisementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/content")
    public ResponseEntity<AdvertisementDTO> updateAdvertisement(@Valid @RequestBody AdvertisementDTO advertisementDTO) throws URISyntaxException {
        log.debug("REST request to update Advertisement : {}", advertisementDTO);
        if (advertisementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdvertisementDTO result = advertisementService.save(advertisementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, advertisementDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /advertisements} : get all the advertisements.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of advertisements in body.
     */
    @GetMapping("/content")
    public List<AdvertisementDTO> getAllAdvertisements() {
        log.debug("REST request to get all Advertisements");
        return advertisementService.findAll();
    }

    /**
     * {@code GET  /advertisements/:id} : get the "id" advertisement.
     *
     * @param id the id of the advertisementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the advertisementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/content/{id}")
    public ResponseEntity<AdvertisementDTO> getAdvertisement(@PathVariable Long id) {
        log.debug("REST request to get Advertisement : {}", id);
        Optional<AdvertisementDTO> advertisementDTO = advertisementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(advertisementDTO);
    }

    /**
     * {@code DELETE  /advertisements/:id} : delete the "id" advertisement.
     *
     * @param id the id of the advertisementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/content/{id}")
    public ResponseEntity<Void> deleteAdvertisement(@PathVariable Long id) {
        log.debug("REST request to delete Advertisement : {}", id);
        advertisementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
