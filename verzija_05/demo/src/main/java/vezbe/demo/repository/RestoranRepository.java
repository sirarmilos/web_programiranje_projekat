package vezbe.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vezbe.demo.model.Lokacija;
import vezbe.demo.model.Porudzbina;
import vezbe.demo.model.Restoran;

import java.util.List;

@Repository
public interface RestoranRepository extends JpaRepository<Restoran, Long> {

    List<Restoran> findAllByNazivStartingWithAndTipStartingWith/*AndLokacijaContains*/(String naziv, String tip);//, Lokacija lokacija);
    List<Restoran> findAllByNazivEqualsAndTipEquals/*AndLokacijaContains*/(String naziv, String tip);//, Lokacija lokacija);
    List<Restoran> findByTipLike/*AndLokacijaContains*/(String tip);
}
