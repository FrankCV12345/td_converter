package com.traduciendoelderecho.converter.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Topic {
    private String subtitulo;
    private List<Paragraph> paragraphs = new ArrayList<>();
    public Topic(String subtitulo){
        this.subtitulo = subtitulo;
    }
}
