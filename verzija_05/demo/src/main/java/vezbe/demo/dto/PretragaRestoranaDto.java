package vezbe.demo.dto;

public class PretragaRestoranaDto {

    private String naziv;
    private String tip;
    private String adresa;

    public PretragaRestoranaDto() {
    }

    public PretragaRestoranaDto(String naziv, String tip, String adresa) {
        this.naziv = naziv;
        this.tip = tip;
        this.adresa = adresa;
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

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
}
