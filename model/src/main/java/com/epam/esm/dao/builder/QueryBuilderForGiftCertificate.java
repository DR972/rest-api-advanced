package com.epam.esm.dao.builder;

import com.epam.esm.entity.GiftCertificate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class QueryBuilderForGiftCertificate {
    private static final String NAME = "name";
    private static final String TAG = "tag";
    private static final String SORTING = "sorting";
    private static final String DESCRIPTION = "description";
    private static final String QUERY_PART_1 = "SELECT g FROM GiftCertificate g LEFT JOIN g.tags t ";
    private static final String QUERY_FOR_SECOND_TAG = " having g in (SELECT ga FROM GiftCertificate ga LEFT JOIN ga.tags ta where ta.name=?";

    public Query<GiftCertificate> build(EntityManager entityManager, MultiValueMap<String, String> requestParams) {
        List<List<String>> allQueryParams = searchByDescriptionOrName(requestParams.get(NAME), new ArrayList<>(), new ArrayList<>(), NAME);
        allQueryParams = searchByDescriptionOrName(requestParams.get(DESCRIPTION), allQueryParams.get(0), allQueryParams.get(1), DESCRIPTION);
        allQueryParams = searchByTag(requestParams.get(TAG), allQueryParams.get(0), allQueryParams.get(1));

        String queryForSecondTag = "";
        if (requestParams.get(TAG) != null && requestParams.get(TAG).size() > 1) {
            queryForSecondTag = QUERY_FOR_SECOND_TAG + (allQueryParams.get(1).size() + 1) + ")";
            allQueryParams.get(1).add(requestParams.get(TAG).get(1));
        }

        String finalQuery;
        if (!allQueryParams.get(0).isEmpty()) {
            finalQuery = QUERY_PART_1 + allQueryParams.get(0).stream().collect(Collectors.joining(" AND ", " WHERE ", " ")) +
                    "GROUP BY g.id" + queryForSecondTag + buildOrder(requestParams.get(SORTING));
        } else {
            finalQuery = QUERY_PART_1 + "GROUP BY g.id" + buildOrder(requestParams.get(SORTING));
        }
        System.out.println(finalQuery);
        Query<GiftCertificate> query = entityManager.unwrap(Session.class).createQuery(finalQuery, GiftCertificate.class);
        List<String> finalQueryParams = allQueryParams.get(1);
        IntStream.range(0, finalQueryParams.size()).forEach(i->query.setParameter(i + 1, finalQueryParams.get(i)));
        return query;
    }

    private String buildOrder(List<String> orders) {
        if (orders != null) {
            List<String> orderList = new ArrayList<>();
            orders.forEach(o-> {
                if (o.startsWith("-")) {
                    orderList.add("g." + o.substring(1) + " desc ");
                } else orderList.add("g." + o);
            });
            return " ORDER BY " + String.join(",", orderList);
        } else return "";
    }

    private List<List<String>> searchByDescriptionOrName(List<String> requestParams, List<String> partsOfQuery, List<String> queryParams, String paramName) {
        if (requestParams != null) {
            requestParams.forEach(p-> {
                partsOfQuery.add("g." + paramName + " LIKE CONCAT('%',?" + (queryParams.size() + 1) + ",'%')");
                queryParams.add(p);
            });
        }
        return Arrays.asList(partsOfQuery, queryParams);
    }

    private List<List<String>> searchByTag(List<String> requestParams, List<String> partsOfQuery, List<String> queryParams) {
        if (requestParams != null) {
            partsOfQuery.add("t.name = ?" + (queryParams.size() + 1));
            queryParams.add(requestParams.get(0));
        }
        return Arrays.asList(partsOfQuery, queryParams);
    }
}
