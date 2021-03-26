package it.unipd.dei.yourwaytoitaly.resource;

/**
 * Class to define the objects which represents the City in the DB
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