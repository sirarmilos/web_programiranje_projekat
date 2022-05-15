package vezbe.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vezbe.demo.dto.DodavanjeNovogArtiklaDto;
import vezbe.demo.dto.ObrisiArtikalDto;
import vezbe.demo.dto.RegistracijaDto;
import vezbe.demo.model.Artikal;
import vezbe.demo.model.Korisnik;
import vezbe.demo.model.Porudzbina;
import vezbe.demo.model.Restoran;
import vezbe.demo.service.ArtikalService;
import vezbe.demo.service.MenadzerService;
import vezbe.demo.service.RestoranService;
import vezbe.demo.service.SesijaService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RestController
public class MenadzerRestController {

    @Autowired
    private MenadzerService menadzerService;

    @Autowired
    private SesijaService sesijaService;

    @Autowired
    private RestoranService restoranService;

    @Autowired
    private ArtikalService artikalService;

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

    @PostMapping("api/menadzer/dodavanje_novog_artikla")
    public ResponseEntity MenadzerDodajeNoviArtikal(@RequestBody DodavanjeNovogArtiklaDto dodavanjeNovogArtiklaDto, HttpSession sesija)
    {
        HashMap<String, String> podaciGreske = Validacija(dodavanjeNovogArtiklaDto);

        Boolean povratna = sesijaService.validacijaUloge(sesija, "Menadzer");

        if(povratna == false)
        {
            return new ResponseEntity("Ne mozete da dodate novi artikal, niste menadzer.", HttpStatus.BAD_REQUEST);
        }

        Restoran restoran = menadzerService.NadjiRestoranGdeMenadzerRadi(sesijaService.getKorisnickoIme(sesija));

        Artikal artikal = dodavanjeNovogArtiklaDto.PrebaciUArtikal(dodavanjeNovogArtiklaDto, restoran);

        try{
            artikalService.MenadzerDodajeNoviArtikal(artikal);
        } catch (Exception e)
        {
            podaciGreske.put("Artikal", e.getMessage());
        }

        if(podaciGreske.isEmpty() == false)
        {
            return new ResponseEntity(podaciGreske, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Menadzer je dodao novi artikal u restoran za koji je zaduzen", HttpStatus.OK);
    }

    @PostMapping("api/menadzer/obrisi_artikal") // sa @PathVariable kad proradi, ne znam zasto nece
    public ResponseEntity ObrisiArtikal(@RequestBody ObrisiArtikalDto obrisiArtikalDto, HttpSession sesija)
    {
        Boolean povratna = sesijaService.validacijaUloge(sesija, "Menadzer");

        if(povratna == false)
        {
            return new ResponseEntity("Ne mozete da obrisete artikal, niste menadzer.", HttpStatus.BAD_REQUEST);
        }

        Long id = obrisiArtikalDto.PrebaciULong(obrisiArtikalDto);
        Boolean povratnaVrednost = false;

        povratnaVrednost = artikalService.ObrisiArtikal(id);

        if(povratnaVrednost == false)
        {
            return new ResponseEntity("Ne mozete da obrisete artikal koji ne postoji.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Artikal je uspesno obrisan", HttpStatus.OK);
    }

    private HashMap<String, String> Validacija(DodavanjeNovogArtiklaDto dodavanjeNovogArtiklaDto)
    {
        HashMap<String, String> podaciGreske = new HashMap<>();

        if(dodavanjeNovogArtiklaDto.getNaziv() == null || dodavanjeNovogArtiklaDto.getNaziv().isEmpty() == true)
        {
            podaciGreske.put("Naziv", "Naziv je obavezan podatak");
        }

        if(dodavanjeNovogArtiklaDto.getCena() == null)
        {
            podaciGreske.put("Cena", "Cena je obavezan podatak");
        }

        if(dodavanjeNovogArtiklaDto.getTip() == null)
        {
            podaciGreske.put("Tip", "Tip je obavezan podatak");
        }

        return podaciGreske;
    }
}
