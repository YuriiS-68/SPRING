package dz_spring3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "STORAGE_")
public class Storage {

    private Long id;
    private List<File> files;
    private String formatsSupported;
    private String storageCountry;
    private Long storageSize;

    public Storage() {
    }

    public Storage(long id){
        this.id = id;
    }

    public Storage(long id, List<File> files, String formatsSupported, String storageCountry, long storageSize) {
        this.id = id;
        this.files = files;
        this.formatsSupported = formatsSupported;
        this.storageCountry = storageCountry;
        this.storageSize = storageSize;
    }

    Storage(long id, String formatsSupported, String storageCountry, long storageSize) {
        this.id = id;
        this.formatsSupported = formatsSupported;
        this.storageCountry = storageCountry;
        this.storageSize = storageSize;
    }

    @Id
    @SequenceGenerator(name = "ST_SQ", sequenceName = "STORAGE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ST_SQ")
    @Column(name = "STORAGE_ID")
    public Long getId() {
        return id;
    }

    @Column(name = "FORMAT_SUPPORTED")
    String getFormatsSupported() {
        return formatsSupported;
    }

    @Column(name = "COUNTRY_STORAGE")
    String getStorageCountry() {
        return storageCountry;
    }

    @Column(name = "SIZE_STORAGE")
    Long getStorageSize() {
        return storageSize;
    }

    @OneToMany(mappedBy = "storage", fetch = FetchType.LAZY, targetEntity = File.class)
    List<File> getFiles() {
        return files;
    }

    @JsonCreator
    public static Storage createFromJson(String jsonString){

        ObjectMapper objectMapper = new ObjectMapper();

        Storage storage = null;
        try {
            storage = objectMapper.readValue(jsonString, Storage.class);
        }catch (IOException e){
            e.printStackTrace();
        }

        return storage;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFormatsSupported(String formatsSupported) {
        this.formatsSupported = formatsSupported;
    }

    public void setStorageCountry(String storageCountry) {
        this.storageCountry = storageCountry;
    }

    public void setStorageSize(Long storageSize) {
        this.storageSize = storageSize;
    }

    void setFiles(List<File> files) {
        this.files = files;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Storage storage = (Storage) o;
        return Objects.equals(files, storage.files) &&
                Objects.equals(formatsSupported, storage.formatsSupported) &&
                Objects.equals(storageCountry, storage.storageCountry) &&
                Objects.equals(storageSize, storage.storageSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(files, formatsSupported, storageCountry, storageSize);
    }

    @Override
    public String toString() {
        return "Storage{" +
                "id=" + id +
                ", files=" + files +
                ", formatsSupported='" + formatsSupported + '\'' +
                ", storageCountry='" + storageCountry + '\'' +
                ", storageSize=" + storageSize +
                '}';
    }
}
