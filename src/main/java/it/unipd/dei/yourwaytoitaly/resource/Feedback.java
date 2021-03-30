package it.unipd.dei.yourwaytoitaly.resource;

import java.sql.Date;

/**
 * Class to define the objects which represents the Feedback in the DB
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public final class Feedback {
    private final String emailTourist;
    private final int idAdvertisement;
    private final int rate;
    private final String text;
    private final Date date;

    public Feedback(final String emailTourist, final int idAdvertisement,
                    final int rate, final String text, final Date date) {
        this.emailTourist = emailTourist;
        this.idAdvertisement = idAdvertisement;
        this.rate = rate;
        this.text = text;
        this.date = date;
    }

    public final String getEmailTourist() {
        return emailTourist;
    }

    public final int getIdAdvertisement() {
        return idAdvertisement;
    }

    public final int getRate() {
        return rate;
    }

    public final String getText() {
        return text;
    }

    public final Date getDate() {
        return date;
    }
}
