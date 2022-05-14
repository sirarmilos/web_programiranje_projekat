package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.dto.KreiranjeMenadzeraDto;
import vezbe.demo.model.*;
import vezbe.demo.repository.*;

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

    public void KreiranjeLokacije(Lokacija lokacija) throws Exception
    {
        ProveraLokacija(lokacija.getAdresa(), (List<Lokacija>)lokacijaRepository.findAll());

        lokacijaRepository.save(lokacija);
    }

    public void KreiranjeRestorana(Restoran restoran) throws Exception
    {
        ProveraRestorana(restoran.getNaziv(), (List<Restoran>)restoranRepository.findAll());

        restoranRepository.save(restoran);
    }

    public void KreiranjeDostavljaca(Korisnik korisnik, String uloga) throws Exception
    {
        Provera(korisnik.getKorisnickoIme(), (List<Korisnik>) (List<?>)korisnikRepository.findAll());

        dostavljacRepository.save((Dostavljac) korisnik);
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

    private void ProveraLokacija(String adresa, List<Lokacija> set) throws Exception
    {
        for(Lokacija lokacija : set)
        {
            if(lokacija.getAdresa().equals(adresa))
            {
                throw new Exception("Lokacija sa ovom adresom: " + adresa + " vec postoji!");
            }
        }
    }

    private void ProveraRestorana(String nazivRestorana, List<Restoran> set) throws Exception
    {
        for(Restoran restoran : set)
        {
            if(restoran.getNaziv().equals(nazivRestorana))
            {
                throw new Exception("Restoran sa ovim nazivom: " + nazivRestorana + " vec postoji!");
            }
        }
    }

}
