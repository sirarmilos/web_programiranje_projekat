package vezbe.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vezbe.demo.dto.AzuriranjeArtiklaDto;
import vezbe.demo.dto.DodavanjeNovogArtiklaDto;
import vezbe.demo.model.*;
import vezbe.demo.service.ArtikalService;
import vezbe.demo.service.MenadzerService;
import vezbe.demo.service.RestoranService;
import vezbe.demo.service.SesijaService;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
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
        HashMap<String, String> podaciGreske = ValidacijaDodavanja(dodavanjeNovogArtiklaDto);

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

        return new ResponseEntity("Menadzer je dodao novi artikal u restoran za koji je zaduzen.", HttpStatus.OK);
    }

    @DeleteMapping("api/menadzer/obrisi_artikal/{id}")
    public ResponseEntity ObrisiArtikal(@ModelAttribute Artikal artikal, HttpSession sesija) {
        Boolean povratna = sesijaService.validacijaUloge(sesija, "Menadzer");

        if (povratna == false) {
            return new ResponseEntity("Ne mozete da obrisete artikal, niste menadzer.", HttpStatus.BAD_REQUEST);
        }

        Artikal artikalTrazeni = artikalService.NadjiArtikal(artikal.getId());

        if(artikalTrazeni == null)
        {
            return new ResponseEntity("Ne mozete da obrisete artikal koji ne postoji.", HttpStatus.BAD_REQUEST);
        }

        String korisnickoIme = sesijaService.getKorisnickoIme(sesija);
        Restoran restoran = menadzerService.NadjiRestoranGdeMenadzerRadi(korisnickoIme);

        if(artikalTrazeni.getRestoran() == null)
        {
            return new ResponseEntity("Ne mozete da obrisete artikal koji ne postoji.", HttpStatus.BAD_REQUEST);
        }

        if(restoran.getId() == artikalTrazeni.getRestoran().getId()) {
            this.artikalService.ObrisiArtikal(artikal);
        }
        else
        {
            return new ResponseEntity("Ne mozete da obrisete artikal koji nije u restoranu od ovog menadzera.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Artikal je uspesno obrisan", HttpStatus.OK);
    }

    @PutMapping("api/menadzer/azuriranje_artikla")
    public ResponseEntity AzurirajArtikal(@RequestBody AzuriranjeArtiklaDto azuriranjeArtiklaDto, HttpSession sesija)
    {
        HashMap<String, String> podaciGreske = ValidacijaAzuriranja(azuriranjeArtiklaDto);

        Boolean povratna = sesijaService.validacijaUloge(sesija, "Menadzer");

        if (povratna == false) {
            return new ResponseEntity("Ne mozete da azurirati artikal, niste menadzer.", HttpStatus.BAD_REQUEST);
        }

        String korisnickoIme = sesijaService.getKorisnickoIme(sesija);
        Restoran restoran = menadzerService.NadjiRestoranGdeMenadzerRadi(korisnickoIme);

        Boolean daLiJeIzTogRestorana = artikalService.NadjiRestoranOvogArtikla(azuriranjeArtiklaDto.getId(), restoran);

        if(daLiJeIzTogRestorana == false)
        {
            return new ResponseEntity("Ne mozete da azurirate artikal koji nije u restoranu od ovog menadzera.", HttpStatus.BAD_REQUEST);
        }

        if(podaciGreske.isEmpty() == false)
        {
            return new ResponseEntity(podaciGreske, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(artikalService.AzurirajArtikal(azuriranjeArtiklaDto), HttpStatus.OK);
    }

    private HashMap<String, String> ValidacijaDodavanja(DodavanjeNovogArtiklaDto dodavanjeNovogArtiklaDto)
    {
        HashMap<String, String> podaciGreske = new HashMap<>();

        if(dodavanjeNovogArtiklaDto.getNaziv() == null || dodavanjeNovogArtiklaDto.getNaziv().isEmpty() == true)
        {
            podaciGreske.put("Naziv", "Naziv je obavezan podatak");
        }

        if(dodavanjeNovogArtiklaDto.getCena() == null || dodavanjeNovogArtiklaDto.getCena().equals("") == true)
        {
            podaciGreske.put("Cena", "Cena je obavezan podatak");
        }

        if(dodavanjeNovogArtiklaDto.getTip() == null || dodavanjeNovogArtiklaDto.getTip().equals("") == true)
        {
            podaciGreske.put("Tip", "Tip je obavezan podatak");
        }

        return podaciGreske;
    }

    private HashMap<String, String> ValidacijaAzuriranja(AzuriranjeArtiklaDto azuriranjeArtiklaDto)
    {
        HashMap<String, String> podaciGreske = new HashMap<>();

        if(azuriranjeArtiklaDto.getNaziv() == null || azuriranjeArtiklaDto.getNaziv().isEmpty() == true)
        {
            podaciGreske.put("Naziv", "Naziv je obavezan podatak");
        }

        BigDecimal nula = new BigDecimal(0);

        if(azuriranjeArtiklaDto.getCena() == null)
        {
            podaciGreske.put("Cena", "Cena je obavezan podatak");
        }
        else if((azuriranjeArtiklaDto.getCena().compareTo(nula)) != 1)
        {
            podaciGreske.put("Cena", "Cena mora biti broj veci od 0.");
        }

        if(azuriranjeArtiklaDto.getTip() == null || azuriranjeArtiklaDto.getTip().isEmpty() == true)
        {
            podaciGreske.put("Tip", "Tip je obavezan podatak");
        }

       /* if(azuriranjeArtiklaDto.getKolicina() == null || azuriranjeArtiklaDto.getKolicina().isEmpty() == true)
        {
            podaciGreske.put("Kolicina", "Kolicina ne moze biti null");
        }*/

        Long nulaLong = 0l;

        if(azuriranjeArtiklaDto.getId() == null)
        {
            podaciGreske.put("Id", "Id je obavezan da se unese ako zelite da azurirate neki proizvod");
        }
        else if(azuriranjeArtiklaDto.getId().compareTo(nulaLong) != 1)
        {
            podaciGreske.put("Id", "Id mora biti broj veci od 0");
        }

       /* if(azuriranjeArtiklaDto.getOpis() == null || azuriranjeArtiklaDto.getOpis().isEmpty() == true)
        {
            podaciGreske.put("Opis", "Opis ne moze biti null");
        }*/

        return podaciGreske;
    }
}
