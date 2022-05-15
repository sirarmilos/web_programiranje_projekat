package vezbe.demo.dto;

import vezbe.demo.model.Lokacija;

public class PretragaRestoranaDto {

    private String naziv;
    private String tip;
    private Lokacija lokacija;

    public PretragaRestoranaDto() {
    }

    public PretragaRestoranaDto(String naziv, String tip, Lokacija lokacija) {
        this.naziv = naziv;
        this.tip = tip;
        this.lokacija = lokacija;
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

    public Lokacija getLokacija() {
        return lokacija;
    }

    public void setLokacija(Lokacija lokacija) {
        this.lokacija = lokacija;
    }
}
