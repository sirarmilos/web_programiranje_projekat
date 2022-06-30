package vezbe.demo.dto;

import java.math.BigDecimal;

public class TipKupcaBrojPoenaDto {

    private String tipKupca;
    private BigDecimal brojSkupljenihPoena;

    public TipKupcaBrojPoenaDto() {
    }

    public TipKupcaBrojPoenaDto(String tipKupca, BigDecimal brojSkupljenihPoena) {
        this.tipKupca = tipKupca;
        this.brojSkupljenihPoena = brojSkupljenihPoena;
    }

    public String getTipKupca() {
        return tipKupca;
    }

    public void setTipKupca(String tipKupca) {
        this.tipKupca = tipKupca;
    }

    public BigDecimal getBrojSkupljenihPoena() {
        return brojSkupljenihPoena;
    }

    public void setBrojSkupljenihPoena(BigDecimal brojSkupljenihPoena) {
        this.brojSkupljenihPoena = brojSkupljenihPoena;
    }
}
