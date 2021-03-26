package it.unipd.dei.yourwaytoitaly.resource;

import java.sql.Time;
import java.util.Date;
import java.util.Calendar;

/**
 * Class to define the objects which represents the entities in the DB
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

final class City {
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

class User {
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

final class Company extends User {
    private final int idUser;
    private final String denomination;

    public Company(final int idUser, final String email, final String phoneNumber, final String address,
                   final String password, final String userName, final int idCity, final int idUser1,
                   final String denomination) {
        super(idUser, email, phoneNumber, address, password, userName, idCity);
        this.idUser = idUser1;
        this.denomination = denomination;
    }
    public final String getDenomination() {
        return denomination;
    }
}

final class Tourist extends User {
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

class FeedBack {
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

final class Advertisement {
    private final int idAdvertisement;
    private final int idUser;
    private final int idType;
    private final String description;
    private final int score;
    private final int price;
    private final int numTotItem;
    private final Date dateStart;
    private final Date dateEnd;
    private final Time timeStart;
    private final Time timeEnd;

    public Advertisement(final int idAdvertisement, final int idUser, final int idType, final String description,
                         final int score, final int price, final int numTotItem, final Date dateStart,
                         final Date dateEnd, final Time timeStart, final Time timeEnd) {
        this.idAdvertisement = idAdvertisement;
        this.idUser = idUser;
        this.idType = idType;
        this.description = description;
        this.score = score;
        this.price = price;
        this.numTotItem = numTotItem;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
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
}

final class TypeAdvertisement {
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

final class Booking {
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

final class Image {
    private final int idImage;
    private final String path;
    private final int idAdvertisement;

    public Image(final int idImage, final String path, final int idAdvertisement) {
        this.idImage = idImage;
        this.path = path;
        this.idAdvertisement = idAdvertisement;
    }
    public final int getIdImage() {
        return idImage;
    }
    public final String getPath() {
        return path;
    }
    public final int getIdAdvertisement() {
        return idAdvertisement;
    }
}