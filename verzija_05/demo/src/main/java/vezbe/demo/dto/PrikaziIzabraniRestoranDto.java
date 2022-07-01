package vezbe.demo.dto;

import vezbe.demo.model.Artikal;
import vezbe.demo.model.Komentar;
import vezbe.demo.model.Lokacija;
import vezbe.demo.model.Restoran;

import java.util.List;



public class PrikaziIzabraniRestoranDto {

    public enum StatusEnum{RADI, NE_RADI}

    private Restoran restoran;
    private Lokacija lokacija;
    private StatusEnum status;
    private Boolean b;
    private String prosecnaOcena;
    private List<KoJeDaoKomentarDto> listaKomentara;
    private List<Artikal> listaArtikala;

    public PrikaziIzabraniRestoranDto() {
    }

    public PrikaziIzabraniRestoranDto(Restoran restoran, Lokacija lokacija, Boolean b, String prosecnaOcena, List<KoJeDaoKomentarDto> listaKomentara, List<Artikal> listaArtikala) {
        this.restoran = restoran;
        this.lokacija = lokacija;
        this.status = status;
        this.prosecnaOcena = prosecnaOcena;
        this.listaKomentara = listaKomentara;
        this.listaArtikala = listaArtikala;
        if(b == true) {
            this.status = StatusEnum.RADI;
        }
        else
        {
            this.status = StatusEnum.NE_RADI;
        }
    }

    public Restoran getRestoran() {
        return restoran;
    }

    public void setRestoran(Restoran restoran) {
        this.restoran = restoran;
    }

    public Lokacija getLokacija() {
        return lokacija;
    }

    public void setLokacija(Lokacija lokacija) {
        this.lokacija = lokacija;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void setProsecnaOcena(String prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
    }

    public List<KoJeDaoKomentarDto> getListaKomentara() {
        return listaKomentara;
    }

    public void setListaKomentara(List<KoJeDaoKomentarDto> listaKomentara) {
        this.listaKomentara = listaKomentara;
    }

    public List<Artikal> getListaArtikala() {
        return listaArtikala;
    }

    public void setListaArtikala(List<Artikal> listaArtikala) {
        this.listaArtikala = listaArtikala;
    }
}
