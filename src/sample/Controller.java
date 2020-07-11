package sample;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import sample.models.*;
import sample.utilitarios.FormUtilitarios;
import static sample.utilitarios.FormUtilitarios.*;
import static sample.utilitarios.ConverterUtilitarios.*;
import static sample.utilitarios.AlertUtilitario.*;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class Controller implements Initializable {

    //BUTTONS
    @FXML
    Button btnUploadWord;

    //INPUTS
    @FXML
    TextField inputNombreImg;
    @FXML
    TextField inputCategoria;
    @FXML
    TextField inputAutores;
    @FXML
    TextField inputKeyDefinicion;
    @FXML
    TextField inputDefinicion;
    @FXML
    TextField inputKeyUrl;
    @FXML
    TextField inputUrl;

    //LISTVIEWS
    @FXML
    ListView lstViewAutores;
    @FXML
    ListView lstViewDefiniciones;
    @FXML
    ListView lstViewLinks;

    @FXML
    ComboBox<String> comboTiposPublicacion = new ComboBox<>();
    @FXML
    ProgressIndicator progresIndicator;
    //contendores
    @FXML
    AnchorPane anchorContenerdorProgress;

    ItemForProgress itemForProgress = new ItemForProgress();

    //ALERTS
    Alert alertError = new Alert(Alert.AlertType.ERROR);
    Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
    Alert confirmation =  new Alert(Alert.AlertType.CONFIRMATION);

    //HyperLink
    @FXML
    Hyperlink MySite;

    ObservableList<String> itemsTipoPublicacion = FXCollections.observableArrayList("Tradutema","Traduarticulo","Traduley","Traduinspiracion");
    private static Publicacion publicacion = new Publicacion();
    private static String carpeta = "";
    private static String nombreArchivo =  "";
    Gson gson = new Gson();
    public void UploadWord(MouseEvent event)  {
        String[] nombreAndCarpeta = new String[0];
        try {
            anchorContenerdorProgress.setVisible(true);
            nombreAndCarpeta = processWord(publicacion);
            nombreArchivo = nombreAndCarpeta[0];
            carpeta = nombreAndCarpeta[1];
        } catch (Exception e) {
            showAlert("Error", e.getMessage(), alertError);
        }finally {
            anchorContenerdorProgress.setVisible(false);
        }
    }
    public void converter(MouseEvent event){
        try {
            anchorContenerdorProgress.setVisible(true);
            publicacion.isValidForConverter().ifPresent(convert);
        } catch (Exception e) {
            showAlert("Error",e.getMessage(),alertError);
        }finally {
            anchorContenerdorProgress.setVisible(false);
        }
    }
    public Consumer<IPublicacion> convert = (p) -> {
        try {
            converterToFileJson(p,carpeta,nombreArchivo,gson);
            publicacion = new Publicacion();
            showAlert("Proceso terminado","Archivo procesado puede encontrar el archivo .Json en : "+carpeta,alertInformation);
            clearAllForm(
                    Arrays.asList(inputNombreImg,inputCategoria,inputAutores,inputKeyDefinicion,inputDefinicion,inputKeyUrl,inputUrl),
                    Arrays.asList(comboTiposPublicacion),
                    Arrays.asList(lstViewAutores,lstViewDefiniciones,lstViewLinks)
            );
        } catch (IOException e) {
            showAlert("Error interno",e.getMessage(),alertError);
        }
    };
    public void  addAdutors(MouseEvent event){
        new Autor(inputAutores.getText()).isValidOptional().ifPresent( autor -> addAutor(autor,lstViewAutores,publicacion,inputAutores));
    }
    public void  addDefinicion(MouseEvent event){
        new Dicionario(inputKeyDefinicion.getText(),inputDefinicion.getText(),Publicacion.DEFINICIONNAME).isValidOpcional().ifPresent( definition -> FormUtilitarios.addDefinicion(publicacion,lstViewDefiniciones,Arrays.asList(inputKeyDefinicion,inputDefinicion),definition));
    }
    public void addLink(MouseEvent event){
        new Dicionario(inputKeyUrl.getText(),inputUrl.getText(),Publicacion.LINKSNAME).isValidOpcional().ifPresent(link -> FormUtilitarios.addDefinicion(publicacion,lstViewLinks,Arrays.asList(inputKeyUrl,inputUrl),link));
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboTiposPublicacion.setItems(itemsTipoPublicacion);

        inputNombreImg.textProperty()
                .addListener( (obs,oldText,newText) -> publicacion.setImg(newText));

        inputCategoria.textProperty()
                .addListener( (obs,oldTex,newText)-> publicacion.getTipo().setCategoria(newText) );

        comboTiposPublicacion.getSelectionModel().
                selectedItemProperty().
                addListener((options, value, newValue)-> publicacion.getTipo().setTipoPublicacion(newValue));

        itemForProgress.setQuantityI(0);
        itemForProgress.quantityProperty().addListener((observable,oldValue,newValue) -> progresIndicator.progressProperty().bind(itemForProgress.quantityProperty()));

        lstViewAutores.addEventHandler(MouseEvent.MOUSE_CLICKED,(e) -> FormUtilitarios.deleteItem(publicacion,lstViewAutores,confirmation,Publicacion.AUTORSNNAME));
        lstViewDefiniciones.addEventHandler(MouseEvent.MOUSE_CLICKED,(e) -> FormUtilitarios.deleteItem(publicacion,lstViewDefiniciones,confirmation,Publicacion.DEFINICIONNAME));
        lstViewLinks.addEventHandler(MouseEvent.MOUSE_CLICKED,(e) -> FormUtilitarios.deleteItem(publicacion,lstViewLinks,confirmation,Publicacion.DEFINICIONNAME));

        MySite.addEventHandler(MouseEvent.MOUSE_CLICKED,(e) -> {
            if(Desktop.isDesktopSupported())
            {
                try {
                    Desktop.getDesktop().browse(new URI("https://frankcv12345.github.io/"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
