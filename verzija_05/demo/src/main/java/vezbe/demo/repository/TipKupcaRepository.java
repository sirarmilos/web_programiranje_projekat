package vezbe.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vezbe.demo.model.TipKupca;

@Repository
public interface TipKupcaRepository extends JpaRepository<TipKupca, Long> {

}
