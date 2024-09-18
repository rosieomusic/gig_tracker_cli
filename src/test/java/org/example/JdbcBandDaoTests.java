package org.example;

import org.example.dao.JdbcBandDao;
import org.example.model.Band;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.List;



public class JdbcBandDaoTests extends BaseDaoTests{
    private JdbcBandDao jdbcBandDao;

    private static final Band BAND_1 = new Band(1,"Diuretic", "Philadelphia", "PA","USA","diuretic.bandcamp.com","@diureticthebandthatilove");
    private static final Band BAND_2 = new Band(2,"Fake Dust","Portland","OR","USA","fakedust1.bandcamp.com", null);
    private static final Band BAND_3 = new Band(3,"Bohemian","Portland","OR", "USA","bohemianrock.bandcamp.com", null);
    private static final Band BAND_4 = new Band(4,"Ratpiss","Montreal",null,"Canada","ratpissmtl.bandcamp.com","@ratpissmtl");
    private static final Band BAND_5 = new Band(5,"Artificial Scarcity", "Philadelphia","PA","USA","artificialscarcity.bandcamp.com","@artificial-scarcity");
    private static final Band BAND_6 = new Band(6,"Insecticide","Philadelphia","PA","USA","insecticide-grind.bandcamp.com","@insecticide.grind");

    private Band testBand;
    private JdbcBandDao sut;

    @Before
    public void setup() {
        //Arrange!!! lol
        jdbcBandDao = new JdbcBandDao(dataSource);
        sut = new JdbcBandDao(dataSource);
        testBand = new Band(0,"Test Band","Needham",
                "MA","USA","testband.bandcamp.com","@testband");
    }

    @Test
    public void getBandById_returns_correct_band() {
        //Act
        Band band = sut.getBandById(1);
        //Assert
        assertBandsMatch(BAND_1,band);

    }

    @Test
    public void getBands_returns_all_bands() {
        //Act
        List<Band> bands = jdbcBandDao.getBands();

        //Assert
        Assert.assertEquals(6,bands.size());
        assertBandsMatch(BAND_1, bands.get(0));
        assertBandsMatch(BAND_2, bands.get(1));
        assertBandsMatch(BAND_3, bands.get(2));
        assertBandsMatch(BAND_4, bands.get(3));
        assertBandsMatch(BAND_5, bands.get(4));
        assertBandsMatch(BAND_6, bands.get(5));
    }

    @Test
    public void createBand_returns_band_with_id_and_expected_values(){
        // Arrange (requires test object)

        // Act create band
        Band createdBand = sut.createBand(testBand);

        // Assert new id of created band is greater than 0
        // and the passed in id is ignored bc serial
        int newId = createdBand.getBandId();
        Assert.assertTrue(newId > 0);

        //Assert retrieved band matches test band
        Band retrievedBand = sut.getBandById(newId);
        assertBandsMatch(createdBand,retrievedBand);
    }
    @Test
    public void updateBand_has_expected_values_when_retrieved() {
        //Arrange 1. get starter band
        Band bandToUpdate = sut.getBandById(1);
        //Arrange 2. change data of retrieved band. id not changed
        bandToUpdate.setName("Updated");
        bandToUpdate.setHometown("Boston");
        bandToUpdate.setState("MA");
        bandToUpdate.setCountry("USA");
        bandToUpdate.setBandcampLink("updatedband.bandcamp.com");
        bandToUpdate.setContact("@rose");


        //Act update band
        sut.updateBand(bandToUpdate);

        //Assert band matches when retrieved after update
        Band retrievedBand = sut.getBandById(1);
        assertBandsMatch(bandToUpdate,retrievedBand);
    }

    private void assertBandsMatch(Band expected, Band actual) {
        Assert.assertEquals(expected.getBandId(), actual.getBandId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getHometown(), actual.getHometown());
        Assert.assertEquals(expected.getState(), actual.getState());
        Assert.assertEquals(expected.getCountry(),actual.getCountry());
        Assert.assertEquals(expected.getBandcampLink(),actual.getBandcampLink());
        Assert.assertEquals(expected.getContact(), actual.getContact());
    }


}
