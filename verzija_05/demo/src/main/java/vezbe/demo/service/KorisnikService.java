package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.dto.RegistracijaDto;
import vezbe.demo.model.Korisnik;
import vezbe.demo.model.Kupac;
import vezbe.demo.repository.KorisnikRepository;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Service
public class KorisnikService {

    @Autowired
    private KorisnikRepository korisnikRepository;

    public Korisnik PregledPodataka(String korisnickoIme) throws AccountNotFoundException
    {
        Korisnik korisnik = PronadjiKorisnika(korisnickoIme, (List<Korisnik>) ((List<?>)korisnikRepository.findAll()));

        if(korisnik == null)
        {
            throw new AccountNotFoundException("Korisnik sa ovim korisnickim imenom ne postoji");
        }

        return korisnik;
    }

    public Korisnik Azuriranje(RegistracijaDto registracijaDto)
    {
        Korisnik korisnik = korisnikRepository.findKorisnikByKorisnickoIme(registracijaDto.getKorisnickoIme());

        korisnik.setLozinka(registracijaDto.getLozinka());
        korisnik.setIme(registracijaDto.getIme());
        korisnik.setPrezime(registracijaDto.getPrezime());
        // kupac.setDatumRodjenja(registracijaDto.PrebaciUKupca().getDatumRodjenja());
        // kupac.setPol(registracijaDto.PrebaciUKupca().getPol());

        korisnikRepository.save(korisnik);

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
