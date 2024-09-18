package org.example;

import org.example.connection.DataConnection;
import org.example.dao.*;
import org.example.exception.DaoException;
import org.example.model.Band;
import org.example.model.Bill;
import org.example.model.Venue;
import org.example.util.BasicConsole;

import java.util.List;


public class GigTrackerController {
    private final GigTrackerView view;
    private BasicConsole console;
    private BandDao bandDao;
    private BillDao billDao;
    private VenueDao venueDao;


    public GigTrackerController(BasicConsole console){
        view = new GigTrackerView(console);
        bandDao = new JdbcBandDao(DataConnection.get());
        billDao = new JdbcBillDao(DataConnection.get());
        venueDao = new JdbcVenueDao(DataConnection.get());

    }

    /**
     * The run() method starts the program flow by running the main menu.
     */
    public void run() {
        displayMainMenu();
    }

    /**
     * A loop which displays the main program menu, and responds to the user's selection
     */
    private void displayMainMenu() {
        // Menu options
        final String BAND_MENU = "Band menu";
        final String BILL_MENU = "Bill menu";
        final String VENUE_MENU = "Venue menu";
        final String EXIT = "Exit the program";
        final String[] MENU_OPTIONS = {BAND_MENU, BILL_MENU, VENUE_MENU, EXIT};

        boolean finished = false;

        // The menu loop
        while (!finished) {
            String selection =
                    view.getMenuSelection("<<<<<Welcome to the Gig Tracker App>>>>>", MENU_OPTIONS);
            switch (selection) {
                case BAND_MENU:
                    displayBandMenu();
                    break;
                case BILL_MENU:
                    displayBillMenu();
                    break;
                case VENUE_MENU:
                    displayVenueMenu();
                    break;
                case EXIT:
                    // Set finished to true so the loop exits.
                    finished = true;
                    break;
            }
        }
    }

    /**
     * A loop which displays the customer sub-menu, and responds to the user's selection
     */
    private void displayBandMenu() {
        // Menu options
        final String BAND_LIST = "List all bands";
        final String BAND_DETAILS = "View band details";
        final String BAND_ADD = "Add band";
        final String BAND_UPDATE = "Update band details";
        final String DONE = "Main menu";
        final String[] MENU_OPTIONS = {BAND_LIST, BAND_DETAILS, BAND_ADD, BAND_UPDATE, DONE};

        boolean finished = false;

        // The menu loop
        while (!finished) {
            String selection = view.getMenuSelection("BAND MENU", MENU_OPTIONS);
            try {
                switch (selection) {
                    case BAND_LIST:
                        listBands();
                        break;
                    case BAND_DETAILS:
                        displayBands();
                        break;
                    case BAND_ADD:
                        addBand();
                        break;
                    case BAND_UPDATE:
                        updateBand();
                        break;
                    case DONE:
                        // Set finished to true so the loop exits.
                        finished = true;
                        break;
                }
            }
            catch (DaoException e) {
                view.printErrorMessage("DAO error - " + e.getMessage());
            }
        }
    }

    /**
     * A loop which displays the bill sub-menu, and responds to the user's selection
     */
    private void displayBillMenu() {
        // Menu options
        final String BILL_LIST = "List all bills";
        final String BILL_DETAILS = "View bills";
        final String BILL_ADD = "Add bill";
        final String BILL_UPDATE = "Update bill details";
        final String BILL_DELETE = "Delete bill";
        final String DONE = "Main menu";
        final String[] MENU_OPTIONS = {BILL_LIST, BILL_DETAILS, BILL_ADD, BILL_UPDATE, BILL_DELETE, DONE};

        boolean finished = false;

        // The menu loop
        while (!finished) {
            String selection = view.getMenuSelection("BILL MENU", MENU_OPTIONS);
            try {
                switch (selection) {
                    case BILL_LIST:
                        listBills();
                        break;
                    case BILL_DETAILS:
                        displayBills();
                        break;
                    case BILL_ADD:
                        addBill();
                        break;
                    case BILL_UPDATE:
                        updateBill();
                        break;
                    case BILL_DELETE:
                        deleteBill();
                        break;
                    case DONE:
                        // Set finished to true so the loop exits.
                        finished = true;
                        break;
                }
            }
            catch (DaoException e) {
                view.printErrorMessage("DAO error - " + e.getMessage());
            }
        }
    }

