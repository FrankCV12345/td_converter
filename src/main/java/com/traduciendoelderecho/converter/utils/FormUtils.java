package com.traduciendoelderecho.converter.utils;

import com.traduciendoelderecho.converter.models.entities.Author;
import com.traduciendoelderecho.converter.models.entities.Definition;
import com.traduciendoelderecho.converter.models.entities.Publication;
import com.traduciendoelderecho.converter.utils.constants.Constants;
import javafx.scene.control.*;

import java.util.List;
import java.util.function.Consumer;

public class FormUtils {
    
    private FormUtils() {
    }

    private static Consumer<List<ListView>> removeItemsListView =
            listViews -> listViews.forEach(listView -> listView.getItems().clear());

    private static Consumer<List<ComboBox>> clearSelectionCombos =
            comboBoxList -> comboBoxList.forEach(combo -> combo.getSelectionModel().clearSelection());

    private static Consumer<List<TextInputControl>> clearTextFields =
            textInputControls -> textInputControls.forEach(textInputControl -> textInputControl.setText(Constants.EMPTY_STRING));

    public static void clearAllForm(List<TextInputControl> textInputControls, List<ComboBox> comboBoxes, List<ListView> listViews) {
        removeItemsListView.accept(listViews);
        clearSelectionCombos.accept(comboBoxes);
        clearTextFields.accept(textInputControls);

    }

    public static void deleteItem(Publication publication, ListView listView, Alert confirmation, String namelist) {
        int index = listView.getSelectionModel().getSelectedIndex();
        if (index != -1) {
            final String SelectedItem = (String) listView.getSelectionModel().getSelectedItem();
            AlertUtil.showAlert("Desea Eliminar este item ?", "Confirme que quiere  eliminar : " + SelectedItem,
                    confirmation);
            if (confirmation.getResult() == ButtonType.OK) {
                listView.getItems().remove(index);
                publication.removeItem(index, namelist);
                listView.getSelectionModel().clearSelection();
            }
        }
    }

    public static void addDefinition(Publication publication, ListView lstView, List<TextField> textFieldList, Definition definicion) {
        textFieldList.forEach(t -> t.setText(Constants.EMPTY_STRING));
        lstView.getItems().add(definicion.getPalabra() + " : " + definicion.getDefinicion());
        publication.addDefinition(definicion);
    }

    public static void addAuthor(Author author, ListView lstView, Publication publication, TextField textField) {
        publication.addAuthors(author);
        lstView.getItems().add(author.getName());
        textField.setText(Constants.EMPTY_STRING);
    }
}
