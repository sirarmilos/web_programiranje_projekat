package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.dto.AzuriranjeArtiklaDto;
import vezbe.demo.dto.DodavanjeNovogArtiklaDto;
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

    public void ObrisiArtikal(Artikal artikal)
    {
        artikalRepository.delete(artikal);

    }

    public Artikal NadjiArtikal(Long id)
    {
        return artikalRepository.findAllById(id);
    }

    public Artikal AzurirajArtikal(AzuriranjeArtiklaDto azuriranjeArtiklaDto)
    {
        Artikal artikal = artikalRepository.findArtikalById(azuriranjeArtiklaDto.getId());

        artikal.setNaziv(azuriranjeArtiklaDto.getNaziv());
        artikal.setCena(azuriranjeArtiklaDto.getCena());
        if(azuriranjeArtiklaDto.getTip().equals("Jelo"))
        {
            artikal.setTip(Artikal.Tip.Jelo);
        }
        else
        {
            artikal.setTip(Artikal.Tip.Pice);
        }

        if(azuriranjeArtiklaDto.getKolicina().equals("ml"))
        {
            artikal.setKolicina(Artikal.Kolicina.ml);
        }
        else
        {
            artikal.setKolicina(Artikal.Kolicina.g);
        }

        artikal.setOpis(azuriranjeArtiklaDto.getOpis());

        artikalRepository.save(artikal);

        return artikal;
    }
}
