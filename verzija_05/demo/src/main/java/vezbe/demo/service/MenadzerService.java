package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.model.Menadzer;
import vezbe.demo.model.Porudzbina;
import vezbe.demo.model.Restoran;
import vezbe.demo.repository.MenadzerRepository;
import vezbe.demo.repository.RestoranRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MenadzerService {

    @Autowired
    private MenadzerRepository menadzerRepository;

    @Autowired
    private RestoranRepository restoranRepository;

    public Restoran NadjiRestoranGdeMenadzerRadi(String korisnickoIme)
    {
        Menadzer menadzer = menadzerRepository.findByKorisnickoIme(korisnickoIme);

        return menadzer.getRestoran();
    }
}
