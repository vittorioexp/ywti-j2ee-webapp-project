package it.unipd.dei.yourwaytoitaly.resource;

import java.sql.Date;

/**
 * Class to define the objects which represents the Tourist in the DB
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public final class Tourist extends User {
    private final String surname;
    private final Date birthDate;

    public Tourist(final String email, final String password, final String name, final String address,
                   final String phoneNumber, final int idCity, final String surname, final Date birthDate) {
        super(email, password, name, address, phoneNumber, idCity);
        this.surname = surname;
        this.birthDate = birthDate;
    }

    @Override
    public final String getEmail() {
        return email;
    }

    @Override
    public final String getPassword() {
        return password;
    }

    @Override
    public final String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public final String getAddress() {
        return address;
    }

    @Override
    public final int getIdCity() {
        return idCity;
    }

    @Override
    public final String getType() {
        return new String("Tourist");
    }

    public final String getName() {
        return name;
    }

    public final String getSurname() {
        return surname;
    }

    public final Date getBirthDate() {
        return birthDate;
    }
}
