package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.dto.KreiranjeMenadzeraDto;
import vezbe.demo.model.*;
import vezbe.demo.repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private MenadzerRepository menadzerRepository;

    @Autowired
    private DostavljacRepository dostavljacRepository;

    @Autowired
    private LokacijaRepository lokacijaRepository;

    @Autowired
    private RestoranRepository restoranRepository;

    @Autowired
    private KomentarRepository komentarRepository;

    @Autowired
    private PorudzbinaRepository porudzbinaRepository;

    public List<Korisnik> PregledSvihPodatakaOdStraneAdmina()
    {
        List<Korisnik> listaSvihKorisnika = korisnikRepository.findAll();

        return listaSvihKorisnika;
    }

    public void KreiranjeMenadzera(Korisnik korisnik) throws Exception
    {
        ProveraMenadzer(korisnik.getKorisnickoIme(), (List<Korisnik>) (List<?>)korisnikRepository.findAll());
    }

    public void KreiranjeLokacije(Lokacija lokacija) throws Exception
    {
        ProveraLokacija(lokacija.getAdresa(), lokacijaRepository.findAll());
    }

    public void KreiranjeRestorana(Restoran restoran) throws Exception
    {
        ProveraRestorana(restoran.getNaziv(), restoranRepository.findAll());
    }

    public void SacuvajMenadzera(Lokacija lokacija, Restoran restoran, Menadzer menadzer)
    {
        lokacijaRepository.save(lokacija);
        restoranRepository.save(restoran);
        menadzerRepository.save(menadzer);
    }

    public void KreiranjeDostavljaca(Korisnik korisnik) throws Exception
    {
        ProveraDostavljac(korisnik.getKorisnickoIme(), (List<Korisnik>) (List<?>)korisnikRepository.findAll());

        dostavljacRepository.save((Dostavljac) korisnik);
    }

    private void ProveraMenadzer(String korisnickoIme, List<Korisnik> set) throws Exception
    {
        for(Korisnik korisnik : set)
        {
            if(korisnik.getKorisnickoIme().equals(korisnickoIme))
            {
                throw new Exception("Korisnik sa korisnickim imenom: " + korisnickoIme + " vec postoji.");
            }
        }
    }

    private void ProveraLokacija(String adresa, List<Lokacija> set) throws Exception
    {
        for(Lokacija lokacija : set)
        {
            if(lokacija.getAdresa().equals(adresa))
            {
                throw new Exception("Lokacija sa ovom adresom: " + adresa + " vec postoji.");
            }
        }
    }

    private void ProveraRestorana(String nazivRestorana, List<Restoran> set) throws Exception
    {
        for(Restoran restoran : set)
        {
            if(restoran.getNaziv().equals(nazivRestorana))
            {
                throw new Exception("Restoran sa ovim nazivom: " + nazivRestorana + " vec postoji.");
            }
        }
    }

    private void ProveraDostavljac(String korisnickoIme, List<Korisnik> set) throws Exception
    {
        for(Korisnik korisnik : set)
        {
            if(korisnik.getKorisnickoIme().equals(korisnickoIme))
            {
                throw new Exception("Korisnik sa korisnickim imenom: " + korisnickoIme + " vec postoji.");
            }
        }
    }

    public void sacuvajMenadzera(Menadzer menadzer)
    {
        menadzerRepository.save(menadzer);
    }

    public List<Komentar> nadjiSveKomentare(Restoran restoran)
    {
        List<Komentar> komen = komentarRepository.findAll();
        List<Komentar> potrebniKomen = new ArrayList<>();

        for(Komentar kom : komen)
        {
            if(kom.getRestoran().equals(restoran) == true)
            {
                potrebniKomen.add(kom);
            }
        }

        return potrebniKomen;
    }

    public void sacuvajKomentar(Komentar komentar)
    {
        komentarRepository.save(komentar);
    }

    public void sacuvajPorudzbinu(Porudzbina p)
    {
        porudzbinaRepository.save(p);
    }

    public void obrisiRestoran(Restoran restoran)
    {
        restoranRepository.delete(restoran);
    }

}
