package com.traduciendoelderecho.converter.utils;

import com.google.gson.Gson;
import com.traduciendoelderecho.converter.models.entities.IPublication;
import com.traduciendoelderecho.converter.models.entities.Paragraph;
import com.traduciendoelderecho.converter.models.entities.Publication;
import com.traduciendoelderecho.converter.models.entities.XWFParagraphPublication;
import javafx.stage.FileChooser;
import lombok.SneakyThrows;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConverterUtil {
    // todo : adecuar con patroes como factory o stregy para aceptar nuevos formatos de publiacion
    private ConverterUtil() {
    }

    public static void writePublicationJsonInFile(IPublication publication, String carpeta, String fileName) throws IOException {

        // TODO : ESTO FALLA EN UBUNTO GENERA UNA CARPETA EN LA RUTA BASE DEL USUARIO
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(carpeta + "\\" + ValidateStringUtil.getNameWithOutExtension(fileName) + ".json"),
                StandardCharsets.UTF_8));
        new Gson().toJson(publication, bufferedWriter);
        bufferedWriter.close();
    }

    public static Optional<File> selectAndGetFile() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Documents", "*.docx"));
        return Optional.ofNullable(fc.showOpenDialog(null));
    }


    @SneakyThrows
    public static String[] processWordFile(File file, Publication publication) {

        String[] nombreArchivoAndCarpeta = new String[2];
        nombreArchivoAndCarpeta[0] = file.getName();
        nombreArchivoAndCarpeta[1] = file.getParent();

        XWPFDocument document = getDocument(file);

        document.getParagraphs()
                .stream()
                .filter(xwpfParagraph -> !xwpfParagraph.isEmpty())
                .map(xwpfParagraph -> new XWFParagraphPublication(xwpfParagraph.getCTP(), xwpfParagraph.getBody()))
                .collect(Collectors.toList())
                .forEach(xwfParagraphPublication -> processParagraph(publication, xwfParagraphPublication));

        document.close();

        return nombreArchivoAndCarpeta;
    }

    private static XWPFDocument getDocument(File file) throws IOException {

        try (FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath())) {

            return new XWPFDocument(fileInputStream);

        } catch (IOException ex) {
            throw new IOException();
        }

    }

    private static void processParagraph(Publication publication, XWFParagraphPublication paragraphPublication) {

        // todo : CREO QUE ESTO SE PUEDE HACER  POR COMPOSICION FUNCIONAL
        String text = paragraphPublication.getText();
        if (paragraphPublication.isTitle()) {

            Optional.ofNullable(publication.getTituloPublicacion())
                    .ifPresentOrElse(tittle -> publication.addSubtituloForTema(text), () -> publication.setTituloPublicacion(text));
        } else {
            if (publication.getTopicList().isEmpty()) publication.addParrafoForintro(new Paragraph(text));
            else publication.addParrafoForTema(text);
        }
        /*
        if (paragraphPublication.isTitle())
            Optional.ofNullable(publication.getTituloPublicacion())
                    .ifPresentOrElse(
                            title -> publication.addSubtituloForTema(paragraphPublication.getText()),
                            () -> publication.setTituloPublicacion(paragraphPublication.getText()));
        else if (publication.getTopicList().isEmpty())
            publication.addParrafoForintro(new Paragraph(paragraphPublication.getText()));
        else
            publication.addParrafoForTema(paragraphPublication.getText());

         */
    }
}
