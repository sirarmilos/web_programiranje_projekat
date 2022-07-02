package vezbe.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vezbe.demo.model.SlikaProba;

@Repository
public interface SlikaProbaRepository extends JpaRepository<SlikaProba, Long> {
}
