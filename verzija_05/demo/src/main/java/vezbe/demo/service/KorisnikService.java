package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.dto.AzuriranjeKorisnikDto;
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

    public Korisnik Azuriranje(AzuriranjeKorisnikDto azuriranjeKorisnikDto)
    {
        Korisnik korisnik = korisnikRepository.findKorisnikByKorisnickoIme(azuriranjeKorisnikDto.getKorisnickoIme());

        korisnik.setLozinka(azuriranjeKorisnikDto.getLozinka());
        korisnik.setIme(azuriranjeKorisnikDto.getIme());
        korisnik.setPrezime(azuriranjeKorisnikDto.getPrezime());
        if(azuriranjeKorisnikDto.getDatumRodjenja() != null)
        {
            korisnik.setDatumRodjenja(azuriranjeKorisnikDto.getDatumRodjenja());
        }
        if(azuriranjeKorisnikDto.getPol() != null && azuriranjeKorisnikDto.getPol().equals("") != true)
        {
            korisnik.setPol(azuriranjeKorisnikDto.getPol());
        }

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
