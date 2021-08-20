package com.traduciendoelderecho.converter.models.entities;

import com.traduciendoelderecho.converter.models.exceptions.PublicationInvalidException;

import java.util.Optional;

public interface IPublication {
    boolean isValid();
    Optional<IPublication> isValidForConverter() throws PublicationInvalidException;
}
