package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.model.Porudzbina;
import vezbe.demo.model.Restoran;
import vezbe.demo.repository.PorudzbinaRepository;
import vezbe.demo.repository.RestoranRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

}
