package vezbe.demo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "Lokacija")
public class Lokacija implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "geografska_duzina", nullable = false)
    private BigDecimal geografskaDuzina;

    @Column(name = "geografska_sirina", nullable = false)
    private BigDecimal geografskaSirina;

    @Column(name = "adresa", nullable = false)
    private String adresa;

    public Lokacija() {
    }

    public Lokacija(BigDecimal geografskaDuzina, BigDecimal geografskaSirina, String adresa) {
        this.geografskaDuzina = geografskaDuzina;
        this.geografskaSirina = geografskaSirina;
        this.adresa = adresa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Lokacija{" +
                "id=" + id +
                ", geografskaDuzina=" + geografskaDuzina +
                ", geografskaSirina=" + geografskaSirina +
                ", adresa='" + adresa + '\'' +
                '}';
    }
}
