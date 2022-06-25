package vezbe.demo.dto;
import java.math.BigDecimal;

public class ArtikalZaPretragaArtikalPoIdDto {

    private String naziv;
    private BigDecimal cena;
    private String opis;

    public ArtikalZaPretragaArtikalPoIdDto() {
    }

    public ArtikalZaPretragaArtikalPoIdDto(String naziv, BigDecimal cena, String opis) {
        this.naziv = naziv;
        this.cena = cena;
        this.opis = opis;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public void setCena(BigDecimal cena) {
        this.cena = cena;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }


}
