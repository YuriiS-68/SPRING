package dz_spring7.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import dz_spring7.model.Ad;
import dz_spring7.model.Filter;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TemporalType;
import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository("adDAO")
@Transactional
public class AdDAO extends GeneralDAO<Ad> {

    @SuppressWarnings("SqlResolve")
    private static final String SQL_GET_ADS_BY_CURRENT_DATE = "SELECT * FROM AD WHERE ROWNUM < 101 AND DATE_TO > ? ORDER BY DATE_TO DESC";

    @SuppressWarnings("unchecked")
    public List<Ad> get100Ad() {
        Date currentDate = new Date();

        NativeQuery<Ad> query = (NativeQuery<Ad>) getEntityManager().createNativeQuery(SQL_GET_ADS_BY_CURRENT_DATE, Ad.class);
        return query.setParameter(1, currentDate, TemporalType.TIMESTAMP).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Ad> findAdsByFilter(Filter filter) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> filtersParams = objectMapper.convertValue(filter, Map.class);

        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Ad> criteriaQuery = criteriaBuilder.createQuery(Ad.class);

        Root<Ad> adRoot = criteriaQuery.from(Ad.class);

        Predicate predicate = criteriaBuilder.conjunction();
        for (String param : filtersParams.keySet()) {
            if (filtersParams.get(param) != null) {
                switch (param) {
                    case "categoryType":
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(adRoot.get(param), filter.getCategoryType()));
                        break;
                    case "description":
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(adRoot.get(param), "%" + filtersParams.get(param) + "%"));
                        break;
                    case "city":
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(adRoot.get(param), filtersParams.get(param)));
                        break;
                }
            }
        }
        criteriaQuery.select(adRoot).where(predicate).orderBy(criteriaBuilder.desc(adRoot.get("dateTo")));
        return getEntityManager().createQuery(criteriaQuery).getResultList();
    }
}