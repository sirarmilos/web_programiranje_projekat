package vezbe.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vezbe.demo.model.Porudzbina;
import vezbe.demo.model.Restoran;

import java.util.List;

@Repository
public interface RestoranRepository extends JpaRepository<Restoran, Long> {

}
