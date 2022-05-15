package vezbe.demo.dto;

import vezbe.demo.model.Artikal;
import vezbe.demo.model.Restoran;

import java.math.BigDecimal;

enum Tip{Jelo, Pice}
enum Kolicina{g, ml}

public class DodavanjeNovogArtiklaDto {

    private String naziv;
    private BigDecimal cena;
    private String tip;
    private String opis;
    private String kolicina;

    public DodavanjeNovogArtiklaDto() {
    }

    public DodavanjeNovogArtiklaDto(String naziv, BigDecimal cena, String tip) {
        this.naziv = naziv;
        this.cena = cena;
        this.tip = tip;
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

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public void setKolicina(String kolicina) {
        this.kolicina = kolicina;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Artikal PrebaciUArtikal(DodavanjeNovogArtiklaDto dodavanjeNovogArtiklaDto, Restoran restoran)
    {
        return new Artikal(dodavanjeNovogArtiklaDto.naziv, dodavanjeNovogArtiklaDto.cena, dodavanjeNovogArtiklaDto.tip, dodavanjeNovogArtiklaDto.kolicina, dodavanjeNovogArtiklaDto.opis, restoran);
    }
}
