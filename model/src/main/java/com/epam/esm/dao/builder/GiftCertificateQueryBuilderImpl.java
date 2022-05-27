package com.epam.esm.dao.builder;

import com.epam.esm.entity.GiftCertificate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The class {@code GiftCertificateQueryBuilderImpl} creates a query to get a list of GiftCertificate from the database.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Component
public class GiftCertificateQueryBuilderImpl implements GiftCertificateQueryBuilder {
    private static final String NAME = "name";
    private static final String TAG = "tag";
    private static final String SORTING = "sorting";
    private static final String DESCRIPTION = "description";
    private static final String QUERY_PART_1 = "SELECT g FROM GiftCertificate g LEFT JOIN g.tags t ";
    private static final String QUERY_FOR_SECOND_TAG = " having g in (SELECT ga FROM GiftCertificate ga LEFT JOIN ga.tags ta where ta.name=?";

    /**
     * This method creates a query to get a list of GiftCertificate from the database.
     *
     * @param entityManager EntityManager
     * @param requestParams MultiValueMap<String, String>
     * @return query
     */
    public Query<GiftCertificate> build(EntityManager entityManager, MultiValueMap<String, String> requestParams) {
        List<List<String>> allQueryParams = searchByDescriptionOrName(requestParams.get(NAME), new ArrayList<>(), new ArrayList<>(), NAME);
        allQueryParams = searchByDescriptionOrName(requestParams.get(DESCRIPTION), allQueryParams.get(0), allQueryParams.get(1), DESCRIPTION);
        allQueryParams = searchByTag(requestParams.get(TAG), allQueryParams.get(0), allQueryParams.get(1));
        allQueryParams = searchBySecondTag(requestParams.get(TAG), allQueryParams.get(0), allQueryParams.get(1));

        Query<GiftCertificate> query = entityManager.unwrap(Session.class).createQuery(createQuery(allQueryParams, requestParams.get(SORTING)), GiftCertificate.class);
        List<String> finalQueryParams = allQueryParams.get(1);
        IntStream.range(0, finalQueryParams.size()).forEach(i -> query.setParameter(i + 1, finalQueryParams.get(i)));
        return query;
    }

    /**
     * This method creates a sort string for the query.
     *
     * @param orders List<String>
     * @return a sort string
     */
    private String buildOrder(List<String> orders) {
        return orders != null ? " ORDER BY " + orders.stream().map(o -> o.startsWith("-") ? "g." + o.substring(1) + " desc " : "g." + o).collect(Collectors.joining(",")) : "";
    }

    /**
     * This method creates a list parts and parameters of the query for the part of the name and description.
     *
     * @param requestParams List<String>
     * @param partsOfQuery  List<String>
     * @param queryParams   List<String>
     * @param paramName     String
     * @return list with parts and parameters of the query
     */
    private List<List<String>> searchByDescriptionOrName(List<String> requestParams, List<String> partsOfQuery, List<String> queryParams, String paramName) {
        if (requestParams != null) {
            requestParams.forEach(p -> {
                partsOfQuery.add("g." + paramName + " LIKE CONCAT('%',?" + (queryParams.size() + 1) + ",'%')");
                queryParams.add(p);
            });
        }
        return Arrays.asList(partsOfQuery, queryParams);
    }

    /**
     * This method creates a list parts and parameters of the query for the tag.
     *
     * @param requestParams List<String>
     * @param partsOfQuery  List<String>
     * @param queryParams   List<String>
     * @return list with parts and parameters of the query
     */
    private List<List<String>> searchByTag(List<String> requestParams, List<String> partsOfQuery, List<String> queryParams) {
        if (requestParams != null) {
            partsOfQuery.add("t.name = ?" + (queryParams.size() + 1));
            queryParams.add(requestParams.get(0));
        }
        return Arrays.asList(partsOfQuery, queryParams);
    }

    /**
     * This method creates a list parts and parameters of the query for the second tag.
     *
     * @param requestParams List<String>
     * @param partsOfQuery  List<String>
     * @param queryParams   List<String>
     * @return list with parts and parameters of the query
     */
    private List<List<String>> searchBySecondTag(List<String> requestParams, List<String> partsOfQuery, List<String> queryParams) {
        String queryForSecondTag = "";
        if ((requestParams != null) && (requestParams.size() > 1)) {
            queryForSecondTag = QUERY_FOR_SECOND_TAG + "2)";
            queryParams.add(requestParams.get(1));
        }
        return Arrays.asList(partsOfQuery, queryParams, Collections.singletonList(queryForSecondTag));
    }

    /**
     * This method creates a list parts and parameters of the query for the second tag.
     *
     * @param allQueryParams List<List<String>>
     * @param orders         List<String>
     * @return String query
     */
    private String createQuery(List<List<String>> allQueryParams, List<String> orders) {
        return !allQueryParams.get(0).isEmpty() ? QUERY_PART_1 + allQueryParams.get(0).stream().collect(Collectors.joining(" AND ", " WHERE ", " ")) +
                "GROUP BY g.id" + allQueryParams.get(2).get(0) + buildOrder(orders) : QUERY_PART_1 + "GROUP BY g.id" + buildOrder(orders);
    }
}
