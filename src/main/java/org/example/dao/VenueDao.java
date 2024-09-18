package org.example.dao;

import org.example.model.Venue;

import java.util.List;

public interface VenueDao {
    /**
     * Get the venue from the datastore with the given id
     * @param venueId the id of the venue to retrieve
     * @return a complete venue object
     */
    Venue getVenueById(int venueId);
    /** Get all the venues
     *
     * @return a list of all the venues in the datastore
     */
    List<Venue> getVenues();


    /** Create a new venue in the datastore with the given information
     *
     * @param venueToAdd the venue information to add
     * @return a venue object with the id populated
     */

    Venue createVenue(Venue venueToAdd);

    /** Update an existing venue in the datastore with the given information
     *
     * @param venueToUpdate the venue information to update
     * @return updated venue object
     */
    Venue updateVenue(Venue venueToUpdate);

    /** delete a venue with the given id
     *
     * @param venueId the id of the venue to update
     * @return number of venues deleted (rows affected)
     */
    int deleteVenueById(int venueId);
}
