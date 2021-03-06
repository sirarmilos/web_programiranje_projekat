package vezbe.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vezbe.demo.model.Korisnik;
import vezbe.demo.model.Kupac;

import java.util.List;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, String> {

    public Korisnik findKorisnikByKorisnickoIme(String korisnickoIme);
    public List<Korisnik> findAll();

}
