package it.unipd.dei.yourwaytoitaly.resource;

/**
 * Class to define the objects which represents the User in the DB
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public abstract class User {
    protected int idUser;
    protected String email;
    protected String phoneNumber;
    protected String address;
    protected String password;
    protected String userName;
    protected int idCity;

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
    public abstract int getIdUser();
    public abstract String getEmail();
    public abstract String getPhoneNumber();
    public abstract String getAddress();
    public abstract String getPassword();
    public abstract String getUserName();
    public abstract int getIdCity();

}
