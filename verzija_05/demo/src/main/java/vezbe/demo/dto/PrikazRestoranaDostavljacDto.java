package vezbe.demo.dto;

import java.math.BigDecimal;

public class PrikazRestoranaDostavljacDto {

    private String naziv;
    private String tip;
    private String adresa;
    private BigDecimal gSirina;
    private BigDecimal gDuzina;

    public PrikazRestoranaDostavljacDto() {
    }

    public PrikazRestoranaDostavljacDto(String naziv, String tip, String adresa, BigDecimal gSirina, BigDecimal gDuzina) {
        this.naziv = naziv;
        this.tip = tip;
        this.adresa = adresa;
        this.gSirina = gSirina;
        this.gDuzina = gDuzina;
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

    public BigDecimal getgSirina() {
        return gSirina;
    }

    public void setgSirina(BigDecimal gSirina) {
        this.gSirina = gSirina;
    }

    public BigDecimal getgDuzina() {
        return gDuzina;
    }

    public void setgDuzina(BigDecimal gDuzina) {
        this.gDuzina = gDuzina;
    }
}
