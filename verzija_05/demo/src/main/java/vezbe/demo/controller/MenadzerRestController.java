package vezbe.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vezbe.demo.model.Korisnik;
import vezbe.demo.model.Porudzbina;
import vezbe.demo.model.Restoran;
import vezbe.demo.service.MenadzerService;
import vezbe.demo.service.RestoranService;
import vezbe.demo.service.SesijaService;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class MenadzerRestController {

    @Autowired
    private MenadzerService menadzerService;

    @Autowired
    private SesijaService sesijaService;

    @Autowired
    private RestoranService restoranService;

    @GetMapping("api/menadzer/pregled_restorana")
    ResponseEntity MenadzerPregledRestorana(HttpSession sesija)
    {
        Boolean povratna = sesijaService.validacijaUloge(sesija, "Menadzer");

        if(povratna == false)
        {
            return new ResponseEntity("Ne mozete da vidite podatke o restoranu, niste menadzer.", HttpStatus.BAD_REQUEST);
        }

        Restoran restoran = menadzerService.NadjiRestoranGdeMenadzerRadi(sesijaService.getKorisnickoIme(sesija));

        return new ResponseEntity(restoran, HttpStatus.OK);
    }

    @GetMapping("api/menadzer/pregled_porudzbina")
    ResponseEntity MenadzerPregledPorudzbina(HttpSession sesija)
    {
        Boolean povratna = sesijaService.validacijaUloge(sesija, "Menadzer");

        if(povratna == false)
        {
            return new ResponseEntity("Ne mozete da vidite podatke o narudzbinama restorana, niste menadzer.", HttpStatus.BAD_REQUEST);
        }

        Restoran restoran = menadzerService.NadjiRestoranGdeMenadzerRadi(sesijaService.getKorisnickoIme(sesija));

        List<Porudzbina> listaPorudzbina = restoranService.NadjiSvePorudzbineZaRestoranZaKojiJeMenadzerNadlezan(restoran);

        return new ResponseEntity(listaPorudzbina, HttpStatus.OK);
    }
}
