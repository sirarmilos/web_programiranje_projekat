package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.model.*;
import vezbe.demo.repository.PorudzbinaArtikalRepository;
import vezbe.demo.repository.PorudzbinaRepository;

import java.util.List;
import java.util.UUID;

@Service
public class PorudzbinaService {

    @Autowired
    private PorudzbinaRepository porudzbinaRepository;

    @Autowired
    private PorudzbinaArtikalRepository porudzbinaArtikalRepository;


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

}
