package vezbe.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "porudzbine_artikli")
public class PorudzbinaArtikal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "artikal_id")
    private Artikal artikal;

    @ManyToOne
    @JoinColumn(name = "porudzbina_id")
    private Porudzbina porudzbina;

    private int kolicina;

    public PorudzbinaArtikal() {
    }

    public PorudzbinaArtikal(Artikal artikal, Porudzbina porudzbina, int kolicina) {
        this.artikal = artikal;
        this.porudzbina = porudzbina;
        this.kolicina = kolicina;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Artikal getArtikal() {
        return artikal;
    }

    public void setArtikal(Artikal artikal) {
        this.artikal = artikal;
    }

    public Porudzbina getPorudzbina() {
        return porudzbina;
    }

    public void setPorudzbina(Porudzbina porudzbina) {
        this.porudzbina = porudzbina;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "p_id")
    private UUID p_id;

    @Column(name = "a_id")
    private Long a_id;

    @ManyToOne
    @JoinColumn(name = "p_id", insertable = false, updatable = false)
    @JsonIgnore
    private Porudzbina porudzbina;

    @ManyToOne
    @JoinColumn(name = "a_id", insertable = false, updatable = false)
    @JsonIgnore
    private Artikal artikal;

    public PorudzbinaArtikal() {
    }

    public PorudzbinaArtikal(UUID p_id, Long a_id, Porudzbina porudzbina, Artikal artikal) {
        this.p_id = p_id;
        this.a_id = a_id;
        this.porudzbina = porudzbina;
        this.artikal = artikal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getP_id() {
        return p_id;
    }

    public void setP_id(UUID p_id) {
        this.p_id = p_id;
    }

    public Long getA_id() {
        return a_id;
    }

    public void setA_id(Long a_id) {
        this.a_id = a_id;
    }

    public Porudzbina getPorudzbina() {
        return porudzbina;
    }

    public void setPorudzbina(Porudzbina porudzbina) {
        this.porudzbina = porudzbina;
    }

    public Artikal getArtikal() {
        return artikal;
    }

    public void setArtikal(Artikal artikal) {
        this.artikal = artikal;
    }

    @Override
    public String toString() {
        return "PorudzbinaArtikal{" +
                "id=" + id +
                ", p_id=" + p_id +
                ", a_id=" + a_id +
                ", porudzbina=" + porudzbina +
                ", artikal=" + artikal +
                '}';
    }*/
}
