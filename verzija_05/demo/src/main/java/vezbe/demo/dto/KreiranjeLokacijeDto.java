package vezbe.demo.dto;

import vezbe.demo.model.Lokacija;

import java.math.BigDecimal;

public class KreiranjeLokacijeDto {

    private BigDecimal geografskaDuzina;
    private BigDecimal geografskaSirina;
    private String adresa;

    public KreiranjeLokacijeDto() {
    }

    public KreiranjeLokacijeDto(BigDecimal geografskaDuzina, BigDecimal geografskaSirina, String adresa) {
        this.geografskaDuzina = geografskaDuzina;
        this.geografskaSirina = geografskaSirina;
        this.adresa = adresa;
    }

    public BigDecimal getGeografskaDuzina() {
        return geografskaDuzina;
    }

    public void setGeografskaDuzina(BigDecimal geografskaDuzina) {
        this.geografskaDuzina = geografskaDuzina;
    }

    public BigDecimal getGeografskaSirina() {
        return geografskaSirina;
    }

    public void setGeografskaSirina(BigDecimal geografskaSirina) {
        this.geografskaSirina = geografskaSirina;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Lokacija PrebaciULokaciju()
    {
        return new Lokacija(geografskaDuzina, geografskaSirina, adresa);
    }
}
