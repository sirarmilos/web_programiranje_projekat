package vezbe.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vezbe.demo.model.Artikal;
import vezbe.demo.model.Restoran;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtikalRepository extends JpaRepository<Artikal, Long> {

    List<Artikal> findByRestoran(Restoran restoran);
    Optional<Artikal> findById(Long id);
    void delete(Artikal artikal);
}
