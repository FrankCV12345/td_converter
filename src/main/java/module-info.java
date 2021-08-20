module com.traduciendoelderecho.converter {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires static lombok;
    requires poi.ooxml;
    requires poi.ooxml.schemas;
    requires com.google.gson;

    opens com.traduciendoelderecho.converter to javafx.fxml;
    exports com.traduciendoelderecho.converter;
}