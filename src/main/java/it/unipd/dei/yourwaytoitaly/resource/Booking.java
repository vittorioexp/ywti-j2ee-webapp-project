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
    private final int idUser;
    private final int idAdvertisement;
    private final Date date;
    private final Time time;
    private final int numBooking;

    public Booking(final int idUser, final int idAdvertisement,
                   final Date date, final Time time, final int numBooking) {
        this.idUser = idUser;
        this.idAdvertisement = idAdvertisement;
        this.date = date;
        this.time = time;
        this.numBooking = numBooking;
    }
    public final int getIdUser() {
        return idUser;
    }
    public final int getIdAdvertisement() {
        return idAdvertisement;
    }
    public final Date getDate() {
        return date;
    }
    public final Time getTime() { return time; }
    public final int getNumBooking() {
        return numBooking;
    }
}