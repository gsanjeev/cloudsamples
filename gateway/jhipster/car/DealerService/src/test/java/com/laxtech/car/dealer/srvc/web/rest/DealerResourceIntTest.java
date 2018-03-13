package com.laxtech.car.dealer.srvc.web.rest;

import com.laxtech.car.dealer.srvc.DealerServiceApp;

import com.laxtech.car.dealer.srvc.domain.Dealer;
import com.laxtech.car.dealer.srvc.repository.DealerRepository;
import com.laxtech.car.dealer.srvc.repository.search.DealerSearchRepository;
import com.laxtech.car.dealer.srvc.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.laxtech.car.dealer.srvc.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DealerResource REST controller.
 *
 * @see DealerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DealerServiceApp.class)
public class DealerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    @Autowired
    private DealerRepository dealerRepository;

    @Autowired
    private DealerSearchRepository dealerSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDealerMockMvc;

    private Dealer dealer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DealerResource dealerResource = new DealerResource(dealerRepository, dealerSearchRepository);
        this.restDealerMockMvc = MockMvcBuilders.standaloneSetup(dealerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dealer createEntity(EntityManager em) {
        Dealer dealer = new Dealer()
            .name(DEFAULT_NAME)
            .location(DEFAULT_LOCATION);
        return dealer;
    }

    @Before
    public void initTest() {
        dealerSearchRepository.deleteAll();
        dealer = createEntity(em);
    }

    @Test
    @Transactional
    public void createDealer() throws Exception {
        int databaseSizeBeforeCreate = dealerRepository.findAll().size();

        // Create the Dealer
        restDealerMockMvc.perform(post("/api/dealers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dealer)))
            .andExpect(status().isCreated());

        // Validate the Dealer in the database
        List<Dealer> dealerList = dealerRepository.findAll();
        assertThat(dealerList).hasSize(databaseSizeBeforeCreate + 1);
        Dealer testDealer = dealerList.get(dealerList.size() - 1);
        assertThat(testDealer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDealer.getLocation()).isEqualTo(DEFAULT_LOCATION);

        // Validate the Dealer in Elasticsearch
        Dealer dealerEs = dealerSearchRepository.findOne(testDealer.getId());
        assertThat(dealerEs).isEqualToIgnoringGivenFields(testDealer);
    }

    @Test
    @Transactional
    public void createDealerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dealerRepository.findAll().size();

        // Create the Dealer with an existing ID
        dealer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDealerMockMvc.perform(post("/api/dealers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dealer)))
            .andExpect(status().isBadRequest());

        // Validate the Dealer in the database
        List<Dealer> dealerList = dealerRepository.findAll();
        assertThat(dealerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDealers() throws Exception {
        // Initialize the database
        dealerRepository.saveAndFlush(dealer);

        // Get all the dealerList
        restDealerMockMvc.perform(get("/api/dealers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dealer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())));
    }

    @Test
    @Transactional
    public void getDealer() throws Exception {
        // Initialize the database
        dealerRepository.saveAndFlush(dealer);

        // Get the dealer
        restDealerMockMvc.perform(get("/api/dealers/{id}", dealer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dealer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDealer() throws Exception {
        // Get the dealer
        restDealerMockMvc.perform(get("/api/dealers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDealer() throws Exception {
        // Initialize the database
        dealerRepository.saveAndFlush(dealer);
        dealerSearchRepository.save(dealer);
        int databaseSizeBeforeUpdate = dealerRepository.findAll().size();

        // Update the dealer
        Dealer updatedDealer = dealerRepository.findOne(dealer.getId());
        // Disconnect from session so that the updates on updatedDealer are not directly saved in db
        em.detach(updatedDealer);
        updatedDealer
            .name(UPDATED_NAME)
            .location(UPDATED_LOCATION);

        restDealerMockMvc.perform(put("/api/dealers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDealer)))
            .andExpect(status().isOk());

        // Validate the Dealer in the database
        List<Dealer> dealerList = dealerRepository.findAll();
        assertThat(dealerList).hasSize(databaseSizeBeforeUpdate);
        Dealer testDealer = dealerList.get(dealerList.size() - 1);
        assertThat(testDealer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDealer.getLocation()).isEqualTo(UPDATED_LOCATION);

        // Validate the Dealer in Elasticsearch
        Dealer dealerEs = dealerSearchRepository.findOne(testDealer.getId());
        assertThat(dealerEs).isEqualToIgnoringGivenFields(testDealer);
    }

    @Test
    @Transactional
    public void updateNonExistingDealer() throws Exception {
        int databaseSizeBeforeUpdate = dealerRepository.findAll().size();

        // Create the Dealer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDealerMockMvc.perform(put("/api/dealers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dealer)))
            .andExpect(status().isCreated());

        // Validate the Dealer in the database
        List<Dealer> dealerList = dealerRepository.findAll();
        assertThat(dealerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDealer() throws Exception {
        // Initialize the database
        dealerRepository.saveAndFlush(dealer);
        dealerSearchRepository.save(dealer);
        int databaseSizeBeforeDelete = dealerRepository.findAll().size();

        // Get the dealer
        restDealerMockMvc.perform(delete("/api/dealers/{id}", dealer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean dealerExistsInEs = dealerSearchRepository.exists(dealer.getId());
        assertThat(dealerExistsInEs).isFalse();

        // Validate the database is empty
        List<Dealer> dealerList = dealerRepository.findAll();
        assertThat(dealerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDealer() throws Exception {
        // Initialize the database
        dealerRepository.saveAndFlush(dealer);
        dealerSearchRepository.save(dealer);

        // Search the dealer
        restDealerMockMvc.perform(get("/api/_search/dealers?query=id:" + dealer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dealer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dealer.class);
        Dealer dealer1 = new Dealer();
        dealer1.setId(1L);
        Dealer dealer2 = new Dealer();
        dealer2.setId(dealer1.getId());
        assertThat(dealer1).isEqualTo(dealer2);
        dealer2.setId(2L);
        assertThat(dealer1).isNotEqualTo(dealer2);
        dealer1.setId(null);
        assertThat(dealer1).isNotEqualTo(dealer2);
    }
}
