package vezbe.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vezbe.demo.model.*;
import vezbe.demo.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.*;

@Configuration
public class DatabaseConfiguration {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MenadzerRepository menadzerRepository;

    @Autowired
    private DostavljacRepository dostavljacRepository;

    @Autowired
    private KupacRepository kupacRepository;

    @Autowired
    private TipKupcaRepository tipKupcaRepository;

    @Autowired
    private LokacijaRepository lokacijaRepository;

    @Autowired
    private RestoranRepository restoranRepository;

    @Autowired
    private ArtikalRepository artikalRepository;

    @Autowired
    private PorudzbinaRepository porudzbinaRepository;

    @Autowired
    private KomentarRepository komentarRepository;

    @Autowired
    private PorudzbinaArtikalRepository porudzbinaArtikalRepository;

    @Bean

    public boolean instantiate()
    {
        LocalDate datumRodjenja1 = LocalDate.of(1993, 4, 12);
        LocalDate datumRodjenja2 = LocalDate.of(1978, 3, 8);
        LocalDate datumRodjenja3 = LocalDate.of(1996, 11, 24);
        LocalDate datumRodjenja4 = LocalDate.of(1983, 9, 29);
        LocalDate datumRodjenja5 = LocalDate.of(1992, 5, 21);
        LocalDate datumRodjenja6 = LocalDate.of(1987, 2, 12);
        LocalDate datumRodjenja7 = LocalDate.of(1976, 10, 1);
        LocalDate datumRodjenja8 = LocalDate.of(1970, 3, 14);
        LocalDate datumRodjenja9 = LocalDate.of(1980, 10, 15);
        LocalDate datumRodjenja10 = LocalDate.of(1982, 4, 8);
        LocalDate datumRodjenja11 = LocalDate.of(1996, 1, 23);
        LocalDate datumRodjenja12 = LocalDate.of(1997, 7, 17);
        LocalDate datumRodjenja13 = LocalDate.of(1966, 11, 6);

        Admin admin1 = new Admin("glavni_admin", "lozinkaZaGlavnogAdmina", "Marko", "Markovic", "muski", datumRodjenja1);
        Admin admin2 = new Admin("pomocni_admin", "lozinkaZaPomocnogAdmina", "Zorana", "Zivic", "zenski", datumRodjenja2);

        adminRepository.save(admin1);
        adminRepository.save(admin2);

        Dostavljac dostavljac1 = new Dostavljac("miki123", "lozinkaDostavljac1", "Milan", "Jovanovic", "muski", datumRodjenja3);
        Dostavljac dostavljac2 = new Dostavljac("zoran7", "lozinkaDostavljac2", "Zoran", "Ivanovic", "muski", datumRodjenja4);
        Dostavljac dostavljac3 = new Dostavljac("jelena56", "lozinkaDostavljac3", "Jelena", "Filipovic", "zenski", datumRodjenja5);

        dostavljacRepository.save(dostavljac1);
        dostavljacRepository.save(dostavljac2);
        dostavljacRepository.save(dostavljac3);

        TipKupca tipKupcaZlatni = new TipKupca(TipKupca.Ime.Zlatni, new BigDecimal(40), new BigDecimal(1200));
        TipKupca tipKupcaSrebrni = new TipKupca(TipKupca.Ime.Srebrni, new BigDecimal(30), new BigDecimal(700));
        TipKupca tipKupcaBronzani = new TipKupca(TipKupca.Ime.Bronzani, new BigDecimal(20), new BigDecimal(300));

        tipKupcaRepository.save(tipKupcaZlatni);
        tipKupcaRepository.save(tipKupcaSrebrni);
        tipKupcaRepository.save(tipKupcaBronzani);

        Kupac kupac1 = new Kupac("bojan61", "lozinkaKupac1", "Bojan", "Ivanovic", "muski", datumRodjenja6, new BigDecimal(1600), tipKupcaZlatni);
        Kupac kupac2 = new Kupac("marijaaa", "lozinkaKupac2", "Marija", "Randjelovic", "zenski", datumRodjenja7, new BigDecimal(1000), tipKupcaSrebrni);
        Kupac kupac3 = new Kupac("deki1976", "lozinkaKupac3", "Dejan", "Jovic", "muski", datumRodjenja8, new BigDecimal(200), null);
        Kupac kupac4 = new Kupac("ivan9", "lozinkaKupac4", "Ivan", "Gagic", "muski", datumRodjenja9, new BigDecimal(50), null);
        Kupac kupac5 = new Kupac("mgorana", "lozinkaKupac5", "Gorana", "Matic", "zenski", datumRodjenja10, new BigDecimal(650), tipKupcaBronzani);

        kupacRepository.save(kupac1);
        kupacRepository.save(kupac2);
        kupacRepository.save(kupac3);
        kupacRepository.save(kupac4);
        kupacRepository.save(kupac5);

        Lokacija lokacija1 = new Lokacija(new BigDecimal(43.2342),new BigDecimal(20.0132), "Banatska 23 Novi Sad");
        Lokacija lokacija2 = new Lokacija(new BigDecimal(45.911223), new BigDecimal(20.8302), "Zelegorska 11 Mladenovac");
        Lokacija lokacija3 = new Lokacija(new BigDecimal(43.19923), new BigDecimal(22.991342), "Ivana Bolica 24 Zajecar");
        Lokacija lokacija4 = new Lokacija(new BigDecimal(44.1007), new BigDecimal(21.66000091), "Zvonarska 14 Ivanjica");
        Lokacija lokacija5 = new Lokacija(new BigDecimal(43.1325427), new BigDecimal(23.2410251), "Belogorska 134 Sombor");
        Lokacija lokacija6 = new Lokacija(new BigDecimal(43.17), new BigDecimal(21.91), "Doboska 1 Valjevo");

        lokacijaRepository.save(lokacija1);
        lokacijaRepository.save(lokacija2);
        lokacijaRepository.save(lokacija3);
        lokacijaRepository.save(lokacija4);
        lokacijaRepository.save(lokacija5);
        lokacijaRepository.save(lokacija6);

        Restoran restoran1 = new Restoran("SpagetiMireti", "italijanski", lokacija1);
        Restoran restoran2 = new Restoran("PicaMaks", "italijanski", lokacija2);
        Restoran restoran3 = new Restoran("Zao Bao Bo", "kineski", lokacija3);
        Restoran restoran4 = new Restoran("Kod Gazda Mileta", "srpski", lokacija4);
        Restoran restoran5 = new Restoran("Spanska kuhinja", "spanski", lokacija5);

        restoranRepository.save(restoran1);
        restoranRepository.save(restoran2);
        restoranRepository.save(restoran3);
        restoranRepository.save(restoran4);
        restoranRepository.save(restoran5);

        Menadzer menadzer1 = new Menadzer("vesnavesna", "lozinkaMenadzer1", "Vesna", "Bozovic", "zenski", datumRodjenja11 , restoran1);
        Menadzer menadzer2 = new Menadzer("ema777", "lozinkaMenadzer2", "Ema", "Sofranovic", "zenski", datumRodjenja12 , restoran2); // restoran1
        Menadzer menadzer3 = new Menadzer("bogdanteslic", "lozinkaMenadzer3", "Bogdan", "Teslic", "muski", datumRodjenja13 , restoran3); // restoran2

        menadzerRepository.save(menadzer1);
        menadzerRepository.save(menadzer2);
        menadzerRepository.save(menadzer3);

        Artikal artikal1 = new Artikal("Pasta", new BigDecimal(400), Artikal.Tip.Jelo, Artikal.Kolicina.g, "bolonjezi", restoran1);
        Artikal artikal2 = new Artikal("Pica kapricoza", new BigDecimal(140), Artikal.Tip.Jelo, Artikal.Kolicina.g, "pica parce", restoran1);
        Artikal artikal3 = new Artikal("Pica margarita", new BigDecimal(600), Artikal.Tip.Jelo, Artikal.Kolicina.g, "porodicna pica", restoran1);
        Artikal artikal4 = new Artikal("Spageti", new BigDecimal(500), Artikal.Tip.Jelo, Artikal.Kolicina.g, "porcija spageta prelivenih sa bolonjezi sosom", restoran1);
        Artikal artikal5 = new Artikal("Koka kola", new BigDecimal(120), Artikal.Tip.Pice, Artikal.Kolicina.ml, "500ml", restoran1);
        Artikal artikal6 = new Artikal("Pasta", new BigDecimal(380), Artikal.Tip.Jelo, Artikal.Kolicina.g, "sos po vasoj zelji", restoran2);
        Artikal artikal7 = new Artikal("Pica kapricoza", new BigDecimal(590), Artikal.Tip.Jelo, Artikal.Kolicina.g, "porodicna pica", restoran2);
        Artikal artikal8 = new Artikal("Pica margarita", new BigDecimal(600), Artikal.Tip.Jelo, Artikal.Kolicina.g, "porodicna pica", restoran2);
        Artikal artikal9 = new Artikal("Spageti", new BigDecimal(400), Artikal.Tip.Jelo, Artikal.Kolicina.g, "porcija spageta", restoran2);
        Artikal artikal10 = new Artikal("Koka kola", new BigDecimal(140), Artikal.Tip.Pice, Artikal.Kolicina.ml, "500ml osvezavajuca hladna kola", restoran2);
        Artikal artikal11 = new Artikal("Voda", new BigDecimal(100), Artikal.Tip.Pice, Artikal.Kolicina.ml, "330ml, staklena flasa", restoran2);
        Artikal artikal12 = new Artikal("Prolecne rolnice", new BigDecimal(170), Artikal.Tip.Jelo, Artikal.Kolicina.g, "sa povrcem", restoran3);
        Artikal artikal13 = new Artikal("Teletina u sosu od ostriga", new BigDecimal(530), Artikal.Tip.Jelo, Artikal.Kolicina.g, "ukusna teletina sa prelivom", restoran3);
        Artikal artikal14 = new Artikal("Pljeskavica", new BigDecimal(230), Artikal.Tip.Jelo, Artikal.Kolicina.g, "velika pljeskavica", restoran4);
        Artikal artikal15 = new Artikal("Cevapi", new BigDecimal(350), Artikal.Tip.Jelo, Artikal.Kolicina.g, "mali cevapi", restoran4);

        artikalRepository.save(artikal1);
        artikalRepository.save(artikal2);
        artikalRepository.save(artikal3);
        artikalRepository.save(artikal4);
        artikalRepository.save(artikal5);
        artikalRepository.save(artikal6);
        artikalRepository.save(artikal7);
        artikalRepository.save(artikal8);
        artikalRepository.save(artikal9);
        artikalRepository.save(artikal10);
        artikalRepository.save(artikal11);
        artikalRepository.save(artikal12);
        artikalRepository.save(artikal13);
        artikalRepository.save(artikal14);
        artikalRepository.save(artikal15);

        LocalDateTime datumIVremePorudzbine1 = LocalDateTime.of(2022, 12, 13, 22, 11, 32);
        LocalDateTime datumIVremePorudzbine2 = LocalDateTime.of(2022, 11, 10, 20, 41, 52);
        LocalDateTime datumIVremePorudzbine3 = LocalDateTime.of(2022, 12, 21, 13, 44, 22);
        LocalDateTime datumIVremePorudzbine4 = LocalDateTime.of(2022, 12, 16, 23, 50, 15);

        BigDecimal c1 = new BigDecimal(0);
        BigDecimal pomc1 = artikal1.getCena();
        BigDecimal pomc2 = artikal2.getCena();
        c1 = c1.add(pomc1);
        c1 = c1.add(pomc1);
        c1 = c1.add(pomc1);
        c1 = c1.add(pomc2);
        c1 = c1.add(pomc2);

        BigDecimal c2 = new BigDecimal(0);
        BigDecimal pomc5 = artikal5.getCena();
        c2 = c2.add(pomc2);
        c2 = c2.add(pomc2);
        c2 = c2.add(pomc5);

        BigDecimal c3 = new BigDecimal(0);
        BigDecimal pomc14 = artikal14.getCena();
        BigDecimal pomc15 = artikal15.getCena();
        c3 = c3.add(pomc14);
        c3 = c3.add(pomc15);

        BigDecimal c4 = new BigDecimal(0);
        BigDecimal pomc7 = artikal7.getCena();
        BigDecimal pomc8 = artikal8.getCena();
        c4 = c4.add(pomc7);
        c4 = c4.add(pomc8);

        Porudzbina porudzbina1 = new Porudzbina(restoran1, datumIVremePorudzbine1, c1, kupac3, Porudzbina.Status.UTransportu, dostavljac1);

        Porudzbina porudzbina2 = new Porudzbina(restoran1, datumIVremePorudzbine2, c2, kupac4, Porudzbina.Status.UPripremi, null);

        Porudzbina porudzbina3 = new Porudzbina(restoran4, datumIVremePorudzbine3, c3, kupac5, Porudzbina.Status.UTransportu, dostavljac1);

        Porudzbina porudzbina4 = new Porudzbina(restoran2, datumIVremePorudzbine4, c4, kupac3, Porudzbina.Status.Dostavljena, dostavljac1);

        Porudzbina porudzbina5 = new Porudzbina(restoran1, datumIVremePorudzbine2, c1, kupac1, Porudzbina.Status.Obrada, null);



        porudzbinaRepository.save(porudzbina1);
        porudzbinaRepository.save(porudzbina2);
        porudzbinaRepository.save(porudzbina3);
        porudzbinaRepository.save(porudzbina4);
        porudzbinaRepository.save(porudzbina5);

        PorudzbinaArtikal pa1 = new PorudzbinaArtikal(artikal1, porudzbina1, 3);
        PorudzbinaArtikal pa2 = new PorudzbinaArtikal(artikal2, porudzbina1, 2);
        PorudzbinaArtikal pa3 = new PorudzbinaArtikal(artikal2, porudzbina2, 2);
        PorudzbinaArtikal pa4 = new PorudzbinaArtikal(artikal5, porudzbina2, 1);
        PorudzbinaArtikal pa5 = new PorudzbinaArtikal(artikal14, porudzbina3, 1);
        PorudzbinaArtikal pa6 = new PorudzbinaArtikal(artikal15, porudzbina3, 1);
        PorudzbinaArtikal pa7 = new PorudzbinaArtikal(artikal7, porudzbina4, 1);
        PorudzbinaArtikal pa8 = new PorudzbinaArtikal(artikal8, porudzbina4, 1);
        PorudzbinaArtikal pa9 = new PorudzbinaArtikal(artikal1, porudzbina5, 3);
        PorudzbinaArtikal pa10 = new PorudzbinaArtikal(artikal2, porudzbina5, 2);



        porudzbinaArtikalRepository.save(pa1);
        porudzbinaArtikalRepository.save(pa2);
        porudzbinaArtikalRepository.save(pa3);
        porudzbinaArtikalRepository.save(pa4);
        porudzbinaArtikalRepository.save(pa5);
        porudzbinaArtikalRepository.save(pa6);
        porudzbinaArtikalRepository.save(pa7);
        porudzbinaArtikalRepository.save(pa8);
        porudzbinaArtikalRepository.save(pa9);
        porudzbinaArtikalRepository.save(pa10);

        Komentar komentar1 = new Komentar(kupac1, restoran1, "vrlo lep restoran", Komentar.Ocena.VeomaDobro);
        Komentar komentar2 = new Komentar(kupac4, restoran1, "vrlo lose...", Komentar.Ocena.Lose);

        komentarRepository.save(komentar1);
        komentarRepository.save(komentar2);

        return true;
    }
}