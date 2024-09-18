package org.example.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.example.exception.DaoException;
import org.example.model.Venue;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcVenueDao implements VenueDao {
    private JdbcTemplate jdbcTemplate;
    public JdbcVenueDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public Venue getVenueById(int venueId) {
        Venue venue = null;
        String sql = "SELECT venue_id, name, city, state, country, contact, email_instagram " +
                     "FROM venue " +
                     "WHERE venue_id = ?;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, venueId);
            if (results.next()) {
                venue = mapRowToVenue(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return venue;
    }

    @Override
    public List<Venue> getVenues() {
        List<Venue> venues = new ArrayList<>();
        String sql = "SELECT venue_id, name, city, state, country, contact, email_instagram " +
                                     "FROM VENUE " +
                                     "ORDER BY name;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while(results.next()) {
                Venue venue = mapRowToVenue(results);
                venues.add(venue);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return venues;
    }

    @Override
    public Venue createVenue(Venue venueToAdd) {
        Venue newVenue = null;
        String sql = "INSERT INTO venue(name, city, state, country, contact, email_instagram) " +
                     "VALUES(?, ?, ?, ?, ?, ?) RETURNING venue_id;";

        try {
            int venueId =
                    jdbcTemplate.queryForObject(sql, int.class, venueToAdd.getName(), venueToAdd.getCity(),
                            venueToAdd.getState(), venueToAdd.getCountry(), venueToAdd.getContact(),
                            venueToAdd.getEmail_instagram());
            newVenue = getVenueById(venueId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newVenue;
    }

    @Override
    public Venue updateVenue(Venue venueToUpdate) {
        Venue updatedVenue = null;
        String sql = "UPDATE venue SET name = ?, city = ?, state = ?, country = ?, " +
                     "contact = ?, email_instagram = ? " +
                     "WHERE venue_id = ?;";
        try{
            int numberOfRows =
                    jdbcTemplate.update(sql, venueToUpdate.getName(), venueToUpdate.getCity(),
                            venueToUpdate.getState(), venueToUpdate.getCountry(),
                            venueToUpdate.getContact(), venueToUpdate.getEmail_instagram(), venueToUpdate.getVenueId());
            if (numberOfRows == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            } else {
                updatedVenue = getVenueById(venueToUpdate.getVenueId());
            }
            } catch (CannotGetJdbcConnectionException e) {
                throw new DaoException("Unable to connect to server or database", e);
            } catch (DataIntegrityViolationException e) {
                throw new DaoException("Data integrity violation", e);
            }
            return updatedVenue;
    }

    @Override
    public int deleteVenueById(int venueId) {
        int numberOfRows = 0;
        String bandBillSql = "DELETE FROM band_bill WHERE bill_id = (SELECT bill_id FROM bill WHERE venue_id = ?);";
        String billSql = "DELETE FROM bill WHERE venue_id = ?;";
        String venueSql = "DELETE FROM venue WHERE venue_id = ?;";
        try {
            jdbcTemplate.update(bandBillSql,venueId);
            jdbcTemplate.update(billSql,venueId);
            numberOfRows = jdbcTemplate.update(venueSql,venueId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    public Venue mapRowToVenue(SqlRowSet results) {
        Venue venue = new Venue();
        venue.setVenueId(results.getInt("venue_id"));
        venue.setName(results.getString("name"));
        venue.setCity(results.getString("city"));
        venue.setState(results.getString("state"));
        venue.setCountry(results.getString("country"));
        venue.setContact(results.getString("contact"));
        venue.setEmail_instagram(results.getString("email_instagram"));
        return venue;
    }
}
