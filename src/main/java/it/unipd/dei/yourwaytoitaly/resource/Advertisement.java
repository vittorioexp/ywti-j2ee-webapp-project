package it.unipd.dei.yourwaytoitaly.resource;

import java.sql.Time;
import java.sql.Date;

/**
 * Class to define the objects which represents the Advertisement in the DB
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public final class Advertisement {
    private final int idAdvertisement;
    private final String description;
    private final int score;
    private final int price;
    private final int numTotItem;
    private final Date dateStart;
    private final Date dateEnd;
    private final Time timeStart;
    private final Time timeEnd;
    private final String emailCompany;
    private final int idType;

    public Advertisement(final int idAdvertisement, final String description, final int score, final int price,
                         final int numTotItem, final Date dateStart, final Date dateEnd, final Time timeStart,
                         final Time timeEnd, final String emailCompany, final int idType) {
        this.idAdvertisement = idAdvertisement;
        this.description = description;
        this.score = score;
        this.price = price;
        this.numTotItem = numTotItem;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.emailCompany = emailCompany;
        this.idType = idType;
    }

    public final int getIdAdvertisement() {
        return idAdvertisement;
    }
    public final String getDescription() {
        return description;
    }
    public final int getScore() {
        return score;
    }
    public final int getPrice() {
        return price;
    }
    public final int getNumTotItem() {
        return numTotItem;
    }
    public final Date getDateStart() {
        return dateStart;
    }
    public final Date getDateEnd() {
        return dateEnd;
    }
    public final Time getTimeStart() {
        return timeStart;
    }
    public final Time getTimeEnd() {
        return timeEnd;
    }
    public final String getEmailCompany() {
        return emailCompany;
    }
    public final int getIdType() {
        return idType;
    }
}
