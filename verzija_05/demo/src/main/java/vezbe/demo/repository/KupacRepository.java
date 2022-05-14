package vezbe.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vezbe.demo.model.Kupac;

@Repository
public interface KupacRepository extends JpaRepository<Kupac, String> {

}