    /**
     * A loop which displays the sales sub-menu, and responds to the user's selection
     */
    private void displayVenueMenu() {
        // Menu options
        final String VENUE_LIST = "Venue list";
        final String VENUE_DETAILS = "View venue details";
        final String VENUE_ADD = "Add venue";
        final String VENUE_UPDATE = "Update venue details";
        final String VENUE_DELETE = "Delete venue";
        final String DONE = "Main menu";
        final String[] MENU_OPTIONS = {VENUE_LIST, VENUE_DETAILS, VENUE_ADD, VENUE_UPDATE, VENUE_DELETE, DONE};

        boolean finished = false;

        // The menu loop
        while (!finished) {
            String selection = view.getMenuSelection("VENUE MENU", MENU_OPTIONS);
            try {
                switch (selection) {
                    case VENUE_LIST:
                        listVenues();
                        break;
                    case VENUE_DETAILS:
                        displayVenues();
                        break;
                    case VENUE_ADD:
                        addVenue();
                        break;
                    case VENUE_UPDATE:
                        updateVenue();
                        break;
                    case VENUE_DELETE:
                        deleteVenue();
                        break;
                    case DONE:
                        // Set finished to true so the loop exits.
                        finished = true;
                        break;
                }
            }
            catch (DaoException e) {
                view.printErrorMessage("DAO error - " + e.getMessage());
            }
        }
    }

    //*******************************************************
    //region Customer menu actions
    //*******************************************************

    private void listBands() {
        // Make sure we have the appropriate DAOs
        if (bandDao == null) {
            view.printErrorMessage("You must implement BandDao and pass it into the controller for this option to work.");
            return;
        }

        // Use the DAO to get the list
        List<Band> bands = bandDao.getBands();
        // Use the view to display to the user
        view.listBands(bands);
    }

    private void displayBands() {
        // Make sure we have the appropriate DAOs
        if (bandDao == null) {
            view.printErrorMessage("You must implement BandDao and pass it into the controller for this option to work.");
            return;
        }
        // Get the list of bands so the user can choose one
        List<Band> bands = bandDao.getBands();

        // Display the list of bands
        // and ask for selection
        Band band = view.selectBand(bands);
        if (band == null) {
            // User cancelled
            return;
        }
        // Show details to the user
        view.printBandDetail(band);
    }

    private void addBand() {
        // Make sure we have the appropriate DAOs
        if (bandDao == null) {
            view.printErrorMessage("You must implement jdbcBandDao and pass it into the controller for this option to work.");
            return;
        }

        // Prompt the user for customer information
        Band newBand = view.promptForBandInformation(null);

        // If customer is null, user cancelled
        if (newBand == null) {
            return;
        }
        // Call the DAO to add the new customer
        newBand = bandDao.createBand(newBand);
        // Inform the user
        view.printMessage("Band " + newBand.getBandId() + " has been created.");
    }

    private void updateBand() {
        // Make sure we have the appropriate DAOs
        if (bandDao == null) {
            view.printErrorMessage("You must implement BandDao and pass it into the controller for this option to work.");
            return;
        }

        // Get the list of bands so the user can choose one
        List<Band> bands = bandDao.getBands();

        // Display the list of bands and ask for selection
        Band band = view.selectBand(bands);
        if (band == null) {
            // User cancelled
            return;
        }

        Band bandToUpdate = view.promptForBandInformationToUpdate(band);


        // Call the DAO to update the band

        bandToUpdate = bandDao.updateBand(bandToUpdate);



        // Inform the user
        view.printMessage("Band " + bandToUpdate.getBandId() + " has been updated" );
    }
    //*******************************************************
    //endregion Customer menu actions
    //*******************************************************

    //*******************************************************
    //region Product menu actions
    //*******************************************************

    private void listBills() {
        // Make sure we have the appropriate DAOs
        if (billDao == null) {
            view.printErrorMessage("You must implement BillDao and pass it into the controller for this option to work.");
            return;
        }

        // Use the DAO to get the list
        List<Bill> bills = billDao.getBills();
        // Use the view to display to the user
        view.listBills(bills);
    }

    private void displayBills() {
        // Make sure we have the appropriate DAOs
        if (billDao == null) {
            view.printErrorMessage("You must implement BillDao and pass it into the controller for this option to work.");
            return;
        }
        // Get the list of bills so the user can choose one
        List<Bill> bills = billDao.getBills();

        // Display the list of bills and ask for selection
        Bill bill = view.selectBill(bills);
        if (bill == null) {
            // User cancelled
            return;
        }
        // Show details to the user
        view.printBillDetail(bill);
    }

    private void addBill() {
        // Make sure we have the appropriate DAOs
        if (billDao == null) {
            view.printErrorMessage("You must implement BillDao and pass it into the controller for this option to work.");
            return;
        }

        // Prompt the user for product information
        Bill newBill = view.promptForBillInformation(null);

        // If product is null, user cancelled
        if (newBill == null) {
            return;
        }
        // Call the DAO to add the new product
        newBill = billDao.createBill(newBill);
        // Inform the user
        view.printMessage("Bill " + newBill.getBillId() + " has been created.");
    }

