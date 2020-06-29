package sample.utilitarios;

import sample.models.Dicionario;
import sample.models.Publicacion;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateStringUtilitario {
    private final static String LETRAS ="a-záéíóú";
    public static boolean isValid(String cadena){
        if(cadena.length() >= 2 && cadena.length() >= 2 && cadena.split(" ").length > 0 ){
            return  true;
        }else{
            return false;
        }
    }
    public static String recortName(String name){
        String[] arrgname = name.split("");
        String nvoNombre = "";
        for (String letra: arrgname){
            if(letra.equals(".")){
                break;
            }
            nvoNombre += letra;
        }
        return nvoNombre;
    }

    public static String searchDefinicionsAndLinks(String parrafo , List<Dicionario> definicions ){
        for (Dicionario definicion : definicions ) {
                if (definicion.getTipo().equals(Publicacion.DEFINICIONNAME)){
                    parrafo = parrafo.replaceAll(definicion.getPalabra(),converterToTagSpan.apply(definicion));
                }else {
                    parrafo = parrafo.replaceAll(definicion.getPalabra(),convertierToTagA.apply(definicion));
                }
        }
       return  parrafo;
    }
    private  static Function<Dicionario,String> converterToTagSpan = ( definicion ) -> "<span onClick='utlitarios.showDefinicion()' " +
                "data-definicion='"+definicion.getDefinicion()+"' class='para-definir cursor-pointer'>"+definicion.getPalabra()+"</span>";
    private  static Function<Dicionario,String> convertierToTagA = (definicion ) -> "<a target='_blank' href='"+definicion.getDefinicion()+"'>"+definicion.getPalabra()+"</a>";

    /*
    static BiFunction<List<Dicionario>,String,String> search = ( definiciones, palabra ) -> definiciones.stream()
           .filter(defincinion -> defincinion.getPalabra().equalsIgnoreCase(palabra))
           .findFirst()
           .map( d -> converterToTagSpan.apply(d))
           .orElse(palabra);

     */




}
