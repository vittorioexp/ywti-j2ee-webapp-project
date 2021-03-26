package it.unipd.dei.yourwaytoitaly.resource;

import java.sql.Date;

/**
 * Class to define the objects which represents the Tourist in the DB
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public final class Tourist extends User {
    //private final int idUser;
    private final String surname;
    private final String name;
    private final Date birthDate;

    public Tourist(int idUser, String email, String phoneNumber, String address, String password, String userName,
                   int idCity, String surname, String name, Date birthDate) {
        super(idUser, email, phoneNumber, address, password, userName, idCity);
        this.surname = surname;
        this.name = name;
        this.birthDate = birthDate;
    }


    @Override
    public int getIdUser() {
        return idUser;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public int getIdCity() {
        return idCity;
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
