package it.unipd.dei.yourwaytoitaly.resource;

/**
 * Abstract class to define the objects which represents a User
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public abstract class User {
    protected String email;
    protected String password;
    protected final String name;
    protected String address;
    protected String phoneNumber;
    protected int idCity;

    public User(final String email, final String password, final String name, final String address,
                final String phoneNumber, final int idCity) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.idCity = idCity;
    }

    public abstract String getEmail();
    public abstract String getPassword();
    public abstract String getName();
    public abstract String getAddress();
    public abstract String getPhoneNumber();
    public abstract int getIdCity();
    public abstract String getType();

}
