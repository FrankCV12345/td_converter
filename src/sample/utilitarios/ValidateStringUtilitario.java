package sample.utilitarios;

import sample.models.intities.Dicionario;
import sample.models.intities.Publicacion;

import java.util.List;
import java.util.function.Function;

public class ValidateStringUtilitario {
    private static final int MIN_CARACTERES = 2;
    public static boolean isValid(String cadena){
        return (cadena != null && cadena.length() >= MIN_CARACTERES && cadena.split(" ").length > 0) ? true : false;
    }
    public static String obtenerNombreSinExtencion(String nameFile){
        String[] lettersOfName = nameFile.split("");
        String name = "";
        for (String letra: lettersOfName)
            if(letra.equals(".")) break;
            else name += letra;

        return name;
    }

    public static String searchDefinicionsAndLinks(String  parrafo , List<Dicionario> definicions ){
        for (Dicionario definicion : definicions ) {
                if ( definicion.getTipo().equals(Publicacion.DEFINICIONNAME) )
                    parrafo = parrafo.replaceAll(definicion.getPalabra(),converterToTagSpan.apply(definicion));
                else if( definicion.getTipo().equals(Publicacion.LINKSNAME) )
                    parrafo = parrafo.replaceAll(definicion.getPalabra(),convertierToTagA.apply(definicion));
        }
       return  parrafo;
    }
    private  static Function<Dicionario,String> converterToTagSpan =
            definicion -> "<span onClick='utlitarios.showDefinicion()' data-definicion='"+definicion.getDefinicion()+"' class='para-definir cursor-pointer'>"+definicion.getPalabra()+"</span>";
    private  static Function<Dicionario,String> convertierToTagA =
            definicion -> "<a target='_blank' href='"+definicion.getDefinicion()+"'>"+definicion.getPalabra()+"</a>";

}
