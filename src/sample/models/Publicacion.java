package sample.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Publicacion {
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
      if(this.img.length() >= 2 && this.tipo.getTipoPublicacion() != null && this.autores.size() > 0 && this.tipo.getCategoria().length() >= 2){
          return true;
      }  else {
          return false;
      }
    }
    public Optional<Publicacion> isValidForConverter() throws Exception {

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

}
