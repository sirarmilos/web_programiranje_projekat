package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.dto.RegistracijaDto;
import vezbe.demo.model.Kupac;
import vezbe.demo.repository.KupacRepository;

import java.util.List;

@Service
public class KupacService {

    @Autowired
    private KupacRepository kupacRepository;

    public List<Kupac> findAll()
    {
        return kupacRepository.findAll();
    }

    public Kupac findByKorisnickoIme(String korisnickoIme){
        return kupacRepository.findKupacByKorisnickoIme(korisnickoIme);
    }

}
