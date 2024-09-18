package org.example.dao;

import org.example.exception.DaoException;
import org.example.model.Band;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcBandDao implements BandDao{
    JdbcTemplate jdbcTemplate;
    public JdbcBandDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public Band getBandById(int bandId) {
        Band band = null;
        String sql =  "SELECT band_id, name, hometown, state, country, bandcamp_link, contact " +
                      "FROM band " +
                      "WHERE band_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, bandId);
            if (results.next()) {
                band = mapRowToBand(results);
            }
        } catch(CannotGetJdbcConnectionException e){
                throw new DaoException("Unable to connect to server or database", e);
        }
        return band;

    }
    @Override
    public List<Band> getBands() {
        List<Band> bands = new ArrayList<>();
        String sql = "SELECT band_id, name, hometown, state, country, bandcamp_link, contact " +
                     "FROM band ";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Band band = mapRowToBand(results);
                bands.add(band);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return bands;
    }

    @Override
    public Band createBand(Band bandToAdd) {
        Band newBand = null;
        String sql = "INSERT INTO band(name, hometown, state, country, bandcamp_link, contact) " +
                     "VALUES (?, ?, ?, ?, ?, ?) RETURNING band_id;";
        try {
            int bandId =
                    jdbcTemplate.queryForObject(sql, int.class, bandToAdd.getName(), bandToAdd.getHometown(),
                            bandToAdd.getState(), bandToAdd.getCountry(), bandToAdd.getBandcampLink(), bandToAdd.getContact());
            newBand = getBandById(bandId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newBand;
    }

    @Override
    public Band updateBand(Band bandToUpdate) {
        Band updatedBand = null;
        String sql = "UPDATE band SET name = ?, hometown = ?, state = ?, country = ?, " +
                     "bandcamp_link = ?, contact = ? " +
                     "WHERE band_id = ?;";
        try {
            int numberOfRows =
                    jdbcTemplate.update(sql, bandToUpdate.getName(), bandToUpdate.getHometown(), bandToUpdate.getState(), bandToUpdate.getCountry(),
                            bandToUpdate.getBandcampLink(), bandToUpdate.getContact(), bandToUpdate.getBandId());
            if (numberOfRows == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            } else {
                updatedBand = getBandById(bandToUpdate.getBandId());
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedBand;
    }

    public Band mapRowToBand(SqlRowSet results) {
        Band band = new Band();
        band.setBandId(results.getInt("band_id"));
        band.setName(results.getString("name"));
        band.setHometown(results.getString("hometown"));
        band.setState(results.getString("state"));
        band.setCountry(results.getString("country"));
        band.setBandcampLink(results.getString("bandcamp_link"));
        band.setContact(results.getString("contact"));
        return band;

    }
}
