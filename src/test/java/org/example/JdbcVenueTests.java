package org.example;

import org.example.dao.JdbcVenueDao;
import org.example.model.Venue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class JdbcVenueTests extends BaseDaoTests{
    private JdbcVenueDao jdbcVenueDao;

    private static final Venue VENUE_1 = new Venue(1,"Haus (fka haus of yarga)", "Phiadelphia",
            "PA", "USA", "Salt", "@haus");
    private static final Venue VENUE_2 = new Venue(2,"The WvrmHole", "Philadelphia",
            "PA", "USA", null, "@wvrmhole.philly");

    private Venue testVenue;
    private JdbcVenueDao sut;

    @Before
    public void setup() {
        //Arrange
        jdbcVenueDao = new JdbcVenueDao(dataSource);
        sut = new JdbcVenueDao(dataSource);
        testVenue = new Venue(0,"Test Venue", "Boston", "MA", "USA", "Rose",
                "@Rose");
    }

    @Test
    public void getVenueById_returns_correct_venue(){
        //Act
        Venue venue = sut.getVenueById(1);
        //Assert
        assertVenuesMatch(VENUE_1,venue);
    }
    @Test
    public void getVenues_returns_all_venues() {
        //Act
        List<Venue> venues = jdbcVenueDao.getVenues();

        //Assert
        Assert.assertEquals(2, venues.size());
        assertVenuesMatch(VENUE_1, venues.get(0));
        assertVenuesMatch(VENUE_2, venues.get(1));
    }
    @Test
    public void createVenue_returns_venue_with_id_and_expected_values(){
        // Arrange (requires venue object)

        // Act create venue
        Venue createVenue = sut.createVenue(testVenue);

        // Assert new id of created venue is greater than 0
        // and the passed in id is ignored bc serial
        int newId = createVenue.getVenueId();
        Assert.assertTrue(newId > 0);

        // Assert retrieved venue matches test venue
        Venue retrievedVenue = sut.getVenueById(newId);
        assertVenuesMatch(createVenue,retrievedVenue);
    }
    @Test
    public void updateVenue_has_expected_values_when_retrieved(){
        // Arrange 1. get starter venue
        Venue venueToUpdate = sut.getVenueById(1);
        //Arrange 2. change data of the retrieved band id not changed
        venueToUpdate.setName("Club H3LL");
        venueToUpdate.setCity("NYC");
        venueToUpdate.setState("New York");
        venueToUpdate.setCountry("USA");
        venueToUpdate.setContact("Alfi");
        venueToUpdate.setEmail_instagram("@Alfi_in_H3LL");
    }
    @Test
    public void deleted_venue_cant_be_retrieved(){
        //Act delete venue in db
        sut.deleteVenueById(1);

        //Assert get venue by id returns null
        Venue retrievedVenue = sut.getVenueById(1);
        Assert.assertNull(retrievedVenue);

    }
    private void assertVenuesMatch(Venue expected, Venue actual) {
        Assert.assertEquals(expected.getVenueId(), actual.getVenueId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getCity(), actual.getCity());
        Assert.assertEquals(expected.getState(), actual.getState());
        Assert.assertEquals(expected.getCountry(),actual.getCountry());
        Assert.assertEquals(expected.getContact(),actual.getContact());
        Assert.assertEquals(expected.getEmail_instagram(),actual.getEmail_instagram());
    }

}
