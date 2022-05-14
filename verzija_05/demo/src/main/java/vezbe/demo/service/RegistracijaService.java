package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.model.Korisnik;
import vezbe.demo.model.Kupac;
import vezbe.demo.repository.KupacRepository;

import java.util.List;

@Service
public class RegistracijaService {

    @Autowired
    private KupacRepository kupacRepository;

    public void Registracija(Korisnik korisnik, String uloga) throws Exception
    {
        Provera(korisnik.getKorisnickoIme(), (List<Korisnik>) (List<?>)kupacRepository.findAll());

        if(uloga.equals("Kupac"))
        {
            kupacRepository.save((Kupac) korisnik);
        }
        else
        {
            throw new Exception("Ne mozete se registovati sa ovom ulogom: " + uloga);
        }

    }

    private void Provera(String korisnickoIme, List<Korisnik> set) throws Exception
    {
        for(Korisnik korisnik : set)
        {
            if(korisnik.getKorisnickoIme().equals(korisnickoIme))
            {
                throw new Exception("Korisnik sa korisnickim imenom: " + korisnickoIme + " vec postoji!");
            }
        }
    }

}
