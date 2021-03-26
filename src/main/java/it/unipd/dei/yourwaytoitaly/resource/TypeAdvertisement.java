package it.unipd.dei.yourwaytoitaly.resource;

/**
 * Class to define the objects which represents the TypeAdvertisement in the DB
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

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
