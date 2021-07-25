package sample.models.intities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sample.utilitarios.ValidateStringUtilitario;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dicionario {
    private String palabra;
    private String definicion;
    private String tipo;

    private boolean isValid() {
        return (ValidateStringUtilitario.isValid(this.palabra) && ValidateStringUtilitario.isValid(this.definicion)) ? true : false;
    }

    public Optional<Dicionario> isValidOpcional() {
        return (this.isValid()) ? Optional.of(this) : Optional.empty();
    }

}
