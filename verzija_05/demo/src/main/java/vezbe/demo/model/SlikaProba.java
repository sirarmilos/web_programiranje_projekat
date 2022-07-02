package vezbe.demo.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SlikaProba")
public class SlikaProba implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 64)
    private String photos;

    public SlikaProba() {
    }

    public SlikaProba(Long id, String photos) {
        this.id = id;
        this.photos = photos;
    }

    public SlikaProba(String photos) {
        this.photos = photos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "SlikaProba{" +
                "id=" + id +
                ", photos='" + photos + '\'' +
                '}';
    }
}
