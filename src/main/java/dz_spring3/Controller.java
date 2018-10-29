package dz_spring3;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    Service service;

    @Autowired
    StorageDAO storageDAO;

    @Autowired
    GeneralDAO generalDAO;
    //Ограничения:
    //Storage может хранить файлы только поддерживаемого формата Учитывайте макс размер хранилища
    //В одном хранилище не могут хранится файлы с одинаковым айди, но могут хранится файлы с одинаковыми именами
    //Имя файла не может быть больше 10 символов, то есть файл с таким именем не может быть создан

    @RequestMapping(method = RequestMethod.POST, value = "/saveFile", produces = "text/plain")
    @ResponseBody
    public String save(HttpServletRequest req)throws IOException{

        File file = mappingObject(req);

        try {
            validFields(file);
        } catch (BadRequestException e) {
            e.printStackTrace();
            return String.valueOf(e);
        }

        service.save(file);

        return "Object with id " + file.getId() + " saved success.";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/file", produces = "text/plain")
    @ResponseBody
    public String getAll(){

        return service.getAll().toString();
    }

    public File put(Storage storage, File file)throws Exception {

        validate(storage, file);

        file.setStorageId(storage.getId());

        service.update(file);

        return file;
    }

    public void delete(Storage storage, File file)throws Exception{

        if (file.getStorageId().equals(storage.getId())){
            file.setStorageId(null);
            service.update(file);
        }else {
            throw new Exception("File with id " + file.getId() + " not found in storage " + storage.getId() + ".");
        }
    }

    public void transferFile(Storage storageFrom, Storage storageTo, long id)throws Exception{
        //1. проверить есть ли файл в хранилище, в которое файл перемещаем
        //2. проверить поддерживает ли хранилище формат файла
        //3. проверить хватит ли свободного места для записываемого файла
        File file = service.findById(id);

        validateForTransferFile(storageFrom, storageTo, file, id);

        if (file.getStorageId().equals(storageFrom.getId())){
            file.setStorageId(storageTo.getId());
            service.update(file);
        }else {
            throw new Exception("File with id " + file.getId() + " not found in storage " + storageFrom.getId() + ".");
        }
    }

    public void transferAll(Storage storageFrom, Storage storageTo)throws Exception{
        //1. по айди хранилища получить список находящихся в нём файлов
        //2. проверить чтобы суммарный размер файлов из storageFrom не превышал размер свободного места в хранилищу storageTo
        //3. проверить чтобы файлы из storageFrom не находились в storageTo
        //4. проверить хранилище storageTo на соответствие форматов файлов из хранилища storageFrom

        List<File> fileListFrom = new ArrayList<>(storageFrom.getFiles());

        List<File> fileListTo = new ArrayList<>(storageTo.getFiles());

        validateTransferAll(fileListFrom, fileListTo, storageTo);

        if (storageFrom.getId() != storageTo.getId()){
            for (File fileFrom : fileListFrom){
                if (fileFrom != null){
                    fileFrom.setStorageId(storageTo.getId());
                    fileListTo.add(fileFrom);
                }
            }

            fileListFrom.clear();

            File[] filesFrom = new File[fileListFrom.size()];
            for (int i = 0; i != fileListFrom.size() ; i++) {
                if (filesFrom[i] == null){
                    filesFrom[i] = fileListFrom.get(i);
                }
            }

            File[] filesTo = new File[fileListTo.size()];
            for (int i = 0; i != fileListTo.size() ; i++) {
                if (filesTo[i] == null){
                    filesTo[i] = fileListTo.get(i);
                }
            }

            storageTo.setFiles(Arrays.asList(filesTo));
            storageFrom.setFiles(Arrays.asList(filesFrom));

            generalDAO.transferAll(filesTo);
        }else {
            throw new Exception("Can not transfer files from storage " + storageFrom.getId());
        }
    }

    private File mappingObject(HttpServletRequest req)throws IOException {

        StringBuilder stringBuilder = new StringBuilder();

        try(BufferedReader reader = req.getReader()) {
            String line;

            while ((line = reader.readLine()) != null){
                stringBuilder.append(line);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();

        String input = objectMapper.writeValueAsString(stringBuilder.toString());

        return objectMapper.convertValue(input, File.class);
    }

    private void validFields(File file)throws BadRequestException{
        if (file.getId() == null || file.getName() == null || file.getFormat() == null || file.getSize() == null)
            throw new BadRequestException("Check the entered data. One of the object fields is missing.");
    }

    private void validate(Storage storage, File file)throws Exception{
        //1. проверить файл и хранилище на совпадение форматов
        //2. проверить остаток свободного места в хранилище
        //3. проверить что такого файла нет в хранилище
        if (checkFreeSpace(storage, file))
            throw new Exception("For storage with id: " + storage.getId() + " the file with id: " + file.getId() + " - is too large");

        if (!checkFormat(storage, file))
            throw new Exception("The storage with id: " + storage.getId() + " does not support the file : " + file.getId());
    }

    private void validateForTransferFile(Storage storageFrom, Storage storageTo, File file, long id)throws Exception{
        //1. проверяю наличие файла в хранилище1, из которой он переносится, и на отсутствие файла в хранилище2, в которую переносится
        //2. проверяю остаток свободного места в хранилище2
        //3. проверяю хранилище2 на поддержку формата файла
        if (checkOnSameIdOnTransfer(file, storageFrom, storageTo, id))
            throw new Exception("A file with id: " + file.getId() + " with the same id already exists in the storage with id: " + storageTo.getId());

        validate(storageTo, file);
    }

    private void validateTransferAll(List<File> filesFrom, List<File> filesTo, Storage storageTo)throws Exception{
        if (!checkFreeSpaceInStorageTo(filesFrom, filesTo, storageTo))
            throw new Exception("The amount of free space in the repository " + storageTo.getId() + " is less than the total size of the files for writing");

        if (!checkOnSameIdTransferAll(filesFrom, filesTo))
            throw new Exception("One of the files already exists in the repository " + storageTo.getId());

        if (!checkFormatAll(filesFrom, storageTo))
            throw new Exception("The repository " + storageTo.getId() + " does not support all file formats");
    }


    private boolean checkFreeSpaceInStorageTo(List<File> fileListFrom, List<File> fileListTo, Storage storageTo){

        long sumSizeFilesFrom = 0;
        for (File file : fileListFrom){
            if (file != null){
                sumSizeFilesFrom += file.getSize();
            }
        }

        long sumSizeFileTo = 0;
        for (File file : fileListTo){
            if (file != null){
                sumSizeFileTo += file.getSize();
            }
        }

        long freeSpace = storageTo.getStorageSize() - sumSizeFileTo;

        return freeSpace >= sumSizeFilesFrom;
    }

    private boolean checkOnSameIdTransferAll(List<File> filesFrom, List<File> filesTo){

        for (File fileFrom : filesFrom){
            if (fileFrom != null){
                for (File fileTo : filesTo){
                    if (fileTo != null && fileTo.getId() == fileFrom.getId()){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkFormatAll(List<File> files, Storage storageTo){

        String[] formatsStorage = storageTo.getFormatsSupported().split(",");

        List<String> storageFormats = new ArrayList<>();

        for (String element : formatsStorage){
            if (element != null){
                storageFormats.add(element.trim());
            }
        }

        Set<String> fileFormats = new HashSet<>();

        for (File file : files){
            if (file != null){
                fileFormats.add(file.getFormat());
            }
        }

        return storageFormats.containsAll(fileFormats);
    }

    private boolean checkOnSameIdOnTransfer(File file, Storage storageFrom, Storage storageTo, long id)throws Exception{
        if (file == null || storageFrom == null || storageTo == null || id == 0)
            throw new Exception("Incoming data contains an error");

        if (file.getStorageId() != storageFrom.getId())
            throw new Exception("The file with id " + id + " is not in the database " + storageFrom.getId());

        return file.getStorageId() == storageTo.getId();
    }

    private boolean checkFreeSpace(Storage storage, File file)throws Exception{

        long sum = 0;
        for (File element : storageDAO.findById(storage.getId()).getFiles()){
            if (element != null){
                sum += element.getSize();
            }
        }

        long freeSpace = storage.getStorageSize() - sum;

        return freeSpace < file.getSize();
    }

    private boolean checkFormat(Storage storage, File file){

        String[] formatsStorage = storage.getFormatsSupported().split(",");

        for (String elStorage : formatsStorage){
            if (elStorage != null && file.getFormat() != null && file.getFormat().equals(elStorage.trim())){
                return true;
            }
        }
        return false;
    }
}
