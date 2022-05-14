package vezbe.demo.dto;

import vezbe.demo.model.Restoran;

public class KreiranjeRestoranaDto {

    private String naziv;
    private String tip;

    public KreiranjeRestoranaDto() {
    }

    public KreiranjeRestoranaDto(String naziv, String tip) {
        this.naziv = naziv;
        this.tip = tip;
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

    public Restoran PrebaciURestoran()
    {
        return new Restoran(naziv, tip);
    }
}
