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
import java.math.BigDecimal;
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
            return new ResponseEntity("Ne mozete da vidite podatke o drugim korisnicima, niste admin.", HttpStatus.BAD_REQUEST);
        }

        List<Korisnik> listaKorisnika = adminService.PregledSvihPodatakaOdStraneAdmina();

        return new ResponseEntity(listaKorisnika, HttpStatus.OK);
    }

    @PostMapping("api/admin/kreiraj_menadzera")
    public ResponseEntity KreiranjeMenadzera(@RequestBody KreiranjeTriDto kreiranjeTriDto, HttpSession sesija)
    {
        HashMap<String, String> podaciGreske = ValidacijaKreiranjeMenadzera(kreiranjeTriDto);

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

        Lokacija lokacija = kreiranjeTriDto.PrebaciULokaciju();

        try{
            adminService.KreiranjeLokacije(lokacija);
        } catch (Exception e){
            podaciGreske.put("Lokacija", e.getMessage());
        }

        Restoran restoran = kreiranjeTriDto.PrebaciURestoran(lokacija);

        try{
            adminService.KreiranjeRestorana(restoran);
        } catch (Exception e){
            podaciGreske.put("Restoran", e.getMessage());
        }

        Menadzer menadzer = kreiranjeTriDto.PrebaciUMenadzera(restoran);

        try{
            adminService.KreiranjeMenadzera(menadzer);
        } catch (Exception e){
            podaciGreske.put("Korisnicko ime", e.getMessage());
        }

        if(podaciGreske.isEmpty() == false)
        {
            return new ResponseEntity(podaciGreske, HttpStatus.BAD_REQUEST);
        }

        adminService.SacuvajMenadzera(lokacija, restoran, menadzer);

        return new ResponseEntity("Uspesno ste kreirali menadzera, njegov novi restoran, kao i lokaciju restorana.", HttpStatus.OK);
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
            adminService.KreiranjeDostavljaca(dostavljac);
        } catch (Exception e){
            podaciGreske.put("Korisnicko ime", e.getMessage());
        }

        if(podaciGreske.isEmpty() == false)
        {
            return new ResponseEntity(podaciGreske, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Uspesno ste kreirali dostavljaca.", HttpStatus.OK);
    }

    @PostMapping("api/admin/kreiraj_restoran")
    public ResponseEntity KreiranjeRestoran(@RequestBody KreiranjeTriDto kreiranjeTriDto, HttpSession sesija)
    {
        HashMap<String, String> podaciGreske = ValidacijaKreiranjeMenadzera(kreiranjeTriDto);

        if(podaciGreske.isEmpty() == false)
        {
            return new ResponseEntity<>(podaciGreske, HttpStatus.BAD_REQUEST);
        }

        Boolean povratna = sesijaService.validacijaUloge(sesija, "Admin");

        if(povratna == false)
        {
            return new ResponseEntity("Vi niste admin i ne mozete da kreirate restoran.", HttpStatus.BAD_REQUEST);
        }

        // prvo kreiram lokaciju, pa onda nakacim to na restoran koji kreiram, pa onda to vezem za menadzera kojeg kreiram

        Lokacija lokacija = kreiranjeTriDto.PrebaciULokaciju();

        try{
            adminService.KreiranjeLokacije(lokacija);
        } catch (Exception e){
            podaciGreske.put("Lokacija", e.getMessage());
        }

        Restoran restoran = kreiranjeTriDto.PrebaciURestoran(lokacija);

        try{
            adminService.KreiranjeRestorana(restoran);
        } catch (Exception e){
            podaciGreske.put("Restoran", e.getMessage());
        }

        Menadzer menadzer = kreiranjeTriDto.PrebaciUMenadzera(restoran);

        try{
            adminService.KreiranjeMenadzera(menadzer);
        } catch (Exception e){
            podaciGreske.put("Korisnicko ime", e.getMessage());
        }

        if(podaciGreske.isEmpty() == false)
        {
            return new ResponseEntity(podaciGreske, HttpStatus.BAD_REQUEST);
        }

        adminService.SacuvajMenadzera(lokacija, restoran, menadzer);

        return new ResponseEntity("Uspesno ste kreirali restoran, njegovu lokaciju i menadzera koji ce biti zaduzen za njega.", HttpStatus.OK);
    }

    private HashMap<String, String> ValidacijaKreiranjeMenadzera(KreiranjeTriDto kreiranjeMenadzeraDto)
    {
        HashMap<String, String> podaciGreske = new HashMap<>();

        if(kreiranjeMenadzeraDto.getIme() == null || kreiranjeMenadzeraDto.getIme().isEmpty() == true)
        {
            podaciGreske.put("Ime", "Ime je obavezan podatak.");
        }

        if(kreiranjeMenadzeraDto.getPrezime() == null || kreiranjeMenadzeraDto.getPrezime().isEmpty() == true)
        {
            podaciGreske.put("Prezime", "Prezime je obavezan podatak.");
        }

        if(kreiranjeMenadzeraDto.getKorisnickoIme() == null || kreiranjeMenadzeraDto.getKorisnickoIme().isEmpty() == true)
        {
            podaciGreske.put("Korisnicko ime", "Korisnicko ime je obavezan podatak.");
        }

        if(kreiranjeMenadzeraDto.getLozinka() == null || kreiranjeMenadzeraDto.getLozinka().isEmpty() == true)
        {
            podaciGreske.put("Lozinka", "Lozinka je obavezan podatak.");
        }

        if(kreiranjeMenadzeraDto.getNaziv() == null || kreiranjeMenadzeraDto.getNaziv().isEmpty() == true)
        {
            podaciGreske.put("Naziv", "Naziv je obavezan podatak.");
        }

        if(kreiranjeMenadzeraDto.getTip() == null || kreiranjeMenadzeraDto.getTip().isEmpty() == true)
        {
            podaciGreske.put("Tip", "Tip je obavezan podatak.");
        }

        BigDecimal nula = new BigDecimal(0);

        if(kreiranjeMenadzeraDto.getGeografskaDuzina() == null)
        {
            podaciGreske.put("Geografska duzina", "Geografska duzina je obavezan podatak.");
        }
        else if((kreiranjeMenadzeraDto.getGeografskaDuzina().compareTo(nula)) != 1)
        {
            podaciGreske.put("Geografska duzina", "Geografska duzina mora biti broj veci od 0.");
        }

        if(kreiranjeMenadzeraDto.getGeografskaSirina() == null)
        {
            podaciGreske.put("Geografska sirina", "Geografska sirina je obavezan podatak.");
        }
        else if((kreiranjeMenadzeraDto.getGeografskaSirina().compareTo(nula)) != 1)
        {
            podaciGreske.put("Geografska sirina", "Geografska sirina mora biti broj veci od 0.");
        }

        if(kreiranjeMenadzeraDto.getAdresa() == null || kreiranjeMenadzeraDto.getAdresa().isEmpty() == true)
        {
            podaciGreske.put("Adresa", "Adresa je obavezan podatak");
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
