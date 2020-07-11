package sample.utilitarios;

import sample.models.Dicionario;
import sample.models.Publicacion;

import java.util.List;
import java.util.function.Function;

public class ValidateStringUtilitario {
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
