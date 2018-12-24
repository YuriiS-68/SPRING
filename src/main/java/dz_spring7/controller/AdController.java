package dz_spring7.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dz_spring7.dao.AdDAO;
import dz_spring7.execption.BadRequestException;
import dz_spring7.model.Ad;
import dz_spring7.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@Controller
public class AdController {

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
        Ad ad = mappingAd(req);

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
        Ad ad = mappingAd(req);
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

    @RequestMapping(method = RequestMethod.GET, value = "/ades", produces = "text/plain")
    @ResponseBody
    public String getAll()throws BadRequestException{
        return adService.getAdes().toString();
    }

    private Ad mappingAd(HttpServletRequest req)throws IOException{
        StringBuilder stringBuilder = new StringBuilder();

        try(BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null){
                stringBuilder.append(line);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String input = objectMapper.writeValueAsString(stringBuilder.toString());
        return objectMapper.convertValue(input, Ad.class);
    }

    public void setAdService(AdService adService) {
        this.adService = adService;
    }

    public void setAdDAO(AdDAO adDAO) {
        this.adDAO = adDAO;
    }
}
