package vezbe.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vezbe.demo.model.Lokacija;

@Repository
public interface LokacijaRepository extends JpaRepository<Lokacija, Long> {

}
