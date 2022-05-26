package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.model.Artikal;
import vezbe.demo.model.Porudzbina;
import vezbe.demo.model.PorudzbinaArtikal;
import vezbe.demo.repository.PorudzbinaArtikalRepository;
import vezbe.demo.repository.PorudzbinaRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PorudzbinaArtikalService {

    @Autowired
    private PorudzbinaArtikalRepository porudzbinaArtikalRepository;

    public List<PorudzbinaArtikal> NadjiSvePorudzbinaArtikalSaOvimArtiklom(Artikal artikal)
    {
        List<PorudzbinaArtikal> listaPorudzbinaArtikal = porudzbinaArtikalRepository.findAll();
        List<PorudzbinaArtikal> trazenePorudzbineArtikal = new ArrayList<>();

        for(PorudzbinaArtikal pa : listaPorudzbinaArtikal)
        {

            if(pa.getArtikal().getId().equals(artikal.getId()) == true)
            {
                trazenePorudzbineArtikal.add(pa);
            }
        }

        System.out.println(trazenePorudzbineArtikal);

        return  trazenePorudzbineArtikal;
    }

    public int NadjiKolicinu(Artikal artikal, Porudzbina porudzbina)
    {
        PorudzbinaArtikal pa = porudzbinaArtikalRepository.findPorudzbinArtikalByArtikalAndPorudzbina(artikal, porudzbina);

        return  pa.getKolicina();
    }

}
