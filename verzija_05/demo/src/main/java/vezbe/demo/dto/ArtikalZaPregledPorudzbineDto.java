package vezbe.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vezbe.demo.model.PorudzbinaArtikal;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArtikalZaPregledPorudzbineDto {

    private Long id;
    private String naziv;
    private int kolicina;
    private BigDecimal cena;

    public ArtikalZaPregledPorudzbineDto(PorudzbinaArtikal porudzbinaArtikal){
        this.naziv = porudzbinaArtikal.getArtikal().getNaziv();
        this.kolicina = porudzbinaArtikal.getKolicina();
        this.cena = porudzbinaArtikal.getArtikal().getCena();
        this.id = porudzbinaArtikal.getArtikal().getId();
    }

}
