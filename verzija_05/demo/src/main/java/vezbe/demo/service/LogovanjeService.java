package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.model.Korisnik;
import vezbe.demo.repository.DostavljacRepository;
import vezbe.demo.repository.KorisnikRepository;
import vezbe.demo.repository.KupacRepository;
import vezbe.demo.repository.MenadzerRepository;

import javax.naming.directory.InvalidAttributesException;
import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Service
public class LogovanjeService {

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private KupacRepository kupacRepository;

    @Autowired
    private MenadzerRepository menadzerRepository;

    @Autowired
    private DostavljacRepository dostavljacRepository;

    public Korisnik Logovanje(String korisnickoIme, String lozinka) throws AccountNotFoundException, InvalidAttributesException
    {
        Korisnik korisnik = PronadjiKorisnika(korisnickoIme, (List<Korisnik>) ((List<?>)korisnikRepository.findAll()));

        if(korisnik == null)
        {
            throw new AccountNotFoundException("Korisnik sa ovim korisnickim imenom ne postoji");
        }

        if(korisnik.getLozinka().equals(lozinka) == false)
        {
            throw new InvalidAttributesException("Niste uneli ispravnu lozinku");
        }

        return korisnik;
    }

    private Korisnik PronadjiKorisnika(String korisnickoIme, List<Korisnik> set)
    {
        for(Korisnik korisnik : set)
        {
            if(korisnik.getKorisnickoIme().equals(korisnickoIme))
            {
                return korisnik;
            }
        }

        return null;
    }

}
