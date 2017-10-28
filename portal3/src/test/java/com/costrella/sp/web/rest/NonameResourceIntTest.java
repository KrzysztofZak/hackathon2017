package com.costrella.sp.web.rest;

import com.costrella.sp.SpPortalApp;

import com.costrella.sp.domain.Noname;
import com.costrella.sp.repository.NonameRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the NonameResource REST controller.
 *
 * @see NonameResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpPortalApp.class)
public class NonameResourceIntTest {

    private static final String DEFAULT_TEST = "AAAAA";
    private static final String UPDATED_TEST = "BBBBB";

    @Inject
    private NonameRepository nonameRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restNonameMockMvc;

    private Noname noname;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NonameResource nonameResource = new NonameResource();
        ReflectionTestUtils.setField(nonameResource, "nonameRepository", nonameRepository);
        this.restNonameMockMvc = MockMvcBuilders.standaloneSetup(nonameResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Noname createEntity(EntityManager em) {
        Noname noname = new Noname()
                .test(DEFAULT_TEST);
        return noname;
    }

    @Before
    public void initTest() {
        noname = createEntity(em);
    }

    @Test
    @Transactional
    public void createNoname() throws Exception {
        int databaseSizeBeforeCreate = nonameRepository.findAll().size();

        // Create the Noname

        restNonameMockMvc.perform(post("/api/nonames")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(noname)))
                .andExpect(status().isCreated());

        // Validate the Noname in the database
        List<Noname> nonames = nonameRepository.findAll();
        assertThat(nonames).hasSize(databaseSizeBeforeCreate + 1);
        Noname testNoname = nonames.get(nonames.size() - 1);
        assertThat(testNoname.getTest()).isEqualTo(DEFAULT_TEST);
    }

    @Test
    @Transactional
    public void getAllNonames() throws Exception {
        // Initialize the database
        nonameRepository.saveAndFlush(noname);

        // Get all the nonames
        restNonameMockMvc.perform(get("/api/nonames?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(noname.getId().intValue())))
                .andExpect(jsonPath("$.[*].test").value(hasItem(DEFAULT_TEST.toString())));
    }

    @Test
    @Transactional
    public void getNoname() throws Exception {
        // Initialize the database
        nonameRepository.saveAndFlush(noname);

        // Get the noname
        restNonameMockMvc.perform(get("/api/nonames/{id}", noname.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(noname.getId().intValue()))
            .andExpect(jsonPath("$.test").value(DEFAULT_TEST.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNoname() throws Exception {
        // Get the noname
        restNonameMockMvc.perform(get("/api/nonames/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNoname() throws Exception {
        // Initialize the database
        nonameRepository.saveAndFlush(noname);
        int databaseSizeBeforeUpdate = nonameRepository.findAll().size();

        // Update the noname
        Noname updatedNoname = nonameRepository.findOne(noname.getId());
        updatedNoname
                .test(UPDATED_TEST);

        restNonameMockMvc.perform(put("/api/nonames")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedNoname)))
                .andExpect(status().isOk());

        // Validate the Noname in the database
        List<Noname> nonames = nonameRepository.findAll();
        assertThat(nonames).hasSize(databaseSizeBeforeUpdate);
        Noname testNoname = nonames.get(nonames.size() - 1);
        assertThat(testNoname.getTest()).isEqualTo(UPDATED_TEST);
    }

    @Test
    @Transactional
    public void deleteNoname() throws Exception {
        // Initialize the database
        nonameRepository.saveAndFlush(noname);
        int databaseSizeBeforeDelete = nonameRepository.findAll().size();

        // Get the noname
        restNonameMockMvc.perform(delete("/api/nonames/{id}", noname.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Noname> nonames = nonameRepository.findAll();
        assertThat(nonames).hasSize(databaseSizeBeforeDelete - 1);
    }
}
