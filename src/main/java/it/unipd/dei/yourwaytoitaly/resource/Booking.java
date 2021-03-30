package it.unipd.dei.yourwaytoitaly.resource;

import java.sql.Time;
import java.sql.Date;

/**
 * Class to define the objects which represents the Booking in the DB
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public final class Booking {
    private final String emailTourist;
    private final int idAdvertisement;
    private final Date date;
    private final Time time;
    private final int numBooking;
    private final String state;

    public Booking(final String emailTourist, final int idAdvertisement, final Date date, final Time time,
                   final int numBooking, final String state) {
        this.emailTourist = emailTourist;
        this.idAdvertisement = idAdvertisement;
        this.date = date;
        this.time = time;
        this.numBooking = numBooking;
        this.state = state;
    }

    public final String getEmailTourist() {
        return emailTourist;
    }
    public final int getIdAdvertisement() {
        return idAdvertisement;
    }
    public final Date getDate() {
        return date;
    }
    public final Time getTime() {
        return time;
    }
    public final int getNumBooking() {
        return numBooking;
    }
    public final String getState() {
        return state;
    }
}