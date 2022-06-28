package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vezbe.demo.model.Lokacija;
import vezbe.demo.repository.LokacijaRepository;

@Service
public class LokacijaService {

    @Autowired
    private LokacijaRepository lokacijaRepository;

    @Transactional
    public void ObrisiLokacijuPoId(Long id)
    {
        lokacijaRepository.deleteLokacijaById(id);
    }

    public Lokacija NadjiLokacijuPoId(Long id)
    {
       return lokacijaRepository.findLokacijaById(id);
    }
}
