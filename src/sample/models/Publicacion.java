package sample.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sample.utilitarios.ValidateStringUtilitario;

import static sample.utilitarios.ValidateStringUtilitario.searchDefinicionsAndLinks;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Publicacion implements IPublicacion {

    public final static String DEFINICIONNAME ="definiciones";
    public final static String AUTORSNNAME ="autores";
    public final static String LINKSNAME ="links";

    private String tituloPublicacion;
    private String img = "";
    private List<Parrafo> introducion = new ArrayList<>();
    private List<Tema> temas = new ArrayList<>();
    private Tipo tipo = new Tipo();
    private Date fecha = new Date();

    private List<Dicionario> definiciones = new ArrayList<>();
    private List<Autor> autores = new ArrayList<>();

    public void addDefinicion(Dicionario newDefinicion){
        this.definiciones.add(newDefinicion);
    }
    public void addAutores(Autor autor){
        this.autores.add(autor);
    }
    public boolean isValid(){
        boolean imgValid = ValidateStringUtilitario.isValid(this.img);
        boolean categorieValid = ValidateStringUtilitario.isValid(this.tipo.getCategoria());
        return (imgValid && this.tipo.getTipoPublicacion() != null && this.autores.size() > 0 && categorieValid ) ? true : false;
    }
    public Optional<IPublicacion> isValidForConverter() throws Exception {
        boolean estate = (this.isValid() && (this.getTemas().size() > 0) ) ? true : false;
        if(estate) return Optional.of(this);
        throw new Exception("Publicacion no valida");
    }
    public void removeItem(int index , String nameList){
        switch (nameList){
            case DEFINICIONNAME :
                this.definiciones.remove(index);
                break;
            case AUTORSNNAME:
                this.autores.remove(index);
                break;
            default:
                System.out.println("Error");
                break;
        }
    }
    public void addParrafoForintro(Parrafo parrafo){
        this.introducion.add(parrafo);
    }
    public void addParrafoForTema(String parrafoTexto ){
        this.temas.get(this.temas.size() - 1 )
                .getParrafos()
                .add(new Parrafo(searchDefinicionsAndLinks(parrafoTexto,this.definiciones)));
    }
    public void addSubtituloForTema(String subtitulo){
        this.temas.add(new Tema(subtitulo));
    }
}
