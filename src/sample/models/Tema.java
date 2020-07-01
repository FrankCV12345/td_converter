package sample.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tema {
    private String subtitulo;
    private List<Parrafo> parrafos = new ArrayList<>();
    public Tema(String subtitulo){
        this.subtitulo = subtitulo;
    }
}
