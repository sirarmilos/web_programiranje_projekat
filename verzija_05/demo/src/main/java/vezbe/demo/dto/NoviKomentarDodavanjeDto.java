package vezbe.demo.dto;

public class NoviKomentarDodavanjeDto {

    private String korisnickoIme;
    private Long restoran_id;
    private String tekstKomentara;
    private String ocena;

    public NoviKomentarDodavanjeDto() {
    }

    public NoviKomentarDodavanjeDto(String korisnickoIme, Long restoran_id, String tekstKomentara, String ocena) {
        this.korisnickoIme = korisnickoIme;
        this.restoran_id = restoran_id;
        this.tekstKomentara = tekstKomentara;
        this.ocena = ocena;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public Long getRestoran_id() {
        return restoran_id;
    }

    public void setRestoran_id(Long restoran_id) {
        this.restoran_id = restoran_id;
    }

    public String getTekstKomentara() {
        return tekstKomentara;
    }

    public void setTekstKomentara(String tekstKomentara) {
        this.tekstKomentara = tekstKomentara;
    }

    public String getOcena() {
        return ocena;
    }

    public void setOcena(String ocena) {
        this.ocena = ocena;
    }
}
