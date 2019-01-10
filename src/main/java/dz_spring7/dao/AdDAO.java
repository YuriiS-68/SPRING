package dz_spring7.dao;

import dz_spring7.model.Ad;
import dz_spring7.model.Filter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Repository("adDAO")
@Transactional
public class AdDAO extends GeneralDAO<Ad> {

    private static final DateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy'T'hh:mm");
    private static final String SQL_GET_ADS_BY_FILTER = "SELECT * FROM ";

    public List<Ad> findAds(Filter filter){

        List<Ad> ads;

        TypedQuery<Ad> query = getEntityManager().createNamedQuery(createQuery(filter), Ad.class);
        ads = query.getResultList();

        return ads;
    }

    private String createQuery(Filter filter){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(SQL_GET_ADS_BY_FILTER);

        if (filter.getCategoryType() != null){
            //stringBuilder.append()
        }


        return stringBuilder.toString();
    }
}
