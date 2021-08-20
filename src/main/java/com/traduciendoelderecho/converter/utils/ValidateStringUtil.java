package com.traduciendoelderecho.converter.utils;

import com.traduciendoelderecho.converter.models.entities.Definition;
import com.traduciendoelderecho.converter.utils.constants.Constants;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class ValidateStringUtil {

    private ValidateStringUtil() {
    }

    public static boolean isValid(String string) {
        return Objects.nonNull(string) && string.length() >= Constants.MIN_CHARACTERS && string.split(" ").length > 0;
    }

    public static String getNameWithOutExtension(String nameFile) {

        String[] lettersOfName = nameFile.split(Constants.EMPTY_STRING);
        StringBuilder name = new StringBuilder();

        for (String letter : lettersOfName)
            if (letter.equals(".")) break;
            else name.append(letter);

        return name.toString();
    }

    public static String searchDefinicionsAndLinks(String parrafo, List<Definition> definicions) {
        for (Definition definicion : definicions) {
            if (definicion.getTipo().equals(Constants.DEFINITION_NAME))
                parrafo = parrafo.replaceAll(definicion.getPalabra(), converterToTagSpan.apply(definicion));
            else if (definicion.getTipo().equals(Constants.LINKS_NAME))
                parrafo = parrafo.replaceAll(definicion.getPalabra(), convertierToTagA.apply(definicion));
        }
        return parrafo;
    }

    private static Function<Definition, String> converterToTagSpan =
            definicion -> "<span onClick='utlitarios.showDefinicion()' data-definicion='" + definicion.getDefinicion()
                    + "' class='para-definir cursor-pointer'>" + definicion.getPalabra() + "</span>";
    private static Function<Definition, String> convertierToTagA =
            definicion -> "<a target='_blank' href='" + definicion.getDefinicion() + "'>" + definicion.getPalabra() + "</a>";

}
