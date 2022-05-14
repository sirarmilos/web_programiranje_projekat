package vezbe.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vezbe.demo.model.Menadzer;

@Repository
public interface MenadzerRepository extends JpaRepository<Menadzer, String> {

}
