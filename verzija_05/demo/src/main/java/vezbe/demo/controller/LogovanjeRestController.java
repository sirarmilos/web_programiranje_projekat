package vezbe.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vezbe.demo.dto.LogovanjeDto;
import vezbe.demo.dto.LogovanjeDtoSlanje;
import vezbe.demo.dto.RegistracijaDto;
import vezbe.demo.model.Korisnik;
import vezbe.demo.service.LogovanjeService;
import vezbe.demo.service.SesijaService;

import javax.naming.directory.InvalidAttributesException;
import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
public class LogovanjeRestController {

    @Autowired
    private LogovanjeService logovanjeService;

    @Autowired
    private SesijaService sesijaService;

    @Autowired
    public LogovanjeRestController(LogovanjeService logovanjeService) {this.logovanjeService = logovanjeService;}

    @PostMapping(value="api/odlogovanje",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity Odlogovanje(HttpSession session){
        session.invalidate();
        return new ResponseEntity("Successfully logged out", HttpStatus.OK);
    }

    @PostMapping(value ="api/logovanje",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity Logovanje(@RequestBody LogovanjeDto logovanjeDto, HttpSession sesija)
    {
        HashMap<String, String> podaciGreske = ValidacijaLogovanja(logovanjeDto);

        if(podaciGreske.isEmpty() == false)
        {
            return new ResponseEntity(podaciGreske, HttpStatus.BAD_REQUEST);
        }

        Korisnik korisnik = null;

        try{
            korisnik = logovanjeService.Logovanje(logovanjeDto.getKorisnickoIme(), logovanjeDto.getLozinka());
        } catch (AccountNotFoundException e)
        {
            podaciGreske.put("Korisnicko ime", e.getMessage());
        } catch (InvalidAttributesException e)
        {
            podaciGreske.put("Lozinka", e.getMessage());
        }

        if(podaciGreske.isEmpty() == false || korisnik == null)
        {
            return new ResponseEntity(podaciGreske, HttpStatus.BAD_REQUEST);
        }

        sesija.setAttribute("uloga", korisnik.getClass().getName());
        sesija.setAttribute("korisnickoIme", korisnik.getKorisnickoIme());

        String ulogaZaSlanje = "proba";

        if(korisnik.getClass().getName().equals("vezbe.demo.model.Kupac"))
        {
            ulogaZaSlanje = "kupac";
        }
        else if(korisnik.getClass().getName().equals("vezbe.demo.model.Admin"))
        {
            ulogaZaSlanje = "admin";
        }
        else if(korisnik.getClass().getName().equals("vezbe.demo.model.Dostavljac"))
        {
            ulogaZaSlanje = "dostavljac";
        }
        else if(korisnik.getClass().getName().equals("vezbe.demo.model.Menadzer"))
        {
            ulogaZaSlanje = "menadzer";
        }

        String sesijaId = (String)sesija.getAttribute("jsessionid");

        //LogovanjeDtoSlanje logovanjeDtoSlanje = new LogovanjeDtoSlanje(korisnik, ulogaZaSlanje, sesija);

        LogovanjeDtoSlanje logovanjeDtoSlanje = new LogovanjeDtoSlanje(korisnik, ulogaZaSlanje);

        return new ResponseEntity(logovanjeDtoSlanje, HttpStatus.OK);
    }

    private HashMap<String, String> ValidacijaLogovanja(LogovanjeDto logovanjeDto)
    {
        HashMap<String, String> podaciGreske = new HashMap<>();

        if(logovanjeDto.getKorisnickoIme() == null || logovanjeDto.getKorisnickoIme().isEmpty() == true)
        {
            podaciGreske.put("Korisnicko ime", "Korisnicko ime je obavezan podatak");
        }

        if(logovanjeDto.getLozinka() == null || logovanjeDto.getLozinka().isEmpty() == true)
        {
            podaciGreske.put("Lozinka", "Lozinka je obavezno polje");
        }

        return podaciGreske;
    }
}
