package vezbe.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "Menadzer")
@DiscriminatorValue("Menadzer")
public class Menadzer extends Korisnik implements Serializable {

    // restoran

    @ManyToOne
    @JoinColumn(name = "restoran_id", nullable = false)
    @JsonIgnore
    private Restoran restoran;

    public Menadzer() {
    }

    public Menadzer(String korisnickoIme, String lozinka, String ime, String prezime, String pol, LocalDate datumRodjenja, Restoran restoran) {
        super(korisnickoIme, lozinka, ime, prezime, pol, datumRodjenja);
        this.restoran = restoran;
    }

    public Menadzer(String korisnickoIme, String lozinka, String ime, String prezime, Restoran restoran) {
        super(korisnickoIme, lozinka, ime, prezime);
        this.restoran = restoran;
    }

    public Menadzer(String korisnickoIme, String lozinka, String ime, String prezime) {
        super(korisnickoIme, lozinka, ime, prezime);
    }

    public Restoran getRestoran() {
        return restoran;
    }

    public void setRestoran(Restoran restoran) {
        this.restoran = restoran;
    }

    @Override
    public String toString() {
        return "Menadzer{"/* +
                "restoran=" + restoran +
                '}'*/;
    }
}
