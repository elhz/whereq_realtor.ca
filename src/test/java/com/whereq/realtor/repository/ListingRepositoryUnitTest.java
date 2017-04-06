package com.whereq.realtor.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.whereq.realtor.WhereQRealtorCaApp;
import com.whereq.realtor.domain.Listing;
import com.whereq.realtor.domain.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WhereQRealtorCaApp.class)
@Transactional
public class ListingRepositoryUnitTest {
	@Autowired
	private ListingRepository listingRepository;

	@Test
    public void addNewListing() {
		Listing listing = new Listing();
		
		assertThat(null == listing.getId()).isTrue();
		listing = listingRepository.save(listing);
		assertThat(null == listing.getId()).isFalse();
		
	}

	@Test
	public void testAssertSuccess() throws Exception {
	
	    List<User> userList = new ArrayList<User>();
	    assertThat(userList).hasSize(0);
	}
}
