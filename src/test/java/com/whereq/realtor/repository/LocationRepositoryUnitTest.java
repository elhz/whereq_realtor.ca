package com.whereq.realtor.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.whereq.realtor.WhereQRealtorCaApp;
import com.whereq.realtor.domain.Location;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WhereQRealtorCaApp.class)
@Transactional
public class LocationRepositoryUnitTest {
	@Autowired
	private LocationRepository locationRepository;

	@Test
    public void addNewLocation() {
		Location location = new Location();
		
		assertThat(null == location.getId()).isTrue();
		
		location.address("509 Forest Drive");
		location.fullAddress("509 Forest Drive, Vaugha, Ontario, Canada");
		location.postcode("L4L 6M6");
		location.description("Home");
		location = locationRepository.save(location);
		assertThat(null == location.getId()).isFalse();
		
	}	
}
