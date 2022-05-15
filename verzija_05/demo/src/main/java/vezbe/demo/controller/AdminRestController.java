package vezbe.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vezbe.demo.dto.*;
import vezbe.demo.model.*;
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
    //public ResponseEntity KreiranjeMenadzera(@RequestBody KreiranjeMenadzeraDto kreiranjeMenadzeraDto, @RequestBody KreiranjeRestoranaDto kreiranjeRestoranaDto, @RequestBody KreiranjeLokacijeDto kreiranjeLokacijeDto, HttpSession sesija)
    //public ResponseEntity KreiranjeMenadzera(@RequestBody KreiranjeMenadzeraDto kreiranjeMenadzeraDto, KreiranjeRestoranaDto kreiranjeRestoranaDto, KreiranjeLokacijeDto kreiranjeLokacijeDto, HttpSession sesija)
    public ResponseEntity KreiranjeMenadzera(@RequestBody KreiranjeTriDto kreiranjeTriDto, HttpSession sesija)
    {
        /*
        {
    "korisnickoIme" : "mmmm",
    "lozinka" : "mmmm",
    "ime" : "mmmmm",
    "prezime" : "aaaaaaaaa",
    "naziv" : "prvi",
    "tip" : "neki",
    "geografskaDuzina" : 10,
    "geografskaSirina" : 20,
    "adresa" : "neka adresa"
}

         */


        HashMap<String, String> podaciGreske = ValidacijaT1(kreiranjeTriDto);// HashMap<String, String> podaciGreske = Validacija(kreiranjeMenadzeraDto);

        if(podaciGreske.isEmpty() == false)
        {
            return new ResponseEntity<>(podaciGreske, HttpStatus.BAD_REQUEST);
        }

        Boolean povratna = sesijaService.validacijaUloge(sesija, "Admin");

        if(povratna == false)
        {
            return new ResponseEntity("Vi niste admin i ne mozete da kreirate menadzera", HttpStatus.BAD_REQUEST);
        }

        // prvo kreiram lokaciju, pa onda nakacim to na restoran koji kreiram, pa onda to vezem za menadzera kojeg kreiram

        Lokacija lokacija = kreiranjeTriDto.PrebaciULokaciju();// Lokacija lokacija = kreiranjeLokacijeDto.PrebaciULokaciju();

        try{
            adminService.KreiranjeLokacije(lokacija);
        } catch (Exception e){
            podaciGreske.put("Lokacija", e.getMessage());
        }

        Restoran restoran = kreiranjeTriDto.PrebaciURestoran(lokacija);// Restoran restoran = kreiranjeRestoranaDto.PrebaciURestoran();

        try{
            adminService.KreiranjeRestorana(restoran);
        } catch (Exception e){
            podaciGreske.put("Restoran", e.getMessage());
        }

        Menadzer menadzer = kreiranjeTriDto.PrebaciUMenadzera(restoran);// Menadzer menadzer = kreiranjeMenadzeraDto.PrebaciUMenadzera(restoran);

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

    @PostMapping("api/admin/kreiraj_dostavljaca")
    public ResponseEntity KreiranjeDostavljaca(@RequestBody KreiranjeDostavljacaDto kreiranjeDostavljacaDto, HttpSession sesija)
    {
        HashMap<String, String> podaciGreske = ValidacijaDostavljac(kreiranjeDostavljacaDto);

        if(podaciGreske.isEmpty() == false)
        {
            return new ResponseEntity<>(podaciGreske, HttpStatus.BAD_REQUEST);
        }

        Boolean povratna = sesijaService.validacijaUloge(sesija, "Admin");

        if(povratna == false)
        {
            return new ResponseEntity("Vi niste admin i ne mozete da kreirate dostavljaca", HttpStatus.BAD_REQUEST);
        }

        Dostavljac dostavljac = kreiranjeDostavljacaDto.PrebaciUDostavljaca();

        try{
            adminService.KreiranjeDostavljaca(dostavljac, "Dostavljac");
        } catch (Exception e){
            podaciGreske.put("Korisnicko ime", e.getMessage());
        }

        if(podaciGreske.isEmpty() == false)
        {
            return new ResponseEntity(podaciGreske, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Ok", HttpStatus.OK);
    }

    @PostMapping("api/admin/kreiraj_restoran")
    public ResponseEntity KreiranjeRestoran(@RequestBody KreiranjeTriDto kreiranjeTriDto, HttpSession sesija)
    {
        HashMap<String, String> podaciGreske = ValidacijaT1(kreiranjeTriDto);// HashMap<String, String> podaciGreske = Validacija(kreiranjeMenadzeraDto);

        if(podaciGreske.isEmpty() == false)
        {
            return new ResponseEntity<>(podaciGreske, HttpStatus.BAD_REQUEST);
        }

        Boolean povratna = sesijaService.validacijaUloge(sesija, "Admin");

        if(povratna == false)
        {
            return new ResponseEntity("Vi niste admin i ne mozete da kreirate restoran", HttpStatus.BAD_REQUEST);
        }

        // prvo kreiram lokaciju, pa onda nakacim to na restoran koji kreiram, pa onda to vezem za menadzera kojeg kreiram

        Lokacija lokacija = kreiranjeTriDto.PrebaciULokaciju();// Lokacija lokacija = kreiranjeLokacijeDto.PrebaciULokaciju();

        try{
            adminService.KreiranjeLokacije(lokacija);
        } catch (Exception e){
            podaciGreske.put("Lokacija", e.getMessage());
        }

        Restoran restoran = kreiranjeTriDto.PrebaciURestoran(lokacija);// Restoran restoran = kreiranjeRestoranaDto.PrebaciURestoran();

        try{
            adminService.KreiranjeRestorana(restoran);
        } catch (Exception e){
            podaciGreske.put("Restoran", e.getMessage());
        }

        Menadzer menadzer = kreiranjeTriDto.PrebaciUMenadzera(restoran);// Menadzer menadzer = kreiranjeMenadzeraDto.PrebaciUMenadzera(restoran);

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

    private HashMap<String, String> ValidacijaT1(KreiranjeTriDto kreiranjeMenadzeraDto)
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

    private HashMap<String, String> ValidacijaDostavljac(KreiranjeDostavljacaDto kreiranjeDostavljacaDto)
    {
        HashMap<String, String> podaciGreske = new HashMap<>();

        if(kreiranjeDostavljacaDto.getIme() == null || kreiranjeDostavljacaDto.getIme().isEmpty() == true)
        {
            podaciGreske.put("Ime", "Ime je obavezan podatak");
        }

        if(kreiranjeDostavljacaDto.getPrezime() == null || kreiranjeDostavljacaDto.getPrezime().isEmpty() == true)
        {
            podaciGreske.put("Prezime", "Prezime je obavezan podatak");
        }

        if(kreiranjeDostavljacaDto.getKorisnickoIme() == null || kreiranjeDostavljacaDto.getKorisnickoIme().isEmpty() == true)
        {
            podaciGreske.put("Korisnicko ime", "Korisnicko ime je obavezan podatak");
        }

        if(kreiranjeDostavljacaDto.getLozinka() == null || kreiranjeDostavljacaDto.getLozinka().isEmpty() == true)
        {
            podaciGreske.put("Lozinka", "Lozinka je obavezan podatak");
        }

        return podaciGreske;
    }

}
