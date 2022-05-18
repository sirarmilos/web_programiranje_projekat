package vezbe.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vezbe.demo.model.Artikal;
import vezbe.demo.model.Porudzbina;
import vezbe.demo.model.PorudzbinaArtikal;

import java.util.List;
import java.util.UUID;

@Repository
public interface PorudzbinaArtikalRepository extends JpaRepository<PorudzbinaArtikal, Integer> {

    PorudzbinaArtikal getPorudzbinaArtikalByArtikalAndPorudzbina(Artikal artikal, Porudzbina porudzbina);
}
