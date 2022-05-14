package vezbe.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import vezbe.demo.dto.KreiranjeMenadzeraDto;
import vezbe.demo.dto.RegistracijaDto;
import vezbe.demo.model.Korisnik;
import vezbe.demo.model.Kupac;
import vezbe.demo.model.Menadzer;
import vezbe.demo.service.AdminService;
import vezbe.demo.service.SesijaService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RestController
public class AdminRestController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private SesijaService sesijaService;

    @GetMapping("api/admin/pregled_svih_korisnika")
    public ResponseEntity PregledSvihPodatakaOdStraneAdmina(HttpSession sesija)
    {
        Boolean povratna = sesijaService.validacijaUloge(sesija, "Admin");

        if(povratna == false)
        {
            return new ResponseEntity("Ne mozete da vidite podatke o drugim osobama.", HttpStatus.BAD_REQUEST);
        }

        List<Korisnik> listaKorisnika = adminService.PregledSvihPodatakaOdStraneAdmina();

        return new ResponseEntity(listaKorisnika, HttpStatus.OK);
    }

    @PostMapping("api/admin/kreiraj_menadzera")
    public ResponseEntity KreiranjeMenadzera(KreiranjeMenadzeraDto kreiranjeMenadzeraDto, HttpSession sesija)
    {
        HashMap<String, String> podaciGreske = Validacija(kreiranjeMenadzeraDto);

        if(podaciGreske.isEmpty() == false)
        {
            return new ResponseEntity<>(podaciGreske, HttpStatus.BAD_REQUEST);
        }

        Boolean povratna = sesijaService.validacijaUloge(sesija, "Admin");

        if(povratna == false)
        {
            return new ResponseEntity("Vi niste admin i ne mozete da kreirate menadzera", HttpStatus.BAD_REQUEST);
        }

        Menadzer menadzer = kreiranjeMenadzeraDto.PrebaciUMenadzera();

        try{
            adminService.KreiranjeMenadzera(menadzer, "Menadzer");
        } catch (Exception e){
            podaciGreske.put("Korisnicko ime", e.getMessage());
        }

        if(podaciGreske.isEmpty() == false)
        {
            return new ResponseEntity(podaciGreske, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Ok", HttpStatus.OK);
    }

    private HashMap<String, String> Validacija(KreiranjeMenadzeraDto kreiranjeMenadzeraDto)
    {
        HashMap<String, String> podaciGreske = new HashMap<>();

        if(kreiranjeMenadzeraDto.getIme() == null || kreiranjeMenadzeraDto.getIme().isEmpty() == true)
        {
            podaciGreske.put("Ime", "Ime je obavezan podatak");
        }

        if(kreiranjeMenadzeraDto.getPrezime() == null || kreiranjeMenadzeraDto.getPrezime().isEmpty() == true)
        {
            podaciGreske.put("Prezime", "Prezime je obavezan podatak");
        }

        if(kreiranjeMenadzeraDto.getKorisnickoIme() == null || kreiranjeMenadzeraDto.getKorisnickoIme().isEmpty() == true)
        {
            podaciGreske.put("Korisnicko ime", "Korisnicko ime je obavezan podatak");
        }

        if(kreiranjeMenadzeraDto.getLozinka() == null || kreiranjeMenadzeraDto.getLozinka().isEmpty() == true)
        {
            podaciGreske.put("Lozinka", "Lozinka je obavezan podatak");
        }

        return podaciGreske;
    }

}
