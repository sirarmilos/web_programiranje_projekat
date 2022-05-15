package vezbe.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vezbe.demo.dto.PretragaRestoranaDto;
import vezbe.demo.model.Restoran;
import vezbe.demo.service.RestoranService;
import vezbe.demo.service.SesijaService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RestController
public class RestoranRestController {

    @Autowired
    private RestoranService restoranService;

    @Autowired
    private SesijaService sesijaService;

    @GetMapping("api/restoran/prikaz_restorana")
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

    @GetMapping("api/restoran/pretraga")
    public ResponseEntity PretragaRestorana(@RequestBody PretragaRestoranaDto pretragaRestoranaDto)
    {
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

    @GetMapping("api/korisnik/izbor_restorana/{id}")
    public ResponseEntity PrikazIzabranogRestorana(@PathVariable Long id)
    {

    }

}
