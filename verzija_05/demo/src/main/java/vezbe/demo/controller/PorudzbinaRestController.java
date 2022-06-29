package vezbe.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vezbe.demo.dto.ArtikalZaPregledPorudzbineDto;
import vezbe.demo.dto.IspisPojedinacnePorudzbineDto;
import vezbe.demo.dto.PorudzbinaArtikalDto;
import vezbe.demo.dto.PregledPorudzbineDto;
import vezbe.demo.model.*;
import vezbe.demo.service.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/porudzbina")
public class PorudzbinaRestController {

    @Autowired
    private PorudzbinaService porudzbinaService;

    @Autowired
    private MenadzerService menadzerService;

    @Autowired
    private SesijaService sesijaService;

    @Autowired
    private DostavljacService dostavljacService;

    @Autowired
    private KupacService kupacService;

    @Autowired
    private ArtikalService artikalService;

    @Autowired
    private PorudzbinaArtikalService porudzbinaArtikalService;

    public BigDecimal updateCena(Porudzbina p){
        BigDecimal suma = BigDecimal.ZERO;
        for(PorudzbinaArtikal pa: p.getPorudzbineArtikli()){
            suma = suma.add( pa.getArtikal().getCena().multiply(BigDecimal.valueOf(pa.getKolicina())));
            System.out.println(suma + "*");
        }

        return suma;
    }

    public BigDecimal updatePopust(Porudzbina p){
        BigDecimal cenaSaPopustom = new BigDecimal(0);
        cenaSaPopustom = (BigDecimal.ONE.subtract(p.getKupac().getTipKupca().getPopust().divide(BigDecimal.valueOf(100)))).multiply(p.getCena());
        return cenaSaPopustom;
    }

