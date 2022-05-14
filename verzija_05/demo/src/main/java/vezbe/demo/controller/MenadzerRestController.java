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
import vezbe.demo.service.SesijaService;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class MenadzerRestController {

    @Autowired
    private MenadzerService menadzerService;

    @Autowired
    private SesijaService sesijaService;

    @GetMapping("api/menadzer/pregled_restorana_i_porudzbina")
    ResponseEntity PregledPodatakaOdStraneMenadzera(HttpSession sesija)
    {
        Boolean povratna = sesijaService.validacijaUloge(sesija, "Menadzer");

        System.out.println("1");

        if(povratna == false)
        {
            System.out.println("2");
            return new ResponseEntity("Ne mozete da vidite podatke o restoranu i porudzbini.", HttpStatus.BAD_REQUEST);
        }
        System.out.println("3");

        Restoran restoran = menadzerService.NadjiRestoranGdeMenadzerRadi(sesijaService.getKorisnickoIme(sesija));
        System.out.println("4");

        List<Porudzbina> listaPorudzbina = menadzerService.PregledPodatakaOdStraneMenadzera(restoran);
        System.out.println("5");

        return new ResponseEntity(listaPorudzbina, HttpStatus.OK);
    }

}
