package ee.bonly.advertisement.web.rest;

import ee.bonly.advertisement.BonlyApp;
import ee.bonly.advertisement.domain.AdvertisementAnswers;
import ee.bonly.advertisement.repository.AdvertisementAnswersRepository;
import ee.bonly.advertisement.service.AdvertisementAnswersService;
import ee.bonly.advertisement.service.dto.AdvertisementAnswersDTO;
import ee.bonly.advertisement.service.mapper.AdvertisementAnswersMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AdvertisementAnswersResource} REST controller.
 */
@SpringBootTest(classes = BonlyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AdvertisementAnswersResourceIT {

    private static final String DEFAULT_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_PROVINCE = "BBBBBBBBBB";

    @Autowired
    private AdvertisementAnswersRepository advertisementAnswersRepository;

    @Autowired
    private AdvertisementAnswersMapper advertisementAnswersMapper;

    @Autowired
    private AdvertisementAnswersService advertisementAnswersService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdvertisementAnswersMockMvc;

    private AdvertisementAnswers advertisementAnswers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdvertisementAnswers createEntity(EntityManager em) {
        AdvertisementAnswers advertisementAnswers = new AdvertisementAnswers()
            .answer(DEFAULT_ANSWER)
            .city(DEFAULT_CITY)
            .stateProvince(DEFAULT_STATE_PROVINCE);
        return advertisementAnswers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdvertisementAnswers createUpdatedEntity(EntityManager em) {
        AdvertisementAnswers advertisementAnswers = new AdvertisementAnswers()
            .answer(UPDATED_ANSWER)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE);
        return advertisementAnswers;
    }

    @BeforeEach
    public void initTest() {
        advertisementAnswers = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdvertisementAnswers() throws Exception {
        int databaseSizeBeforeCreate = advertisementAnswersRepository.findAll().size();
        // Create the AdvertisementAnswers
        AdvertisementAnswersDTO advertisementAnswersDTO = advertisementAnswersMapper.toDto(advertisementAnswers);
        restAdvertisementAnswersMockMvc.perform(post("/api/advertisement-answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(advertisementAnswersDTO)))
            .andExpect(status().isCreated());

        // Validate the AdvertisementAnswers in the database
        List<AdvertisementAnswers> advertisementAnswersList = advertisementAnswersRepository.findAll();
        assertThat(advertisementAnswersList).hasSize(databaseSizeBeforeCreate + 1);
        AdvertisementAnswers testAdvertisementAnswers = advertisementAnswersList.get(advertisementAnswersList.size() - 1);
        assertThat(testAdvertisementAnswers.getAnswer()).isEqualTo(DEFAULT_ANSWER);
        assertThat(testAdvertisementAnswers.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testAdvertisementAnswers.getStateProvince()).isEqualTo(DEFAULT_STATE_PROVINCE);
    }

    @Test
    @Transactional
    public void createAdvertisementAnswersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = advertisementAnswersRepository.findAll().size();

        // Create the AdvertisementAnswers with an existing ID
        advertisementAnswers.setId(1L);
        AdvertisementAnswersDTO advertisementAnswersDTO = advertisementAnswersMapper.toDto(advertisementAnswers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdvertisementAnswersMockMvc.perform(post("/api/advertisement-answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(advertisementAnswersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdvertisementAnswers in the database
        List<AdvertisementAnswers> advertisementAnswersList = advertisementAnswersRepository.findAll();
        assertThat(advertisementAnswersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAdvertisementAnswers() throws Exception {
        // Initialize the database
        advertisementAnswersRepository.saveAndFlush(advertisementAnswers);

        // Get all the advertisementAnswersList
        restAdvertisementAnswersMockMvc.perform(get("/api/advertisement-answers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(advertisementAnswers.getId().intValue())))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE)));
    }
    
    @Test
    @Transactional
    public void getAdvertisementAnswers() throws Exception {
        // Initialize the database
        advertisementAnswersRepository.saveAndFlush(advertisementAnswers);

        // Get the advertisementAnswers
        restAdvertisementAnswersMockMvc.perform(get("/api/advertisement-answers/{id}", advertisementAnswers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(advertisementAnswers.getId().intValue()))
            .andExpect(jsonPath("$.answer").value(DEFAULT_ANSWER))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.stateProvince").value(DEFAULT_STATE_PROVINCE));
    }
    @Test
    @Transactional
    public void getNonExistingAdvertisementAnswers() throws Exception {
        // Get the advertisementAnswers
        restAdvertisementAnswersMockMvc.perform(get("/api/advertisement-answers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdvertisementAnswers() throws Exception {
        // Initialize the database
        advertisementAnswersRepository.saveAndFlush(advertisementAnswers);

        int databaseSizeBeforeUpdate = advertisementAnswersRepository.findAll().size();

        // Update the advertisementAnswers
        AdvertisementAnswers updatedAdvertisementAnswers = advertisementAnswersRepository.findById(advertisementAnswers.getId()).get();
        // Disconnect from session so that the updates on updatedAdvertisementAnswers are not directly saved in db
        em.detach(updatedAdvertisementAnswers);
        updatedAdvertisementAnswers
            .answer(UPDATED_ANSWER)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE);
        AdvertisementAnswersDTO advertisementAnswersDTO = advertisementAnswersMapper.toDto(updatedAdvertisementAnswers);

        restAdvertisementAnswersMockMvc.perform(put("/api/advertisement-answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(advertisementAnswersDTO)))
            .andExpect(status().isOk());

        // Validate the AdvertisementAnswers in the database
        List<AdvertisementAnswers> advertisementAnswersList = advertisementAnswersRepository.findAll();
        assertThat(advertisementAnswersList).hasSize(databaseSizeBeforeUpdate);
        AdvertisementAnswers testAdvertisementAnswers = advertisementAnswersList.get(advertisementAnswersList.size() - 1);
        assertThat(testAdvertisementAnswers.getAnswer()).isEqualTo(UPDATED_ANSWER);
        assertThat(testAdvertisementAnswers.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAdvertisementAnswers.getStateProvince()).isEqualTo(UPDATED_STATE_PROVINCE);
    }

    @Test
    @Transactional
    public void updateNonExistingAdvertisementAnswers() throws Exception {
        int databaseSizeBeforeUpdate = advertisementAnswersRepository.findAll().size();

        // Create the AdvertisementAnswers
        AdvertisementAnswersDTO advertisementAnswersDTO = advertisementAnswersMapper.toDto(advertisementAnswers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdvertisementAnswersMockMvc.perform(put("/api/advertisement-answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(advertisementAnswersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdvertisementAnswers in the database
        List<AdvertisementAnswers> advertisementAnswersList = advertisementAnswersRepository.findAll();
        assertThat(advertisementAnswersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdvertisementAnswers() throws Exception {
        // Initialize the database
        advertisementAnswersRepository.saveAndFlush(advertisementAnswers);

        int databaseSizeBeforeDelete = advertisementAnswersRepository.findAll().size();

        // Delete the advertisementAnswers
        restAdvertisementAnswersMockMvc.perform(delete("/api/advertisement-answers/{id}", advertisementAnswers.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdvertisementAnswers> advertisementAnswersList = advertisementAnswersRepository.findAll();
        assertThat(advertisementAnswersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
