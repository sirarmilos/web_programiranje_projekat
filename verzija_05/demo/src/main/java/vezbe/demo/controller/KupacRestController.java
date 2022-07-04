package vezbe.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vezbe.demo.dto.TipKupcaBrojPoenaDto;
import vezbe.demo.dto.VratiKupcaDto;
import vezbe.demo.model.Kupac;
import vezbe.demo.service.KupacService;
import vezbe.demo.service.SesijaService;

import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class KupacRestController {

    @Autowired
    private KupacService kupacService;

    @Autowired
    private SesijaService sesijaService;

    @GetMapping(value = "/api/kupac/pregled_podataka",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity PregledTipKupca(HttpSession sesija)
    {
        HashMap<String, String> podaciGreske = new HashMap<>();

        Boolean povratna;
        povratna = sesijaService.validacijaUloge(sesija, "Admin");
        if(povratna != true)
        {
            povratna = sesijaService.validacijaUloge(sesija, "Kupac");
        }

        if(povratna == false)
        {
            return new ResponseEntity("Ne mozete da tip kupca. Ulogujte se.", HttpStatus.BAD_REQUEST);
        }

        TipKupcaBrojPoenaDto tipKupcaBrojPoenaDto = null;

        Kupac kupac = null;
        try{
            kupac = kupacService.VratiKupca(sesijaService.getKorisnickoIme(sesija));
        }catch (AccountNotFoundException e)
        {
            podaciGreske.put("Korisnicko ime", e.getMessage());
        }

        if(podaciGreske.isEmpty() == false || kupac == null)
        {
            return new ResponseEntity(podaciGreske, HttpStatus.BAD_REQUEST);
        }

        String tip_kupca = "";
        BigDecimal br_poena;

        if(kupac.getTipKupca() == null)
        {
            tip_kupca = "Nijedan tip kupca";
            br_poena = new BigDecimal(0);
        }
        else
        {
            tip_kupca = kupac.getTipKupca().getIme().toString();
            br_poena = kupac.getBrojSakupljenihBodova();
        }

        // broj sakupljenih bodova i tip kupca

        VratiKupcaDto vratiKupcaDto = new VratiKupcaDto(kupac.getKorisnickoIme(), kupac.getLozinka(), kupac.getIme(), kupac.getPrezime(), kupac.getPol(), kupac.getDatumRodjenja(), tip_kupca, br_poena);

        return new ResponseEntity(vratiKupcaDto, HttpStatus.OK);
    }
}
