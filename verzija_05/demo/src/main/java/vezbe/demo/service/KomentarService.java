package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.model.Komentar;
import vezbe.demo.model.Restoran;
import vezbe.demo.repository.KomentarRepository;

import java.util.List;

@Service
public class KomentarService {

    @Autowired
    private KomentarRepository komentarRepository;

    enum Ocena{JakoLose, Lose, Dobro, VeomaDobro, Odlicno}

    public String ProsecnaOcena(Restoran restoran)
    {
        List<Komentar> komentari = komentarRepository.findByRestoran(restoran);

        double prosecna_ocena = 0;
        int broj = 0;

        String ocena;

        for(Komentar komentar : komentari)
        {
            if(komentar.getOcena().equals(Komentar.Ocena.JakoLose))
            {
                prosecna_ocena += 1;
            }
            if(komentar.getOcena().equals(Komentar.Ocena.Lose))
            {
                prosecna_ocena += 2;
            }
            if(komentar.getOcena().equals(Komentar.Ocena.Dobro))
            {
                prosecna_ocena += 3;
            }
            if(komentar.getOcena().equals(Komentar.Ocena.VeomaDobro))
            {
                prosecna_ocena += 4;
            }
            if(komentar.getOcena().equals(Komentar.Ocena.Odlicno))
            {
                prosecna_ocena += 5;
            }

            broj = broj + 1;
        }

        if(prosecna_ocena == 0)
        {
            ocena = "Nema nijedne ocene za ovaj restoran";
        }
        else
        {
            prosecna_ocena = prosecna_ocena / broj;

            if(prosecna_ocena >= 4.5)
            {
                ocena = "Odlicno";
            }
            else if(prosecna_ocena >= 3.5)
            {
                ocena = "Veoma dobro";
            }
            else if(prosecna_ocena >= 2.5)
            {
                ocena = "Dobro";
            }
            else if(prosecna_ocena >= 1.5)
            {
                ocena = "Lose";
            }
            else
            {
                ocena = "Jako lose";
            }
        }

        return ocena;
    }

    public List<Komentar> SviKomentariZaRestoran(Restoran restoran)
    {
        List<Komentar> komentari = komentarRepository.findByRestoran(restoran);

        return komentari;
    }

}
