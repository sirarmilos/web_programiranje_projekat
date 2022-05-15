package vezbe.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vezbe.demo.model.Dostavljac;
import vezbe.demo.model.Kupac;
import vezbe.demo.model.Porudzbina;
import vezbe.demo.model.Restoran;

import java.util.List;
import java.util.UUID;

@Repository
public interface PorudzbinaRepository extends JpaRepository<Porudzbina, UUID> {
    List<Porudzbina> findPorudzbinaByRestoran(Restoran restoran);

    @Query(value = "SELECT * FROM Porudzbina p where p.status='CekaDostavljaca' or p.dostavljac_korisnicko_ime=:korisnickoImeDostavljaca", nativeQuery = true)
    List<Porudzbina> findPorudzbinaByDostavljac(String korisnickoImeDostavljaca);

    List<Porudzbina> findPorudzbinaByKupac(Kupac kupac);
}
