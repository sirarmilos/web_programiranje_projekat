package vezbe.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vezbe.demo.dto.AzuriranjeKorisnikDto;
import vezbe.demo.dto.LogovanjeDto;
import vezbe.demo.dto.PregledPodatakaDto;
import vezbe.demo.dto.RegistracijaDto;
import vezbe.demo.model.Korisnik;
import vezbe.demo.service.KorisnikService;
import vezbe.demo.service.SesijaService;

import javax.naming.directory.InvalidAttributesException;
import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
public class KorisnikRestController {

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private SesijaService sesijaService;

    @Autowired
    public KorisnikRestController(KorisnikService korisnikService, SesijaService sesijaService) {this.korisnikService = korisnikService; this.sesijaService = sesijaService;}

    @GetMapping(value ="api/korisnik/pregled_podataka",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity PregledPodataka(HttpSession sesija)
    {
        HashMap<String, String> podaciGreske = new HashMap<>();

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
            return new ResponseEntity("Ne mozete da vidite svoje podatke, zato sto niste nijedan tip korisnika. Ulogujte se.", HttpStatus.BAD_REQUEST);
        }

        Korisnik korisnik = null;

        try{
            korisnik = korisnikService.PregledPodataka(sesijaService.getKorisnickoIme(sesija)); //("bojan61");
        } catch (AccountNotFoundException e)
        {
            podaciGreske.put("Korisnicko ime", e.getMessage());
        }

        if(podaciGreske.isEmpty() == false || korisnik == null)
        {
            return new ResponseEntity(podaciGreske, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(korisnik, HttpStatus.OK);
    }

    @PutMapping(value = "api/korisnik/azuriranje_podataka",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity AzuriranjePodataka(@RequestBody AzuriranjeKorisnikDto azuriranjeKorisnikDto, HttpSession sesija)
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
            return new ResponseEntity("Ne mozete da azurirate podatke o korisniku, zato sto niste nijedan tip korisnika. Ulogujte se.", HttpStatus.BAD_REQUEST);
        }

        String korisnickoIme = sesijaService.getKorisnickoIme(sesija);

        if(korisnickoIme.equals(azuriranjeKorisnikDto.getKorisnickoIme()) == false)
        {
            return new ResponseEntity("Ne mozete da menjate podatke drugog korisnika.", HttpStatus.BAD_REQUEST);
        }

        HashMap<String, String> podaciGreske = ValidacijaAzuriranje(azuriranjeKorisnikDto);

        if(podaciGreske.isEmpty() == false)
        {
            return new ResponseEntity(podaciGreske, HttpStatus.BAD_REQUEST);
        }

        azuriranjeKorisnikDto.setKorisnickoIme(korisnickoIme);

        return new ResponseEntity(korisnikService.Azuriranje(azuriranjeKorisnikDto), HttpStatus.OK);
    }

    private HashMap<String, String> ValidacijaAzuriranje(AzuriranjeKorisnikDto azuriranjeKorisnikDto)
    {
        HashMap<String, String> podaciGreske = new HashMap<>();

        if(azuriranjeKorisnikDto.getKorisnickoIme() == null || azuriranjeKorisnikDto.getKorisnickoIme().equals("") == true)
        {
            podaciGreske.put("Korisnicko ime ", "Korisnicko ime je obavezan podatak.");
        }

        if(azuriranjeKorisnikDto.getLozinka() == null || azuriranjeKorisnikDto.getLozinka().equals("") == true)
        {
            podaciGreske.put("Lozinka", "Lozinka je obavezan podatak.");
        }

        if(azuriranjeKorisnikDto.getIme() == null || azuriranjeKorisnikDto.getIme().equals("") == true)
        {
            podaciGreske.put("Ime", "Ime je obavezan podatak.");
        }

        if(azuriranjeKorisnikDto.getPrezime() == null || azuriranjeKorisnikDto.getPrezime().equals("") == true)
        {
            podaciGreske.put("Prezime", "Prezime je obavezan podatak.");
        }

        return podaciGreske;
    }


}
