package vezbe.demo.dto;

import vezbe.demo.model.Porudzbina;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class IspisPojedinacnePorudzbineDto {

    private String naziv;
    private BigDecimal cena;
    private String opis;
    private int kolicina;

    private LocalDateTime datumVreme;
    private BigDecimal ukupnaCena;
    private Porudzbina.Status status;

    public IspisPojedinacnePorudzbineDto() {
    }

    public IspisPojedinacnePorudzbineDto(String naziv, BigDecimal cena, String opis, int kolicina, LocalDateTime datumVreme, BigDecimal ukupnaCena, Porudzbina.Status status) {
        this.naziv = naziv;
        this.cena = cena;
        this.opis = opis;
        this.kolicina = kolicina;
        this.datumVreme = datumVreme;
        this.ukupnaCena = ukupnaCena;
        this.status = status;
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

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public LocalDateTime getDatumVreme() {
        return datumVreme;
    }

    public void setDatumVreme(LocalDateTime datumVreme) {
        this.datumVreme = datumVreme;
    }

    public BigDecimal getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(BigDecimal ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }

    public Porudzbina.Status getStatus() {
        return status;
    }

    public void setStatus(Porudzbina.Status status) {
        this.status = status;
    }
}
