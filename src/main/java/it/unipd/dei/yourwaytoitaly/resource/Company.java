package it.unipd.dei.yourwaytoitaly.resource;

/**
 * Class to define the objects which represents the Company in the DB
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public final class Company extends User {
    //private final int idUser;
    private final String denomination;

    public Company(int idUser, String email, String phoneNumber, String address, String password,
                   String userName, int idCity, String denomination) {
        super(idUser, email, phoneNumber, address, password, userName, idCity);
        this.denomination = denomination;
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

    public String getDenomination() {
        return denomination;
    }
}