package org.example.dao;

import org.example.exception.DaoException;
import org.example.model.Bill;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JdbcBillDao implements BillDao{
    JdbcTemplate jdbcTemplate;

    public JdbcBillDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Bill getBillById(int billId) {
        Bill bill = null;
        String sql = "SELECT bill_id, description, num_of_bands, booker_name, " +
                "date_time, cost, flyer, venue_id " +
                "FROM bill " +
                "WHERE bill_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql,billId);
            if (results.next()) {
                bill = mapRowToBill(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return bill;
    }

    @Override
    public List<Bill> getBills() {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT bill_id, description, num_of_bands, booker_name, " +
                     "date_time, cost, flyer, venue_id " +
                     "FROM bill " +
                     "ORDER BY date_time;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Bill bill = mapRowToBill(results);
                bills.add(bill);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return bills;
    }

    @Override
    public List<Bill> getBillsByBandId(int bandId) {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT bill.bill_id, description, num_of_bands, booker_name, " +
                     "date_time, cost, flyer, venue_id " +
                     "FROM bill " +
                     "JOIN band_bill ON band_bill.bill_id = bill.bill_id " +
                     "JOIN band ON band_bill.band_id = band.band_id " +
                     "WHERE band.band_id = ?;";



        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, bandId);
            while (results.next()) {
                Bill bill = mapRowToBill(results);
                bills.add(bill);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return bills;
    }

    @Override
    public Bill createBill(Bill billToAdd) {
        Bill newBill = null;
        String sql = "INSERT INTO bill(description, num_of_bands, booker_name, " +
                     "date_time, cost, flyer, venue_id) VALUES(?, ?, ?, ?, ?, ?, ?) " +
                     "RETURNING bill_id;";
        try {
            int billId =
                    jdbcTemplate.queryForObject(sql, int.class, billToAdd.getDescription(),
                            billToAdd.getNumOfBands(), billToAdd.getBookerName(), billToAdd.getDateTime(),
                            billToAdd.getCost(), billToAdd.getFlyer(), billToAdd.getVenueId());
            newBill = getBillById(billId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newBill;
    }

    @Override
    public Bill updateBill(Bill billToUpdate) {
        Bill updatedBill = null;
        String sql = "UPDATE bill SET description = ?, num_of_bands = ?, booker_name = ?, " +
                     "date_time = ?, cost = ?, flyer = ?, venue_id = ? " +
                     "WHERE bill_id = ?;";
        try{
            int numberOfRows =
                    jdbcTemplate.update(sql, billToUpdate.getDescription(),
                            billToUpdate.getNumOfBands(), billToUpdate.getBookerName(), billToUpdate.getDateTime(),
                            billToUpdate.getCost(), billToUpdate.getFlyer(), billToUpdate.getVenueId(), billToUpdate.getBillId());
            if (numberOfRows == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            } else {
                updatedBill = getBillById(billToUpdate.getBillId());
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedBill;
    }

    @Override
    public int deleteBillById(int billId) {
        int numberOfRows = 0;
        String bandBillSql = "DELETE FROM band_bill WHERE bill_id = ?;";
        String billSql = "DELETE FROM bill WHERE bill_id = ?;";
        try {
            jdbcTemplate.update(bandBillSql,billId);
            numberOfRows = jdbcTemplate.update(billSql,billId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    public Bill mapRowToBill(SqlRowSet results) {
        Bill bill = new Bill();
        bill.setBillId(results.getInt("bill_id"));
        bill.setDescription(results.getString("description"));
        bill.setNumOfBands(results.getInt("num_of_bands"));
        bill.setBookerName(results.getString("booker_name"));
        bill.setDateTime(Objects.requireNonNull(results.getDate("date_time")).toLocalDate());
        bill.setCost(results.getInt("cost"));
        bill.setFlyer(results.getString("flyer"));
        bill.setVenueId(results.getInt("venue_id"));
        return bill;
    }
}
