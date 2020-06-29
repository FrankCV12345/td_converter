package sample.utilitarios;

import javafx.scene.control.*;
import sample.models.Autor;
import sample.models.Dicionario;
import sample.models.ExceptionsModels.DefinicionInvalidException;
import sample.models.Publicacion;

import java.util.List;
import java.util.Optional;

public class FormUtilitarios {
    public static void clearTextField(List<TextInputControl> TextInputControls){
        TextInputControls.forEach( TextInputControl -> TextInputControl.setText(""));
    }
    public static  void resetCombo(List<ComboBox> Combos){
        Combos.forEach(comboBox -> comboBox.getSelectionModel().clearSelection());
    }
    public static void  clearListView(List<ListView> listViews){
        listViews.forEach(listView -> listView.getItems().clear());
    }
    public static void clearAllForm(List<TextInputControl> TextInputControls,List<ComboBox> Combos,List<ListView> listViews){
        clearTextField(TextInputControls);
        resetCombo(Combos);
        clearListView(listViews);
    }
    public static void deleteItem(Publicacion publicacion, ListView listView, Alert confirmation,String NAMELIST){
        int index = listView.getSelectionModel().getSelectedIndex();
        if(index != -1){
            final String SelectedItem =(String) listView.getSelectionModel().getSelectedItem();
            AlertUtilitario.showAlert("Desea Eliminar este item ?","Confirme que quiere  eliminar : "+SelectedItem,confirmation);
            if(confirmation.getResult() == ButtonType.OK ){
                listView.getItems().remove(index);
                publicacion.removeItem(index,NAMELIST);
                listView.getSelectionModel().clearSelection();
            }
        }
    }
    public static void add(Publicacion publicacion,ListView lstView,List<TextField> textFieldList,Dicionario definicion){
        textFieldList.forEach( t -> t.setText(""));
        lstView.getItems().add(definicion.getPalabra()+" : "+definicion.getDefinicion());
        publicacion.addDefinicion(definicion);
    }
    public static void addAutor(Autor autor, ListView lstView, Publicacion publicacion, TextField textField){
        publicacion.addAutores(autor);
        lstView.getItems().add(autor.getNombre());
        textField.setText("");
    }
}
