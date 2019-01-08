package dz_spring7.service;

import dz_spring7.dao.AdDAO;
import dz_spring7.execption.BadRequestException;
import dz_spring7.model.Ad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdService {

    private AdDAO adDAO;

    @Autowired
    public AdService(AdDAO adDAO) {
        this.adDAO = adDAO;
    }

    public Ad save(Ad ad)throws BadRequestException{
        if (ad != null && ad.getId() != null){
            throw new BadRequestException("This Ad with ID - " + ad.getId() + " can not save in DB.");
        }
        else {
            adDAO.save(ad);
        }
        return ad;
    }

    public void update(Ad ad)throws BadRequestException{
        if (ad == null){
            throw new BadRequestException("Ad is not exist");
        }
        validFields(ad);
        adDAO.update(ad);
    }

    public void delete(Long id)throws BadRequestException{
        if (id == null){
            throw new BadRequestException("Input id is not exist");
        }
        adDAO.delete(id);
    }

    private void validFields(Ad ad)throws BadRequestException {
        if (ad == null){
            throw new BadRequestException("Ad is not exist");
        }
        if (ad.getId() == null || ad.getUser() == null || ad.getName() == null ||
                ad.getDescription() == null || ad.getPrice() == null || ad.getCurrencyType() == null){
            throw new BadRequestException("Check the entered data. One of the object fields is missing.");
        }
    }

    public void setAdDAO(AdDAO adDAO) {
        this.adDAO = adDAO;
    }
}
