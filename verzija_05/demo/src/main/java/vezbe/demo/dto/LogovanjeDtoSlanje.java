package vezbe.demo.dto;

import vezbe.demo.model.Korisnik;

import javax.servlet.http.HttpSession;

public class LogovanjeDtoSlanje {

    private Korisnik korisnik;
    private String uloga;
    //private String sesijaId;
    private HttpSession sesija;

    public LogovanjeDtoSlanje() {
    }

    public LogovanjeDtoSlanje(Korisnik korisnik, String uloga, HttpSession sesija) {
        this.korisnik = korisnik;
        this.uloga = uloga;
        this.sesija = sesija;
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

    public HttpSession getSesija() {
        return sesija;
    }

    public void setSesija(HttpSession sesija) {
        this.sesija = sesija;
    }
}
