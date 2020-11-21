package ee.bonly.advertisement.web.rest;

import ee.bonly.advertisement.BonlyApp;
import ee.bonly.advertisement.domain.Advertisement;
import ee.bonly.advertisement.repository.AdvertisementRepository;
import ee.bonly.advertisement.service.AdvertisementService;
import ee.bonly.advertisement.service.dto.AdvertisementDTO;
import ee.bonly.advertisement.service.mapper.AdvertisementMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AdvertisementResource} REST controller.
 */
@SpringBootTest(classes = BonlyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AdvertisementResourceIT {

    private static final Instant DEFAULT_ACTIVE_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTIVE_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ACTIVE_UNTILL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTIVE_UNTILL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private AdvertisementMapper advertisementMapper;

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdvertisementMockMvc;

    private Advertisement advertisement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Advertisement createEntity(EntityManager em) {
        Advertisement advertisement = new Advertisement()
            .activeFrom(DEFAULT_ACTIVE_FROM)
            .activeUntill(DEFAULT_ACTIVE_UNTILL)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .question(DEFAULT_QUESTION);
        return advertisement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Advertisement createUpdatedEntity(EntityManager em) {
        Advertisement advertisement = new Advertisement()
            .activeFrom(UPDATED_ACTIVE_FROM)
            .activeUntill(UPDATED_ACTIVE_UNTILL)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .question(UPDATED_QUESTION);
        return advertisement;
    }

    @BeforeEach
    public void initTest() {
        advertisement = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdvertisement() throws Exception {
        int databaseSizeBeforeCreate = advertisementRepository.findAll().size();
        // Create the Advertisement
        AdvertisementDTO advertisementDTO = advertisementMapper.toDto(advertisement);
        restAdvertisementMockMvc.perform(post("/api/advertisements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(advertisementDTO)))
            .andExpect(status().isCreated());

        // Validate the Advertisement in the database
        List<Advertisement> advertisementList = advertisementRepository.findAll();
        assertThat(advertisementList).hasSize(databaseSizeBeforeCreate + 1);
        Advertisement testAdvertisement = advertisementList.get(advertisementList.size() - 1);
        assertThat(testAdvertisement.getActiveFrom()).isEqualTo(DEFAULT_ACTIVE_FROM);
        assertThat(testAdvertisement.getActiveUntill()).isEqualTo(DEFAULT_ACTIVE_UNTILL);
        assertThat(testAdvertisement.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testAdvertisement.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testAdvertisement.getQuestion()).isEqualTo(DEFAULT_QUESTION);
    }

    @Test
    @Transactional
    public void createAdvertisementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = advertisementRepository.findAll().size();

        // Create the Advertisement with an existing ID
        advertisement.setId(1L);
        AdvertisementDTO advertisementDTO = advertisementMapper.toDto(advertisement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdvertisementMockMvc.perform(post("/api/advertisements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(advertisementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Advertisement in the database
        List<Advertisement> advertisementList = advertisementRepository.findAll();
        assertThat(advertisementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkActiveFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = advertisementRepository.findAll().size();
        // set the field null
        advertisement.setActiveFrom(null);

        // Create the Advertisement, which fails.
        AdvertisementDTO advertisementDTO = advertisementMapper.toDto(advertisement);


        restAdvertisementMockMvc.perform(post("/api/advertisements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(advertisementDTO)))
            .andExpect(status().isBadRequest());

        List<Advertisement> advertisementList = advertisementRepository.findAll();
        assertThat(advertisementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveUntillIsRequired() throws Exception {
        int databaseSizeBeforeTest = advertisementRepository.findAll().size();
        // set the field null
        advertisement.setActiveUntill(null);

        // Create the Advertisement, which fails.
        AdvertisementDTO advertisementDTO = advertisementMapper.toDto(advertisement);


        restAdvertisementMockMvc.perform(post("/api/advertisements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(advertisementDTO)))
            .andExpect(status().isBadRequest());

        List<Advertisement> advertisementList = advertisementRepository.findAll();
        assertThat(advertisementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuestionIsRequired() throws Exception {
        int databaseSizeBeforeTest = advertisementRepository.findAll().size();
        // set the field null
        advertisement.setQuestion(null);

        // Create the Advertisement, which fails.
        AdvertisementDTO advertisementDTO = advertisementMapper.toDto(advertisement);


        restAdvertisementMockMvc.perform(post("/api/advertisements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(advertisementDTO)))
            .andExpect(status().isBadRequest());

        List<Advertisement> advertisementList = advertisementRepository.findAll();
        assertThat(advertisementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdvertisements() throws Exception {
        // Initialize the database
        advertisementRepository.saveAndFlush(advertisement);

        // Get all the advertisementList
        restAdvertisementMockMvc.perform(get("/api/advertisements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(advertisement.getId().intValue())))
            .andExpect(jsonPath("$.[*].activeFrom").value(hasItem(DEFAULT_ACTIVE_FROM.toString())))
            .andExpect(jsonPath("$.[*].activeUntill").value(hasItem(DEFAULT_ACTIVE_UNTILL.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)));
    }
    
    @Test
    @Transactional
    public void getAdvertisement() throws Exception {
        // Initialize the database
        advertisementRepository.saveAndFlush(advertisement);

        // Get the advertisement
        restAdvertisementMockMvc.perform(get("/api/advertisements/{id}", advertisement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(advertisement.getId().intValue()))
            .andExpect(jsonPath("$.activeFrom").value(DEFAULT_ACTIVE_FROM.toString()))
            .andExpect(jsonPath("$.activeUntill").value(DEFAULT_ACTIVE_UNTILL.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION));
    }
    @Test
    @Transactional
    public void getNonExistingAdvertisement() throws Exception {
        // Get the advertisement
        restAdvertisementMockMvc.perform(get("/api/advertisements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdvertisement() throws Exception {
        // Initialize the database
        advertisementRepository.saveAndFlush(advertisement);

        int databaseSizeBeforeUpdate = advertisementRepository.findAll().size();

        // Update the advertisement
        Advertisement updatedAdvertisement = advertisementRepository.findById(advertisement.getId()).get();
        // Disconnect from session so that the updates on updatedAdvertisement are not directly saved in db
        em.detach(updatedAdvertisement);
        updatedAdvertisement
            .activeFrom(UPDATED_ACTIVE_FROM)
            .activeUntill(UPDATED_ACTIVE_UNTILL)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .question(UPDATED_QUESTION);
        AdvertisementDTO advertisementDTO = advertisementMapper.toDto(updatedAdvertisement);

        restAdvertisementMockMvc.perform(put("/api/advertisements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(advertisementDTO)))
            .andExpect(status().isOk());

        // Validate the Advertisement in the database
        List<Advertisement> advertisementList = advertisementRepository.findAll();
        assertThat(advertisementList).hasSize(databaseSizeBeforeUpdate);
        Advertisement testAdvertisement = advertisementList.get(advertisementList.size() - 1);
        assertThat(testAdvertisement.getActiveFrom()).isEqualTo(UPDATED_ACTIVE_FROM);
        assertThat(testAdvertisement.getActiveUntill()).isEqualTo(UPDATED_ACTIVE_UNTILL);
        assertThat(testAdvertisement.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testAdvertisement.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testAdvertisement.getQuestion()).isEqualTo(UPDATED_QUESTION);
    }

    @Test
    @Transactional
    public void updateNonExistingAdvertisement() throws Exception {
        int databaseSizeBeforeUpdate = advertisementRepository.findAll().size();

        // Create the Advertisement
        AdvertisementDTO advertisementDTO = advertisementMapper.toDto(advertisement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdvertisementMockMvc.perform(put("/api/advertisements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(advertisementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Advertisement in the database
        List<Advertisement> advertisementList = advertisementRepository.findAll();
        assertThat(advertisementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdvertisement() throws Exception {
        // Initialize the database
        advertisementRepository.saveAndFlush(advertisement);

        int databaseSizeBeforeDelete = advertisementRepository.findAll().size();

        // Delete the advertisement
        restAdvertisementMockMvc.perform(delete("/api/advertisements/{id}", advertisement.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Advertisement> advertisementList = advertisementRepository.findAll();
        assertThat(advertisementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
