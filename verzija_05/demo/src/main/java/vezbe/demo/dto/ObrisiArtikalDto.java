package vezbe.demo.dto;

public class ObrisiArtikalDto {

    private Long id;

    public ObrisiArtikalDto() {
    }

    public ObrisiArtikalDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long PrebaciULong(ObrisiArtikalDto obrisiArtikalDto)
    {
        return obrisiArtikalDto.getId();
    }
}
