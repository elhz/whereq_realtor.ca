package com.whereq.realtor.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.whereq.realtor.domain.Listing;

import com.whereq.realtor.repository.ListingRepository;
import com.whereq.realtor.web.rest.util.HeaderUtil;
import com.whereq.realtor.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Listing.
 */
@RestController
@RequestMapping("/api")
public class ListingResource {

    private final Logger log = LoggerFactory.getLogger(ListingResource.class);

    private static final String ENTITY_NAME = "listing";
        
    private final ListingRepository listingRepository;

    public ListingResource(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    /**
     * POST  /listings : Create a new listing.
     *
     * @param listing the listing to create
     * @return the ResponseEntity with status 201 (Created) and with body the new listing, or with status 400 (Bad Request) if the listing has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/listings")
    @Timed
    public ResponseEntity<Listing> createListing(@Valid @RequestBody Listing listing) throws URISyntaxException {
        log.debug("REST request to save Listing : {}", listing);
        if (listing.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new listing cannot already have an ID")).body(null);
        }
        Listing result = listingRepository.save(listing);
        return ResponseEntity.created(new URI("/api/listings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /listings : Updates an existing listing.
     *
     * @param listing the listing to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated listing,
     * or with status 400 (Bad Request) if the listing is not valid,
     * or with status 500 (Internal Server Error) if the listing couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/listings")
    @Timed
    public ResponseEntity<Listing> updateListing(@Valid @RequestBody Listing listing) throws URISyntaxException {
        log.debug("REST request to update Listing : {}", listing);
        if (listing.getId() == null) {
            return createListing(listing);
        }
        Listing result = listingRepository.save(listing);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, listing.getId().toString()))
            .body(result);
    }

    /**
     * GET  /listings : get all the listings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of listings in body
     */
    @GetMapping("/listings")
    @Timed
    public ResponseEntity<List<Listing>> getAllListings(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Listings");
        Page<Listing> page = listingRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/listings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /listings/:id : get the "id" listing.
     *
     * @param id the id of the listing to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the listing, or with status 404 (Not Found)
     */
    @GetMapping("/listings/{id}")
    @Timed
    public ResponseEntity<Listing> getListing(@PathVariable Long id) {
        log.debug("REST request to get Listing : {}", id);
        Listing listing = listingRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(listing));
    }

    /**
     * DELETE  /listings/:id : delete the "id" listing.
     *
     * @param id the id of the listing to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/listings/{id}")
    @Timed
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
        log.debug("REST request to delete Listing : {}", id);
        listingRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
