package com.traduciendoelderecho.converter;

import com.traduciendoelderecho.converter.models.entities.*;
import com.traduciendoelderecho.converter.models.exceptions.FileException;
import com.traduciendoelderecho.converter.models.exceptions.PublicationInvalidException;
import com.traduciendoelderecho.converter.utils.AlertUtil;
import com.traduciendoelderecho.converter.utils.ConverterUtil;
import com.traduciendoelderecho.converter.utils.FormUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import static com.traduciendoelderecho.converter.utils.constants.Constants.*;

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
    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);

    //HyperLink
    @FXML
    Hyperlink mySite;

    ObservableList<String> itemsTipoPublicacion = FXCollections.observableArrayList("Tradutema", "Traduarticulo", "Traduley", "Traduinspiracion");
    // todo : refactor para no usar variables estaticas globales
    private static Publication publication = new Publication();
    private static String carpeta = "";
    private static String nombreArchivo = "";


    public void uploadWord(MouseEvent event) {
        String[] nombreAndCarpeta;
        try {
            anchorContenerdorProgress.setVisible(true);

            nombreAndCarpeta = validPublication(publication)
                    .map(this::getFileWordAndProcessPublication)
                    .orElseThrow(() -> new PublicationInvalidException(PUBLICATION_INVALID_MESSAGE));

            nombreArchivo = nombreAndCarpeta[0];
            carpeta = nombreAndCarpeta[1];
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showAlert("Error", e.getMessage(), alertError);
        } finally {
            anchorContenerdorProgress.setVisible(false);
        }
    }

    private Optional<Publication> validPublication(Publication publication) {
        return publication.isValid() ? Optional.of(publication) : Optional.empty();
    }


    @SneakyThrows
    private String[] getFileWordAndProcessPublication(Publication publication) {
        return ConverterUtil.selectAndGetFile()
                .map(file -> ConverterUtil.processWordFile(file, publication))
                .orElseThrow(() -> new FileException(NO_SELECTED_FILE_MESSAGE));
    }

    public void converter(MouseEvent event) {
        try {
            anchorContenerdorProgress.setVisible(true);
            publication.isValidForConverter().ifPresent(convert);
        } catch (Exception e) {
            AlertUtil.showAlert("Error", e.getMessage(), alertError);
        } finally {
            anchorContenerdorProgress.setVisible(false);
        }
    }

    public final Consumer<IPublication> convert = iPublication -> {
        try {
            ConverterUtil.writePublicationJsonInFile(iPublication, carpeta, nombreArchivo);
            publication = new Publication();
            AlertUtil.showAlert("Proceso terminado", "Archivo procesado puede encontrar el archivo .Json en : " + carpeta, alertInformation);
            FormUtils.clearAllForm(
                    Arrays.asList(inputNombreImg, inputCategoria, inputAutores, inputKeyDefinicion, inputDefinicion, inputKeyUrl,
                            inputUrl),
                    Arrays.asList(comboTiposPublicacion),
                    Arrays.asList(lstViewAutores, lstViewDefiniciones, lstViewLinks)
            );
        } catch (IOException e) {
            AlertUtil.showAlert("Error interno", e.getMessage(), alertError);
        }
    };

    public void addAuthors(MouseEvent event) {
        new Author(inputAutores.getText()).isValidOptional().ifPresent(author -> FormUtils.addAuthor(author, lstViewAutores, publication, inputAutores));
    }

    public void addDefinition(MouseEvent event) {
        new Definition(inputKeyDefinicion.getText(), inputDefinicion.getText(), DEFINITION_NAME).isValidOptional().ifPresent(definition -> FormUtils.addDefinition(publication, lstViewDefiniciones, Arrays.asList(inputKeyDefinicion, inputDefinicion), definition));
    }

    public void addLink(MouseEvent event) {
        new Definition(inputKeyUrl.getText(), inputUrl.getText(), LINKS_NAME).isValidOptional().ifPresent(link -> FormUtils.addDefinition(publication, lstViewLinks, Arrays.asList(inputKeyUrl, inputUrl), link));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboTiposPublicacion.setItems(itemsTipoPublicacion);

        inputNombreImg.textProperty()
                .addListener((obs, oldText, newText) -> publication.setImg(newText));

        inputCategoria.textProperty()
                .addListener((obs, oldTex, newText) -> publication.getType().setCategoria(newText));

        comboTiposPublicacion.getSelectionModel().
                selectedItemProperty().
                addListener((options, value, newValue) -> publication.getType().setTipoPublicacion(newValue));

        itemForProgress.setQuantityI(0);
        itemForProgress.quantityProperty().addListener((observable, oldValue, newValue) -> progresIndicator.progressProperty().bind(itemForProgress.quantityProperty()));

        lstViewAutores.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> FormUtils.deleteItem(publication, lstViewAutores, confirmation, AUTHORS_NAME));
        lstViewDefiniciones.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> FormUtils.deleteItem(publication, lstViewDefiniciones, confirmation, DEFINITION_NAME));
        lstViewLinks.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> FormUtils.deleteItem(publication, lstViewLinks, confirmation, DEFINITION_NAME));
        /*
        MySite.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(new URI("https://frankcv12345.github.io/"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });
         */
    }
}