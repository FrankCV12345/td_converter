package sample.utilitarios;
import com.google.gson.Gson;
import javafx.stage.FileChooser;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import sample.models.*;
import sample.models.ExceptionsModels.FileException;
import sample.models.ExceptionsModels.PublicationInvalidException;

import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConverterUtilitarios {
    public static void WordToPublicationObject(Stream<XWFParagraphPublication> paragraphList,Publicacion publicacion){
        paragraphList
                .filter( paragraph -> !paragraph.isEmpty())
                .map(paragraph -> processParagraph(publicacion,paragraph))
                .collect(Collectors.toList());
    }

    private static Publicacion processParagraph(Publicacion publicacion,XWFParagraphPublication paragraphPublication){
        // CREO QUE ESTO SE PUEDE HACER  POR COMPOSICION FUNCIONAL
        String texto = paragraphPublication.getText();
        if(paragraphPublication.isTitle()){
            if(publicacion.getTituloPublicacion() == null) publicacion.setTituloPublicacion(texto);
            else publicacion.addSubtituloForTema(texto);
        }else{
            if(publicacion.getTemas().size() == 0) publicacion.addParrafoForintro(new Parrafo(texto));
            else publicacion.addParrafoForTema(texto);
        }
        return  publicacion;
    }

    public static void converterToFileJson(IPublicacion publicacion , String carpeta , String nombreArchivo , Gson gson) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(carpeta+"\\"+ValidateStringUtilitario.obtenerNombreSinExtencion(nombreArchivo)+".json"), "UTF-8"
        ));
        gson.toJson(publicacion,out);
        out.close();
    }

    public static String[] processWord(Publicacion publicacion) throws FileException, PublicationInvalidException, IOException {

        if(publicacion.isValid()){
            FileChooser fc =  new FileChooser();
            fc.getExtensionFilters().addAll( new FileChooser.ExtensionFilter("Documents","*.docx"));
            File fileSelected = fc.showOpenDialog(null);
            String[] nombreArchivoAndCarpeta = new String[2];
            if(fileSelected != null){
                FileInputStream fileInputStream = null;
                nombreArchivoAndCarpeta[0] = fileSelected.getName();
                fileInputStream = new FileInputStream(fileSelected.getAbsolutePath());
                XWPFDocument document =  new XWPFDocument(fileInputStream);
                document.getTables();
                Stream<XWFParagraphPublication> paragraphList = document.getParagraphs().stream()
                        .map( elem -> new XWFParagraphPublication( elem.getCTP(),elem.getBody() ) );

                WordToPublicationObject(paragraphList,publicacion);

                nombreArchivoAndCarpeta[1] = fileSelected.getParent();

                document.close();
                return  nombreArchivoAndCarpeta;
            }else{
                throw new FileException("No seleciono ningun archivo");
            }
        }else {
            throw new PublicationInvalidException("Antes de subir el word debe ingresar todos los campos marcados con *");
        }

    }
}
