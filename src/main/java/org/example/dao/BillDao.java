package org.example.dao;

import org.example.model.Bill;

import java.util.List;

public interface BillDao {

    /**
     * Get the bill from the datastore with the given id
     *
     * @param billId the id of the bill to retrieve
     * @return a complete bill object
     */
    Bill getBillById(int billId);

    /** Get all the bills
     *
     * @return a list of all the bills in the datastore
     */

    List<Bill> getBills();

    /** Get all the bills that a band is playing with the given bandId from the datastore
     *
     * @param bandId the id of the band whose bills to retrieve
     * @return a list of bill objects
     */


    List<Bill> getBillsByBandId(int bandId);

    /** Create a new bill in the datastore with the given information
     *
     * @param billToAdd the bill information to add
     * @return a bill object with the id populated
     */

    Bill createBill(Bill billToAdd);

    /** Update an existing bill in the datastore with the given information
     *
     * @param billToUpdate the bill information to update
     * @return updated bill object
     */

    Bill updateBill(Bill billToUpdate);

    /**
     * Delete the bill with the given id
     * @param billId the id of the bill to delete
     * @return number of bills deleted (rows affected)
     */

    int deleteBillById(int billId);




}
