package sample.utilitarios;

import javafx.scene.control.*;
import sample.models.intities.Autor;
import sample.models.intities.Dicionario;
import sample.models.intities.Publicacion;

import java.util.List;
import java.util.function.Consumer;

public class FormUtilitarios {
    private static Consumer<List<ListView>> removeItemsListView =
            l -> l.forEach(listView -> listView.getItems().clear());

    private static Consumer<List<ComboBox>> clearSelectionCombos =
            comboxs -> comboxs.forEach(comboBox -> comboBox.getSelectionModel().clearSelection());

    private static Consumer<List<TextInputControl>> clearTextFields =
            textInputControls -> textInputControls.forEach(textInputControl -> textInputControl.setText(""));

    public static void clearAllForm(List<TextInputControl> TextInputControls, List<ComboBox> Combos, List<ListView> listViews) {
        removeItemsListView.accept(listViews);
        clearSelectionCombos.accept(Combos);
        clearTextFields.accept(TextInputControls);

    }

    public static void deleteItem(Publicacion publicacion, ListView listView, Alert confirmation, String NAMELIST) {
        int index = listView.getSelectionModel().getSelectedIndex();
        if (index != -1) {
            final String SelectedItem = (String) listView.getSelectionModel().getSelectedItem();
            AlertUtilitario.showAlert("Desea Eliminar este item ?", "Confirme que quiere  eliminar : " + SelectedItem, confirmation);
            if (confirmation.getResult() == ButtonType.OK) {
                listView.getItems().remove(index);
                publicacion.removeItem(index, NAMELIST);
                listView.getSelectionModel().clearSelection();
            }
        }
    }

    public static void addDefinicion(Publicacion publicacion, ListView lstView, List<TextField> textFieldList, Dicionario definicion) {
        textFieldList.forEach(t -> t.setText(""));
        lstView.getItems().add(definicion.getPalabra() + " : " + definicion.getDefinicion());
        publicacion.addDefinicion(definicion);
    }

    public static void addAutor(Autor autor, ListView lstView, Publicacion publicacion, TextField textField) {
        publicacion.addAutores(autor);
        lstView.getItems().add(autor.getNombre());
        textField.setText("");
    }
}
