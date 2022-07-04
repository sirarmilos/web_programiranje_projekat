package vezbe.demo.dto;

import vezbe.demo.model.Komentar;

public class KoJeDaoKomentarDto {

    private String korisnickoIme;
    private Komentar.Ocena ocena;
    private String tekstKomentara;

    public KoJeDaoKomentarDto() {
    }

    public KoJeDaoKomentarDto(String korisnickoIme, Komentar.Ocena ocena, String tekstKomentara) {
        this.korisnickoIme = korisnickoIme;
        this.ocena = ocena;
        this.tekstKomentara = tekstKomentara;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public Komentar.Ocena getOcena() {
        return ocena;
    }

    public void setOcena(Komentar.Ocena ocena) {
        this.ocena = ocena;
    }

    public String getTekstKomentara() {
        return tekstKomentara;
    }

    public void setTekstKomentara(String tekstKomentara) {
        this.tekstKomentara = tekstKomentara;
    }
}
