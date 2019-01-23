package dz_spring7.service;

import dz_spring7.dao.AdDAO;
import dz_spring7.execption.BadRequestException;
import dz_spring7.model.Ad;
import dz_spring7.model.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
            validFields(ad);
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

    public List<Ad> get100Ads(){
        return adDAO.get100Ad();
    }

    public List<Ad> findAds(Filter filter)throws BadRequestException{
        if (filter == null)
            throw new BadRequestException("Filter is not exist");

        return adDAO.findAdsByFilter(filter);
    }

    private void validFields(Ad ad)throws BadRequestException {
        if (ad == null){
            throw new BadRequestException("Ad is not exist");
        }

        if (ad.getId() != null){
            if (ad.getId() == null || ad.getUser() == null || ad.getName() == null || ad.getDescription() == null
                    || ad.getPrice() == null){
                throw new BadRequestException("Check the entered data. One of the object fields is missing.");
            }
        }else if (ad.getUser() == null || ad.getName() == null || ad.getDescription() == null || ad.getPrice() == null){
            throw new BadRequestException("Check the entered data. One of the object fields is missing.");
        }

        if (!isValid(ad))
            throw new BadRequestException("Check the entered data. One of the object fields is missing.");

        if (isValidCurrencyType(ad))
            throw new BadRequestException("The Ad with ID " + ad.getId() + " has invalid CurrencyType.");

        if (isValidCategory(ad))
            throw new BadRequestException("The Ad with ID " + ad.getId() + " has invalid Category.");

        if (isValidSubcategory(ad))
            throw new BadRequestException("The Ad with ID " + ad.getId() + " has invalid Subcategory.");

    }

    private boolean isValid(Ad ad){
        return ad.getCity() != null && ad.getNumberPhone() != null && ad.getDateFrom() != null && ad.getDateTo() != null
                && ad.getCategoryType() != null && ad.getSubcategoryType() != null && ad.getCurrencyType() != null;
    }

    private boolean isValidCurrencyType(Ad ad){
        return (ad.getCurrencyType() == null || ad.getCurrencyType().toString().equalsIgnoreCase("USD"))
                && (ad.getCurrencyType() == null || ad.getCurrencyType().toString().equalsIgnoreCase("EUR"))
                && (ad.getCurrencyType() == null || ad.getCurrencyType().toString().equalsIgnoreCase("UAH"));
    }

    private boolean isValidCategory(Ad ad){
        return (ad.getCategoryType() == null || !ad.getCategoryType().toString().equalsIgnoreCase("SALE")) &&
                (ad.getCategoryType() == null || !ad.getCategoryType().toString().equalsIgnoreCase("BUY"));
    }

    private boolean isValidSubcategory(Ad ad){
        return (ad.getSubcategoryType() == null || !ad.getSubcategoryType().toString().equalsIgnoreCase("AUTO"))
                && (ad.getSubcategoryType() == null || !ad.getSubcategoryType().toString().equalsIgnoreCase("HOUSES"))
                && (ad.getSubcategoryType() == null || !ad.getSubcategoryType().toString().equalsIgnoreCase("APARTMENTS"));
    }

    public void setAdDAO(AdDAO adDAO) {
        this.adDAO = adDAO;
    }
}
