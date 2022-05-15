package vezbe.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vezbe.demo.model.Komentar;
import vezbe.demo.model.Restoran;

import java.util.List;

@Repository
public interface KomentarRepository extends JpaRepository<Komentar, Long> {

    List<Komentar> findByRestoran(Restoran restoran);

}
