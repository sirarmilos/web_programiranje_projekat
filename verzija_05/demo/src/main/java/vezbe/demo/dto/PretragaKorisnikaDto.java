package vezbe.demo.dto;

public class PretragaKorisnikaDto {

    private String ime;
    private String prezime;
    private String korisnickoIme;

    public PretragaKorisnikaDto() {
    }

    public PretragaKorisnikaDto(String ime, String prezime, String korisnickoIme) {
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
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

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }
}
