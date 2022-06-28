package vezbe.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vezbe.demo.dto.ArtikalZaPretragaArtikalPoIdDto;
import vezbe.demo.dto.PretragaRestoranaDto;
import vezbe.demo.dto.PrikazRestoranaDostavljacDto;
import vezbe.demo.dto.PrikaziIzabraniRestoranDto;
import vezbe.demo.model.Artikal;
import vezbe.demo.model.Komentar;
import vezbe.demo.model.Lokacija;
import vezbe.demo.model.Restoran;
import vezbe.demo.service.*;

import javax.servlet.http.HttpSession;
import java.time.LocalTime;
import java.util.ArrayList;
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

    @Autowired
    private LokacijaService lokacijaService;

    @GetMapping(value="api/pretraga_artikla_po_id/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity PretragaArtikalaPoId(@PathVariable ("id") Long id)
    {
        Artikal artikal = artikalService.NadjiArtikal(id);

        ArtikalZaPretragaArtikalPoIdDto ar = new ArtikalZaPretragaArtikalPoIdDto(artikal.getNaziv(), artikal.getCena(), artikal.getOpis());
        return new ResponseEntity(ar, HttpStatus.OK);
    }

    @GetMapping(value="api/dostavljac/prikaz_restorana",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity DostavljacPregledRestorana(HttpSession sesija)
    {

        Boolean povratna;
        povratna = sesijaService.validacijaUloge(sesija, "Dostavljac");

        if(povratna == false)
        {
            return new ResponseEntity("Ne mozete da vidite ove podatke o restoranima, zato sto niste dostavljac.", HttpStatus.BAD_REQUEST);
        }


        List<Restoran> listaSvihRestorana = restoranService.PregledSvihRestorana();

        List<PrikazRestoranaDostavljacDto> lista = new ArrayList<>();

        for(Restoran restoran : listaSvihRestorana)
        {
            Lokacija lokacija = restoran.getLokacija();//lokacijaService.NadjiLokacijuPoId(restoran.getLokacija().getId());
            lista.add(new PrikazRestoranaDostavljacDto(restoran.getNaziv(), restoran.getTip(), lokacija.getAdresa(), lokacija.getGeografskaSirina(), lokacija.getGeografskaDuzina()));
        }

        return new ResponseEntity(lista, HttpStatus.OK);
    }

    @PostMapping(value = "api/restoran/dostavljac_pretraga",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity PretragaRestoranaZaDostavljaca(@RequestBody PretragaRestoranaDto pretragaRestoranaDto, HttpSession sesija)
    {
        Boolean povratna;
        povratna = sesijaService.validacijaUloge(sesija, "Dostavljac");

        if(povratna == false)
        {
            return new ResponseEntity("Ne mozete da vidite ove podatke o restoranima, zato sto niste dostavljac.", HttpStatus.BAD_REQUEST);
        }

        HashMap<String, String> podaciGreske = new HashMap<>();

        List<Restoran> listaSvihRestorana = null;

        try{
            listaSvihRestorana = restoranService.PretragaRestorana(pretragaRestoranaDto);//restoranService.PregledSvihRestorana();
        } catch (Exception e){
            podaciGreske.put("Restorani", e.getMessage());
        }

        if(listaSvihRestorana == null)
        {
            return new ResponseEntity(podaciGreske, HttpStatus.BAD_REQUEST);
        }

        List<PrikazRestoranaDostavljacDto> lista = new ArrayList<>();

        try{
            for(Restoran restoran : listaSvihRestorana)
            {
                Lokacija lokacija = restoran.getLokacija();//lokacijaService.NadjiLokacijuPoId(restoran.getLokacija().getId());
                lista.add(new PrikazRestoranaDostavljacDto(restoran.getNaziv(), restoran.getTip(), lokacija.getAdresa(), lokacija.getGeografskaSirina(), lokacija.getGeografskaDuzina()));
            }
        }catch (Exception e){
            return new ResponseEntity("Restorani" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(lista, HttpStatus.OK);
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

        List<PretragaRestoranaDto> lista = new ArrayList<>();

        for(Restoran restoran : listaSvihRestorana)
        {
            Lokacija lokacija = restoran.getLokacija();//lokacijaService.NadjiLokacijuPoId(restoran.getLokacija().getId());
            lista.add(new PretragaRestoranaDto(restoran.getId(), restoran.getNaziv(), restoran.getTip(), lokacija.getAdresa()));
        }

        return new ResponseEntity(lista, HttpStatus.OK);
    }

    @PostMapping(value = "api/restoran/pretraga",
            consumes = MediaType.APPLICATION_JSON_VALUE,
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

        List<PretragaRestoranaDto> lista = new ArrayList<>();

        try{
            for(Restoran restoran : trazeniRestorani)
            {
                Lokacija lokacija = restoran.getLokacija();//lokacijaService.NadjiLokacijuPoId(restoran.getLokacija().getId());
                lista.add(new PretragaRestoranaDto(restoran.getId(), restoran.getNaziv(), restoran.getTip(), lokacija.getAdresa()));
            }
        }catch (Exception e){
            return new ResponseEntity("Restorani" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(lista, HttpStatus.OK);
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
