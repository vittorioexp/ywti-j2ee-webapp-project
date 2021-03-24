package it.unipd.dei.yourwaytoitaly.resource;

import java.util.Date;

/**
 * Class to define the objects which represents the entities in the DB
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public final class City {
    private final int idCity;
    private final String idCityName;

    public City(final int idCity, final String idCityName) {
        this.idCity = idCity;
        this.idCityName = idCityName;
    }
    public final int getIdCity() {
        return idCity;
    }
    public final String getIdCityName() {
        return idCityName;
    }
}

public final class User {
    private final int idUser;
    private final String email;
    private final String phoneNumber;
    private final String address;
    private final String password;
    private final String userName;
    private final int idCity;

    public User(final int idUser, final String email, final String phoneNumber, final String address,
                final String password, final String userName, final int idCity) {
        this.idUser = idUser;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.password = password;
        this.userName = userName;
        this.idCity = idCity;
    }
    public final int getIdUser() {
        return idUser;
    }
    public final String getEmail() {
        return email;
    }
    public final String getPhoneNumber() {
        return phoneNumber;
    }
    public final String getAddress() {
        return address;
    }
    public final String getPassword() {
        return password;
    }
    public final String getUserName() {
        return userName;
    }
    public final int getIdCity() {
        return idCity;
    }
}

public final class Company extends User {
    private final int idUser;
    private final String denomination;

    public Company(final int idUser, final String email, final String phoneNumber, final String address,
                   final String password, final String userName, final int idCity, final int idUser1,
                   final String denomination) {
        super(idUser, email, phoneNumber, address, password, userName, idCity);
        this.idUser = idUser1;
        this.denomination = denomination;
    }
    @java.lang.Override
    public final int getIdUser() {
        return idUser;
    }
    public final String getDenomination() {
        return denomination;
    }
}

public final class Tourist extends User {
    private final int idUser;
    private final String surname;
    private final String name;
    private final Date birthDate;

    public Tourist(final int idUser, final String email, final String phoneNumber, final String address,
                   final String password, final String userName, final int idCity, final int idUser1,
                   final String surname, final String name, final Date birthDate) {
        super(idUser, email, phoneNumber, address, password, userName, idCity);
        this.idUser = idUser1;
        this.surname = surname;
        this.name = name;
        this.birthDate = birthDate;
    }
    @java.lang.Override
    public final int getIdUser() {
        return idUser;
    }
    public final String getSurname() {
        return surname;
    }
    public final String getName() {
        return name;
    }
    public final Date getBirthDate() {
        return birthDate;
    }
}

public final class FeedBack {
    private final int idUser;
    private final int idAdvertisement;
    private final int rate;
    private final String text;

    public FeedBack(final int idUser, final int idAdvertisement, final int rate, final String text) {
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

public final class Advertisement {
    private final int idAdvertisement;
    private final int idUser;
    private final int idType;
    private final String description;
    private final int score;
    private final int price;
    private final int numTotItem;
    private final Timestamp dateStart;
    private final Timestamp dateEnd;

    public Advertisement(final int idAdvertisement, final int idUser, final int idType, final String description,
                         final int score, final int price, final int numTotItem, final Timestamp dateStart,
                         final Timestamp dateEnd) {
        this.idAdvertisement = idAdvertisement;
        this.idUser = idUser;
        this.idType = idType;
        this.description = description;
        this.score = score;
        this.price = price;
        this.numTotItem = numTotItem;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }
    public final int getIdAdvertisement() {
        return idAdvertisement;
    }
    public final int getIdUser() {
        return idUser;
    }
    public final int getIdType() {
        return idType;
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
    public final Timestamp getDateStart() {
        return dateStart;
    }
    public final Timestamp getDateEnd() {
        return dateEnd;
    }
}

public final class TypeAdvertisement {
    private final int idType;
    private final String name;

    public TypeAdvertisement(final int idType, final String name) {
        this.idType = idType;
        this.name = name;
    }
    public final int getIdType() {
        return idType;
    }
    public final String getName() {
        return name;
    }
}

public final class Booking {
    private final int idUser;
    private final int idAdvertisement;
    private final Timestamp dateTime;
    private final int numBooking;

    public Booking(final int idUser, final int idAdvertisement, final Timestamp dateTime, final int numBooking) {
        this.idUser = idUser;
        this.idAdvertisement = idAdvertisement;
        this.dateTime = dateTime;
        this.numBooking = numBooking;
    }
    public final int getIdUser() {
        return idUser;
    }
    public final int getIdAdvertisement() {
        return idAdvertisement;
    }
    public final Timestamp getDateTime() {
        return dateTime;
    }
    public final int getNumBooking() {
        return numBooking;
    }
}