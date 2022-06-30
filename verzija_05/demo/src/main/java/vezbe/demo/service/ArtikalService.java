package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.dto.AzuriranjeArtiklaDto;
import vezbe.demo.dto.AzuriranjeKorisnikDto;
import vezbe.demo.model.Artikal;
import vezbe.demo.model.Porudzbina;
import vezbe.demo.model.PorudzbinaArtikal;
import vezbe.demo.model.Restoran;
import vezbe.demo.repository.ArtikalRepository;
import vezbe.demo.repository.PorudzbinaArtikalRepository;
import vezbe.demo.repository.PorudzbinaRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArtikalService {

    @Autowired
    private ArtikalRepository artikalRepository;

    @Autowired
    private PorudzbinaArtikalRepository porudzbinaArtikalRepository;

    @Autowired
    private PorudzbinaRepository porudzbinaRepository;

    public List<Artikal> NadjiSveArtikleIzDatogRestorana(Restoran restoran)
    {
        List<Artikal> artikli = artikalRepository.findByRestoran(restoran);

        return artikli;
    }

    public void MenadzerDodajeNoviArtikal(Artikal artikal, Restoran restoran) throws Exception
    {
        Provera(artikal.getNaziv(), (List<Artikal>) (List<?>)artikalRepository.findAll(), restoran);

        artikalRepository.save(artikal);
    }

    private void Provera(String naziv, List<Artikal> set, Restoran restoran) throws Exception
    {
        List<Artikal> artikli = new ArrayList<>();

        for(Artikal artikal : set)
        {
            if(artikal.getRestoran().getId().equals(restoran.getId()))
            {
                if(artikal.getNaziv().equals(naziv))
                {
                    throw new Exception("Artikal sa ovim imenom: " + naziv + " vec postoji!");
                }
            }
        }
    }

    //@Transactional
    public void ObrisiArtikal(Artikal artikal)
    {
        artikalRepository.delete(artikal);
    }

    public Artikal NadjiArtikal(Long id)
    {
        return artikalRepository.findAllById(id);
    }

    public Boolean NadjiRestoranOvogArtikla(Long id, Restoran restoran)
    {
        List<Artikal> listaArtikala = artikalRepository.findByRestoran(restoran);

        for(Artikal artikal : listaArtikala)
        {
            if(artikal.getId().equals(id))
            {
                return true;
            }
        }

        return false;
    }

    private void ProveraZaAzuriranje(String naziv, List<Artikal> set, Restoran restoran) throws Exception
    {
        List<Artikal> artikli = new ArrayList<>();

        for(Artikal artikal : set)
        {
            if(artikal.getRestoran().getId().equals(restoran.getId()))
            {
                if(artikal.getNaziv().equals(naziv))
                {
                    throw new Exception("Artikal sa ovim imenom: " + naziv + " vec postoji!");
                }
            }
        }
    }

    public Artikal AzurirajArtikal(AzuriranjeArtiklaDto azuriranjeArtiklaDto, Restoran restoran) throws Exception
    {
        Artikal artikal = artikalRepository.findArtikalById(azuriranjeArtiklaDto.getId());

        String naziv = azuriranjeArtiklaDto.getNaziv();

        ProveraZaAzuriranje(naziv, (List<Artikal>) (List<?>)artikalRepository.findAll(), restoran);

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

        if(azuriranjeArtiklaDto.getKolicina() == null)
        {
            artikal.setKolicina(Artikal.Kolicina.g);
        }
        else
        {
            if (azuriranjeArtiklaDto.getKolicina().equals("ml"))
            {
                artikal.setKolicina(Artikal.Kolicina.ml);
            }
            else
            {
                artikal.setKolicina(Artikal.Kolicina.g);
            }
        }

        if(azuriranjeArtiklaDto.getOpis() != null)
        {
            artikal.setOpis(azuriranjeArtiklaDto.getOpis());
        }

        artikalRepository.save(artikal);

        return artikal;
    }

    /*public List<Artikal> NadjiSveArtikleIzDatePorudzbine(Porudzbina porudzbina)
    {
        return artikalRepository.findAllByPorudzbina(porudzbina);
    }*/

    public List<Artikal> NadjiSveArtikle()
    {
        return artikalRepository.findAll();
    }
}
