package it.unipd.dei.yourwaytoitaly.resource;

/**
 * Class to define the objects which represents the entities in the DB
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */
/*
le date sono timestamp
id = int
num booking = int
num_tot_item = int
state = bool
score = int
denomination = string
email = string
password = string
user_name = string
 */
private final class City {
    private final int idCity;
    private final String idCityName;

    public City(int idCity, String idCityName) {
        this.idCity = idCity;
        this.idCityName = idCityName;
    }
    public int getIdCity() {
        return idCity;
    }
    public String getIdCityName() {
        return idCityName;
    }
}

private final class User {
    private final int idUser;
    private final String email;
    private final String phoneNumber;
    private final String address;
    private final String password;
    private final String userName;
    private final int idCity;

    public User(int idUser, String email, String phoneNumber, String address, String password, String userName, int idCity) {
        this.idUser = idUser;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.password = password;
        this.userName = userName;
        this.idCity = idCity;
    }
    public int getIdUser() {
        return idUser;
    }
    public String getEmail() {
        return email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getAddress() {
        return address;
    }
    public String getPassword() {
        return password;
    }
    public String getUserName() {
        return userName;
    }
    public int getIdCity() {
        return idCity;
    }
}

private final class Company extends User {
    private final int idUser;
    private final String denomination;

    public Company(int idUser, String email, String phoneNumber, String address, String password, String userName,
                   int idCity, int idUser1, String denomination) {
        super(idUser, email, phoneNumber, address, password, userName, idCity);
        this.idUser = idUser1;
        this.denomination = denomination;
    }
    @java.lang.Override
    public int getIdUser() {
        return idUser;
    }
    public String getDenomination() {
        return denomination;
    }
}

private final class Tourist extends User {
    private final int idUser;
    private final String surname;
    private final String name;
    private final Date birthDate;

    public Tourist(int idUser, String email, String phoneNumber, String address, String password, String userName,
                   int idCity, int idUser1, String surname, String name, Date birthDate) {
        super(idUser, email, phoneNumber, address, password, userName, idCity);
        this.idUser = idUser1;
        this.surname = surname;
        this.name = name;
        this.birthDate = birthDate;
    }
    @java.lang.Override
    public int getIdUser() {
        return idUser;
    }
    public String getSurname() {
        return surname;
    }
    public String getName() {
        return name;
    }
    public Date getBirthDate() {
        return birthDate;
    }
}

private final class FeedBack {
    private final int idUser;
    private final int idAdvertisement;
    private final int rate;
    private final String text;

    public FeedBack(int idUser, int idAdvertisement, int rate, String text) {
        this.idUser = idUser;
        this.idAdvertisement = idAdvertisement;
        this.rate = rate;
        this.text = text;
    }
    public int getIdUser() {
        return idUser;
    }
    public int getIdAdvertisement() {
        return idAdvertisement;
    }
    public int getRate() {
        return rate;
    }
    public String getText() {
        return text;
    }
}

private final class Advertisement {
    private final int idAdvertisement;
    private final int idUser;
    private final int idType;
    private final String description;
    private final int score;
    private final int price;
    private final int numTotItem;
    private final Date dateStart;
    private final Date dateEnd;

    public Advertisement(int idAdvertisement, int idUser, int idType, String description, int score, int price,
                         int numTotItem, Date dateStart, Date dateEnd) {
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
    public int getIdAdvertisement() {
        return idAdvertisement;
    }
    public int getIdUser() {
        return idUser;
    }
    public int getIdType() {
        return idType;
    }
    public String getDescription() {
        return description;
    }
    public int getScore() {
        return score;
    }
    public int getPrice() {
        return price;
    }
    public int getNumTotItem() {
        return numTotItem;
    }
    public Date getDateStart() {
        return dateStart;
    }
    public Date getDateEnd() {
        return dateEnd;
    }
}

private final class TypeAdvertisement {
    private final int idType;
    private final String name;

    public TypeAdvertisement(int idType, String name) {
        this.idType = idType;
        this.name = name;
    }
    public int getIdType() {
        return idType;
    }
    public String getName() {
        return name;
    }
}

private final class Booking {
    private final int idUser;
    private final int idAdvertisement;
    private final Date dateTime;
    private final int numBooking;

    public Booking(int idUser, int idAdvertisement, Date dateTime, int numBooking) {
        this.idUser = idUser;
        this.idAdvertisement = idAdvertisement;
        this.dateTime = dateTime;
        this.numBooking = numBooking;
    }
    public int getIdUser() {
        return idUser;
    }
    public int getIdAdvertisement() {
        return idAdvertisement;
    }
    public Date getDateTime() {
        return dateTime;
    }
    public int getNumBooking() {
        return numBooking;
    }
}