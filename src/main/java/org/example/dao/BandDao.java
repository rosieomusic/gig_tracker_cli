package org.example.dao;

import org.example.model.Band;

import java.util.List;

public interface BandDao {
    /** Get the band from the datastore with the given id
     *
     * @param bandId the id of the band to retrieve
     * @return a complete band object
     */

    Band getBandById(int bandId);

    /** Get all the bands
     *
     * @return a list of all the bands in the datastore
     */

    List<Band> getBands();

    /** Create a new band in the datastore with the given information
     *
     * @param bandToAdd the band information to add
     * @return a band object with the id populated
     */

    Band createBand(Band bandToAdd);

    /** Update an existing band in the datastore with given information
     *
     * @param bandToUpdate the band information to update
     * @return updated band object
     */


    Band updateBand(Band bandToUpdate);

    /**
     * purposely did not include a delete band method,
     * I didn't want the user to be able to delete bands, it's good to keep track of them all
     * could remove them from a bill(will add method later) but only admin can delete bands
     */


}
