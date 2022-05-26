package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.dto.RegistracijaDto;
import vezbe.demo.model.Kupac;
import vezbe.demo.model.TipKupca;
import vezbe.demo.repository.KupacRepository;
import vezbe.demo.repository.TipKupcaRepository;

import java.util.List;

@Service
public class KupacService {

    @Autowired
    private KupacRepository kupacRepository;

    @Autowired
    private TipKupcaRepository tipKupcaRepository;

    public List<Kupac> findAll()
    {
        return kupacRepository.findAll();
    }

    public Kupac findByKorisnickoIme(String korisnickoIme){
        return kupacRepository.findKupacByKorisnickoIme(korisnickoIme);
    }

    public void sacuvajKupca(Kupac k){
        kupacRepository.save(k);
    }

    public TipKupca tipKupca(TipKupca.Ime ime){
       return  tipKupcaRepository.findTipKupcaByIme(ime);
    }

}
