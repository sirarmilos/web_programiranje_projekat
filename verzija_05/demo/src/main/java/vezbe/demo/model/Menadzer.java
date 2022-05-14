package vezbe.demo.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "Menadzer")
public class Menadzer extends Korisnik implements Serializable {

    // restoran

    @ManyToOne
    @JoinColumn(name = "restoran_id", nullable = false)
    private Restoran restoran;

    public Menadzer() {
    }

    public Menadzer(String korisnickoIme, String lozinka, String ime, String prezime, String pol, LocalDate datumRodjenja, Restoran restoran) {
        super(korisnickoIme, lozinka, ime, prezime, pol, datumRodjenja);
        this.restoran = restoran;
    }

    public Restoran getRestoran() {
        return restoran;
    }

    public void setRestoran(Restoran restoran) {
        this.restoran = restoran;
    }

    @Override
    public String toString() {
        return "Menadzer{" +
                "restoran=" + restoran +
                '}';
    }
}
