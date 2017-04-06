package com.whereq.realtor.repository;

import com.whereq.realtor.domain.Listing;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Listing entity.
 */
@SuppressWarnings("unused")
public interface ListingRepository extends JpaRepository<Listing,Long> {

}