    private void updateBill() {
        // Make sure we have the appropriate DAOs
        if (billDao == null) {
            view.printErrorMessage("You must implement BillDao and pass it into the controller for this option to work.");
            return;
        }

        // Get the list of products so the user can choose one
        List<Bill> bills = billDao.getBills();

        // Display the list of products and ask for selection
        Bill bill = view.selectBill(bills);
        if (bill == null) {
            // User cancelled
            return;
        }

        Bill billToUpdate = view.promptForBillInformationToUpdate(bill);


        // Call the DAO to update the product
        billToUpdate = billDao.updateBill(billToUpdate);
        // Inform the user
        view.printMessage("Bill" + billToUpdate.getBillId() +  "has been updated.");
    }

    private void deleteBill() {
        // Make sure we have the appropriate DAOs
        if (billDao == null) {
            view.printErrorMessage("You must implement BillDao and pass it into the controller for this option to work.");
            return;
        }

        // Get the list of products so the user can choose one
        List<Bill> bills = billDao.getBills();

        // Display the list of products and ask for selection
        if (bills.size() == 0) {
            // Nothing can be deleted because there are orders
            view.printErrorMessage("There are no bills that may be deleted!");
        }
        Bill bill = view.selectBill(bills);
        if (bill == null) {
            // User cancelled
            return;
        }
        // Prompt the user for confirmation
        boolean isConfirmed = view.promptForYesNo("Are you sure you want to DELETE bill '" + bill.getDescription() + "'?");
        if (!isConfirmed) {
            // User cancelled
            return;
        }

        // Call the DAO to delete the product
        billDao.deleteBillById(bill.getBillId());
        // Inform the user
        view.printMessage("Bill has been deleted.");
    }
    //*******************************************************
    //endregion Product menu actions
    //*******************************************************

    //*******************************************************
    // region Sales menu actions
    //*******************************************************

    private void listVenues() {
        // Make sure we have the appropriate DAOs
        if (venueDao == null) {
            view.printErrorMessage("You must implement VenueDao and pass it into the controller for this option to work.");
            return;
        }

        // Use the DAO to get the list
        List<Venue> venues = venueDao.getVenues();
        // Use the view to display to the user
        view.listVenues(venues);
    }

    private void displayVenues() {
        // Make sure we have the appropriate DAOs
        if (venueDao == null) {
            view.printErrorMessage("You must implement BillDao and pass it into the controller for this option to work.");
            return;
        }
        // Get the list of products so the user can choose one
        List<Venue> venues = venueDao.getVenues();

        // Display the list of products and ask for selection
        Venue venue = view.selectVenue(venues);
        if (venue == null) {
            // User cancelled
            return;
        }
        // Show details to the user
        view.printVenueDetail(venue);
    }

    private void addVenue() {
        // Make sure we have the appropriate DAOs
        if (venueDao == null) {
            view.printErrorMessage("You must implement VenueDao and pass it into the controller for this option to work.");
            return;
        }

        // Prompt the user for Venue information
        Venue newVenue = view.promptForVenueInformation(null);

        // If product is null, user cancelled
        if (newVenue == null) {
            return;
        }
        // Call the DAO to add the new venue
        newVenue = venueDao.createVenue(newVenue);
        // Inform the user
        view.printMessage("Bill " + newVenue.getVenueId() + " has been created.");
    }

    private void updateVenue() {
        // Make sure we have the appropriate DAOs
        if (venueDao == null) {
            view.printErrorMessage("You must implement VenueDao and pass it into the controller for this option to work.");
            return;
        }

        // Get the list of products so the user can choose one
        List<Venue> venues = venueDao.getVenues();

        // Display the list of products and ask for selection
        Venue venue = view.selectVenue(venues);
        if (venue == null) {
            // User cancelled
            return;
        }

        Venue venueToUpdate = view.promptForVenueInformationToUpdate(venue);

        // Call the DAO to update the product
        venueToUpdate = venueDao.updateVenue(venue);
        // Inform the user
        view.printMessage("Venue" + venueToUpdate.getVenueId() + "has been updated.");
    }

    private void deleteVenue() {
        // Make sure we have the appropriate DAOs
        if (venueDao == null) {
            view.printErrorMessage("You must implement BillDao and pass it into the controller for this option to work.");
            return;
        }

        // Get the list of products so the user can choose one
        List<Venue> venues = venueDao.getVenues();

        // Display the list of products and ask for selection
        if (venues.size() == 0) {
            // Nothing can be deleted because there are orders
            view.printErrorMessage("There are no venues that may be deleted!");
        }
        Venue venue = view.selectVenue(venues);
        if (venue == null) {
            // User cancelled
            return;
        }
        // Prompt the user for confirmation
        boolean isConfirmed = view.promptForYesNo("Are you sure you want to DELETE venue '" + venue.getName() + "'?");
        if (!isConfirmed) {
            // User cancelled
            return;
        }

        // Call the DAO to delete the product
        venueDao.deleteVenueById(venue.getVenueId());
        // Inform the user
        view.printMessage("Venue has been deleted.");
    }


    //*******************************************************
    //endregion Sales menu actions
    //*******************************************************









}


