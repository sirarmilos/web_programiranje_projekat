package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.dto.RegistracijaDto;
import vezbe.demo.model.Kupac;
import vezbe.demo.model.TipKupca;
import vezbe.demo.repository.KupacRepository;
import vezbe.demo.repository.TipKupcaRepository;

import javax.security.auth.login.AccountNotFoundException;
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

    public Kupac VratiKupca(String korisnickoIme) throws AccountNotFoundException
    {
        Kupac kupac = PronadjiKupca(korisnickoIme,(List<Kupac>)((List<?>)kupacRepository.findAll()));

        if(kupac == null)
        {
            throw new AccountNotFoundException("Kupac sa ovim korisnickim imenom ne postoji.");
        }

        return kupac;
    }

    private Kupac PronadjiKupca(String korisnickoIme, List<Kupac> set)
    {
        for(Kupac kupac : set)
        {
            if(kupac.getKorisnickoIme().equals(korisnickoIme))
            {
                return kupac;
            }
        }

        return null;
    }
}
