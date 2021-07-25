package sample.models.intities;

import java.util.Optional;

public interface IPublicacion {
    public boolean isValid();
    public Optional<IPublicacion> isValidForConverter() throws Exception ;
}
