package com.traduciendoelderecho.converter.models.entities;

import com.traduciendoelderecho.converter.utils.ValidateStringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Definition {
    private String palabra;
    private String definicion;
    private String tipo;

    private boolean isValid() {
        return ValidateStringUtil.isValid(this.palabra) && ValidateStringUtil.isValid(this.definicion);
    }

    public Optional<Definition> isValidOptional() {
        return (this.isValid()) ? Optional.of(this) : Optional.empty();
    }

}
