package sample.utilitarios;
import com.google.gson.Gson;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import sample.models.*;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConverterUtilitarios {
    public static void WordToPublicationObject(List<XWFParagraphPublication> paragraphList, ItemForProgress itemProgress,Publicacion publicacion){
        //TAREAAA!! HACER ESTO CON PROGRAMACION FUNCIONAL
        List<Tema> temas =  new ArrayList<>();
        List<Parrafo> intro = new ArrayList<>();
        Integer cont = 0;
        /*
        paragraphList.stream()
                .filter( paragraph -> paragraph.getText().length() > 0)
                .map(paragraph ->{});
        */
        for ( XWFParagraphPublication xwpfParagraph : paragraphList) {
            cont++;
            String FormatNum  = xwpfParagraph.getNumFmt();
            BigInteger Ilvl  = xwpfParagraph.getNumIlvl();
            String NumLvlText  = xwpfParagraph.getNumLevelText();
            String paragraph = xwpfParagraph.getText();

            if(FormatNum == null && Ilvl == null  && NumLvlText == null && paragraph.length() > 0){
                if(temas.size() == 0) {
                    intro.add(new Parrafo(paragraph));
                } else {
                    paragraph = ValidateStringUtilitario.searchDefinicionsAndLinks(paragraph,publicacion.getDefiniciones());
                    temas.get(temas.size() -1).getParrafos().add(new Parrafo(paragraph));
                }
            }else if(paragraph.length() > 0){
                if(publicacion.getTituloPublicacion() == null){
                    publicacion.setTituloPublicacion(paragraph);
                }else{
                    Tema newTema = new Tema();
                    newTema.setSubtitulo(paragraph);
                    temas.add(newTema);
                }
            }

            itemProgress.setQuantityI(itemProgress.getQuantity() + (cont/paragraphList.size()));
        }
        publicacion.setIntroducion(intro);
        publicacion.setTemas(temas);
    }

    public static void converterToFileJson(Publicacion publicacion , String carpeta , String nombreArchivo , Gson gson) throws IOException {
            FileWriter file = null;
            file = new FileWriter(carpeta+"\\"+ValidateStringUtilitario.recortName(nombreArchivo)+".json");
            gson.toJson(publicacion,file);
            file.close();
    }


    public static String[] processWord(Publicacion p, AnchorPane anchorContenerdorProgress, ItemForProgress itemForProgress) throws Exception {
        FileChooser fc =  new FileChooser();
        fc.getExtensionFilters().addAll( new FileChooser.ExtensionFilter("Documents","*.docx"));
        File fileSelected = fc.showOpenDialog(null);
        String[] nombreArchivoAndCarpeta = new String[2];
        if(fileSelected != null){
            FileInputStream fileInputStream = null;
            anchorContenerdorProgress.setVisible(true);
            nombreArchivoAndCarpeta[0] = fileSelected.getName();
            fileInputStream = new FileInputStream(fileSelected.getAbsolutePath());
            XWPFDocument document =  new XWPFDocument(fileInputStream);
            List<XWFParagraphPublication> paragraphList = document.getParagraphs().stream()
                    .map( elem -> new XWFParagraphPublication( elem.getCTP(),elem.getBody() ) )
                    .collect(Collectors.toList());
            WordToPublicationObject(paragraphList,itemForProgress,p);
            nombreArchivoAndCarpeta[1] = fileSelected.getParent();
            document.close();
            anchorContenerdorProgress.setVisible(false);
            return  nombreArchivoAndCarpeta;
        }else{
            throw new Exception("No seleciono ningun archivo");
        }
    }




}
