package vezbe.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vezbe.demo.dto.AzuriranjeArtiklaDto;
import vezbe.demo.dto.DodavanjeNovogArtiklaDto;
import vezbe.demo.dto.PrikazRestoranaDto;
import vezbe.demo.model.*;
import vezbe.demo.service.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@RestController
public class MenadzerRestController {

    @Autowired
    private MenadzerService menadzerService;

    @Autowired
    private SesijaService sesijaService;

    @Autowired
    private RestoranService restoranService;

    @Autowired
    private ArtikalService artikalService;

    @Autowired
    private PorudzbinaService porudzbinaService;

    @Autowired
    private PorudzbinaArtikalService porudzbinaArtikalService;


    @GetMapping(value="api/menadzer/dobavi_id_restorana",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity DobaviIdRestoranaZaMenadzer(HttpSession sesija)
    {
        String korIme = (String) sesija.getAttribute("korisnickoIme");

        Menadzer menadzer = menadzerService.NadjiMenadzerSaKorisnickimImenom(korIme);

        Long id = menadzer.getRestoran().getId();
        // nadji id restorana preko sesije, i onda to prosledis preko localstoragea i ucitas u onoj stranici Moj Restoran

        return new ResponseEntity(id, HttpStatus.OK);
    }

    @GetMapping(value = "api/menadzer/pregled_pojedinacnog_artikla/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity InformacijeOPojedinacnomArtiklu(@PathVariable ("id") Long id)
    {
        String tip;
        String kolicina;
        Artikal artikal = artikalService.NadjiArtikal(id);
        if(artikal.getTip() == Artikal.Tip.Jelo)
        {
            tip = "Jelo";
        }else
        {
            tip = "Pice";
        }
        if(artikal.getKolicina() == Artikal.Kolicina.g)
        {
            kolicina = "g";
        }
        else
        {
            kolicina = "ml";
        }

        AzuriranjeArtiklaDto azuriranjeArtiklaDto = new AzuriranjeArtiklaDto(artikal.getId(), artikal.getNaziv(), artikal.getCena(), tip, kolicina, artikal.getOpis());

        return new ResponseEntity(azuriranjeArtiklaDto, HttpStatus.OK);
    }

    @GetMapping(value="api/menadzer/pregled_restorana",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity MenadzerPregledRestorana(HttpSession sesija)
    {
        Boolean povratna = sesijaService.validacijaUloge(sesija, "Menadzer");

        if(povratna == false)
        {
            return new ResponseEntity("Ne mozete da vidite podatke o restoranu, niste menadzer.", HttpStatus.BAD_REQUEST);
        }

        Restoran restoran = menadzerService.NadjiRestoranGdeMenadzerRadi(sesijaService.getKorisnickoIme(sesija));

        List<Artikal> artikli = artikalService.NadjiSveArtikleIzDatogRestorana(restoran);

        List<Porudzbina> porudzbine = porudzbinaService.dobaviPorudzbinePoRestoranu(restoran);

        PrikazRestoranaDto prikazRestoranaDto = new PrikazRestoranaDto(restoran, artikli, porudzbine);

        return new ResponseEntity(prikazRestoranaDto, HttpStatus.OK);
    }

    @GetMapping("api/menadzer/pregled_porudzbina")
    ResponseEntity MenadzerPregledPorudzbina(HttpSession sesija)
    {
        Boolean povratna = sesijaService.validacijaUloge(sesija, "Menadzer");

        if(povratna == false)
        {
            return new ResponseEntity("Ne mozete da vidite podatke o narudzbinama restorana, niste menadzer.", HttpStatus.BAD_REQUEST);
        }

        Restoran restoran = menadzerService.NadjiRestoranGdeMenadzerRadi(sesijaService.getKorisnickoIme(sesija));

        List<Porudzbina> listaPorudzbina = restoranService.NadjiSvePorudzbineZaRestoranZaKojiJeMenadzerNadlezan(restoran);

        return new ResponseEntity(listaPorudzbina, HttpStatus.OK);
    }

    @PostMapping(value="api/menadzer/dodavanje_novog_artikla",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity MenadzerDodajeNoviArtikal(/*@RequestBody DodavanjeNovogArtiklaDto dodavanjeNovogArtiklaDto,*/@RequestBody MultipartFile multipartFile, HttpSession sesija) throws IOException
    {
        System.out.println(multipartFile);
        DodavanjeNovogArtiklaDto dodavanjeNovogArtiklaDto = null;//new ObjectMapper().readValue(multipartFile, DodavanjeNovogArtiklaDto.class);

        HashMap<String, String> podaciGreske = ValidacijaDodavanja(dodavanjeNovogArtiklaDto);

        Boolean povratna = sesijaService.validacijaUloge(sesija, "Menadzer");

        if(povratna == false)
        {
            return new ResponseEntity("Ne mozete da dodate novi artikal, niste menadzer.", HttpStatus.BAD_REQUEST);
        }

        Restoran restoran = menadzerService.NadjiRestoranGdeMenadzerRadi(sesijaService.getKorisnickoIme(sesija));

        Artikal artikal = dodavanjeNovogArtiklaDto.PrebaciUArtikal(dodavanjeNovogArtiklaDto, restoran);

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        artikal.setPhotos(fileName);


        try{
            artikalService.MenadzerDodajeNoviArtikal(artikal, restoran);
        } catch (Exception e)
        {
            podaciGreske.put("Artikal", e.getMessage());
        }

        if(podaciGreske.isEmpty() == false)
        {
            return new ResponseEntity(podaciGreske, HttpStatus.BAD_REQUEST);
        }

        String uploadDir = "user-photos/" + artikal.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);


        return new ResponseEntity("Menadzer je dodao novi artikal u restoran za koji je zaduzen.", HttpStatus.OK);
    }

    @DeleteMapping(value="api/menadzer/obrisi_artikal/{id}")
    public ResponseEntity ObrisiArtikal(@ModelAttribute Artikal artikal, HttpSession sesija) {
        Boolean povratna = sesijaService.validacijaUloge(sesija, "Menadzer");

        if (povratna == false) {
            return new ResponseEntity("Ne mozete da obrisete artikal, niste menadzer.", HttpStatus.BAD_REQUEST);
        }

        Artikal artikalTrazeni = artikalService.NadjiArtikal(artikal.getId());

        if(artikalTrazeni == null)
        {
            return new ResponseEntity("Ne mozete da obrisete artikal koji ne postoji.", HttpStatus.BAD_REQUEST);
        }

        String korisnickoIme = sesijaService.getKorisnickoIme(sesija);
        Restoran restoran = menadzerService.NadjiRestoranGdeMenadzerRadi(korisnickoIme);

        if(artikalTrazeni.getRestoran() == null)
        {
            return new ResponseEntity("Ne mozete da obrisete artikal koji ne postoji.", HttpStatus.BAD_REQUEST);
        }

        if(restoran.getId() == artikalTrazeni.getRestoran().getId()) {

           /* List<PorudzbinaArtikal> listaPorudzbinaArtikal = porudzbinaArtikalService.NadjiSvePorudzbinaArtikalSaOvimArtiklom(artikalTrazeni);

            List<Porudzbina> listaPorudzbina = porudzbinaService.NadjiSvePorudzbineSaOvimId(listaPorudzbinaArtikal);

            porudzbinaService.SmanjiCenuPorudzbinaNakonBrisanjaArtiklaIzRestorana(listaPorudzbina, artikalTrazeni);*/

            this.artikalService.ObrisiArtikal(artikalTrazeni);
        }
        else
        {
            return new ResponseEntity("Ne mozete da obrisete artikal koji nije u restoranu od ovog menadzera.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Artikal je uspesno obrisan", HttpStatus.OK);
    }

    @PutMapping(value="api/menadzer/azuriranje_artikla",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity AzurirajArtikal(@RequestBody AzuriranjeArtiklaDto azuriranjeArtiklaDto, HttpSession sesija)
    {
        HashMap<String, String> podaciGreske = ValidacijaAzuriranja(azuriranjeArtiklaDto);

        Boolean povratna = sesijaService.validacijaUloge(sesija, "Menadzer");

        if(podaciGreske.isEmpty() == false)
        {
            return new ResponseEntity(podaciGreske, HttpStatus.BAD_REQUEST);
        }

        if (povratna == false) {
            return new ResponseEntity("Ne mozete da azurirati artikal, niste menadzer.", HttpStatus.BAD_REQUEST);
        }

        String korisnickoIme = sesijaService.getKorisnickoIme(sesija);
        Restoran restoran = menadzerService.NadjiRestoranGdeMenadzerRadi(korisnickoIme);

        Boolean daLiJeIzTogRestorana = artikalService.NadjiRestoranOvogArtikla(azuriranjeArtiklaDto.getId(), restoran);

        if(daLiJeIzTogRestorana == false)
        {
            return new ResponseEntity("Ne mozete da azurirate artikal koji nije u restoranu od ovog menadzera.", HttpStatus.BAD_REQUEST);
        }

        if(podaciGreske.isEmpty() == false)
        {
            return new ResponseEntity(podaciGreske, HttpStatus.BAD_REQUEST);
        }

        try {
            artikalService.AzurirajArtikal(azuriranjeArtiklaDto, restoran);
        }catch (Exception e)
        {
            podaciGreske.put("Artikal", e.getMessage());
        }

        if(podaciGreske.isEmpty() == false)
        {
            return new ResponseEntity(podaciGreske, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Uspesno ste azurirali artikal!", HttpStatus.OK);

        //return new ResponseEntity(artikalService.AzurirajArtikal(azuriranjeArtiklaDto), HttpStatus.OK);
    }

    private HashMap<String, String> ValidacijaDodavanja(DodavanjeNovogArtiklaDto dodavanjeNovogArtiklaDto)
    {
        HashMap<String, String> podaciGreske = new HashMap<>();

        if(dodavanjeNovogArtiklaDto.getNaziv() == null || dodavanjeNovogArtiklaDto.getNaziv().isEmpty() == true)
        {
            podaciGreske.put("Naziv", "Naziv je obavezan podatak");
        }

        if(dodavanjeNovogArtiklaDto.getCena() == null || dodavanjeNovogArtiklaDto.getCena().equals("") == true)
        {
            podaciGreske.put("Cena", "Cena je obavezan podatak");
        }

        if(dodavanjeNovogArtiklaDto.getTip() == null || dodavanjeNovogArtiklaDto.getTip().equals("") == true)
        {
            podaciGreske.put("Tip", "Tip je obavezan podatak");
        }

        return podaciGreske;
    }

    private HashMap<String, String> ValidacijaAzuriranja(AzuriranjeArtiklaDto azuriranjeArtiklaDto)
    {
        HashMap<String, String> podaciGreske = new HashMap<>();

        if(azuriranjeArtiklaDto.getNaziv() == null || azuriranjeArtiklaDto.getNaziv().isEmpty() == true)
        {
            podaciGreske.put("Naziv", "Naziv je obavezan podatak");
        }

        BigDecimal nula = new BigDecimal(0);

        if(azuriranjeArtiklaDto.getCena() == null)
        {
            podaciGreske.put("Cena", "Cena je obavezan podatak");
        }
        else if((azuriranjeArtiklaDto.getCena().compareTo(nula)) != 1)
        {
            podaciGreske.put("Cena", "Cena mora biti broj veci od 0.");
        }

        if(azuriranjeArtiklaDto.getTip() == null || azuriranjeArtiklaDto.getTip().isEmpty() == true)
        {
            podaciGreske.put("Tip", "Tip je obavezan podatak");
        }

       /* if(azuriranjeArtiklaDto.getKolicina() == null || azuriranjeArtiklaDto.getKolicina().isEmpty() == true)
        {
            podaciGreske.put("Kolicina", "Kolicina ne moze biti null");
        }*/

        Long nulaLong = 0l;

        if(azuriranjeArtiklaDto.getId() == null)
        {
            podaciGreske.put("Id", "Id je obavezan da se unese ako zelite da azurirate neki proizvod");
        }
        else if(azuriranjeArtiklaDto.getId().compareTo(nulaLong) != 1)
        {
            podaciGreske.put("Id", "Id mora biti broj veci od 0");
        }

       /* if(azuriranjeArtiklaDto.getOpis() == null || azuriranjeArtiklaDto.getOpis().isEmpty() == true)
        {
            podaciGreske.put("Opis", "Opis ne moze biti null");
        }*/

        return podaciGreske;
    }
}
