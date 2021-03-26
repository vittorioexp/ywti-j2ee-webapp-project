package it.unipd.dei.yourwaytoitaly.resource;

/**
 * Class to define the objects which represents the Feedback in the DB
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public final class Feedback {
    private final int idUser;
    private final int idAdvertisement;
    private final int rate;
    private final String text;

    public Feedback(final int idUser, final int idAdvertisement, final int rate, final String text) {
        this.idUser = idUser;
        this.idAdvertisement = idAdvertisement;
        this.rate = rate;
        this.text = text;
    }
    public final int getIdUser() {
        return idUser;
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
}