    @GetMapping(value="dobaviSve",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Porudzbina>> dobaviSveporudzbinePoUlogovanomKupcu(HttpSession sesija)
    {
        if(!sesijaService.validacijaUloge(sesija, "Kupac"))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        String korisnickoImeKupca = sesijaService.getKorisnickoIme(sesija);

        Kupac kupac = kupacService.findByKorisnickoIme(korisnickoImeKupca);
        List<Porudzbina> porudzbine  = porudzbinaService.dobaviPorudzbinePoKupcu(kupac);

        return ResponseEntity.ok(porudzbine);
    }

    @GetMapping(value="dobaviZaDostavljaca",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity dobaviSvePorudzbineZaDostavljaca(HttpSession sesija)
    {

        if(!sesijaService.validacijaUloge(sesija, "Dostavljac"))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        String korisnickoImeDostavljaca = sesijaService.getKorisnickoIme(sesija);
        Dostavljac dostavljac = dostavljacService.dobaviDostavljacaPoKorisnickomImenu(korisnickoImeDostavljaca);

        List<Porudzbina> porudzbine  = porudzbinaService.dobaviPorudzbinePoDostavljacu(dostavljac);

        return ResponseEntity.ok(porudzbine);
    }

    @GetMapping(value="MenadzerUrestoranu",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Porudzbina>> dobaviSvePorudzbineZaMenadzera(HttpSession sesija)
    {
        if(!sesijaService.validacijaUloge(sesija, "Menadzer"))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        String korisnickoImeMenadzera = sesijaService.getKorisnickoIme(sesija);
        Restoran restoranGdeMenadzerRadi = menadzerService.NadjiRestoranGdeMenadzerRadi(korisnickoImeMenadzera);

        List<Porudzbina> porudzbine  = porudzbinaService.dobaviPorudzbinePoRestoranu(restoranGdeMenadzerRadi);

        return ResponseEntity.ok(porudzbine);
    }

    @PostMapping(value="dodajArtikal",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Porudzbina> dodajArtikal( @RequestBody PorudzbinaArtikalDto dto, HttpSession sesija)
    {
        if(!sesijaService.validacijaUloge(sesija, "Kupac"))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Artikal artikal = artikalService.NadjiArtikal(dto.getId());

        String korisnickoImeKupca = sesijaService.getKorisnickoIme(sesija);
        Kupac kupac = kupacService.findByKorisnickoIme(korisnickoImeKupca);

        Porudzbina porudzbina = (Porudzbina) sesija.getAttribute("porudzbina");
        //UUID porudzbinaId = (UUID) sesija.getAttribute("porudzbina_id");
        System.out.println(dto.getKolicina());
        System.out.println(dto.getId());
        if(dto.getKolicina() < 1)
            return ResponseEntity.notFound().build();

        if(porudzbina == null){
            porudzbina = new Porudzbina(artikal.getRestoran(), LocalDateTime.now(), artikal.getCena().multiply(BigDecimal.valueOf(dto.getKolicina())), kupac, Porudzbina.Status.Obrada, null);
            //porudzbinaService.sacuvajPorudzbinu(porudzbina);
            //sesija.setAttribute("porudzbina_id", porudzbina.getId());

        }/*else{
            //porudzbina = porudzbinaService.dobaviPorudzbinuPoId(porudzbinaId);
            //porudzbina.setCena(porudzbina.getCena().add(artikal.getCena().multiply(BigDecimal.valueOf(dto.getKolicina()))));
            //porudzbinaService.sacuvajPorudzbinu(porudzbina);
        }*/

        if(porudzbina.getRestoran().getId() != artikal.getRestoran().getId()){
            return ResponseEntity.badRequest().build();
        }


        boolean izmenio = false;
        Set<PorudzbinaArtikal> porudzbinaArtikali = porudzbina.getPorudzbineArtikli();
        for(PorudzbinaArtikal pa: porudzbinaArtikali){
            if(pa.getArtikal().getId() == artikal.getId()){
                pa.setKolicina(dto.getKolicina());
                izmenio = true;
            }
        }
        if(izmenio == false)
            porudzbinaArtikali.add(new PorudzbinaArtikal(artikal, porudzbina, dto.getKolicina()));

        porudzbina.setCena(updateCena(porudzbina));

        /*PorudzbinaArtikal porudzbinaArtikal = porudzbinaService.dobaviPorudzbinuArtikal(porudzbina, artikal);

        if(porudzbinaArtikal == null)
            porudzbinaArtikal = new PorudzbinaArtikal(artikal, porudzbina, dto.getKolicina());
        porudzbinaArtikal.setKolicina(dto.getKolicina());
        porudzbinaService.sacuvajPorudzbinaArtikal(porudzbinaArtikal);*/
        sesija.setAttribute("porudzbina", porudzbina);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("obrisiArtikal/{id}")
    public ResponseEntity obrisiArtikal(@PathVariable("id") Long id, HttpSession sesija){

        if(!sesijaService.validacijaUloge(sesija, "Kupac"))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();


        Porudzbina porudzbina = (Porudzbina) sesija.getAttribute("porudzbina");
        if(porudzbina == null){
            return ResponseEntity.badRequest().build();
        }
        PorudzbinaArtikal porart = new PorudzbinaArtikal();
        boolean nasao = false;
        for(PorudzbinaArtikal pa: porudzbina.getPorudzbineArtikli()){
            if(pa.getArtikal().getId() == id){
                porart = pa;
                nasao = true;
            }
        }
        if(nasao){
            porudzbina.getPorudzbineArtikli().remove(porart);
            porudzbina.setCena(updateCena(porudzbina));
            if(porudzbina.getPorudzbineArtikli().size() == 0){
                sesija.removeAttribute("porudzbina");
            }
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }

        //PorudzbinaArtikal porudzbinaArtikal = porudzbinaService.dobaviPorudzbinuArtikal(porudzbina, artikal);
        //porudzbinaService.obrisiArtikle(porudzbinaArtikal.getId());

    }

    @GetMapping(value= "pregledPorudzbine",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PregledPorudzbineDto> dobaviSveArtikleZaPorudzbinu(HttpSession sesija)
    {
        if(!sesijaService.validacijaUloge(sesija, "Kupac"))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Porudzbina porudzbina = (Porudzbina) sesija.getAttribute("porudzbina");
        if(porudzbina == null){
            return ResponseEntity.badRequest().build();
        }

       List<PorudzbinaArtikal> pa = porudzbina.getPorudzbineArtikli().stream().toList();
       List<ArtikalZaPregledPorudzbineDto> ret = pa.stream().map(porudzbinaArtikal -> new ArtikalZaPregledPorudzbineDto(porudzbinaArtikal)).collect(Collectors.toList());

       if(porudzbina.getKupac().getTipKupca() == null){
           return ResponseEntity.ok(new PregledPorudzbineDto(ret, porudzbina.getCena(), porudzbina.getCena()));
       }else{
           return ResponseEntity.ok(new PregledPorudzbineDto(ret, porudzbina.getCena(), updatePopust(porudzbina)));
       }
    }

    @PostMapping(value="kreiranjePorudzbine",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity kreiranjePorudzbine(HttpSession sesija)
    {
        if(!sesijaService.validacijaUloge(sesija, "Kupac"))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Porudzbina porudzbina = (Porudzbina) sesija.getAttribute("porudzbina");
        if(porudzbina == null){
            return ResponseEntity.badRequest().build();
        }

        if(porudzbina.getKupac().getTipKupca() != null){
            porudzbina.setCena(updatePopust(porudzbina));
        }

        porudzbinaService.sacuvajPorudzbinu(porudzbina);

        sesija.removeAttribute("porudzbina");
        return ResponseEntity.ok().build();

    }

    @GetMapping(value="dobaviPorudzbinu/{uuid}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Porudzbina> dobaviJednuporudzbinuPoUlogovanomKupcu(@PathVariable("uuid") UUID id, HttpSession sesija)
    {
        if(!sesijaService.validacijaUloge(sesija, "Kupac"))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Porudzbina porudzbinaZaVracanje = porudzbinaService.dobaviPorudzbinuPoId(id);

        return ResponseEntity.ok(porudzbinaZaVracanje);
    }


    @GetMapping(value="dobavi_porudzbinu/{uuid}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity dobaviInformacijeOJednojPorudzbiniKupca(@PathVariable("uuid") UUID id, HttpSession sesija)
    {
        if(!sesijaService.validacijaUloge(sesija, "Kupac"))
        {
            return new ResponseEntity("Niste Kupac, ne mozete da vidite ovu informaciju", HttpStatus.BAD_REQUEST);
        }

        Porudzbina porudzbina = porudzbinaService.dobaviPorudzbinuPoId(id);

        List<PorudzbinaArtikal> sve = porudzbinaArtikalService.NadjiSvePorudzbinaArtikalSaOvimId(id);

        List<IspisPojedinacnePorudzbineDto> lista = new ArrayList<>();

        for(PorudzbinaArtikal pa : sve)
        {
            Artikal artikal = pa.getArtikal();
            Porudzbina porudzbina1 = pa.getPorudzbina();
            lista.add(new IspisPojedinacnePorudzbineDto(artikal.getNaziv(), artikal.getCena(), artikal.getOpis(), pa.getKolicina(), porudzbina1.getDatumVreme(), porudzbina1.getCena(), porudzbina1.getStatus()));
        }

        return new ResponseEntity(lista, HttpStatus.OK);
    }




    @PutMapping("izmenaStatusaUPripremi/{uuid}")
    public ResponseEntity promeniStatusUPripremi(@PathVariable("uuid") UUID id, HttpSession sesija)
    {
        if(!sesijaService.validacijaUloge(sesija, "Menadzer"))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        String user = sesijaService.getKorisnickoIme(sesija);
        Restoran restoran = menadzerService.NadjiRestoranGdeMenadzerRadi(user);
        Porudzbina p = porudzbinaService.dobaviPorudzbinuPoId(id);

        if(p.getStatus() == Porudzbina.Status.Obrada && restoran.getId().equals(p.getRestoran().getId())) {
            p.setStatus(Porudzbina.Status.UPripremi);
        }else {
            return ResponseEntity.badRequest().build();
        }

        porudzbinaService.sacuvajPorudzbinu(p);
        return ResponseEntity.ok().build();

    }

    @PutMapping("izmenaStatusaCekaDostavljaca/{uuid}")
    public ResponseEntity promeniStatusCekaDostavljaca(@PathVariable("uuid") UUID id, HttpSession sesija)
    {
        if(!sesijaService.validacijaUloge(sesija, "Menadzer"))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        String user = sesijaService.getKorisnickoIme(sesija);
        Restoran restoran = menadzerService.NadjiRestoranGdeMenadzerRadi(user);
        Porudzbina p = porudzbinaService.dobaviPorudzbinuPoId(id);

        if(p.getStatus() == Porudzbina.Status.UPripremi && restoran.getId().equals(p.getRestoran().getId())) {
            p.setStatus(Porudzbina.Status.CekaDostavljaca);
        }else {
            return ResponseEntity.badRequest().build();
        }

        porudzbinaService.sacuvajPorudzbinu(p);
        return ResponseEntity.ok().build();

    }

    @PutMapping("izmenaStatusaUTransportu/{uuid}")
    public ResponseEntity promeniStatusUTransportu(@PathVariable("uuid") UUID id, HttpSession sesija)
    {
        if(!sesijaService.validacijaUloge(sesija, "Dostavljac"))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        String user = sesijaService.getKorisnickoIme(sesija);
        Dostavljac d = dostavljacService.dobaviDostavljacaPoKorisnickomImenu(user);
        Porudzbina p = porudzbinaService.dobaviPorudzbinuPoId(id);

        if(p.getStatus() == Porudzbina.Status.CekaDostavljaca) {
            p.setStatus(Porudzbina.Status.UTransportu);
        }else {
            return ResponseEntity.badRequest().build();
        }

        p.setDostavljac(d);

        porudzbinaService.sacuvajPorudzbinu(p);
        return ResponseEntity.ok().build();

    }

    @PutMapping("izmenaStatusaDostavljena/{uuid}")
    public ResponseEntity promeniStatusDostavljena(@PathVariable("uuid") UUID id, HttpSession sesija)
    {
        if(!sesijaService.validacijaUloge(sesija, "Dostavljac"))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        String user = sesijaService.getKorisnickoIme(sesija);
        Dostavljac d = dostavljacService.dobaviDostavljacaPoKorisnickomImenu(user);
        Porudzbina p = porudzbinaService.dobaviPorudzbinuPoId(id);

        if(p.getStatus() == Porudzbina.Status.UTransportu && d.getKorisnickoIme().equals(p.getDostavljac().getKorisnickoIme())) {
            p.setStatus(Porudzbina.Status.Dostavljena);
        }else {
            return ResponseEntity.badRequest().build();
        }

        porudzbinaService.sacuvajPorudzbinu(p);

        p.getKupac().setBrojSakupljenihBodova(p.getKupac().getBrojSakupljenihBodova().add(p.getCena().divide(BigDecimal.valueOf(1000)).multiply(BigDecimal.valueOf(133))));

        TipKupca tipBronzani = kupacService.tipKupca(TipKupca.Ime.Bronzani);
        TipKupca tipSrebrni = kupacService.tipKupca(TipKupca.Ime.Srebrni);
        TipKupca tipZlatni = kupacService.tipKupca(TipKupca.Ime.Zlatni);
        /*
        System.out.println(p.getKupac().getBrojSakupljenihBodova());
        System.out.println(tipBronzani.getTrazeniBrojBodova());
        boolean a = p.getKupac().getBrojSakupljenihBodova().compareTo(tipBronzani.getTrazeniBrojBodova()) > 0;
        System.out.println(a);
        */
        if(p.getKupac().getBrojSakupljenihBodova().compareTo(tipZlatni.getTrazeniBrojBodova()) > 0){
            p.getKupac().setTipKupca(tipZlatni);
        }else if(p.getKupac().getBrojSakupljenihBodova().compareTo(tipSrebrni.getTrazeniBrojBodova()) > 0){
            p.getKupac().setTipKupca(tipSrebrni);
        }else if(p.getKupac().getBrojSakupljenihBodova().compareTo(tipBronzani.getTrazeniBrojBodova()) > 0){
            System.out.println(tipZlatni.getTrazeniBrojBodova());
            p.getKupac().setTipKupca(tipBronzani);
        }

        kupacService.sacuvajKupca(p.getKupac());


        return ResponseEntity.ok().build();

    }

}
