package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.dto.RegistracijaDto;
import vezbe.demo.model.Kupac;
import vezbe.demo.repository.KupacRepository;

import java.util.List;

@Service
public class KupacService {

    @Autowired
    private KupacRepository kupacRepository;

    public List<Kupac> findAll()
    {
        return kupacRepository.findAll();
    }

    /*public Kupac Azuriranje(RegistracijaDto registracijaDto)
    {
        Kupac kupac = kupacRepository.findKupacByKorisnickoIme(registracijaDto.getKorisnickoIme());

        kupac.setLozinka(registracijaDto.getLozinka());
        kupac.setIme(registracijaDto.getIme());
        kupac.setPrezime(registracijaDto.getPrezime());
        // kupac.setDatumRodjenja(registracijaDto.PrebaciUKupca().getDatumRodjenja());
        // kupac.setPol(registracijaDto.PrebaciUKupca().getPol());

        kupacRepository.save(kupac);

        return kupac;
    }*/

    public Kupac findByKorisnickoIme(String korisnickoIme){
        return kupacRepository.findKupacByKorisnickoIme(korisnickoIme);
    }

}
