package vezbe.demo.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class SesijaService {

    public boolean validacijaUloge(HttpSession sesija, String nazivUloge)
    {
        Object uloga = sesija.getAttribute("uloga");

        if(uloga == null)
        {
            return false;
        }

        if(!uloga.toString().endsWith(nazivUloge))
        {
            return false;
        }

        return true;
    }

    public String getKorisnickoIme(HttpSession sesija)
    {
        Object korisnickoIme = sesija.getAttribute("korisnickoIme");

        if(korisnickoIme == null)
        {
            return "";
        }

        if(korisnickoIme.toString().isEmpty() == true)
        {
            return "";
        }

        return korisnickoIme.toString();
    }

    public String getUloga(HttpSession sesija)
    {
        Object uloga = sesija.getAttribute("uloga");

        if(uloga == null)
        {
            return  "";
        }

        if(uloga.toString().isEmpty() == true)
        {
            return "";
        }

        return  uloga.toString();
    }

    public boolean validacijaSesije(HttpSession sesija)
    {
        if(sesija == null)
        {
            return  false;
        }

        String korisnickoIme = getKorisnickoIme(sesija);
        String uloga = getUloga(sesija);

        if(korisnickoIme == null || korisnickoIme.isEmpty() == true)
        {
            return false;
        }

        if(uloga == null || uloga.isEmpty() == true)
        {
            return false;
        }

        return true;
    }

}
