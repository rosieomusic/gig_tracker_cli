package org.example;

import org.example.dao.JdbcBillDao;
import org.example.model.Bill;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.format.datetime.joda.LocalDateTimeParser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class JdbcBillDaoTests extends BaseDaoTests{
    private JdbcBillDao jdbcBillDao;

    private static final Bill BILL_1 = new Bill(1, "Grinding violence from the pacific northwest, " +
            "with philadelphia saboteurs diuretic",3,"Mac W. & Zook Productions", LocalDate.parse("2024-04-13"),
            10, "drive.google.com/file/d/1DY3IZP3Sjrjm6DO1o4NSTx4gwd5sa0cj/view",2);

    private static final Bill BILL_2 = new Bill(2,"Another ripper",4,"Salt",LocalDate.parse("2024-04-21"),10,
            "drive.google.com/file/d/1dZZhmtnxcdcSlKfkbXTBsBdUGFZRhoGe/view",1);

    private Bill testBill;
    private JdbcBillDao sut;

    @Before
    public void setup() {
        //Arrange
        jdbcBillDao = new JdbcBillDao(dataSource);
        sut = new JdbcBillDao(dataSource);
        testBill = new Bill(0,"Test Bill description", 500,"rose",  LocalDate.parse("2024-04-03"),
                1, "google.com",1);
    }
    @Test
    public void getBillById_returns_correct_bill() {
        //Act
        Bill bill = sut.getBillById(1);
        //Assert
        assertBillsMatch(BILL_1,bill);
    }
    @Test
    public void getBills_returns_all_bills() {
        //Act
        List<Bill> bills = jdbcBillDao.getBills();
        //Assert
        Assert.assertEquals(2,bills.size());
        assertBillsMatch(BILL_1,bills.get(0));
        assertBillsMatch(BILL_2,bills.get(1));
    }
    @Test
    public void getBillsByBandId_returns_list_of_bills_for_bands() {
        //Act
        List<Bill> bills = sut.getBillsByBandId(1);
        //Assert
        Assert.assertEquals(2,bills.size());
        Assert.assertNotNull(bills);
        assertBillsMatch(BILL_1, bills.get(0));
        assertBillsMatch(BILL_2, bills.get(1));

    }
    @Test
    public void createBill_returns_bill_with_id_and_expected_values() {
        // Arrange (requires test object)

        //Act create bill
        Bill createdBill = sut.createBill(testBill);
        // Assert assert new id of created bill is greater than 0
        // and the passed in id is ignored bc serial
        int newId = createdBill.getBillId();
        Assert.assertTrue(newId > 0);

        //Assert retrieved bill matches test bill
        Bill retrievedBill = sut.getBillById(newId);
        assertBillsMatch(createdBill,retrievedBill);
    }
    @Test
    public void updateBill_has_expected_values_when_retrieved() {
        //Arrange 1. get starter bill
        Bill billToUpdate = sut.getBillById(1);
        //Arrange 2. change data of retrieved bill, id does not change
        billToUpdate.setDescription("Alfi's first DJ set");
        billToUpdate.setNumOfBands(4);
        billToUpdate.setBookerName("Joe Sly");
        billToUpdate.setDateTime(LocalDate.parse("2024-10-31"));
        billToUpdate.setCost(15);
        billToUpdate.setFlyer("halloween.com");
        billToUpdate.setVenueId(1);
    }

    @Test
    public void deleted_bill_cant_be_returned(){
        //Act delete venue in db
        sut.deleteBillById(1);

        //Assert get bill by id returns null
        Bill retrievedBill = sut.getBillById(1);
        Assert.assertNull(retrievedBill);
    }

    private void assertBillsMatch(Bill expected, Bill actual) {
        Assert.assertEquals(expected.getBillId(), actual.getBillId());
        Assert.assertEquals(expected.getDescription(), actual.getDescription());
        Assert.assertEquals(expected.getNumOfBands(), actual.getNumOfBands());
        Assert.assertEquals(expected.getBookerName(), actual.getBookerName());
        Assert.assertEquals(expected.getDateTime(),actual.getDateTime());
        Assert.assertEquals(expected.getCost(), actual.getCost());
        Assert.assertEquals(expected.getFlyer(),actual.getFlyer());
        Assert.assertEquals(expected.getVenueId(),actual.getVenueId());
    }
}
