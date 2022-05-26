package vezbe.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vezbe.demo.dto.RegistracijaDto;
import vezbe.demo.model.Kupac;
import vezbe.demo.service.RegistracijaService;
import vezbe.demo.service.SesijaService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
public class RegistracijaRestController {

    @Autowired
    private RegistracijaService registracijaService;

    @Autowired
    private SesijaService sesijaService;

    @PostMapping("api/registracija")
    public ResponseEntity Registracija(@RequestBody RegistracijaDto registracijaDto, HttpSession sesija)
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

        if(povratna == true)
        {
            return new ResponseEntity("Ne mozete se registrovati, vi ste trenutno ulogovani i imate nalog.", HttpStatus.BAD_REQUEST);
        }

        HashMap<String, String> podaciGreske = Validacija(registracijaDto);

        if(podaciGreske.isEmpty() == false)
        {
            return new ResponseEntity<>(podaciGreske, HttpStatus.BAD_REQUEST);
        }

        Kupac kupac = registracijaDto.PrebaciUKupca();

        try{
            registracijaService.Registracija(kupac);
        } catch (Exception e){
            podaciGreske.put("Korisnicko ime", e.getMessage());
        }

        if(podaciGreske.isEmpty() == false)
        {
            return new ResponseEntity(podaciGreske, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Uspesno ste se registrovali.", HttpStatus.OK);
    }

    private HashMap<String, String> Validacija(RegistracijaDto registracijaDto)
    {
        HashMap<String, String> podaciGreske = new HashMap<>();

        if(registracijaDto.getIme() == null || registracijaDto.getIme().isEmpty() == true)
        {
            podaciGreske.put("Ime", "Ime je obavezan podatak.");
        }

        if(registracijaDto.getPrezime() == null || registracijaDto.getPrezime().isEmpty() == true)
        {
            podaciGreske.put("Prezime", "Prezime je obavezan podatak.");
        }

        if(registracijaDto.getKorisnickoIme() == null || registracijaDto.getKorisnickoIme().isEmpty() == true)
        {
            podaciGreske.put("Korisnicko ime", "Korisnicko ime je obavezan podatak.");
        }

        if(registracijaDto.getLozinka() == null || registracijaDto.getLozinka().isEmpty() == true)
        {
            podaciGreske.put("Lozinka", "Lozinka je obavezan podatak.");
        }

        return podaciGreske;
    }
}
