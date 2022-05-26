package vezbe.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Kupac")
@DiscriminatorValue("Kupac")
public class Kupac extends Korisnik implements Serializable {

    // porudzbina

    @OneToMany(mappedBy = "kupac", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Porudzbina> porudzbine = new HashSet<>();

    @Column(name = "broj_sakupljenih_bodova")
    private BigDecimal brojSakupljenihBodova;

    // tipKupca

    @ManyToOne
    @JoinColumn(name = "tipKupca_id")
    @JsonIgnore
    private TipKupca tipKupca;

    // komentari

    @OneToMany(mappedBy = "kupac", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Komentar> komentari = new HashSet<>();

    public Kupac() {
    }

    public Kupac(String korisnickoIme, String lozinka, String ime, String prezime, String pol, LocalDate datumRodjenja, BigDecimal brojSakupljenihBodova, TipKupca tipKupca) {
        super(korisnickoIme, lozinka, ime, prezime, pol, datumRodjenja);
        this.brojSakupljenihBodova = brojSakupljenihBodova;
        this.tipKupca = tipKupca;
    }

    public Kupac(String korisnickoIme, String lozinka, String ime, String prezime) {
        super(korisnickoIme, lozinka, ime, prezime);
        this.brojSakupljenihBodova = new BigDecimal(0);
    }

    public Set<Porudzbina> getPorudzbine() {
        return porudzbine;
    }

    public void setPorudzbine(Set<Porudzbina> porudzbine) {
        this.porudzbine = porudzbine;
    }

    public BigDecimal getBrojSakupljenihBodova() {
        return brojSakupljenihBodova;
    }

    public void setBrojSakupljenihBodova(BigDecimal brojSakupljenihBodova) {
        this.brojSakupljenihBodova = brojSakupljenihBodova;
    }

    public TipKupca getTipKupca() {
        return tipKupca;
    }

    public void setTipKupca(TipKupca tipKupca) {
        this.tipKupca = tipKupca;
    }

    public Set<Komentar> getKomentari() {
        return komentari;
    }

    public void setKomentari(Set<Komentar> komentari) {
        this.komentari = komentari;
    }

    @Override
    public String toString() {
        return "Kupac{" +
                "porudzbine=" + porudzbine +
                ", brojSakupljenihBodova=" + brojSakupljenihBodova +
                ", tipKupca=" + tipKupca +
                ", komentari=" + komentari +
                '}';
    }
}
