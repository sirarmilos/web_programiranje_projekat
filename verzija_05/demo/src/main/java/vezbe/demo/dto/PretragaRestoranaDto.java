package vezbe.demo.dto;

public class PretragaRestoranaDto {

    private Long id;
    private String naziv;
    private String tip;
    private String adresa;

    public PretragaRestoranaDto() {
    }

    public PretragaRestoranaDto(Long id, String naziv, String tip, String adresa) {
        this.id = id;
        this.naziv = naziv;
        this.tip = tip;
        this.adresa = adresa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
