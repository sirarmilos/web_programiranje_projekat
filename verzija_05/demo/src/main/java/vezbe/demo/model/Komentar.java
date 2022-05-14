package vezbe.demo.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Komentar")
public class Komentar implements Serializable {

    public enum Ocena{JakoLose, Lose, Dobro, VeomaDobro, Odlicno}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // kupac

    @ManyToOne
    @JoinColumn(name = "kupac_korisnickoIme", nullable = false)
    private Kupac kupac;

    // restoran

    @ManyToOne
    @JoinColumn(name = "restoran_id", nullable = false)
    private Restoran restoran;

    @Column(name = "tekst_komentara")
    private String tekstKomentara;

    @Column(name = "ocena", nullable = false)
    @Enumerated(EnumType.STRING)
    private Ocena ocena;

    public Komentar() {
    }

    public Komentar(Kupac kupac, Restoran restoran, String tekstKomentara, Ocena ocena) {
        this.kupac = kupac;
        this.restoran = restoran;
        this.tekstKomentara = tekstKomentara;
        this.ocena = ocena;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Kupac getKupac() {
        return kupac;
    }

    public void setKupac(Kupac kupac) {
        this.kupac = kupac;
    }

    public Restoran getRestoran() {
        return restoran;
    }

    public void setRestoran(Restoran restoran) {
        this.restoran = restoran;
    }

    public String getTekstKomentara() {
        return tekstKomentara;
    }

    public void setTekstKomentara(String tekstKomentara) {
        this.tekstKomentara = tekstKomentara;
    }

    public Ocena getOcena() {
        return ocena;
    }

    public void setOcena(Ocena ocena) {
        this.ocena = ocena;
    }

    @Override
    public String toString() {
        return "Komentar{" +
                "id=" + id +
                ", kupac=" + kupac +
                ", restoran=" + restoran +
                ", tekstKomentara='" + tekstKomentara + '\'' +
                ", ocena=" + ocena +
                '}';
    }
}
