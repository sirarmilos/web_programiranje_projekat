package vezbe.demo.dto;

public class PregledPodatakaDto {

    private String korisnickoIme;

    public PregledPodatakaDto() {
    }

    public PregledPodatakaDto(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }
}
