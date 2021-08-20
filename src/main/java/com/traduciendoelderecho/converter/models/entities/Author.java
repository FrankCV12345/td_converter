package com.traduciendoelderecho.converter.models.entities;

import com.traduciendoelderecho.converter.utils.ValidateStringUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    private String name;

    public Optional<Author> isValidOptional() {
        return (ValidateStringUtil.isValid(this.name)) ? Optional.of(this) : Optional.empty();
    }
}
