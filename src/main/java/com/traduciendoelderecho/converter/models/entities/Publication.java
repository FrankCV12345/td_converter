package com.traduciendoelderecho.converter.models.entities;

import com.traduciendoelderecho.converter.models.exceptions.PublicationInvalidException;
import com.traduciendoelderecho.converter.utils.ValidateStringUtil;
import com.traduciendoelderecho.converter.utils.constants.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Publication implements IPublication {

    private String tituloPublicacion;
    private String img = "";
    private List<Paragraph> introducion = new ArrayList<>();
    private List<Topic> topicList = new ArrayList<>();
    private Type type = new Type();
    private Date date = new Date();

    private List<Definition> definitions = new ArrayList<>();
    private List<Author> authors = new ArrayList<>();

    public void addDefinition(Definition newDefinition) {
        this.definitions.add(newDefinition);
    }

    public void addAuthors(Author author) {
        this.authors.add(author);
    }

    public boolean isValid() {
        boolean imgValid = ValidateStringUtil.isValid(this.img);
        boolean categoryIsValid = ValidateStringUtil.isValid(this.type.getCategoria());
        return imgValid && (this.type.getTipoPublicacion() != null) && !this.authors.isEmpty() && categoryIsValid;
    }

    public Optional<IPublication> isValidForConverter() throws PublicationInvalidException {
        boolean estate = this.isValid() && !this.getTopicList().isEmpty();
        if (estate) return Optional.of(this);
        throw new PublicationInvalidException("Publicacion no valida");
    }

    public void removeItem(int index, String nameList) {
        switch (nameList) {
            case Constants.DEFINITION_NAME:
                this.definitions.remove(index);
                break;
            case Constants.AUTHORS_NAME:
                this.authors.remove(index);
                break;
            default:
                System.out.println("Error");
                break;
        }
    }

    public void addParrafoForintro(Paragraph paragraph) {
        this.introducion.add(paragraph);
    }

    public void addParrafoForTema(String parrafoTexto) {
        this.topicList.get(this.topicList.size() - 1)
                .getParagraphs()
                .add(new Paragraph(ValidateStringUtil.searchDefinicionsAndLinks(parrafoTexto, this.definitions)));
    }

    public void addSubtituloForTema(String subtitulo) {
        this.topicList.add(new Topic(subtitulo));
    }
}
