package vezbe.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Tip_Kupca")
public class TipKupca implements Serializable {

    public enum Ime{Zlatni, Srebrni, Bronzani}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ime", nullable = false)
    @Enumerated(EnumType.STRING)
    private Ime ime;

    @Column(name = "popust", nullable = false)
    private BigDecimal popust;

    @Column(name = "trazeni_broj_bodova", nullable = false)
    private BigDecimal trazeniBrojBodova;

    // kupac

    @OneToMany(mappedBy = "tipKupca", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Kupac> kupci = new HashSet<>();

    public TipKupca() {
    }

    public TipKupca(Ime ime, BigDecimal popust, BigDecimal trazeniBrojBodova) {
        this.ime = ime;
        this.popust = popust;
        this.trazeniBrojBodova = trazeniBrojBodova;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ime getIme() {
        return ime;
    }

    public void setIme(Ime ime) {
        this.ime = ime;
    }

    public BigDecimal getPopust() {
        return popust;
    }

    public void setPopust(BigDecimal popust) {
        this.popust = popust;
    }

    public BigDecimal getTrazeniBrojBodova() {
        return trazeniBrojBodova;
    }

    public void setTrazeniBrojBodova(BigDecimal trazeniBrojBodova) {
        this.trazeniBrojBodova = trazeniBrojBodova;
    }

    public Set<Kupac> getKupci() {
        return kupci;
    }

    public void setKupci(Set<Kupac> kupci) {
        this.kupci = kupci;
    }

    @Override
    public String toString() {
        return "TipKupca{" +
                "id=" + id +
                ", ime=" + ime +
                ", popust=" + popust +
                ", trazeniBrojBodova=" + trazeniBrojBodova +
                ", kupci=" + kupci +
                '}';
    }
}
