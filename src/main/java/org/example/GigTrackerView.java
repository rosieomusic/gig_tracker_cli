package org.example;

import org.example.model.Band;
import org.example.model.Bill;
import org.example.model.Venue;
import org.example.util.BasicConsole;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public class GigTrackerView {

    private final String FOREGROUND_DEFAULT = (char) 27 + "[39m";
    private final String FOREGROUND_RED = (char) 27 + "[31m";
    private final String FOREGROUND_GREEN = (char) 27 + "[32m";
    private final String FOREGROUND_YELLOW = (char) 27 + "[33m";
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String GREEN = "\033[0;32m";   // GREEN

    private final BasicConsole console;

    public GigTrackerView(BasicConsole console) {
        this.console = console;
    }
    public void printMessage(String message) {
        console.printMessage(FOREGROUND_YELLOW + message + FOREGROUND_DEFAULT);
    }

    // printErrorMessage makes the text color RED
    public void printErrorMessage(String message) {
        console.printErrorMessage(FOREGROUND_RED + message + FOREGROUND_DEFAULT);
    }

    // printBanner makes the text color GREEN
    public void printBanner(String message) {
        console.printBanner(FOREGROUND_GREEN + message + FOREGROUND_DEFAULT);
    }

    // promptForYesNo passes call through to console
    public boolean promptForYesNo(String prompt) {
        return console.promptForYesNo(PURPLE + prompt);
    }

    // getMenuSelection prints a RED banner, then passes through to console.
    public String getMenuSelection(String menuTitle, String[] options) {
        printBanner(FOREGROUND_RED + menuTitle + FOREGROUND_DEFAULT);
        System.out.println(GREEN);
        return console.getMenuSelection(options);
    }

    // **************************************************************
    // endregion console pass-through methods
    // **************************************************************

    // **************************************************************
    // region Print lists of objects to the console
    // **************************************************************


    public void listBands(List<Band> bands) {
        printBanner("All Bands");
        printBandList(bands);
        console.pauseOutput();
    }


    public void printBandList(List<Band> bands) {
        String heading1 = "  Id  Name                      City                      ST";
        String heading2 = "====  ========================  ========================  ==";
        String formatString = "%4d  %-24s  %-24s  %2s";
        printMessage(heading1);
        printMessage(heading2);
        for (Band band : bands) {
            String s = String.format(formatString,
                    band.getBandId(),
                    band.getName(),
                    band.getHometown(),
                    band.getState(),
                    band.getCountry(),
                    band.getBandcampLink(),
                    band.getContact()
            );
            printMessage(s);
        }
    }

    public void listBills(List<Bill> bills) {
        printBanner("All Bills");
        printBillList(bills);
        console.pauseOutput();
    }

    public void printBillList(List<Bill> bills) {
        String heading1 = "  Id  Description                     Number of Bands   Date   ";
        String heading2 = "====  ==============================  ============  ============";
        String formatString = "%4d  %-30s  %-12s  %-12s";
        printMessage(heading1);
        printMessage(heading2);
        for (Bill bill : bills) {
            String s = String.format(formatString,
                    bill.getBillId(),
                    bill.getDescription(),
                    bill.getNumOfBands(),
                    bill.getDateTime()
            );
            printMessage(s);
        }
    }

    public void listVenues(List<Venue> venues) {
        printBanner("All Venues");
        printVenueList(venues);
        console.pauseOutput();
    }

    public void printVenueList(List<Venue> venues) {
        String heading1 = "  Id  Name                      City                      ST";
        String heading2 = "====  ========================  ========================  ==";
        String formatString = "%4d  %-24s  %-24s  %2s";
        printMessage(heading1);
        printMessage(heading2);
        for (Venue venue : venues) {
            String s = String.format(formatString,
                    venue.getVenueId(),
                    venue.getName(),
                    venue.getCity(),
                    venue.getState(),
                    venue.getCountry(),
                    venue.getContact(),
                    venue.getEmail_instagram()
            );
            printMessage(s);
        }
    }


    // **************************************************************
    // endregion Print lists of objects to the console
    // **************************************************************

    // **************************************************************
    // region Prompt the user to select an object from the list
    // **************************************************************



    public Band selectBand(List<Band> bands) {
        while (true) {
            printBandList(bands);
            Integer bandId = console.promptForInteger("Enter band id [0 to cancel]: ");
            if (bandId == null || bandId == 0) {
                return null;
            }
            Band selectedBand = findBandById(bands, bandId);
            if (selectedBand != null) {
                return selectedBand;
            }
            printErrorMessage("That's not a valid id, please try again.");
        }
    }

    public Bill selectBill(List<Bill> bills) {
        while (true) {
            printBillList(bills);
            Integer billId = console.promptForInteger("Enter bill id for details [0 to return]: ");
            if (billId == null || billId == 0) {
                return null;
            }
            Bill selectedBill = findBillById(bills, billId);
            if (selectedBill != null) {
                return selectedBill;
            }
            printErrorMessage("That's not a valid id, please try again.");
        }
    }

    public Venue selectVenue(List<Venue> venues) {
        while (true) {
            printVenueList(venues);
            Integer venueId = console.promptForInteger("Enter venue id for details [0 to return]: ");
            if (venueId == null || venueId == 0) {
                return null;
            }
            Venue selectedVenue = findVenueById(venues, venueId);
            if (selectedVenue != null) {
                return selectedVenue;
            }
            printErrorMessage("That's not a valid id, please try again.");
        }
    }


    // **************************************************************
    // endregion Prompt the user to select an object from the list
    // **************************************************************

    // **************************************************************
    // region Find an object in the list
    // **************************************************************


    private Bill findBillById(List<Bill> bills, int billId) {
        for (Bill bill : bills) {
            if (bill.getBillId() == billId) {
                return bill;
            }
        }
        return null;
    }

    private Band findBandById(List<Band> bands, int bandId) {
        for (Band band : bands) {
            if (band.getBandId() == bandId) {
                return band;
            }
        }
        return null;
    }

    private Venue findVenueById(List<Venue> venues, Integer venueId) {
        for (Venue venue: venues) {
            if(venue.getVenueId() == venueId) {
                return venue;
            }
        }
        return null;
    }
    // **************************************************************
    // endregion Find an object in the list
    // **************************************************************

    // **************************************************************
    // region Print single object details to the console
    // **************************************************************

    public void printBandDetail(Band band) {
        printMessage(String.format("Band id: %s", band.getBandId()));
        printMessage(String.format("Band name: %s", band.getName()));
        printMessage(String.format("Hometown: %s", band.getHometown()));
        printMessage(String.format("State: %s", band.getState()));
        printMessage(String.format("Country: %s", band.getCountry()));
        printMessage(String.format("Bandcamp link: %s", band.getBandcampLink()));
        printMessage(String.format("Contact: %s", band.getContact()));
        console.pauseOutput();
    }

    public void printBillDetail(Bill bill) {
        printMessage(String.format("Bill id: %s", bill.getBillId()));
        printMessage(String.format("Description: %s", bill.getDescription()));
        printMessage(String.format("Number of Bands: %s", bill.getNumOfBands()));
        printMessage(String.format("Booker name: %s", bill.getBookerName()));
        printMessage(String.format("Date: %s", bill.getDateTime()));
        printMessage(String.format("Cost: %s", bill.getCost()));
        printMessage(String.format("Flyer: %s", bill.getFlyer()));
        printMessage(String.format("Venue id: %s", bill.getVenueId()));
        console.pauseOutput();
    }

    public void printVenueDetail(Venue venue) {
        printMessage(String.format("Venue id: %s", venue.getVenueId()));
        printMessage(String.format("Name: %s", venue.getName()));
        printMessage(String.format("City: %s", venue.getCity()));
        printMessage(String.format("State: %s", venue.getState()));
        printMessage(String.format("Country: %s", venue.getCountry()));
        printMessage(String.format("Contact: %s", venue.getContact()));
        printMessage(String.format("Email or instagram: %s", venue.getEmail_instagram()));
        console.pauseOutput();
    }





    // **************************************************************
    // endregion Print single object details to the console
    // **************************************************************

    // **************************************************************
    // region Prompt the user for object property information
    // **************************************************************

    public Band promptForBandInformation(Band existingBand) {
        Band newBand = new Band();
        if (existingBand == null) {
            // No default values
            newBand.setName(promptForBandName(null));
            newBand.setHometown(promptForBandHometown(null));
            newBand.setState(promptForBandState(null));
            newBand.setCountry(promptForBandCountry(null));
            newBand.setBandcampLink(promptForBandBandcamplink(null));
            newBand.setContact(promptForBandContact(null));

        } else {
            // This is an update -- make all prompts default to current values
            // Set the id
            newBand.setBandId(existingBand.getBandId());
            newBand.setName(existingBand.getName());
            newBand.setHometown(existingBand.getHometown());
            newBand.setCountry(existingBand.getCountry());
            newBand.setBandcampLink(existingBand.getBandcampLink());
            newBand.setContact(existingBand.getContact());
        }
        return newBand;
    }

    public Band promptForBandInformationToUpdate(Band existingBand) {
        while (true) {
        printMessage(String.format("1 Band name: %s", existingBand.getName()));
        printMessage(String.format("2 Hometown: %s", existingBand.getHometown()));
        printMessage(String.format("3 State: %s", existingBand.getState()));
        printMessage(String.format("4 Country: %s", existingBand.getCountry()));
        printMessage(String.format("5 Bandcamp link: %s", existingBand.getBandcampLink()));
        printMessage(String.format("6 Contact: %s", existingBand.getContact()));
        printMessage(String.format("Choose which field to update: "));

            Integer fieldId = console.promptForInteger("Enter field number[0 to cancel]: ");
            if (fieldId == null || fieldId == 0) {
                return null;
            }
            if(fieldId == 1) {
                String newBandName = promptForBandName(null);
                existingBand.setName(newBandName);
                System.out.println("Continue updating fields?");

                if(console.promptForYesNo("Y/N")) {
                        continue;
                }
                return existingBand;
            } else if (fieldId == 2) {
                String newBandHometown = promptForBandHometown(null);
                existingBand.setHometown(newBandHometown);
                System.out.println("Continue updating fields?");

                if(console.promptForYesNo("Y/N")) {
                    continue;
                }
                return existingBand;

            } else if (fieldId == 3) {
                String newBandState = promptForBandState(null);
                existingBand.setState(newBandState);
                System.out.println("Continue updating fields?");

                if(console.promptForYesNo("Y/N")) {
                    continue;
                }
                return existingBand;
            } else if(fieldId == 4) {
                String newBandCountry = promptForBandCountry(null);
                existingBand.setCountry(newBandCountry);
                System.out.println("Continue updating fields");

                if(console.promptForYesNo("Y/N")) {
                    continue;
                }
                return existingBand;
            } else if(fieldId == 5) {
                String newBandContact = promptForBandContact(null);
                existingBand.setContact(newBandContact);
                System.out.println("Continue updating fields");

                if(console.promptForYesNo("Y/N")) {
                    continue;
                }
                return existingBand;
            } else if (fieldId == 6) {
                String newBandBandcampLink = promptForBandBandcamplink(null);
                existingBand.setBandcampLink(newBandBandcampLink);
            }
            console.pauseOutput();
        }

    }

    private String promptForBandName(String defaultValue) {
        return promptForString("Band name", true, defaultValue);
    }

    private String promptForBandHometown(String defaultValue) {
        return promptForString("Hometown", true, defaultValue);
    }

    private String promptForBandState(String defaultValue) {
        return promptForString("State (2-letter)", true, defaultValue);
    }

    private String promptForBandCountry(String defaultValue) {
        return promptForString("Country", true, defaultValue);
    }

    private String promptForBandBandcamplink(String defaultValue) {
        return promptForString("Bandcamp link", true, defaultValue);
    }

    private String promptForBandContact(String defaultValue) {
        return promptForString("Contact", true, defaultValue);
    }

    public Bill promptForBillInformation(Bill existingBill) {
        Bill newBill = new Bill();
        if (existingBill == null) {
            // No default values
            newBill.setDescription(promptForBillDescription(null));

            newBill.setNumOfBands(Integer.parseInt(promptForBillNumberOfBands(null)));
            newBill.setBookerName(promptForBillBookerName(null));
            newBill.setDateTime(LocalDate.parse(promptForBillDate(null)));
            newBill.setCost(Integer.parseInt(promptForBillCost(null)));
            newBill.setFlyer(promptForBillFlyer(null));
            newBill.setVenueId(Integer.parseInt(promptForBillVenueId(null)));

        } else {
            // This is an update -- make all prompts default to current values
            // Set the id
            newBill.setBillId(existingBill.getBillId());
            newBill.setDescription(existingBill.getDescription());
            newBill.setNumOfBands(existingBill.getNumOfBands());
            newBill.setBookerName(existingBill.getBookerName());
            newBill.setDateTime(existingBill.getDateTime());
            newBill.setCost(existingBill.getCost());
            newBill.setFlyer(existingBill.getFlyer());
            newBill.setVenueId(existingBill.getVenueId());
        }
        return newBill;
    }

    public Bill promptForBillInformationToUpdate(Bill existingBill) {
        while (true) {
            printMessage(String.format("1 Bill description: %s", existingBill.getDescription()));
            printMessage(String.format("2 Number of Bands: %s", existingBill.getNumOfBands()));
            printMessage(String.format("3 Booker name: %s", existingBill.getBookerName()));
            printMessage(String.format("4 Date: %s", existingBill.getDateTime()));
            printMessage(String.format("5 Cost: %s", existingBill.getCost()));
            printMessage(String.format("6 Flyer: %s", existingBill.getFlyer()));
            printMessage(String.format("7 Venue id: %s", existingBill.getVenueId()));
            printMessage(String.format("Choose which field to update: "));

            Integer fieldId = console.promptForInteger("Enter field number [0 to cancel]: ");
            if (fieldId == null || fieldId == 0) {
                return null;
            }
            if(fieldId == 1) {
                String newBillDescription = promptForBillDescription(null);
                existingBill.setDescription(newBillDescription);
                System.out.println("Continue updating fields?");

                if(console.promptForYesNo("Y/N")) {
                    continue;
                }
                return existingBill;
            } else if (fieldId == 2) {
                String newNumberOfBands = promptForBillNumberOfBands(null);
                existingBill.setNumOfBands(Integer.parseInt(newNumberOfBands));
                System.out.println("Continue updating fields?");

                if(console.promptForYesNo("Y/N")) {
                    continue;
                }
                return existingBill;

            } else if (fieldId == 3) {
                String newBookerName = promptForBillBookerName(null);
                existingBill.setBookerName(newBookerName);
                System.out.println("Continue updating fields?");

                if(console.promptForYesNo("Y/N")) {
                    continue;
                }
                return existingBill;
            } else if(fieldId == 4) {
                String newBillDate = promptForBillDate(null);
                existingBill.setDateTime(LocalDate.parse(newBillDate));
                System.out.println("Continue updating fields");

                if(console.promptForYesNo("Y/N")) {
                    continue;
                }
                return existingBill;
            } else if(fieldId == 5) {
                String newBillCost = promptForBillCost(null);
                existingBill.setCost(Integer.parseInt(newBillCost));
                System.out.println("Continue updating fields");

                if(console.promptForYesNo("Y/N")) {
                    continue;
                }
                return existingBill;
            } else if (fieldId == 6) {
                String newBillFlyer = promptForBillFlyer(null);
                existingBill.setFlyer(newBillFlyer);

                if(console.promptForYesNo("Y/N")) {
                    continue;
                }
                return existingBill;
            } else if (fieldId == 7) {
                String newVenueId = promptForBillVenueId(null);
                existingBill.setVenueId(Integer.parseInt(newVenueId));
            }
            console.pauseOutput();
        }

    }

    private String promptForBillDescription(String defaultValue) {
        return promptForString("Description", true, defaultValue);
    }
    private String promptForBillNumberOfBands(String defaultValue) {
        return promptForString("Number Of Bands", true, defaultValue);
    }
    private String promptForBillBookerName(String defaultValue) {
        return promptForString("Booker Name", true, defaultValue);
    }
    private String promptForBillDate(String defaultValue) {
        return promptForString("Date", true, defaultValue);
    }
    private String promptForBillCost(String defaultValue) {
        return promptForString("Cost", true, defaultValue);
    }
    private String promptForBillFlyer(String defaultValue) {
        return promptForString("Flyer", true, defaultValue);
    }
    private String promptForBillVenueId(String defaultValue) {
        return promptForString("Venue id", true, defaultValue);
    }

    public Venue promptForVenueInformation(Venue existingVenue) {
        Venue newVenue = new Venue();
        if (existingVenue == null) {
            // No default values
            newVenue.setName(promptForVenueName(null));
            newVenue.setCity(promptForVenueCity(null));
            newVenue.setState(promptForVenueState(null));
            newVenue.setCountry(promptForVenueCountry(null));
            newVenue.setContact(promptForBandContact(null));
            newVenue.setEmail_instagram(promptForVenueEmailInstagram(null));

        } else {
            // This is an update -- make all prompts default to current values
            // Set the id
            newVenue.setVenueId(existingVenue.getVenueId());
            newVenue.setName(existingVenue.getName());
            newVenue.setCity(existingVenue.getCity());
            newVenue.setState(existingVenue.getState());
            newVenue.setCountry(existingVenue.getCountry());
            newVenue.setContact(existingVenue.getContact());
            newVenue.setEmail_instagram(existingVenue.getEmail_instagram());
        }
        return newVenue;
    }

    public Venue promptForVenueInformationToUpdate(Venue existingVenue) {
        while (true) {
            printMessage(String.format("1 Venue Name: %s", existingVenue.getName()));
            printMessage(String.format("2 City: %s", existingVenue.getCity()));
            printMessage(String.format("3 State: %s", existingVenue.getState()));
            printMessage(String.format("4 Country: %s", existingVenue.getCountry()));
            printMessage(String.format("5 Contact: %s", existingVenue.getContact()));
            printMessage(String.format("6 Email or Instagram: %s", existingVenue.getEmail_instagram()));
            printMessage(String.format("Choose which field to update: "));

            Integer fieldId = console.promptForInteger("Enter field number [0 to cancel]: ");
            if (fieldId == null || fieldId == 0) {
                return null;
            }
            if(fieldId == 1) {
                String newVenueName = promptForVenueName(null);
                existingVenue.setName(newVenueName);
                System.out.println("Continue updating fields?");

                if(console.promptForYesNo("Y/N")) {
                    continue;
                }
                return existingVenue;
            } else if (fieldId == 2) {
                String newVenueCity = promptForVenueCity(null);
                existingVenue.setCity((newVenueCity));
                System.out.println("Continue updating fields?");

                if(console.promptForYesNo("Y/N")) {
                    continue;
                }
                return existingVenue;

            } else if (fieldId == 3) {
                String newVenueState = promptForVenueState(null);
                existingVenue.setState(newVenueState);
                System.out.println("Continue updating fields?");

                if(console.promptForYesNo("Y/N")) {
                    continue;
                }
                return existingVenue;
            } else if(fieldId == 4) {
                String newVenueCountry = promptForVenueCountry(null);
                existingVenue.setCountry((newVenueCountry));
                System.out.println("Continue updating fields");

                if(console.promptForYesNo("Y/N")) {
                    continue;
                }
                return existingVenue;
            } else if(fieldId == 5) {
                String newVenueContact = promptForVenueContact(null);
                existingVenue.setContact((newVenueContact));
                System.out.println("Continue updating fields");

                if(console.promptForYesNo("Y/N")) {
                    continue;
                }
                return existingVenue;
            } else if (fieldId == 6) {
                String newVenueEmailInstagram = promptForVenueEmailInstagram(null);
                existingVenue.setEmail_instagram(newVenueEmailInstagram);
            }
            console.pauseOutput();
        }

    }


    private String promptForVenueName(String defaultValue) {
        return promptForString("Venue name", true, defaultValue);
    }

    private String promptForVenueCity(String defaultValue) {
        return promptForString("City", true, defaultValue);
    }

    private String promptForVenueState(String defaultValue) {
        return promptForString("State (2-letter)", true, defaultValue);
    }

    private String promptForVenueCountry(String defaultValue) {
        return promptForString("Country", true, defaultValue);
    }

    private String promptForVenueContact(String defaultValue) {
        return promptForString("Contact", true, defaultValue);
    }

    private String promptForVenueEmailInstagram(String defaultValue) {
        return promptForString("Email or Instagram", true, defaultValue);
    }


    private String promptWithDefault(String prompt, Object defaultValue) {
        if (defaultValue != null) {
            return prompt + "[" + defaultValue.toString() + "]: ";
        }
        return prompt + ": ";
    }

    private String promptForString(String prompt, boolean required, String defaultValue) {
        prompt = promptWithDefault(prompt, defaultValue);
        while (true) {
            String entry = console.promptForString(prompt);
            if (!entry.isEmpty()) {
                return entry;
            }
            // Entry is empty: see if we have a default, or if empty is OK (!required)
            if (defaultValue != null && !defaultValue.isEmpty()) {
                return defaultValue;
            }
            if (!required) {
                return entry;
            }
            printErrorMessage("A value is required, please try again.");
        }
    }





}
