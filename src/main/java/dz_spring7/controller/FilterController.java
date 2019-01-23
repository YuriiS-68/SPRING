package dz_spring7.controller;

import dz_spring7.execption.BadRequestException;
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
public class FilterController extends Utils<Filter> {

    private AdService adService;

    @Autowired
    public FilterController(AdService adService) {
        this.adService = adService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findAds", produces = "text/plain")
    public @ResponseBody
    String findAds(HttpServletRequest req)throws IOException, BadRequestException {
        Filter filter = mappingObject(req);

        try {
            return String.valueOf(adService.findAds(filter));
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void setAdService(AdService adService) {
        this.adService = adService;
    }
}
