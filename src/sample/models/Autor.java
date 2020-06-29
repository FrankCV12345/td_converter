package sample.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sample.utilitarios.ValidateStringUtilitario;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Autor {
    private String nombre;
    public Optional<Autor> isValidOptional(){
        return (ValidateStringUtilitario.isValid(this.nombre)) ? Optional.of(this) : Optional.empty();
    }
}
