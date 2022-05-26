package vezbe.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.model.Dostavljac;
import vezbe.demo.repository.DostavljacRepository;

@Service
public class DostavljacService {

    @Autowired
    private DostavljacRepository dostavljacRepository;

    public Dostavljac dobaviDostavljacaPoKorisnickomImenu(String korisnickoIme){
        return dostavljacRepository.findDostavljacByKorisnickoIme(korisnickoIme);
    }

}
