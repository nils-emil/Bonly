package ee.bonly.advertisement.web.rest;

import ee.bonly.advertisement.BonlyApp;
import ee.bonly.advertisement.domain.Prize;
import ee.bonly.advertisement.repository.PrizeRepository;
import ee.bonly.advertisement.service.PrizeService;
import ee.bonly.advertisement.service.dto.PrizeDTO;
import ee.bonly.advertisement.service.mapper.PrizeMapper;

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
 * Integration tests for the {@link PrizeResource} REST controller.
 */
@SpringBootTest(classes = BonlyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PrizeResourceIT {

    private static final Instant DEFAULT_REGISTATION_STOPS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REGISTATION_STOPS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_WINNER_CHOSEN_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_WINNER_CHOSEN_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CREDITS_REQUIRED = 1L;
    private static final Long UPDATED_CREDITS_REQUIRED = 2L;

    @Autowired
    private PrizeRepository prizeRepository;

    @Autowired
    private PrizeMapper prizeMapper;

    @Autowired
    private PrizeService prizeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrizeMockMvc;

    private Prize prize;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prize createEntity(EntityManager em) {
        Prize prize = new Prize()
            .registationStops(DEFAULT_REGISTATION_STOPS)
            .winnerChosenAt(DEFAULT_WINNER_CHOSEN_AT)
            .creditsRequired(DEFAULT_CREDITS_REQUIRED);
        return prize;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prize createUpdatedEntity(EntityManager em) {
        Prize prize = new Prize()
            .registationStops(UPDATED_REGISTATION_STOPS)
            .winnerChosenAt(UPDATED_WINNER_CHOSEN_AT)
            .creditsRequired(UPDATED_CREDITS_REQUIRED);
        return prize;
    }

    @BeforeEach
    public void initTest() {
        prize = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrize() throws Exception {
        int databaseSizeBeforeCreate = prizeRepository.findAll().size();
        // Create the Prize
        PrizeDTO prizeDTO = prizeMapper.toDto(prize);
        restPrizeMockMvc.perform(post("/api/prizes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prizeDTO)))
            .andExpect(status().isCreated());

        // Validate the Prize in the database
        List<Prize> prizeList = prizeRepository.findAll();
        assertThat(prizeList).hasSize(databaseSizeBeforeCreate + 1);
        Prize testPrize = prizeList.get(prizeList.size() - 1);
        assertThat(testPrize.getRegistationStops()).isEqualTo(DEFAULT_REGISTATION_STOPS);
        assertThat(testPrize.getWinnerChosenAt()).isEqualTo(DEFAULT_WINNER_CHOSEN_AT);
        assertThat(testPrize.getCreditsRequired()).isEqualTo(DEFAULT_CREDITS_REQUIRED);
    }

    @Test
    @Transactional
    public void createPrizeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prizeRepository.findAll().size();

        // Create the Prize with an existing ID
        prize.setId(1L);
        PrizeDTO prizeDTO = prizeMapper.toDto(prize);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrizeMockMvc.perform(post("/api/prizes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Prize in the database
        List<Prize> prizeList = prizeRepository.findAll();
        assertThat(prizeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPrizes() throws Exception {
        // Initialize the database
        prizeRepository.saveAndFlush(prize);

        // Get all the prizeList
        restPrizeMockMvc.perform(get("/api/prizes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prize.getId().intValue())))
            .andExpect(jsonPath("$.[*].registationStops").value(hasItem(DEFAULT_REGISTATION_STOPS.toString())))
            .andExpect(jsonPath("$.[*].winnerChosenAt").value(hasItem(DEFAULT_WINNER_CHOSEN_AT.toString())))
            .andExpect(jsonPath("$.[*].creditsRequired").value(hasItem(DEFAULT_CREDITS_REQUIRED.intValue())));
    }
    
    @Test
    @Transactional
    public void getPrize() throws Exception {
        // Initialize the database
        prizeRepository.saveAndFlush(prize);

        // Get the prize
        restPrizeMockMvc.perform(get("/api/prizes/{id}", prize.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prize.getId().intValue()))
            .andExpect(jsonPath("$.registationStops").value(DEFAULT_REGISTATION_STOPS.toString()))
            .andExpect(jsonPath("$.winnerChosenAt").value(DEFAULT_WINNER_CHOSEN_AT.toString()))
            .andExpect(jsonPath("$.creditsRequired").value(DEFAULT_CREDITS_REQUIRED.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPrize() throws Exception {
        // Get the prize
        restPrizeMockMvc.perform(get("/api/prizes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrize() throws Exception {
        // Initialize the database
        prizeRepository.saveAndFlush(prize);

        int databaseSizeBeforeUpdate = prizeRepository.findAll().size();

        // Update the prize
        Prize updatedPrize = prizeRepository.findById(prize.getId()).get();
        // Disconnect from session so that the updates on updatedPrize are not directly saved in db
        em.detach(updatedPrize);
        updatedPrize
            .registationStops(UPDATED_REGISTATION_STOPS)
            .winnerChosenAt(UPDATED_WINNER_CHOSEN_AT)
            .creditsRequired(UPDATED_CREDITS_REQUIRED);
        PrizeDTO prizeDTO = prizeMapper.toDto(updatedPrize);

        restPrizeMockMvc.perform(put("/api/prizes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prizeDTO)))
            .andExpect(status().isOk());

        // Validate the Prize in the database
        List<Prize> prizeList = prizeRepository.findAll();
        assertThat(prizeList).hasSize(databaseSizeBeforeUpdate);
        Prize testPrize = prizeList.get(prizeList.size() - 1);
        assertThat(testPrize.getRegistationStops()).isEqualTo(UPDATED_REGISTATION_STOPS);
        assertThat(testPrize.getWinnerChosenAt()).isEqualTo(UPDATED_WINNER_CHOSEN_AT);
        assertThat(testPrize.getCreditsRequired()).isEqualTo(UPDATED_CREDITS_REQUIRED);
    }

    @Test
    @Transactional
    public void updateNonExistingPrize() throws Exception {
        int databaseSizeBeforeUpdate = prizeRepository.findAll().size();

        // Create the Prize
        PrizeDTO prizeDTO = prizeMapper.toDto(prize);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrizeMockMvc.perform(put("/api/prizes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Prize in the database
        List<Prize> prizeList = prizeRepository.findAll();
        assertThat(prizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrize() throws Exception {
        // Initialize the database
        prizeRepository.saveAndFlush(prize);

        int databaseSizeBeforeDelete = prizeRepository.findAll().size();

        // Delete the prize
        restPrizeMockMvc.perform(delete("/api/prizes/{id}", prize.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Prize> prizeList = prizeRepository.findAll();
        assertThat(prizeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
