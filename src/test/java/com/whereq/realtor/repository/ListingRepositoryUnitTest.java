package com.whereq.realtor.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.whereq.realtor.WhereQRealtorCaApp;
import com.whereq.realtor.domain.Listing;
import com.whereq.realtor.domain.enumeration.ListingStatus;
import com.whereq.realtor.domain.enumeration.ListingType;
import com.whereq.realtor.domain.enumeration.Style;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WhereQRealtorCaApp.class)
@Transactional
public class ListingRepositoryUnitTest extends AbstractTransactionalJUnit4SpringContextTests{
	@Autowired
	private ListingRepository listingRepository;

	@Before
    public void setUp() {
		
		deleteFromTables("location", "listing");
		executeSqlScript("classpath:test_data.sql", false);
	}
	
	@Test(expected = javax.validation.ConstraintViolationException.class)
    public void testAddNewListing() {
		Listing listing = new Listing();
		
		assertThat(null == listing.getId()).isTrue();
		listing.status(ListingStatus.AVAILABLE);
		listing.listingType(ListingType.DETACHED);
		listing.style(Style.TWOSTOREY);
		listing.listingPrice(BigDecimal.valueOf(1000000.00));
		listing.inputDate(LocalDate.now());
		
		listing = listingRepository.save(listing);
		assertThat(null == listing.getId()).isFalse();
	}

	@Test
	public void testGetListingById() throws Exception {
	
	    Listing listing = listingRepository.findOne(1L);
	    assertThat(listing == null).isFalse();
	    assertThat(listing.getListingType().equals(ListingType.DETACHED)).isTrue();	
	    assertThat(listing.getLocation().getAddress().equals("509 Forest Drive")).isTrue();
	}
}
