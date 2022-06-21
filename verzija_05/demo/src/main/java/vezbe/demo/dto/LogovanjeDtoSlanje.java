package vezbe.demo.dto;

import vezbe.demo.model.Korisnik;

public class LogovanjeDtoSlanje {

    private Korisnik korisnik;
    private String uloga;

    public LogovanjeDtoSlanje() {
    }

    public LogovanjeDtoSlanje(Korisnik korisnik, String uloga) {
        this.korisnik = korisnik;
        this.uloga = uloga;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public String getUloga() {
        return uloga;
    }

    public void setUloga(String uloga) {
        this.uloga = uloga;
    }
}
