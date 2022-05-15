package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import vezbe.demo.dto.PretragaRestoranaDto;
import vezbe.demo.model.Porudzbina;
import vezbe.demo.model.Restoran;
import vezbe.demo.repository.PorudzbinaRepository;
import vezbe.demo.repository.RestoranRepository;

import java.util.*;

@Service
public class RestoranService {

    @Autowired
    private RestoranRepository restoranRepository;

    @Autowired
    private PorudzbinaRepository porudzbinaRepository;

    public List<Porudzbina> NadjiSvePorudzbineZaRestoranZaKojiJeMenadzerNadlezan(Restoran restoran)
    {
        Set<Porudzbina> porudzbine = restoran.getPorudzbine();

        List<Porudzbina> listaPorudzbina = new ArrayList<>(porudzbine);

        return listaPorudzbina;
    }

    public List<Restoran> PregledSvihRestorana()
    {
        return restoranRepository.findAll();
    }

    public List<Restoran> PretragaRestorana(PretragaRestoranaDto pretragaRestoranaDto) throws Exception {
        //List<Restoran> restorani = restoranRepository.findAllByNazivStartingWithAndTipStartingWithAndLokacijaContains(pretragaRestoranaDto.getNaziv(), pretragaRestoranaDto.getTip(), pretragaRestoranaDto.getAdresa());
        //List<Restoran> restorani = restoranRepository.findAllByNazivStartingWithAndTipStartingWith(pretragaRestoranaDto.getNaziv(), pretragaRestoranaDto.getTip());
        // List<Restoran> restorani = restoranRepository.findAllByTipStartingWith(pretragaRestoranaDto.getTip());

        List<Restoran> trazeniRestorani = new ArrayList<>();

        List<Restoran> sviRestorani = restoranRepository.findAll();

        for(Restoran restoran : sviRestorani)
        {
            System.out.println(restoran.getNaziv());
            System.out.println(restoran.getTip());
            System.out.println(restoran.getLokacija().getAdresa());
            if(restoran.getNaziv().startsWith(pretragaRestoranaDto.getNaziv()) == true && restoran.getTip().startsWith(pretragaRestoranaDto.getTip()) == true && restoran.getLokacija().getAdresa().startsWith(pretragaRestoranaDto.getAdresa()) == true)
            {
                System.out.println("isti su");
                trazeniRestorani.add(restoran);
            }
        }

        System.out.println("JESTE");

        if(trazeniRestorani.isEmpty() == true)
        {
            throw new Exception("Ne postoji trazeni restoran");
        }

        System.out.println("JESTE");

        return trazeniRestorani;
    }

    public Restoran NadjiRestoranPoId(Long id)
    {
        Optional<Restoran> restoran = restoranRepository.findById(id);

        if(restoran.isPresent())
        {
            return restoran.get();
        }

        return null;
    }

}
