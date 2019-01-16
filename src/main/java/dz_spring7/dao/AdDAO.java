package dz_spring7.dao;

import dz_spring7.model.Ad;
import dz_spring7.model.Filter;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Repository("adDAO")
@Transactional
public class AdDAO extends GeneralDAO<Ad> {

    @SuppressWarnings("SqlResolve")
    private static final String SQL_GET_ADS_BY_CURRENT_DATE = "SELECT * FROM AD WHERE ROWNUM < 101 AND DATE_TO > ? ORDER BY DATE_TO DESC";
    private static final String SQL_GET_ADS_BY_FILTER = "SELECT * FROM AD WHERE ROWNUM < 101 AND DATE_TO > ? AND AD_DESCRIPTION LIKE ?";
    private static final String SQL_GET_ADS_BY_FILTER_OUT_DESCRIPTION = "SELECT * FROM AD WHERE ROWNUM < 101 AND DATE_TO > ?";

    @SuppressWarnings("unchecked")
    public List<Ad> get100Ad(){
        Date currentDate = new Date();

        NativeQuery<Ad> query = (NativeQuery<Ad>) getEntityManager().createNativeQuery(SQL_GET_ADS_BY_CURRENT_DATE, Ad.class);
        return query.setParameter(1, currentDate, TemporalType.TIMESTAMP).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Ad> findAds(Filter filter){
        Date currentDate = new Date();
        //String findByWord = filter.getDescription();

        if (filter.getDescription() == null){
            NativeQuery<Ad> adQuery = (NativeQuery<Ad>) getEntityManager().createNativeQuery(createQuery(filter), Ad.class);
            adQuery.setParameter(1, currentDate, TemporalType.TIMESTAMP);

            return adQuery.getResultList();
        }
        else {
            NativeQuery<Ad> adQuery = (NativeQuery<Ad>) getEntityManager().createNativeQuery(createQuery(filter), Ad.class);
            adQuery.setParameter(2, "%" + filter.getDescription() + "%");
            adQuery.setParameter(1, currentDate, TemporalType.TIMESTAMP);

            return adQuery.getResultList();
        }
    }

    private String createQuery(Filter filter){
        StringBuilder stringBuilder = new StringBuilder();

        if (filter.getDescription() != null){
            stringBuilder.append(SQL_GET_ADS_BY_FILTER);
        }
        else {
            stringBuilder.append(SQL_GET_ADS_BY_FILTER_OUT_DESCRIPTION);
        }

        if (filter.getCategoryType() != null){
            stringBuilder.append(" AND CATEGORY_TYPE = '").append(filter.getCategoryType().toString()).append("'");
        }

        if (filter.getCity() != null){
            stringBuilder.append(" AND CITY = '").append(filter.getCity()).append("'");
        }

        return stringBuilder.append(" ORDER BY DATE_TO DESC").toString();
    }
}
