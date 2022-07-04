package vezbe.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class VratiKupcaDto {

    private String korisnickoIme;
    private String lozinka;
    private String ime;
    private String prezime;
    private String pol;
    private LocalDate datumRodjenja;
    private String tipKupca;
    private BigDecimal brojSkupljenihPoena;

    public VratiKupcaDto() {
    }

    public VratiKupcaDto(String korisnickoIme, String lozinka, String ime, String prezime, String pol, LocalDate datumRodjenja, String tipKupca, BigDecimal brojSkupljenihPoena) {
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
        this.pol = pol;
        this.datumRodjenja = datumRodjenja;
        this.tipKupca = tipKupca;
        this.brojSkupljenihPoena = brojSkupljenihPoena;
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

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getTipKupca() {
        return tipKupca;
    }

    public void setTipKupca(String tipKupca) {
        this.tipKupca = tipKupca;
    }

    public BigDecimal getBrojSkupljenihPoena() {
        return brojSkupljenihPoena;
    }

    public void setBrojSkupljenihPoena(BigDecimal brojSkupljenihPoena) {
        this.brojSkupljenihPoena = brojSkupljenihPoena;
    }
}
