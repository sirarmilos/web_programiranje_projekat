package vezbe.demo.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "Admin")
public class Admin extends Korisnik implements Serializable {

    public Admin() {
    }

    public Admin(String korisnickoIme, String lozinka, String ime, String prezime, String pol, LocalDate datumRodjenja) {
        super(korisnickoIme, lozinka, ime, prezime, pol, datumRodjenja);
    }

}
