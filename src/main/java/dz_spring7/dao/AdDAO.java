package dz_spring7.dao;

import dz_spring7.model.Ad;
import dz_spring7.model.Filter;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository("adDAO")
@Transactional
public class AdDAO extends GeneralDAO<Ad> {

    private static final DateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy'T'hh:mm");
    @SuppressWarnings("SqlResolve")
    private static final String SQL_GET_ADS_BY_CURRENT_DATE = "SELECT * FROM AD WHERE ROWNUM < 101 AND DATE_TO > ?1 ORDER BY DATE_TO DESC";
    private static final String SQL_GET_ADS_BY_FILTER = "SELECT * FROM ";

    //1. получить текущую дату
    //2. сравнить в запросе текущую дату с датой объекта
    //3. если дата объекта меньше текущей даты то взять этот объект из базы данных
    //4. выводить только 100 последних объектов удовлетворяющих условию по дате
    @SuppressWarnings("unchecked")
    public List<Ad> get100Ad(){
        Date currentDate = new Date();

        NativeQuery<Ad> query = (NativeQuery<Ad>) getEntityManager().createNativeQuery(SQL_GET_ADS_BY_CURRENT_DATE, Ad.class);
        return query.setParameter(1, currentDate, TemporalType.TIMESTAMP).getResultList();
    }

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
