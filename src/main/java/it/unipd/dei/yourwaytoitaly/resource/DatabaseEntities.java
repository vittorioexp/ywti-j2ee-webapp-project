package it.unipd.dei.yourwaytoitaly.resource;

/**
 * Class to define the objects which represents the entities in the DB
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

private final class City {
    private final int idCity;
    private final String idCityName;
    // Write constructor, getters and setters

}

public final class User {
    private final int idUser;
    private final String email;
    private final String phoneNumber;
    private final String address;
    private final String password;
    private final String userName;
    private final int idCity;
    // Write constructor, getters and setters
}

public final class Company extends User {
    private final int idUser;
    private final String denomination;
    // Write constructor, getters and setters
}

public final class Tourist extends User {
    private final int idUser;
    private final String surname;
    private final String name;
    private final Date birthDate;
    // Write constructor, getters and setters
}

public final class FeedBack {
    private final int idUser;
    private final int idAdvertisement;
    private final int rate;
    private final String text;
    // Write constructor, getters and setters
}

public final class Advertisement {
    private final int idAdvertisement;
    private final int idUser;
    private final int idType;
    private final String description;
    private final int score;
    private final int price;
    private final int numTotItem;
    private final Date dateStart;
    private final Date dateEnd;
    // Write constructor, getters and setters
}

public final class TypeAdvertisement {
    private final int idType;
    private final String name;
    // Write constructor, getters and setters
}

public final class Booking {
    private final int idUser;
    private final int idAdvertisement;
    private final Date dateTime;
    private final int numBooking;
}