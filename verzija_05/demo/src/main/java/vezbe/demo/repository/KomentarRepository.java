package vezbe.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vezbe.demo.model.Komentar;

@Repository
public interface KomentarRepository extends JpaRepository<Komentar, Long> {

}
