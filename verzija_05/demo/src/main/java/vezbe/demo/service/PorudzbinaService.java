package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.model.*;
import vezbe.demo.repository.PorudzbinaArtikalRepository;
import vezbe.demo.repository.PorudzbinaRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PorudzbinaService {

    @Autowired
    private PorudzbinaRepository porudzbinaRepository;

    @Autowired
    private PorudzbinaArtikalRepository porudzbinaArtikalRepository;

    @Autowired
    private ArtikalService artikalService;

    @Autowired
    private PorudzbinaService porudzbinaService;

    @Autowired
    private PorudzbinaArtikalService porudzbinaArtikalService;

    public void ObrisiPorudzbinu(Porudzbina porudzbina)
    {
        System.out.println(porudzbina.getId());
        porudzbinaRepository.deleteById(porudzbina.getId());
    }

    public List<Porudzbina> dobaviSvePorudzbine(){
        return porudzbinaRepository.findAll();

    }

    public List<Porudzbina> dobaviPorudzbinePoDostavljacu(Dostavljac dostavljac){
        return porudzbinaRepository.findPorudzbinaByDostavljac(dostavljac.getKorisnickoIme());
    }

    public List<Porudzbina> dobaviPorudzbinePoRestoranu(Restoran restoran){
        return porudzbinaRepository.findPorudzbinaByRestoran(restoran);
    }

    public List<Porudzbina> dobaviPorudzbinePoKupcu(Kupac kupac){
        return porudzbinaRepository.findPorudzbinaByKupac(kupac);
    }

    public Porudzbina dobaviPorudzbinuPoId(UUID id){
        return porudzbinaRepository.getById(id);
    }

    public Porudzbina nadjiPorudzbinuPoId(UUID id) { return porudzbinaRepository.findPorudzbinaById(id);}

    public void sacuvajPorudzbinu(Porudzbina porudzbina){
        porudzbinaRepository.save(porudzbina);
    }

    public void sacuvajPorudzbinaArtikal(PorudzbinaArtikal pa){
        porudzbinaArtikalRepository.save(pa);
    }

    public PorudzbinaArtikal dobaviPorudzbinuArtikal(Porudzbina porudzbina, Artikal artikal){
        return porudzbinaArtikalRepository.getPorudzbinaArtikalByArtikalAndPorudzbina(artikal, porudzbina);
    }

    public void obrisiArtikle(int id){
        porudzbinaArtikalRepository.deleteById(id);
    }

    public List<PorudzbinaArtikal> dobaviArtikleZaPorudzbinu(Porudzbina p){
        return porudzbinaArtikalRepository.getByPorudzbina(p);
    }


    public List<Porudzbina> NadjiSvePorudzbineSaOvimId(List<PorudzbinaArtikal> porudzbinaArtikalList)
    {
        List<Porudzbina> listaPorudzbina = porudzbinaRepository.findAll();
        List<Porudzbina> trazenePorudzbine = new ArrayList<>();

        for(PorudzbinaArtikal pa : porudzbinaArtikalList)
        {
            for(Porudzbina p : listaPorudzbina)
            {
                if(pa.getPorudzbina().equals(p) == true)
                {
                    trazenePorudzbine.add(p);
                }
            }
        }

        return trazenePorudzbine;
    }

    public void SmanjiCenuPorudzbinaNakonBrisanjaArtiklaIzRestorana(List<Porudzbina> listaPorudzbina, Artikal artikal)
    {
        for(Porudzbina p : listaPorudzbina)
        {
            List<Artikal> listaArtikalaIzDatePorudzbine = artikalService.NadjiSveArtikle();

            for(Artikal a : listaArtikalaIzDatePorudzbine)
            {
                if(a.equals(artikal) == true)
                {
                    int kolicina = porudzbinaArtikalService.NadjiKolicinu(a, p);

                    BigDecimal kolicinaBD = new BigDecimal(kolicina);

                    p.setCena(p.getCena().subtract(kolicinaBD.multiply(a.getCena())));

                    porudzbinaRepository.save(p);
                }
            }
        }
    }

    public BigDecimal updateCena(Porudzbina p){
        BigDecimal suma = BigDecimal.ZERO;
        for(PorudzbinaArtikal pa: p.getPorudzbineArtikli()){
            suma = suma.add( pa.getArtikal().getCena().multiply(BigDecimal.valueOf(pa.getKolicina())));
            System.out.println(suma + "*");
        }

        return suma;
    }

}
