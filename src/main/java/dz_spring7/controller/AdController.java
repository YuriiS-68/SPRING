package dz_spring7.controller;

import dz_spring7.dao.AdDAO;
import dz_spring7.execption.BadRequestException;
import dz_spring7.model.Ad;
import dz_spring7.model.Filter;
import dz_spring7.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class AdController extends UtilsController<Ad> {

    private AdService adService;
    private AdDAO adDAO;

    @Autowired
    public AdController(AdService adService, AdDAO adDAO) {
        this.adService = adService;
        this.adDAO = adDAO;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveAd", produces = "text/plain")
    public @ResponseBody
    String save(HttpServletRequest req) throws IOException, BadRequestException {
        Ad ad = mappingObject(req);

        try {
            adService.save(ad);

        }catch (BadRequestException e){
            System.err.println(e.getMessage());
            throw e;
        }
        return "Ad saved success.";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateAd", produces = "text/plain")
    public @ResponseBody
    String update(HttpServletRequest req)throws IOException, BadRequestException{
        Ad ad = mappingObject(req);
        long inputId = Long.parseLong(req.getParameter("adId"));

        try {
            if (adDAO.findById(inputId) == null){
                return "Ad with ID - " + inputId + " does not exist in the DB.";
            }
            else {
                adService.update(ad);
            }
        }catch (BadRequestException e){
            System.err.println(e.getMessage());
            throw e;
        }
        return "Ad updated success";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteAd", produces = "text/plain")
    public @ResponseBody
    String delete(HttpServletRequest req)throws BadRequestException{
        Ad ad = adDAO.findById(Long.parseLong(req.getParameter("adId")));
        long adId = Long.parseLong(req.getParameter("adId"));

        try {
            if (ad == null){
                return "The Ad with ID " + adId + " does not exist in the DB.";
            }
            else {
                adService.delete(ad.getId());
            }
        }catch (BadRequestException e){
            System.err.println(e.getMessage());
            throw e;
        }
        return "Ad deleted success";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAds", produces = "text/plain")
    public @ResponseBody
    String getAds(){
        return adService.get100Ads().toString();
    }

    public void setAdService(AdService adService) {
        this.adService = adService;
    }

    public void setAdDAO(AdDAO adDAO) {
        this.adDAO = adDAO;
    }
}
