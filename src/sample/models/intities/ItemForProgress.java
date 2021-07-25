package sample.models.intities;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ItemForProgress {
    private final DoubleProperty quantity  =  new SimpleDoubleProperty();
    public final double getQuantity(){
        return this.quantity.get();
    }
    public final void setQuantityI(double value){
        this.quantity.set(value);
    }
    public final DoubleProperty quantityProperty(){
        return quantity;
    }
}
