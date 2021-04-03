package it.unipd.dei.yourwaytoitaly.resource;

/**
 * Class to define the objects which represents the Company in the DB
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public final class Company extends User {


    public Company(final String email, final String password, final String address, final String phoneNumber,
                   final int idCity, final String name) {
        super(email, password, name, address, phoneNumber, idCity);
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
        return new String("Company");
    }

    public final String getName() {
        return name;
    }

}