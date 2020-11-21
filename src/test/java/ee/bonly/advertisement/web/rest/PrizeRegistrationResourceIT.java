package ee.bonly.advertisement.web.rest;

import ee.bonly.advertisement.BonlyApp;
import ee.bonly.advertisement.domain.PrizeRegistration;
import ee.bonly.advertisement.repository.PrizeRegistrationRepository;
import ee.bonly.advertisement.service.PrizeRegistrationService;
import ee.bonly.advertisement.service.dto.PrizeRegistrationDTO;
import ee.bonly.advertisement.service.mapper.PrizeRegistrationMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PrizeRegistrationResource} REST controller.
 */
@SpringBootTest(classes = BonlyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PrizeRegistrationResourceIT {

    private static final Instant DEFAULT_REGISTATION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REGISTATION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PrizeRegistrationRepository prizeRegistrationRepository;

    @Autowired
    private PrizeRegistrationMapper prizeRegistrationMapper;

    @Autowired
    private PrizeRegistrationService prizeRegistrationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrizeRegistrationMockMvc;

    private PrizeRegistration prizeRegistration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrizeRegistration createEntity(EntityManager em) {
        PrizeRegistration prizeRegistration = new PrizeRegistration()
            .registation(DEFAULT_REGISTATION);
        return prizeRegistration;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrizeRegistration createUpdatedEntity(EntityManager em) {
        PrizeRegistration prizeRegistration = new PrizeRegistration()
            .registation(UPDATED_REGISTATION);
        return prizeRegistration;
    }

    @BeforeEach
    public void initTest() {
        prizeRegistration = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrizeRegistration() throws Exception {
        int databaseSizeBeforeCreate = prizeRegistrationRepository.findAll().size();
        // Create the PrizeRegistration
        PrizeRegistrationDTO prizeRegistrationDTO = prizeRegistrationMapper.toDto(prizeRegistration);
        restPrizeRegistrationMockMvc.perform(post("/api/prize-registrations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prizeRegistrationDTO)))
            .andExpect(status().isCreated());

        // Validate the PrizeRegistration in the database
        List<PrizeRegistration> prizeRegistrationList = prizeRegistrationRepository.findAll();
        assertThat(prizeRegistrationList).hasSize(databaseSizeBeforeCreate + 1);
        PrizeRegistration testPrizeRegistration = prizeRegistrationList.get(prizeRegistrationList.size() - 1);
        assertThat(testPrizeRegistration.getRegistation()).isEqualTo(DEFAULT_REGISTATION);
    }

    @Test
    @Transactional
    public void createPrizeRegistrationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prizeRegistrationRepository.findAll().size();

        // Create the PrizeRegistration with an existing ID
        prizeRegistration.setId(1L);
        PrizeRegistrationDTO prizeRegistrationDTO = prizeRegistrationMapper.toDto(prizeRegistration);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrizeRegistrationMockMvc.perform(post("/api/prize-registrations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prizeRegistrationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrizeRegistration in the database
        List<PrizeRegistration> prizeRegistrationList = prizeRegistrationRepository.findAll();
        assertThat(prizeRegistrationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPrizeRegistrations() throws Exception {
        // Initialize the database
        prizeRegistrationRepository.saveAndFlush(prizeRegistration);

        // Get all the prizeRegistrationList
        restPrizeRegistrationMockMvc.perform(get("/api/prize-registrations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prizeRegistration.getId().intValue())))
            .andExpect(jsonPath("$.[*].registation").value(hasItem(DEFAULT_REGISTATION.toString())));
    }
    
    @Test
    @Transactional
    public void getPrizeRegistration() throws Exception {
        // Initialize the database
        prizeRegistrationRepository.saveAndFlush(prizeRegistration);

        // Get the prizeRegistration
        restPrizeRegistrationMockMvc.perform(get("/api/prize-registrations/{id}", prizeRegistration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prizeRegistration.getId().intValue()))
            .andExpect(jsonPath("$.registation").value(DEFAULT_REGISTATION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPrizeRegistration() throws Exception {
        // Get the prizeRegistration
        restPrizeRegistrationMockMvc.perform(get("/api/prize-registrations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrizeRegistration() throws Exception {
        // Initialize the database
        prizeRegistrationRepository.saveAndFlush(prizeRegistration);

        int databaseSizeBeforeUpdate = prizeRegistrationRepository.findAll().size();

        // Update the prizeRegistration
        PrizeRegistration updatedPrizeRegistration = prizeRegistrationRepository.findById(prizeRegistration.getId()).get();
        // Disconnect from session so that the updates on updatedPrizeRegistration are not directly saved in db
        em.detach(updatedPrizeRegistration);
        updatedPrizeRegistration
            .registation(UPDATED_REGISTATION);
        PrizeRegistrationDTO prizeRegistrationDTO = prizeRegistrationMapper.toDto(updatedPrizeRegistration);

        restPrizeRegistrationMockMvc.perform(put("/api/prize-registrations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prizeRegistrationDTO)))
            .andExpect(status().isOk());

        // Validate the PrizeRegistration in the database
        List<PrizeRegistration> prizeRegistrationList = prizeRegistrationRepository.findAll();
        assertThat(prizeRegistrationList).hasSize(databaseSizeBeforeUpdate);
        PrizeRegistration testPrizeRegistration = prizeRegistrationList.get(prizeRegistrationList.size() - 1);
        assertThat(testPrizeRegistration.getRegistation()).isEqualTo(UPDATED_REGISTATION);
    }

    @Test
    @Transactional
    public void updateNonExistingPrizeRegistration() throws Exception {
        int databaseSizeBeforeUpdate = prizeRegistrationRepository.findAll().size();

        // Create the PrizeRegistration
        PrizeRegistrationDTO prizeRegistrationDTO = prizeRegistrationMapper.toDto(prizeRegistration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrizeRegistrationMockMvc.perform(put("/api/prize-registrations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prizeRegistrationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrizeRegistration in the database
        List<PrizeRegistration> prizeRegistrationList = prizeRegistrationRepository.findAll();
        assertThat(prizeRegistrationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrizeRegistration() throws Exception {
        // Initialize the database
        prizeRegistrationRepository.saveAndFlush(prizeRegistration);

        int databaseSizeBeforeDelete = prizeRegistrationRepository.findAll().size();

        // Delete the prizeRegistration
        restPrizeRegistrationMockMvc.perform(delete("/api/prize-registrations/{id}", prizeRegistration.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrizeRegistration> prizeRegistrationList = prizeRegistrationRepository.findAll();
        assertThat(prizeRegistrationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
