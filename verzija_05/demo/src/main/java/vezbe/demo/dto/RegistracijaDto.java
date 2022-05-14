package vezbe.demo.dto;

import vezbe.demo.model.Kupac;

public class RegistracijaDto {

    private String korisnickoIme;
    private String lozinka;
    private String ime;
    private String prezime;

    public RegistracijaDto() {
    }

    public RegistracijaDto(String korisnickoIme, String lozinka, String ime, String prezime) {
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
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

    public Kupac PrebaciUKupca()
    {
        return new Kupac(korisnickoIme, lozinka, ime, prezime);
    }
}
