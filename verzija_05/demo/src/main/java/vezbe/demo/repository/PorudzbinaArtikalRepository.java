package vezbe.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vezbe.demo.model.PorudzbinaArtikal;

import java.util.List;
import java.util.UUID;

public interface PorudzbinaArtikalRepository extends JpaRepository<PorudzbinaArtikal, Long> {

}
