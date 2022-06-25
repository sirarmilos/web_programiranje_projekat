package vezbe.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vezbe.demo.dto.ArtikalZaPretragaArtikalPoIdDto;
import vezbe.demo.dto.PretragaRestoranaDto;
import vezbe.demo.dto.PrikaziIzabraniRestoranDto;
import vezbe.demo.model.Artikal;
import vezbe.demo.model.Komentar;
import vezbe.demo.model.Lokacija;
import vezbe.demo.model.Restoran;
import vezbe.demo.service.ArtikalService;
import vezbe.demo.service.KomentarService;
import vezbe.demo.service.RestoranService;
import vezbe.demo.service.SesijaService;

import javax.servlet.http.HttpSession;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

@RestController
public class RestoranRestController {

    @Autowired
    private RestoranService restoranService;

    @Autowired
    private SesijaService sesijaService;

    @Autowired
    private ArtikalService artikalService;

    @Autowired
    private KomentarService komentarService;

    @GetMapping(value="api/pretraga_artikla_po_id/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity PretragaArtikalaPoId(@PathVariable ("id") Long id)
    {
        Artikal artikal = artikalService.NadjiArtikal(id);

        ArtikalZaPretragaArtikalPoIdDto ar = new ArtikalZaPretragaArtikalPoIdDto(artikal.getNaziv(), artikal.getCena(), artikal.getOpis());
        return new ResponseEntity(ar, HttpStatus.OK);
    }

    @GetMapping(value ="api/restoran/prikaz_restorana",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity PregledSvihRestorana(HttpSession sesija)
    {
        Boolean povratna;
        povratna = sesijaService.validacijaUloge(sesija, "Admin");
        if(povratna != true)
        {
            povratna = sesijaService.validacijaUloge(sesija, "Menadzer");
            if(povratna != true)
            {
                povratna = sesijaService.validacijaUloge(sesija, "Dostavljac");
                if(povratna != true)
                {
                    povratna = sesijaService.validacijaUloge(sesija, "Kupac");
                }
            }
        }

        if(povratna == false)
        {
            return new ResponseEntity("Ne mozete da vidite podatke o restoranima, zato sto niste nijedan tip korisnika. Ulogujte se.", HttpStatus.BAD_REQUEST);
        }

        List<Restoran> listaSvihRestorana = restoranService.PregledSvihRestorana();

        return new ResponseEntity(listaSvihRestorana, HttpStatus.OK);
    }

    @GetMapping(value = "api/restoran/pretraga",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity PretragaRestorana(@RequestBody PretragaRestoranaDto pretragaRestoranaDto, HttpSession sesija)
    {
        Boolean povratna;
        povratna = sesijaService.validacijaUloge(sesija, "Admin");
        if(povratna != true)
        {
            povratna = sesijaService.validacijaUloge(sesija, "Menadzer");
            if(povratna != true)
            {
                povratna = sesijaService.validacijaUloge(sesija, "Dostavljac");
                if(povratna != true)
                {
                    povratna = sesijaService.validacijaUloge(sesija, "Kupac");
                }
            }
        }

        if(povratna == false)
        {
            return new ResponseEntity("Ne mozete da vidite podatke o restoranima, zato sto niste nijedan tip korisnika. Ulogujte se.", HttpStatus.BAD_REQUEST);
        }

        HashMap<String, String> podaciGreske = new HashMap<>();

        List<Restoran> trazeniRestorani = null;

        try{
            trazeniRestorani = restoranService.PretragaRestorana(pretragaRestoranaDto);
        } catch (Exception e){
            podaciGreske.put("Restorani", e.getMessage());
        }

        if(trazeniRestorani == null)
        {
            return new ResponseEntity(podaciGreske, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(trazeniRestorani, HttpStatus.OK);
    }

    @GetMapping(value="api/korisnik/izbor_restorana/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity PrikazIzabranogRestorana(@PathVariable Long id, HttpSession sesija)
    {
        Boolean povratna;
        povratna = sesijaService.validacijaUloge(sesija, "Admin");
        if(povratna != true)
        {
            povratna = sesijaService.validacijaUloge(sesija, "Menadzer");
            if(povratna != true)
            {
                povratna = sesijaService.validacijaUloge(sesija, "Dostavljac");
                if(povratna != true)
                {
                    povratna = sesijaService.validacijaUloge(sesija, "Kupac");
                }
            }
        }

        if(povratna == false)
        {
            return new ResponseEntity("Ne mozete da vidite podatke o restoranima, zato sto niste nijedan tip korisnika. Ulogujte se.", HttpStatus.BAD_REQUEST);
        }

        Restoran restoran;

        restoran = restoranService.NadjiRestoranPoId(id);

        if(restoran == null)
        {
            return new ResponseEntity("Ne postoji restoran sa zadatim ID-jem.", HttpStatus.BAD_REQUEST);
        }

        List<Artikal> listArtikalaIzRestorana = artikalService.NadjiSveArtikleIzDatogRestorana(restoran);

        Lokacija lokacija = restoran.getLokacija();

        LocalTime trenutno = LocalTime.now();
        LocalTime granica = LocalTime.of(20, 0,0);

        int razlika = trenutno.compareTo(granica);

        Boolean b;

        if(razlika <= 0)
        {
            b = true;
        }
        else
        {
            b = false;
        }

        String prosecna_ocena = komentarService.ProsecnaOcena(restoran);

        List<Komentar> listaKomentara = komentarService.SviKomentariZaRestoran(restoran);

        PrikaziIzabraniRestoranDto prikaziIzabraniRestoranDto = new PrikaziIzabraniRestoranDto(restoran, lokacija, b, prosecna_ocena, listaKomentara, listArtikalaIzRestorana);

        return new ResponseEntity(prikaziIzabraniRestoranDto, HttpStatus.OK);
    }
}
