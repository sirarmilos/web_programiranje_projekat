package vezbe.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Artikal")
public class Artikal implements Serializable {

    public enum Tip{Jelo, Pice}
    public enum Kolicina{g, ml}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "cena")
    private BigDecimal cena;

    @Column(name = "tip")
    @Enumerated(EnumType.STRING)
    private Tip tip;

    @Column(name = "kolicina")
    @Enumerated(EnumType.STRING)
    private Kolicina kolicina;

    @Column(name = "opis")
    private String opis;

    // restoran

    @ManyToOne
    @JoinColumn(name = "restoran_id")
    @JsonIgnore
    private Restoran restoran;

    // artikal - porudzbinaArtikal - porudzbina

    @OneToMany(mappedBy = "artikal", orphanRemoval = true)
    @JsonIgnore
    private Set<PorudzbinaArtikal> porudzbineArtikli = new HashSet<>();

    public Artikal() {
    }

    public Artikal(String naziv, BigDecimal cena, Tip tip, Kolicina kolicina, String opis, Restoran restoran) {
        this.naziv = naziv;
        this.cena = cena;
        this.tip = tip;
        this.kolicina = kolicina;
        this.opis = opis;
        this.restoran = restoran;
    }

    public Artikal(String naziv, BigDecimal cena, String tip, String kolicina, String opis, Restoran restoran) {
        this.naziv = naziv;
        this.cena = cena;
        if(tip == null)
        {
            this.tip = Tip.Pice;
        }
        else {
            if (tip.equals("Jelo") == true) {
                this.tip = Tip.Jelo;
            } else {
                this.tip = Tip.Pice;
            }
        }

        if(kolicina == null)
        {
            this.kolicina = Kolicina.ml;
        }
        else {
            if (kolicina.equals("g") == true) {
                this.kolicina = Kolicina.g;
            } else {
                this.kolicina = Kolicina.ml;
            }
        }

        this.opis = opis;
        this.restoran = restoran;
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

    public BigDecimal getCena() {
        return cena;
    }

    public void setCena(BigDecimal cena) {
        this.cena = cena;
    }

    public Tip getTip() {
        return tip;
    }

    public void setTip(Tip tip) {
        this.tip = tip;
    }

    public Kolicina getKolicina() {
        return kolicina;
    }

    public void setKolicina(Kolicina kolicina) {
        this.kolicina = kolicina;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Restoran getRestoran() {
        return restoran;
    }

    public void setRestoran(Restoran restoran) {
        this.restoran = restoran;
    }

    public Set<PorudzbinaArtikal> getPorudzbineArtikli() {
        return porudzbineArtikli;
    }

    public void setPorudzbineArtikli(Set<PorudzbinaArtikal> porudzbineArtikli) {
        this.porudzbineArtikli = porudzbineArtikli;
    }

    @Override
    public String toString() {
        return "Artikal{" +
                "id=" + id +
                ", naziv='" + naziv + '\'' +
                ", cena=" + cena +
                ", tip=" + tip +
                ", kolicina=" + kolicina +
                ", opis='" + opis + '\'' +
                ", restoran=" + restoran +
                ", porudzbineArtikli=" + porudzbineArtikli +
                '}';
    }
}
