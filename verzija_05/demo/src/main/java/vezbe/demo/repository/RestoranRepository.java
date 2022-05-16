package vezbe.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vezbe.demo.model.Lokacija;
import vezbe.demo.model.Porudzbina;
import vezbe.demo.model.Restoran;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestoranRepository extends JpaRepository<Restoran, Long> {

    // m
    //List<Restoran> findAllByNazivStartingWithAndTipStartingWithAndLokacijaContains(@Param("naziv")String naziv, @Param("tip")String tip, @Param("lokacija")Lokacija lokacija);
   //////// List<Restoran> findAllByNazivStartingWithAndTipStartingWithAndLokaContains(@Param("naziv")String naziv, @Param("tip")String tip, @Param("adresa")String adresa);
   // List<Restoran> findAllByNazivEqualsAndTipEquals/*AndLokacijaContains*/(String naziv, String tip);//, Lokacija lokacija);
   // List<Restoran> findAllByTipStartingWith/*AndLokacijaContains*/(@Param("tip")String tip);
   /////////// Optional<Restoran> findById(Long id); /// Vidi da izbacim ovo Optional, napisao sam komentar u jednom Repository-jumu ideju

    // RADI NA KRAJU I BEZ OVIH SILNIH QUERY-ija, u RestoranService je resenje

    /*@Query("select * from Restoran restoran left join Lokacija lokacija on lokacija.id = restoran.id and restoran.naziv like %naziv% and restoran.tip like %tip% and lokacija.adresa like %adresa%")
    List<Restoran> findAllByNazivStartingWithAndTipStartingWithAndLokacijaContains(@Param("naziv")String naziv, @Param("tip")String tip, @Param("adresa")String adresa);*/

    Restoran findRestoranById(Long id);
}
