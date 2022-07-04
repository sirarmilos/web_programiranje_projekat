package vezbe.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vezbe.demo.model.Menadzer;
import vezbe.demo.model.Restoran;

import java.util.List;

@Repository
public interface MenadzerRepository extends JpaRepository<Menadzer, String> {

    Menadzer findByKorisnickoIme(String korisnickoIme);
    void deleteMenadzerByRestoranId(Long id);
    void deleteByKorisnickoIme(String korisnickoIme);

    List<Menadzer> findAll();
}
