package dz_spring3;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Service {

    @Autowired
    FileDAO fileDAO;

    File save(File file){
        return fileDAO.save(file);
    }

    void update(File file){
        fileDAO.update(file);
    }

    File findById(Long id)throws Exception{
        return fileDAO.findById(id);
    }

    List<File> getAll(){

        return fileDAO.getAll();
    }
}
