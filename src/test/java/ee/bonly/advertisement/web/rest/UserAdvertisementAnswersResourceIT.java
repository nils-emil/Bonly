package ee.bonly.advertisement.web.rest;

import ee.bonly.advertisement.BonlyApp;
import ee.bonly.advertisement.domain.UserAdvertisementAnswers;
import ee.bonly.advertisement.repository.UserAdvertisementAnswersRepository;
import ee.bonly.advertisement.service.UserAdvertisementAnswersService;
import ee.bonly.advertisement.service.dto.UserAdvertisementAnswersDTO;
import ee.bonly.advertisement.service.mapper.UserAdvertisementAnswersMapper;

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
 * Integration tests for the {@link UserAdvertisementAnswersResource} REST controller.
 */
@SpringBootTest(classes = BonlyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserAdvertisementAnswersResourceIT {

    private static final String DEFAULT_STATE_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_PROVINCE = "BBBBBBBBBB";

    @Autowired
    private UserAdvertisementAnswersRepository userAdvertisementAnswersRepository;

    @Autowired
    private UserAdvertisementAnswersMapper userAdvertisementAnswersMapper;

    @Autowired
    private UserAdvertisementAnswersService userAdvertisementAnswersService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserAdvertisementAnswersMockMvc;

    private UserAdvertisementAnswers userAdvertisementAnswers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAdvertisementAnswers createEntity(EntityManager em) {
        UserAdvertisementAnswers userAdvertisementAnswers = new UserAdvertisementAnswers()
            .stateProvince(DEFAULT_STATE_PROVINCE);
        return userAdvertisementAnswers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAdvertisementAnswers createUpdatedEntity(EntityManager em) {
        UserAdvertisementAnswers userAdvertisementAnswers = new UserAdvertisementAnswers()
            .stateProvince(UPDATED_STATE_PROVINCE);
        return userAdvertisementAnswers;
    }

    @BeforeEach
    public void initTest() {
        userAdvertisementAnswers = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserAdvertisementAnswers() throws Exception {
        int databaseSizeBeforeCreate = userAdvertisementAnswersRepository.findAll().size();
        // Create the UserAdvertisementAnswers
        UserAdvertisementAnswersDTO userAdvertisementAnswersDTO = userAdvertisementAnswersMapper.toDto(userAdvertisementAnswers);
        restUserAdvertisementAnswersMockMvc.perform(post("/api/user-advertisement-answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAdvertisementAnswersDTO)))
            .andExpect(status().isCreated());

        // Validate the UserAdvertisementAnswers in the database
        List<UserAdvertisementAnswers> userAdvertisementAnswersList = userAdvertisementAnswersRepository.findAll();
        assertThat(userAdvertisementAnswersList).hasSize(databaseSizeBeforeCreate + 1);
        UserAdvertisementAnswers testUserAdvertisementAnswers = userAdvertisementAnswersList.get(userAdvertisementAnswersList.size() - 1);
        assertThat(testUserAdvertisementAnswers.getStateProvince()).isEqualTo(DEFAULT_STATE_PROVINCE);
    }

    @Test
    @Transactional
    public void createUserAdvertisementAnswersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userAdvertisementAnswersRepository.findAll().size();

        // Create the UserAdvertisementAnswers with an existing ID
        userAdvertisementAnswers.setId(1L);
        UserAdvertisementAnswersDTO userAdvertisementAnswersDTO = userAdvertisementAnswersMapper.toDto(userAdvertisementAnswers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAdvertisementAnswersMockMvc.perform(post("/api/user-advertisement-answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAdvertisementAnswersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserAdvertisementAnswers in the database
        List<UserAdvertisementAnswers> userAdvertisementAnswersList = userAdvertisementAnswersRepository.findAll();
        assertThat(userAdvertisementAnswersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserAdvertisementAnswers() throws Exception {
        // Initialize the database
        userAdvertisementAnswersRepository.saveAndFlush(userAdvertisementAnswers);

        // Get all the userAdvertisementAnswersList
        restUserAdvertisementAnswersMockMvc.perform(get("/api/user-advertisement-answers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAdvertisementAnswers.getId().intValue())))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE)));
    }
    
    @Test
    @Transactional
    public void getUserAdvertisementAnswers() throws Exception {
        // Initialize the database
        userAdvertisementAnswersRepository.saveAndFlush(userAdvertisementAnswers);

        // Get the userAdvertisementAnswers
        restUserAdvertisementAnswersMockMvc.perform(get("/api/user-advertisement-answers/{id}", userAdvertisementAnswers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userAdvertisementAnswers.getId().intValue()))
            .andExpect(jsonPath("$.stateProvince").value(DEFAULT_STATE_PROVINCE));
    }
    @Test
    @Transactional
    public void getNonExistingUserAdvertisementAnswers() throws Exception {
        // Get the userAdvertisementAnswers
        restUserAdvertisementAnswersMockMvc.perform(get("/api/user-advertisement-answers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserAdvertisementAnswers() throws Exception {
        // Initialize the database
        userAdvertisementAnswersRepository.saveAndFlush(userAdvertisementAnswers);

        int databaseSizeBeforeUpdate = userAdvertisementAnswersRepository.findAll().size();

        // Update the userAdvertisementAnswers
        UserAdvertisementAnswers updatedUserAdvertisementAnswers = userAdvertisementAnswersRepository.findById(userAdvertisementAnswers.getId()).get();
        // Disconnect from session so that the updates on updatedUserAdvertisementAnswers are not directly saved in db
        em.detach(updatedUserAdvertisementAnswers);
        updatedUserAdvertisementAnswers
            .stateProvince(UPDATED_STATE_PROVINCE);
        UserAdvertisementAnswersDTO userAdvertisementAnswersDTO = userAdvertisementAnswersMapper.toDto(updatedUserAdvertisementAnswers);

        restUserAdvertisementAnswersMockMvc.perform(put("/api/user-advertisement-answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAdvertisementAnswersDTO)))
            .andExpect(status().isOk());

        // Validate the UserAdvertisementAnswers in the database
        List<UserAdvertisementAnswers> userAdvertisementAnswersList = userAdvertisementAnswersRepository.findAll();
        assertThat(userAdvertisementAnswersList).hasSize(databaseSizeBeforeUpdate);
        UserAdvertisementAnswers testUserAdvertisementAnswers = userAdvertisementAnswersList.get(userAdvertisementAnswersList.size() - 1);
        assertThat(testUserAdvertisementAnswers.getStateProvince()).isEqualTo(UPDATED_STATE_PROVINCE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserAdvertisementAnswers() throws Exception {
        int databaseSizeBeforeUpdate = userAdvertisementAnswersRepository.findAll().size();

        // Create the UserAdvertisementAnswers
        UserAdvertisementAnswersDTO userAdvertisementAnswersDTO = userAdvertisementAnswersMapper.toDto(userAdvertisementAnswers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAdvertisementAnswersMockMvc.perform(put("/api/user-advertisement-answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAdvertisementAnswersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserAdvertisementAnswers in the database
        List<UserAdvertisementAnswers> userAdvertisementAnswersList = userAdvertisementAnswersRepository.findAll();
        assertThat(userAdvertisementAnswersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserAdvertisementAnswers() throws Exception {
        // Initialize the database
        userAdvertisementAnswersRepository.saveAndFlush(userAdvertisementAnswers);

        int databaseSizeBeforeDelete = userAdvertisementAnswersRepository.findAll().size();

        // Delete the userAdvertisementAnswers
        restUserAdvertisementAnswersMockMvc.perform(delete("/api/user-advertisement-answers/{id}", userAdvertisementAnswers.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserAdvertisementAnswers> userAdvertisementAnswersList = userAdvertisementAnswersRepository.findAll();
        assertThat(userAdvertisementAnswersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
