package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.model.Dostavljac;
import vezbe.demo.model.Kupac;
import vezbe.demo.model.Porudzbina;
import vezbe.demo.model.Restoran;
import vezbe.demo.repository.PorudzbinaRepository;

import java.util.List;

@Service
public class PorudzbinaService {

    @Autowired
    private PorudzbinaRepository porudzbinaRepository;


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



}
