package vezbe.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vezbe.demo.model.*;
import vezbe.demo.service.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/porudzbina")
public class PorudzbinaRestController {

    @Autowired
    private PorudzbinaService porudzbinaService;

    @Autowired
    private MenadzerService menadzerService;

    @Autowired
    private SesijaService sesijaService;

    @Autowired
    private DostavljacService dostavljacService;

    @Autowired
    private KupacService kupacService;

    @GetMapping("dobaviSve")
    public ResponseEntity<List<Porudzbina>> dobaviSveporudzbinePoUlogovanomKupcu(HttpSession sesija)
    {
        if(!sesijaService.validacijaUloge(sesija, "Kupac"))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        String korisnickoImeKupca = sesijaService.getKorisnickoIme(sesija);

        Kupac kupac = kupacService.findByKorisnickoIme(korisnickoImeKupca);
        List<Porudzbina> porudzbine  = porudzbinaService.dobaviPorudzbinePoKupcu(kupac);

        return ResponseEntity.ok(porudzbine);
    }

    @GetMapping("dobaviZaDostavljaca")
    public ResponseEntity dobaviSvePorudzbineZaDostavljaca(HttpSession sesija)
    {

        if(!sesijaService.validacijaUloge(sesija, "Dostavljac"))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        String korisnickoImeDostavljaca = sesijaService.getKorisnickoIme(sesija);
        Dostavljac dostavljac = dostavljacService.dobaviDostavljacaPoKorisnickomImenu(korisnickoImeDostavljaca);

        List<Porudzbina> porudzbine  = porudzbinaService.dobaviPorudzbinePoDostavljacu(dostavljac);

        return ResponseEntity.ok(porudzbine);
    }

    @GetMapping("MenadzerUrestoranu")
    public ResponseEntity<List<Porudzbina>> dobaviSvePorudzbineZaMenadzera(HttpSession sesija)
    {
        if(!sesijaService.validacijaUloge(sesija, "Menadzer"))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        String korisnickoImeMenadzera = sesijaService.getKorisnickoIme(sesija);
        Restoran restoranGdeMenadzerRadi = menadzerService.NadjiRestoranGdeMenadzerRadi(korisnickoImeMenadzera);

        List<Porudzbina> porudzbine  = porudzbinaService.dobaviPorudzbinePoRestoranu(restoranGdeMenadzerRadi);

        return ResponseEntity.ok(porudzbine);
    }


}
