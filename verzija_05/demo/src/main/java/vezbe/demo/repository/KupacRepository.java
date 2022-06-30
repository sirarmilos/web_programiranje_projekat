package vezbe.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vezbe.demo.model.Kupac;

import java.util.List;

@Repository
public interface KupacRepository extends JpaRepository<Kupac, String> {

    public Kupac findKupacByKorisnickoIme(String korisnickoIme);
    public List<Kupac> findAll();

}
