package com.costrella.sp.web.rest;

import com.costrella.sp.SpPortalApp;

import com.costrella.sp.domain.Family;
import com.costrella.sp.repository.FamilyRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FamilyResource REST controller.
 *
 * @see FamilyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpPortalApp.class)
public class FamilyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final String DEFAULT_SURNAME = "AAAAA";
    private static final String UPDATED_SURNAME = "BBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";

    private static final BigDecimal DEFAULT_LAT = new BigDecimal(1);
    private static final BigDecimal UPDATED_LAT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LNG = new BigDecimal(1);
    private static final BigDecimal UPDATED_LNG = new BigDecimal(2);

    @Inject
    private FamilyRepository familyRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restFamilyMockMvc;

    private Family family;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FamilyResource familyResource = new FamilyResource();
        ReflectionTestUtils.setField(familyResource, "familyRepository", familyRepository);
        this.restFamilyMockMvc = MockMvcBuilders.standaloneSetup(familyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Family createEntity(EntityManager em) {
        Family family = new Family()
                .name(DEFAULT_NAME)
                .surname(DEFAULT_SURNAME)
                .address(DEFAULT_ADDRESS)
                .lat(DEFAULT_LAT)
                .lng(DEFAULT_LNG);
        return family;
    }

    @Before
    public void initTest() {
        family = createEntity(em);
    }

    @Test
    @Transactional
    public void createFamily() throws Exception {
        int databaseSizeBeforeCreate = familyRepository.findAll().size();

        // Create the Family

        restFamilyMockMvc.perform(post("/api/families")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(family)))
                .andExpect(status().isCreated());

        // Validate the Family in the database
        List<Family> families = familyRepository.findAll();
        assertThat(families).hasSize(databaseSizeBeforeCreate + 1);
        Family testFamily = families.get(families.size() - 1);
        assertThat(testFamily.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFamily.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testFamily.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testFamily.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testFamily.getLng()).isEqualTo(DEFAULT_LNG);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = familyRepository.findAll().size();
        // set the field null
        family.setName(null);

        // Create the Family, which fails.

        restFamilyMockMvc.perform(post("/api/families")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(family)))
                .andExpect(status().isBadRequest());

        List<Family> families = familyRepository.findAll();
        assertThat(families).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSurnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = familyRepository.findAll().size();
        // set the field null
        family.setSurname(null);

        // Create the Family, which fails.

        restFamilyMockMvc.perform(post("/api/families")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(family)))
                .andExpect(status().isBadRequest());

        List<Family> families = familyRepository.findAll();
        assertThat(families).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFamilies() throws Exception {
        // Initialize the database
        familyRepository.saveAndFlush(family);

        // Get all the families
        restFamilyMockMvc.perform(get("/api/families?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(family.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.intValue())))
                .andExpect(jsonPath("$.[*].lng").value(hasItem(DEFAULT_LNG.intValue())));
    }

    @Test
    @Transactional
    public void getFamily() throws Exception {
        // Initialize the database
        familyRepository.saveAndFlush(family);

        // Get the family
        restFamilyMockMvc.perform(get("/api/families/{id}", family.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(family.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.intValue()))
            .andExpect(jsonPath("$.lng").value(DEFAULT_LNG.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFamily() throws Exception {
        // Get the family
        restFamilyMockMvc.perform(get("/api/families/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFamily() throws Exception {
        // Initialize the database
        familyRepository.saveAndFlush(family);
        int databaseSizeBeforeUpdate = familyRepository.findAll().size();

        // Update the family
        Family updatedFamily = familyRepository.findOne(family.getId());
        updatedFamily
                .name(UPDATED_NAME)
                .surname(UPDATED_SURNAME)
                .address(UPDATED_ADDRESS)
                .lat(UPDATED_LAT)
                .lng(UPDATED_LNG);

        restFamilyMockMvc.perform(put("/api/families")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFamily)))
                .andExpect(status().isOk());

        // Validate the Family in the database
        List<Family> families = familyRepository.findAll();
        assertThat(families).hasSize(databaseSizeBeforeUpdate);
        Family testFamily = families.get(families.size() - 1);
        assertThat(testFamily.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFamily.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testFamily.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testFamily.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testFamily.getLng()).isEqualTo(UPDATED_LNG);
    }

    @Test
    @Transactional
    public void deleteFamily() throws Exception {
        // Initialize the database
        familyRepository.saveAndFlush(family);
        int databaseSizeBeforeDelete = familyRepository.findAll().size();

        // Get the family
        restFamilyMockMvc.perform(delete("/api/families/{id}", family.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Family> families = familyRepository.findAll();
        assertThat(families).hasSize(databaseSizeBeforeDelete - 1);
    }
}
