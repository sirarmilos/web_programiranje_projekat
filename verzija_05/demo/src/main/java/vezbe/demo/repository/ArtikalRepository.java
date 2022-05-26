package vezbe.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vezbe.demo.model.Artikal;
import vezbe.demo.model.Porudzbina;
import vezbe.demo.model.Restoran;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtikalRepository extends JpaRepository<Artikal, Long> {

    List<Artikal> findByRestoran(Restoran restoran);
    Optional<Artikal> findById(Long id);        /// VAZNA NAPOMENA: PROBAJ findArtikalById da ne bi imao ovaj Optional i to svuda primeni ako radi, bolje je...
    Artikal findAllById(Long id);
    Artikal findArtikalById(Long id);
    void delete(Artikal artikal);

}
