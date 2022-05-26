package vezbe.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vezbe.demo.model.Artikal;
import vezbe.demo.model.PorudzbinaArtikal;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PregledPorudzbineDto {

    private List<ArtikalZaPregledPorudzbineDto> artikliZaPregledPorudzbineDtos;

    private BigDecimal ukupnaCena;

    private BigDecimal cena_sa_popustom;

}
