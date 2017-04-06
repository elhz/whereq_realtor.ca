package com.whereq.realtor.web.rest;

import com.whereq.realtor.WhereQRealtorCaApp;

import com.whereq.realtor.domain.Listing;
import com.whereq.realtor.domain.Location;
import com.whereq.realtor.repository.ListingRepository;
import com.whereq.realtor.web.rest.errors.ExceptionTranslator;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.whereq.realtor.domain.enumeration.ListingType;
import com.whereq.realtor.domain.enumeration.ListingStatus;
import com.whereq.realtor.domain.enumeration.Style;
/**
 * Test class for the ListingResource REST controller.
 *
 * @see ListingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WhereQRealtorCaApp.class)
public class ListingResourceIntTest {

    private static final ListingType DEFAULT_LISTING_TYPE = ListingType.CONDOMINIUMS;
    private static final ListingType UPDATED_LISTING_TYPE = ListingType.DETACHED;

    private static final ListingStatus DEFAULT_STATUS = ListingStatus.AVAILABLE;
    private static final ListingStatus UPDATED_STATUS = ListingStatus.UNAVAILABLE;

    private static final LocalDate DEFAULT_INPUT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INPUT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Style DEFAULT_STYLE = Style.BUNGALOW;
    private static final Style UPDATED_STYLE = Style.THREESTOREY;

    private static final BigDecimal DEFAULT_LISTING_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LISTING_PRICE = new BigDecimal(2);

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restListingMockMvc;

    private Listing listing;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ListingResource listingResource = new ListingResource(listingRepository);
        this.restListingMockMvc = MockMvcBuilders.standaloneSetup(listingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Listing createEntity(EntityManager em) {
        Listing listing = new Listing()
            .listingType(DEFAULT_LISTING_TYPE)
            .status(DEFAULT_STATUS)
            .inputDate(DEFAULT_INPUT_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE)
            .style(DEFAULT_STYLE)
            .listingPrice(DEFAULT_LISTING_PRICE);
        // Add required entity
        Location location = LocationResourceIntTest.createEntity(em);
        em.persist(location);
        em.flush();
        listing.setLocation(location);
        return listing;
    }

    @Before
    public void initTest() {
        listing = createEntity(em);
    }

    @Test
    @Transactional
    public void createListing() throws Exception {
        int databaseSizeBeforeCreate = listingRepository.findAll().size();

        // Create the Listing
        restListingMockMvc.perform(post("/api/listings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listing)))
            .andExpect(status().isCreated());

        // Validate the Listing in the database
        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeCreate + 1);
        Listing testListing = listingList.get(listingList.size() - 1);
        assertThat(testListing.getListingType()).isEqualTo(DEFAULT_LISTING_TYPE);
        assertThat(testListing.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testListing.getInputDate()).isEqualTo(DEFAULT_INPUT_DATE);
        assertThat(testListing.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testListing.getStyle()).isEqualTo(DEFAULT_STYLE);
        assertThat(testListing.getListingPrice()).isEqualTo(DEFAULT_LISTING_PRICE);
    }

    @Test
    @Transactional
    public void createListingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = listingRepository.findAll().size();

        // Create the Listing with an existing ID
        listing.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restListingMockMvc.perform(post("/api/listings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listing)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkListingTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = listingRepository.findAll().size();
        // set the field null
        listing.setListingType(null);

        // Create the Listing, which fails.

        restListingMockMvc.perform(post("/api/listings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listing)))
            .andExpect(status().isBadRequest());

        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = listingRepository.findAll().size();
        // set the field null
        listing.setStatus(null);

        // Create the Listing, which fails.

        restListingMockMvc.perform(post("/api/listings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listing)))
            .andExpect(status().isBadRequest());

        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInputDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = listingRepository.findAll().size();
        // set the field null
        listing.setInputDate(null);

        // Create the Listing, which fails.

        restListingMockMvc.perform(post("/api/listings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listing)))
            .andExpect(status().isBadRequest());

        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStyleIsRequired() throws Exception {
        int databaseSizeBeforeTest = listingRepository.findAll().size();
        // set the field null
        listing.setStyle(null);

        // Create the Listing, which fails.

        restListingMockMvc.perform(post("/api/listings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listing)))
            .andExpect(status().isBadRequest());

        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkListingPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = listingRepository.findAll().size();
        // set the field null
        listing.setListingPrice(null);

        // Create the Listing, which fails.

        restListingMockMvc.perform(post("/api/listings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listing)))
            .andExpect(status().isBadRequest());

        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllListings() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList
        restListingMockMvc.perform(get("/api/listings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listing.getId().intValue())))
            .andExpect(jsonPath("$.[*].listingType").value(hasItem(DEFAULT_LISTING_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].inputDate").value(hasItem(DEFAULT_INPUT_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].style").value(hasItem(DEFAULT_STYLE.toString())))
            .andExpect(jsonPath("$.[*].listingPrice").value(hasItem(DEFAULT_LISTING_PRICE.intValue())));
    }

    @Test
    @Transactional
    public void getListing() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get the listing
        restListingMockMvc.perform(get("/api/listings/{id}", listing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(listing.getId().intValue()))
            .andExpect(jsonPath("$.listingType").value(DEFAULT_LISTING_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.inputDate").value(DEFAULT_INPUT_DATE.toString()))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.style").value(DEFAULT_STYLE.toString()))
            .andExpect(jsonPath("$.listingPrice").value(DEFAULT_LISTING_PRICE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingListing() throws Exception {
        // Get the listing
        restListingMockMvc.perform(get("/api/listings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateListing() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);
        int databaseSizeBeforeUpdate = listingRepository.findAll().size();

        // Update the listing
        Listing updatedListing = listingRepository.findOne(listing.getId());
        updatedListing
            .listingType(UPDATED_LISTING_TYPE)
            .status(UPDATED_STATUS)
            .inputDate(UPDATED_INPUT_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .style(UPDATED_STYLE)
            .listingPrice(UPDATED_LISTING_PRICE);

        restListingMockMvc.perform(put("/api/listings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedListing)))
            .andExpect(status().isOk());

        // Validate the Listing in the database
        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeUpdate);
        Listing testListing = listingList.get(listingList.size() - 1);
        assertThat(testListing.getListingType()).isEqualTo(UPDATED_LISTING_TYPE);
        assertThat(testListing.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testListing.getInputDate()).isEqualTo(UPDATED_INPUT_DATE);
        assertThat(testListing.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testListing.getStyle()).isEqualTo(UPDATED_STYLE);
        assertThat(testListing.getListingPrice()).isEqualTo(UPDATED_LISTING_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingListing() throws Exception {
        int databaseSizeBeforeUpdate = listingRepository.findAll().size();

        // Create the Listing

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restListingMockMvc.perform(put("/api/listings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listing)))
            .andExpect(status().isCreated());

        // Validate the Listing in the database
        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteListing() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);
        int databaseSizeBeforeDelete = listingRepository.findAll().size();

        // Get the listing
        restListingMockMvc.perform(delete("/api/listings/{id}", listing.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Listing.class);
    }
}
