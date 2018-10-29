package dz_spring3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.io.IOException;
import java.util.Objects;

@Entity
@Table(name = "FILE_")
class File {

    private Long id;
    private Long storageId;
    private String name;
    private String format;
    private Long size;
    private Storage storage;

    public File() {
    }

    public File(Long storageId, Storage storage, String name, String format, Long size) throws Exception{
        if (!checkOnLengthNameFile(name))
            throw new BadRequestException("File name can't be more 10 chars. File with this name can't be created");
        this.storageId = storageId;
        this.storage = storage;
        this.name = name;
        this.format = format;
        this.size = size;
    }

    public File(String name, String format, Long size) throws Exception{
        if (!checkOnLengthNameFile(name))
            throw new BadRequestException("File name can't be more 10 chars. File with this name can't be created");
        this.name = name;
        this.format = format;
        this.size = size;
    }

    File(Long id, Long storageId, String name, String format, Long size) throws BadRequestException{
        if (!checkOnLengthNameFile(name))
            throw new BadRequestException("File name can't be more 10 chars. File with this name can't be created");
        this.id = id;
        this.storageId = storageId;
        this.name = name;
        this.format = format;
        this.size = size;
    }


    private static boolean checkOnLengthNameFile(String name){
        return name.isEmpty() || name.length() > 10;
    }

    @Id
    @SequenceGenerator(name = "F_SQ", sequenceName = "FILE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "F_SQ")
    @Column(name = "FILE_ID")
    public Long getId() {
        return id;
    }

    @Column(name = "NAME_FILE")
    String getName() {
        return name;
    }

    @Column(name = "FORMAT_FILE")
    String getFormat() {
        return format;
    }

    @Column(name = "SIZE_FILE")
    Long getSize() {
        return size;
    }

    public Long getStorageId() {
        return storageId;
    }

    @ManyToOne
    @JoinColumn(name = "STORAGE_ID_F")
    public Storage getStorage() {
        return storage;
    }

    @JsonCreator
    public static File createFromJson(String jsonString){

        ObjectMapper objectMapper = new ObjectMapper();

        File file = null;
        try {
            file = objectMapper.readValue(jsonString, File.class);
        }catch (IOException e){
            e.printStackTrace();
        }

        return file;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    void setStorageId(Long storageId) {
        this.storageId = storageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return Objects.equals(name, file.name) &&
                Objects.equals(format, file.format) &&
                Objects.equals(size, file.size) &&
                Objects.equals(storage, file.storage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, format, size, storage);
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", format='" + format + '\'' +
                ", size=" + size +
                '}';
    }
}
