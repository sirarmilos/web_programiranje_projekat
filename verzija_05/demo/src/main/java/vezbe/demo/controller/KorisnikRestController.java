package vezbe.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("api/korisnik/pregled_podataka")
    public ResponseEntity PregledPodataka(HttpSession sesija)
    {
        HashMap<String, String> podaciGreske = new HashMap<>();

        Korisnik korisnik = null;

        try{
            korisnik = korisnikService.PregledPodataka(sesijaService.getKorisnickoIme(sesija));//(pregledPodatakaDto.getKorisnickoIme());
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

    @PutMapping("api/korisnik/azuriranje_podataka")
    public ResponseEntity AzuriranjePodataka(@RequestBody RegistracijaDto registracijaDto, HttpSession sesija)
    {
        String korisnickoIme = sesijaService.getKorisnickoIme(sesija);

        if(korisnickoIme.equals(registracijaDto.getKorisnickoIme()) == false)
        {
            return new ResponseEntity("Ne mozete da menjate podatke od druge osobe.", HttpStatus.BAD_REQUEST);
        }

        if(korisnickoIme.isEmpty() == true)
        {
            return new ResponseEntity("Odbaceno", HttpStatus.FORBIDDEN);
        }

        String uloga = sesijaService.getUloga(sesija);

        registracijaDto.setKorisnickoIme(korisnickoIme);

        HashMap<String, String> podaciGreske = Validacija(registracijaDto);

        if(podaciGreske.isEmpty() == false)
        {
            return new ResponseEntity(podaciGreske, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(korisnikService.Azuriranje(registracijaDto), HttpStatus.OK);
    }

    private HashMap<String, String> Validacija(RegistracijaDto registracijaDto)
    {
            HashMap<String, String> podaciGreske = new HashMap<>();

            if(registracijaDto.getIme().isEmpty() == true)
            {
                podaciGreske.put("Ime ", "Ime je obavezan podatak");
            }

            if(registracijaDto.getPrezime().isEmpty() == true)
            {
                podaciGreske.put("Prezime", "Prezime je obavezan podatak");
            }

            if(registracijaDto.getKorisnickoIme().isEmpty() == true)
            {
                podaciGreske.put("Korisnicko ime", "Korisnicko ime je obavezan podatak");
            }

            if(registracijaDto.getLozinka().isEmpty() == true)
            {
                podaciGreske.put("Lozinka", "Lozinka je obavezan podatak");
            }

            return podaciGreske;
    }


}
