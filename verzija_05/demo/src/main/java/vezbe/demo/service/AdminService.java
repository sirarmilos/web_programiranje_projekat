package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.dto.KreiranjeMenadzeraDto;
import vezbe.demo.model.Korisnik;
import vezbe.demo.model.Menadzer;
import vezbe.demo.repository.AdminRepository;
import vezbe.demo.repository.KorisnikRepository;
import vezbe.demo.repository.MenadzerRepository;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private MenadzerRepository menadzerRepository;

    public List<Korisnik> PregledSvihPodatakaOdStraneAdmina()
    {
        List<Korisnik> listaSvihKorisnika = korisnikRepository.findAll();

        return listaSvihKorisnika;
    }

    public void KreiranjeMenadzera(Korisnik korisnik, String uloga) throws Exception
    {
        Provera(korisnik.getKorisnickoIme(), (List<Korisnik>) (List<?>)korisnikRepository.findAll());

        menadzerRepository.save((Menadzer) korisnik);
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
