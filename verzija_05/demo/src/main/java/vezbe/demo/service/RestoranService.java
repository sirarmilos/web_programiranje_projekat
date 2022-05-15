package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
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
        //List<Restoran> restorani = restoranRepository.findAllByNazivContainsAndTipContainsAndLokacijaContains(pretragaRestoranaDto.getNaziv(), pretragaRestoranaDto.getTip(), pretragaRestoranaDto.getLokacija());
        //List<Restoran> restorani = restoranRepository.findAllByNazivStartingWithAndTipStartingWith(pretragaRestoranaDto.getNaziv(), pretragaRestoranaDto.getTip());
        List<Restoran> restorani = restoranRepository.findByTipLike(pretragaRestoranaDto.getTip());

        System.out.println("JESTE");

        if(restorani.isEmpty() == true)
        {
            throw new Exception("Ne postoji trazeni restoran");
        }

        return  restorani;
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
