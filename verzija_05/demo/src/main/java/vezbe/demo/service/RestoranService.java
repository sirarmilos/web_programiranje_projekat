package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

        List<Restoran> trazeniRestorani = new ArrayList<>();

        List<Restoran> sviRestorani = restoranRepository.findAll();

        for(Restoran restoran : sviRestorani)
        {
            if(restoran.getNaziv().startsWith(pretragaRestoranaDto.getNaziv()) == true && restoran.getTip().startsWith(pretragaRestoranaDto.getTip()) == true && restoran.getLokacija().getAdresa().startsWith(pretragaRestoranaDto.getAdresa()) == true)
            {
                trazeniRestorani.add(restoran);
            }
        }

        if(trazeniRestorani.isEmpty() == true)
        {
            throw new Exception("Ne postoji trazeni restoran.");
        }

        return trazeniRestorani;
    }

    public Restoran NadjiRestoranPoId(Long id)
    {
        Restoran restoran = restoranRepository.findRestoranById(id);

        if(restoran != null)
        {
            return restoran;
        }

        return null;
    }

    public Restoran DobaviRestoranPoId(Long id)
    {
        return restoranRepository.findRestoranById(id);
    }

   /* public Long DobaviRestoranIDPoUUID(UUID id)
    {
        Restoran restoran = restoranRepository.findRestoranByUUID(id);
        return  restoran.getId();
    }*/

    @Transactional
    public void ObrisiRestoranSaId(Long id)
    {
        restoranRepository.deleteRestoranById(id);
    }

}
