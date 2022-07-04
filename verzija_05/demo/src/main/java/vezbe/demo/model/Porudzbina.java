package vezbe.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Porudzbina")
public class Porudzbina implements Serializable {

    public enum Status {Obrada, UPripremi, CekaDostavljaca, UTransportu, Dostavljena, Otkazana}

    @Id
    @Type(type="uuid-char")
    private UUID id = UUID.randomUUID();

    // porudzbina - porudzbinaArtikal - artikal

    @OneToMany(mappedBy = "porudzbina", orphanRemoval = true)
    private Set<PorudzbinaArtikal> porudzbineArtikli = new HashSet<>();

/*
    @OneToMany(mappedBy = "porudzbina")
    @JsonIgnore
    private Set<PorudzbinaArtikal> porudzbineArtikli = new HashSet<>();
*/
    // restoran

    @ManyToOne
    @JoinColumn(name = "restoran_id"/*, nullable = false*/)
    @JsonIgnore
    private Restoran restoran;

    @Column(name = "datum_vreme", nullable = false)
    private LocalDateTime datumVreme;

    @Column(name = "cena", nullable = false)
    private BigDecimal cena;

    // kupac

    @ManyToOne
    @JoinColumn(name = "kupac_korisnickoIme")
    @JsonIgnore
    private Kupac kupac;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    // dostavljac

    @ManyToOne
    @JoinColumn(name = "dostavljac_korisnickoIme")
    @JsonIgnore
    private Dostavljac dostavljac;

    public Porudzbina() {
    }

    public Porudzbina(Restoran restoran, LocalDateTime datumVreme, BigDecimal cena, Kupac kupac, Status status, Dostavljac dostavljac) {
        this.restoran = restoran;
        this.datumVreme = datumVreme;
        this.cena = cena;
        this.kupac = kupac;
        this.status = status;
        this.dostavljac = dostavljac;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<PorudzbinaArtikal> getPorudzbineArtikli() {
        return porudzbineArtikli;
    }

    public void setPorudzbineArtikli(Set<PorudzbinaArtikal> porudzbineArtikli) {
        this.porudzbineArtikli = porudzbineArtikli;
    }

    public Restoran getRestoran() {
        return restoran;
    }

    public void setRestoran(Restoran restoran) {
        this.restoran = restoran;
    }

    public LocalDateTime getDatumVreme() {
        return datumVreme;
    }

    public void setDatumVreme(LocalDateTime datumVreme) {
        this.datumVreme = datumVreme;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public void setCena(BigDecimal cena) {
        this.cena = cena;
    }

    public Kupac getKupac() {
        return kupac;
    }

    public void setKupac(Kupac kupac) {
        this.kupac = kupac;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Dostavljac getDostavljac() {
        return dostavljac;
    }

    public void setDostavljac(Dostavljac dostavljac) {
        this.dostavljac = dostavljac;
    }

    @Override
    public String toString() {
        return "Porudzbina{" +
                "id=" + id +
                ", porudzbineArtikli=" + porudzbineArtikli +
                ", restoran=" + restoran +
                ", datumVreme=" + datumVreme +
                ", cena=" + cena +
                ", kupac=" + kupac +
                ", status=" + status +
                ", dostavljac=" + dostavljac +
                '}';
    }
}
