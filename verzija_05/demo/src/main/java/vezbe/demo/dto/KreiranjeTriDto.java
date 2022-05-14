package vezbe.demo.dto;

import vezbe.demo.model.Lokacija;
import vezbe.demo.model.Menadzer;
import vezbe.demo.model.Restoran;

import java.math.BigDecimal;

public class KreiranjeTriDto {

    private String korisnickoIme;
    private String lozinka;
    private String ime;
    private String prezime;

    private String naziv;
    private String tip;

    private BigDecimal geografskaDuzina;
    private BigDecimal geografskaSirina;
    private String adresa;

    public KreiranjeTriDto() {
    }

    public KreiranjeTriDto(String korisnickoIme, String lozinka, String ime, String prezime, String naziv, String tip, BigDecimal geografskaDuzina, BigDecimal geografskaSirina, String adresa) {
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
        this.naziv = naziv;
        this.tip = tip;
        this.geografskaDuzina = geografskaDuzina;
        this.geografskaSirina = geografskaSirina;
        this.adresa = adresa;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public BigDecimal getGeografskaDuzina() {
        return geografskaDuzina;
    }

    public void setGeografskaDuzina(BigDecimal geografskaDuzina) {
        this.geografskaDuzina = geografskaDuzina;
    }

    public BigDecimal getGeografskaSirina() {
        return geografskaSirina;
    }

    public void setGeografskaSirina(BigDecimal geografskaSirina) {
        this.geografskaSirina = geografskaSirina;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Lokacija PrebaciULokaciju()
    {
        return new Lokacija(geografskaDuzina, geografskaSirina, adresa);
    }

    public Restoran PrebaciURestoran(Lokacija lokacija)
    {
        return new Restoran(naziv, tip, lokacija);
    }

    public Menadzer PrebaciUMenadzera(Restoran restoran)
    {
        return new Menadzer(korisnickoIme, lozinka, ime, prezime, restoran);
    }
}
