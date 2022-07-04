package vezbe.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vezbe.demo.dto.NoviKomentarDodavanjeDto;
import vezbe.demo.model.*;
import vezbe.demo.service.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
public class KomentarRestController {

    @Autowired
    private KomentarService komentarService;

    @Autowired
    private RestoranService restoranService;

    @Autowired
    private KupacService kupacService;

    @Autowired
    private PorudzbinaService porudzbinaService;

    @Autowired
    private PorudzbinaArtikalService porudzbinaArtikalService;

    @Autowired
    private ArtikalService artikalService;

    @GetMapping(value="api/nadji_restoran_po_id_porudzbini/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity NadjiRestoranPoPorudzbiniId(@PathVariable ("id") UUID id)
    {
        System.out.println("a");
        System.out.println(id);
        Porudzbina porudzbina = porudzbinaService.nadjiPorudzbinuPoId(id);
        //System.out.println(porudzbina.getId());
        //System.out.println(porudzbina);

        if(porudzbina.getStatus().equals(Porudzbina.Status.Dostavljena) == false)
        {
            return new ResponseEntity("Ne mozete da ostavite komentar zato sto vase porudzbina nije jos u statusu 'Dostavljena'.", HttpStatus.BAD_REQUEST);
        }

        List<PorudzbinaArtikal> lista = porudzbinaArtikalService.NadjiSvePorudzbinaArtikalSaOvimId(id);

        Artikal artikal = artikalService.NadjiArtikal(1l);

        for(PorudzbinaArtikal pa : lista)
        {
            artikal = pa.getArtikal();
            break;
        }

        Restoran restoran;

        restoran = Objects.requireNonNull(artikal).getRestoran();

        System.out.println(artikal.getId());
        System.out.println(restoran.getId());

        //return new ResponseEntity(restoranService.DobaviRestoranIDPoUUID(id), HttpStatus.OK);
        return new ResponseEntity(restoran.getId(), HttpStatus.OK);
    }

    @PostMapping(value="api/dodavanje_komentara",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity DodavanjeNovogKomentara(@RequestBody NoviKomentarDodavanjeDto noviKomentarDodavanjeDto, HttpSession sesija)
    {
        Long id_restorana = noviKomentarDodavanjeDto.getRestoran_id();

        Restoran restoran = restoranService.DobaviRestoranPoId(id_restorana);

        String korIme = noviKomentarDodavanjeDto.getKorisnickoIme();

        Kupac kupac = kupacService.findByKorisnickoIme(korIme);

        String kom = noviKomentarDodavanjeDto.getTekstKomentara();

        Komentar.Ocena ocena = Komentar.Ocena.Dobro;

        if(noviKomentarDodavanjeDto.getOcena().equals("JakoLose"))
        {
            ocena = Komentar.Ocena.JakoLose;
        }
        if(noviKomentarDodavanjeDto.getOcena().equals("Lose"))
        {
            ocena = Komentar.Ocena.Lose;
        }
        if(noviKomentarDodavanjeDto.getOcena().equals("Dobro"))
        {
            ocena = Komentar.Ocena.Dobro;
        }
        if(noviKomentarDodavanjeDto.getOcena().equals("VeomaDobro"))
        {
            ocena = Komentar.Ocena.VeomaDobro;
        }
        if(noviKomentarDodavanjeDto.getOcena().equals("Odlicno"))
        {
            ocena = Komentar.Ocena.Odlicno;
        }

        Komentar komentar = new Komentar(kupac, restoran, kom, ocena);

        komentarService.SacuvajKomentar(komentar);

        return new ResponseEntity("Uspesno ste dodali novi komentar", HttpStatus.OK);
    }
}
