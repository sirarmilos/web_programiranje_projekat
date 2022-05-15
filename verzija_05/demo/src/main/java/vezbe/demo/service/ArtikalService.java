package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.model.Artikal;
import vezbe.demo.model.Korisnik;
import vezbe.demo.model.Restoran;
import vezbe.demo.repository.ArtikalRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ArtikalService {

    @Autowired
    private ArtikalRepository artikalRepository;

    public List<Artikal> NadjiSveArtikleIzDatogRestorana(Restoran restoran)
    {
        List<Artikal> artikli = artikalRepository.findByRestoran(restoran);

        return artikli;
    }

    public void MenadzerDodajeNoviArtikal(Artikal artikal) throws Exception
    {
        Provera(artikal.getNaziv(), (List<Artikal>) (List<?>)artikalRepository.findAll());

        artikalRepository.save(artikal);
    }

    private void Provera(String naziv, List<Artikal> set) throws Exception
    {
        for(Artikal artikal : set)
        {
            if(artikal.getNaziv().equals(naziv))
            {
                throw new Exception("Artikal sa ovim imenom: " + naziv + " vec postoji!");
            }
        }
    }

    public boolean ObrisiArtikal(Long id)
    {
        //Optional<Artikal> artikal = artikalRepository.findById(id);
        Boolean provera =  ProveraZaBrisanje(id, (List<Artikal>) (List<?>)artikalRepository.findAll());

        /*if(artikal.isPresent())
        {
            System.out.println(artikal.get());
            System.out.println(id);
             //artikalRepository.deleteById(id);
            Artikal artikal1 = artikal.get();
            artikalRepository.delete(artikal1);
            return true;
        }*/

        return provera;
    }

    private Boolean ProveraZaBrisanje(Long id, List<Artikal> set)
    {
        for(Artikal artikal : set)
        {
            if(artikal.getId().equals(id))
            {
                artikalRepository.delete(artikal);
                return true;
            }
        }
        return false;
    }

}
