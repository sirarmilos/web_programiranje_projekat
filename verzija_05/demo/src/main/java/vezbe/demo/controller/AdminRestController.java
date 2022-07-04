package vezbe.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vezbe.demo.dto.*;
import vezbe.demo.model.*;
import vezbe.demo.service.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class AdminRestController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private SesijaService sesijaService;

    @Autowired
    private RestoranService restoranService;

    @Autowired
    private MenadzerService menadzerService;

    @Autowired
    private LokacijaService lokacijaService;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private ArtikalService artikalService;

    @Autowired
    private PorudzbinaService porudzbinaService;

    @Autowired
    private PorudzbinaArtikalService porudzbinaArtikalService;

    @GetMapping(value="api/admin/pregled_svih_korisnika",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity PregledSvihPodatakaOdStraneAdmina(HttpSession sesija)
    {
        Boolean povratna = sesijaService.validacijaUloge(sesija, "Admin");

        if(povratna == false)
        {
            return new ResponseEntity("Ne mozete da vidite podatke o drugim korisnicima, niste admin.", HttpStatus.BAD_REQUEST);
        }

        List<Korisnik> listaKorisnika = korisnikService.UzmiSveKorisnike();//adminService.PregledSvihPodatakaOdStraneAdmina();

        List<PregledSvihKorisnikaDto> listaPregledSvihKorisnikaDto = new ArrayList<>();

        try {
            for (Korisnik korisnik : listaKorisnika) {
                String korisnickaUloga = "proba";

                if (korisnik.getClass().getName().equals("vezbe.demo.model.Kupac")) {
                    korisnickaUloga = "kupac";
                } else if (korisnik.getClass().getName().equals("vezbe.demo.model.Admin")) {
                    korisnickaUloga = "admin";
                } else if (korisnik.getClass().getName().equals("vezbe.demo.model.Dostavljac")) {
                    korisnickaUloga = "dostavljac";
                } else if (korisnik.getClass().getName().equals("vezbe.demo.model.Menadzer")) {
                    korisnickaUloga = "menadzer";
                }

                PregledSvihKorisnikaDto pregledSvihKorisnikaDto = new PregledSvihKorisnikaDto(korisnik.getKorisnickoIme(), korisnik.getLozinka(), korisnik.getIme(), korisnik.getPrezime(), korisnik.getPol(), korisnik.getDatumRodjenja(), korisnickaUloga);

                listaPregledSvihKorisnikaDto.add(pregledSvihKorisnikaDto);
            }
        } catch (Exception e){
            return new ResponseEntity("Korisnici" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(listaPregledSvihKorisnikaDto, HttpStatus.OK);
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

    @PostMapping(value="api/admin/kreiraj_dostavljaca",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
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

    @PostMapping(value="api/admin/kreiraj_restoran",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
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

    @DeleteMapping(value="api/admin/obrisi_restoran/{id}")
    public ResponseEntity ObrisiRestoran(@PathVariable("id") Long id)
    {
        Restoran restoran = restoranService.NadjiRestoranPoId(id);

        Lokacija lokacija = restoran.getLokacija();

        List<Menadzer> menadzeri = menadzerService.SviMenadzeri();

        if(menadzeri == null)
        {
            System.out.println("pozzzz");
        }

        Menadzer menadzer = null;

        for(Menadzer m : menadzeri)
        {
            System.out.println("bbbb");


            if(m.getRestoran().getId().equals(id) == true)
            {
                menadzer = m;
                System.out.println(m.getKorisnickoIme());
                break;
            }
        }

        menadzer.setRestoran(null);
        this.adminService.sacuvajMenadzera(menadzer);

        List<Komentar> listaKomentara = this.adminService.nadjiSveKomentare(restoran);

        for(Komentar k : listaKomentara)
        {
            k.setRestoran(null);
            this.adminService.sacuvajKomentar(k);
        }

        List<Artikal> artikli = artikalService.NadjiSveArtikleIzDatogRestorana(restoran);

        List<Porudzbina> porudzbine = porudzbinaService.dobaviPorudzbinePoRestoranu(restoran);

        for(Porudzbina p : porudzbine)
        {
            p.setRestoran(null);
            adminService.sacuvajPorudzbinu(p);
        }

        adminService.obrisiRestoran(restoran);

       /* List<PorudzbinaArtikal> paLista = new ArrayList<>();
        List<List<PorudzbinaArtikal>> dzumbus = new ArrayList<>(new ArrayList<>());

        for(Artikal a : artikli)
        {
            paLista = porudzbinaArtikalService.NadjiSvePorudzbinaArtikalSaOvimArtiklom(a);
            dzumbus.add(paLista);
        }

        System.out.println("a");

        System.out.println(menadzer);

        menadzerService.ObrisiMenadzera(menadzer);

        System.out.println("b");

        for(List<PorudzbinaArtikal> pa : dzumbus)
        {
            for(PorudzbinaArtikal jednaPa : pa)
            {
                porudzbinaArtikalService.Obrisi(jednaPa);
            }
        }

        System.out.println("c");

        for(Porudzbina p : porudzbine)
        {
            porudzbinaService.ObrisiPorudzbinu(p);
        }

        System.out.println("d");

        for(Artikal aa : artikli)
        {
            artikalService.ObrisiArtikal(aa);
        }

        System.out.println("e");

        restoranService.ObrisiRestoranSaId(id);

        System.out.println("f");

        lokacijaService.ObrisiLokacijuPoId(lokacija.getId());*/



        return new ResponseEntity("ok", HttpStatus.OK);




        /*Restoran restoran = restoranService.NadjiRestoranPoId(id);

        List<Menadzer> listaSvihMenadzera = menadzerService.SviMenadzeri();

        System.out.println("a");

        List<Artikal> listaArtikala = artikalService.NadjiSveArtikleIzDatogRestorana(restoran);

        System.out.println("b");

        for(Artikal artikal : listaArtikala)
        {
            System.out.println("c");
            List<PorudzbinaArtikal> listaPA = porudzbinaArtikalService.NadjiSvePorudzbinaArtikalSaOvimArtiklom(artikal);
            for(PorudzbinaArtikal pa : listaPA)
            {
                System.out.println("d");
                porudzbinaArtikalService.Obrisi(pa);
            }
            //artikalService.ObrisiArtikal(artikal);
        }
        System.out.println("e");
        menadzerService.ObrisiMenadzeraSaIdDatogRestorana(id);

        System.out.println("f");

        Long id_lokacije = restoran.getLokacija().getId();

        restoranService.ObrisiRestoranSaId(id);

        System.out.println("g");

        lokacijaService.ObrisiLokacijuPoId(id_lokacije);

        System.out.println("h");

        for(Artikal artikal : listaArtikala)
        {
            System.out.println("m");
            artikalService.ObrisiArtikal(artikal);
        }

        return new ResponseEntity("Obrisano", HttpStatus.OK);*/
        // ides obrnuto, prvo brises menadzera, pa restoran, pa lokaciju
    }

    @PostMapping(value="api/admin/pretraga_korisnika",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity PretragaKorisnika(@RequestBody PretragaKorisnikaDto pretragaKorisnikaDto, HttpSession sesija)
    {
        Boolean povratna;
        povratna = sesijaService.validacijaUloge(sesija, "Admin");

        if(povratna == false)
        {
            return new ResponseEntity("Ne mozete da vidite podatke o korisnicima, zato sto niste admin.", HttpStatus.BAD_REQUEST);
        }

        HashMap<String, String> podaciGreske = new HashMap<>();

        List<Korisnik> trazeniKorisnici = null;

        try{
            trazeniKorisnici = korisnikService.PretragaKorisnika(pretragaKorisnikaDto);
        } catch (Exception e){
            podaciGreske.put("Korisnici", e.getMessage());
        }

        if(trazeniKorisnici == null)
        {
            return new ResponseEntity(podaciGreske, HttpStatus.BAD_REQUEST);
        }

        List<PregledSvihKorisnikaDto> listaPregledSvihKorisnikaDto = new ArrayList<>();

        try {
            for (Korisnik korisnik : trazeniKorisnici) {
                String korisnickaUloga = "proba";

                if (korisnik.getClass().getName().equals("vezbe.demo.model.Kupac")) {
                    korisnickaUloga = "kupac";
                } else if (korisnik.getClass().getName().equals("vezbe.demo.model.Admin")) {
                    korisnickaUloga = "admin";
                } else if (korisnik.getClass().getName().equals("vezbe.demo.model.Dostavljac")) {
                    korisnickaUloga = "dostavljac";
                } else if (korisnik.getClass().getName().equals("vezbe.demo.model.Menadzer")) {
                    korisnickaUloga = "menadzer";
                }

                PregledSvihKorisnikaDto pregledSvihKorisnikaDto = new PregledSvihKorisnikaDto(korisnik.getKorisnickoIme(), korisnik.getLozinka(), korisnik.getIme(), korisnik.getPrezime(), korisnik.getPol(), korisnik.getDatumRodjenja(), korisnickaUloga);

                listaPregledSvihKorisnikaDto.add(pregledSvihKorisnikaDto);
            }
        } catch (Exception e){
            return new ResponseEntity("Korisnici" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(listaPregledSvihKorisnikaDto, HttpStatus.OK);
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
