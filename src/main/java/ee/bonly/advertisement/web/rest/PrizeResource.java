package ee.bonly.advertisement.web.rest;

import ee.bonly.advertisement.service.PrizeService;
import ee.bonly.advertisement.web.rest.errors.BadRequestAlertException;
import ee.bonly.advertisement.service.dto.PrizeDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ee.bonly.advertisement.domain.Prize}.
 */
@RestController
@RequestMapping("/api")
public class PrizeResource {

    private final Logger log = LoggerFactory.getLogger(PrizeResource.class);

    private static final String ENTITY_NAME = "prize";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrizeService prizeService;

    public PrizeResource(PrizeService prizeService) {
        this.prizeService = prizeService;
    }

    /**
     * {@code POST  /prizes} : Create a new prize.
     *
     * @param prizeDTO the prizeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prizeDTO, or with status {@code 400 (Bad Request)} if the prize has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prizes")
    public ResponseEntity<PrizeDTO> createPrize(@RequestBody PrizeDTO prizeDTO) throws URISyntaxException {
        log.debug("REST request to save Prize : {}", prizeDTO);
        if (prizeDTO.getId() != null) {
            throw new BadRequestAlertException("A new prize cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrizeDTO result = prizeService.save(prizeDTO);
        return ResponseEntity.created(new URI("/api/prizes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prizes} : Updates an existing prize.
     *
     * @param prizeDTO the prizeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prizeDTO,
     * or with status {@code 400 (Bad Request)} if the prizeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prizeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prizes")
    public ResponseEntity<PrizeDTO> updatePrize(@RequestBody PrizeDTO prizeDTO) throws URISyntaxException {
        log.debug("REST request to update Prize : {}", prizeDTO);
        if (prizeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrizeDTO result = prizeService.save(prizeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prizeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prizes} : get all the prizes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prizes in body.
     */
    @GetMapping("/prizes")
    public ResponseEntity<List<PrizeDTO>> getAllPrizes(Pageable pageable) {
        log.debug("REST request to get a page of Prizes");
        Page<PrizeDTO> page = prizeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prizes/:id} : get the "id" prize.
     *
     * @param id the id of the prizeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prizeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prizes/{id}")
    public ResponseEntity<PrizeDTO> getPrize(@PathVariable Long id) {
        log.debug("REST request to get Prize : {}", id);
        Optional<PrizeDTO> prizeDTO = prizeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prizeDTO);
    }

    /**
     * {@code DELETE  /prizes/:id} : delete the "id" prize.
     *
     * @param id the id of the prizeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prizes/{id}")
    public ResponseEntity<Void> deletePrize(@PathVariable Long id) {
        log.debug("REST request to delete Prize : {}", id);
        prizeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
