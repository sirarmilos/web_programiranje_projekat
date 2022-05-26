package vezbe.demo.dto;

import vezbe.demo.model.Artikal;
import vezbe.demo.model.Porudzbina;
import vezbe.demo.model.Restoran;

import java.util.List;

public class PrikazRestoranaDto {

    private Restoran restoran;
    private List<Artikal> artikli;
    private List<Porudzbina> porudzbine;

    public PrikazRestoranaDto() {
    }

    public PrikazRestoranaDto(Restoran restoran, List<Artikal> artikli, List<Porudzbina> porudzbine) {
        this.restoran = restoran;
        this.artikli = artikli;
        this.porudzbine = porudzbine;
    }

    public Restoran getRestoran() {
        return restoran;
    }

    public void setRestoran(Restoran restoran) {
        this.restoran = restoran;
    }

    public List<Artikal> getArtikli() {
        return artikli;
    }

    public void setArtikli(List<Artikal> artikli) {
        this.artikli = artikli;
    }

    public List<Porudzbina> getPorudzbine() {
        return porudzbine;
    }

    public void setPorudzbine(List<Porudzbina> porudzbine) {
        this.porudzbine = porudzbine;
    }
}
