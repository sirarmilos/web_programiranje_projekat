package vezbe.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vezbe.demo.dto.PorudzbinaArtikalDto;
import vezbe.demo.model.*;
import vezbe.demo.service.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    @Autowired
    private ArtikalService artikalService;

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

    @PostMapping("dodajArtikal")
    public ResponseEntity<Porudzbina> dodajArtikal( @RequestBody PorudzbinaArtikalDto dto, HttpSession sesija)
    {
        if(!sesijaService.validacijaUloge(sesija, "Kupac"))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Artikal artikal = artikalService.NadjiArtikal(dto.getId());

        String korisnickoImeKupca = sesijaService.getKorisnickoIme(sesija);
        Kupac kupac = kupacService.findByKorisnickoIme(korisnickoImeKupca);

        Porudzbina porudzbina;
        UUID porudzbinaId = (UUID) sesija.getAttribute("porudzbina_id");
        if(porudzbinaId == null){
            porudzbina = new Porudzbina(artikal.getRestoran(), LocalDateTime.now(), artikal.getCena().multiply(BigDecimal.valueOf(dto.getKolicina())), kupac, Porudzbina.Status.Obrada, null);
            porudzbinaService.sacuvajPorudzbinu(porudzbina);
            sesija.setAttribute("porudzbina_id", porudzbina.getId());
        }else{
            porudzbina = porudzbinaService.dobaviPorudzbinuPoId(porudzbinaId);
            porudzbina.setCena(porudzbina.getCena().add(artikal.getCena().multiply(BigDecimal.valueOf(dto.getKolicina()))));
            porudzbinaService.sacuvajPorudzbinu(porudzbina);
        }


        PorudzbinaArtikal porudzbinaArtikal = porudzbinaService.dobaviPorudzbinuArtikal(porudzbina, artikal);
        if(porudzbinaArtikal == null)
            porudzbinaArtikal = new PorudzbinaArtikal(artikal, porudzbina, dto.getKolicina());
        porudzbinaArtikal.setKolicina(dto.getKolicina());
        porudzbinaService.sacuvajPorudzbinaArtikal(porudzbinaArtikal);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("obrisiArtikal/{id}")
    public ResponseEntity obrisiArtikal(@PathVariable("id") Long id, HttpSession sesija){

        if(!sesijaService.validacijaUloge(sesija, "Kupac"))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Porudzbina porudzbina;
        Artikal artikal = artikalService.NadjiArtikal(id);
        UUID porudzbinaId = (UUID) sesija.getAttribute("porudzbina_id");
        if(porudzbinaId == null){
            return ResponseEntity.badRequest().build();
        }else{
            porudzbina = porudzbinaService.dobaviPorudzbinuPoId(porudzbinaId);
        }
        PorudzbinaArtikal porudzbinaArtikal = porudzbinaService.dobaviPorudzbinuArtikal(porudzbina, artikal);
        porudzbinaService.obrisiArtikle(porudzbinaArtikal.getId());
        return ResponseEntity.ok().build();
    }

}
