//package com.epam.esm.dao.util;
//
//public final class SqlQueryTest {
//    /* CriteriaQueryBuilderForGiftCertificate to the GiftCertificate table */
//    public static final String FIND_CERTIFICATE_BY_ID =
//            "SELECT gift_certificate.id, gift_certificate.name, description, price, duration, create_date, last_update_date, " +
//                    "ARRAY_AGG('(' || tag.id || ',' || tag.name || ')') AS tags FROM gift_certificate " +
//                    "LEFT JOIN gift_certificate_tag ON gift_certificate_id = gift_certificate.id " +
//                    "LEFT JOIN tag ON tag.id = tag_id WHERE gift_certificate.id=? GROUP BY gift_certificate.id";
//
//    public static final String FIND_ALL_CERTIFICATES_ORDER_BY_DATE_ASC =
//            "SELECT gift_certificate.id, gift_certificate.name, description, price, duration, create_date, last_update_date, " +
//                    "ARRAY_AGG('(' || tag.id || ',' || tag.name || ')') AS tags FROM gift_certificate " +
//                    "LEFT JOIN gift_certificate_tag ON gift_certificate_id = gift_certificate.id " +
//                    "LEFT JOIN tag ON tag.id = tag_id  GROUP BY gift_certificate.id ORDER BY ? offset (? * ?) ROWS fetch next ? ROWS only";
//
//    public static final String FIND_ALL_CERTIFICATES_BY_PART_NAME_OR_DESCRIPTION_ORDER_BY_DATE_DESC =
//            "SELECT gift_certificate.id, gift_certificate.name, description, price, duration, create_date, last_update_date, " +
//                    "ARRAY_AGG('(' || tag.id || ',' || tag.name || ')') AS tags FROM gift_certificate " +
//                    "LEFT JOIN gift_certificate_tag ON gift_certificate_id = gift_certificate.id " +
//                    "LEFT JOIN tag ON tag.id = tag_id  " +
//                    "WHERE (gift_certificate.name ILIKE '%' || ? || '%' or gift_certificate.description ILIKE '%' || ? || '%') " +
//                    "GROUP BY gift_certificate.id ORDER BY ? offset (? * ?) ROWS fetch next ? ROWS only";
//
//    public static final String FIND_ALL_CERTIFICATES_WITH_SPECIFIC_TAG_ORDER_BY_NAME =
//            "SELECT gift_certificate.id, gift_certificate.name, description, price, duration, create_date, last_update_date, " +
//                    "ARRAY_AGG('(' || tag.id || ',' || tag.name || ')') AS tags FROM gift_certificate " +
//                    "LEFT JOIN gift_certificate_tag ON gift_certificate_id = gift_certificate.id " +
//                    "LEFT JOIN tag ON tag.id = tag_id  " +
//                    "WHERE (gift_certificate.id IN (SELECT gift_certificate_id FROM gift_certificate_tag " +
//                    "WHERE tag_id = (SELECT id FROM tag WHERE name=?))) " +
//                    "GROUP BY gift_certificate.id ORDER BY ? offset (? * ?) ROWS fetch next ? ROWS only";
//
//    public static final String FIND_ALL_CERTIFICATES_BY_PART_NAME_OR_DESCRIPTION_WITH_SPECIFIC_TAG_ORDER_BY_DATE_DESC_AND_NAME =
//            "SELECT gift_certificate.id, gift_certificate.name, description, price, duration, create_date, last_update_date, " +
//                    "ARRAY_AGG('(' || tag.id || ',' || tag.name || ')') AS tags FROM gift_certificate " +
//                    "LEFT JOIN gift_certificate_tag ON gift_certificate_id = gift_certificate.id " +
//                    "LEFT JOIN tag ON tag.id = tag_id  " +
//                    "WHERE (gift_certificate.name ILIKE '%' || ? || '%' or gift_certificate.description ILIKE '%' || ? || '%') " +
//                    "AND (gift_certificate.id IN (SELECT gift_certificate_id FROM gift_certificate_tag " +
//                    "WHERE tag_id = (SELECT id FROM tag WHERE name=?))) GROUP BY gift_certificate.id " +
//                    "ORDER BY ?, ? offset (? * ?) ROWS fetch next ? ROWS only";
//
//    public static final String CREATE_CERTIFICATE =
//            "INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date) VALUES(?, ?, ?, ?, ?, ?)";
//    public static final String UPDATE_ALL_CERTIFICATE_FIELDS =
//            "UPDATE gift_certificate SET name=?, description=?, price=?, duration=?, last_update_date=? WHERE id=?";
//    public static final String UPDATE_CERTIFICATE_NAME_PRICE = "UPDATE gift_certificate SET name=?, price=?, last_update_date=? WHERE id=?";
//    public static final String DELETE_CERTIFICATE = "DELETE FROM gift_certificate WHERE id=?";
//
//    /* CriteriaQueryBuilderForGiftCertificate to the Tag table */
//    public static final String FIND_ALL_TAGS = "SELECT id, name FROM tag ORDER BY ?";
//    public static final String FIND_TAG_BY_ID = "SELECT id, name FROM tag WHERE id=?";
//    public static final String FIND_TAG_BY_NAME = "SELECT id, name FROM tag WHERE name=?";
//    public static final String CREATE_TAG = "INSERT INTO tag (name) VALUES(?)";
//    public static final String UPDATE_TAG = "UPDATE tag SET name=? WHERE id=?";
//    public static final String DELETE_TAG = "DELETE FROM tag WHERE id=?";
//
//    /* CriteriaQueryBuilderForGiftCertificate to the GiftCertificateTag table */
//    public static final String CREATE_CERTIFICATE_TAG_BY_TAG_ID = "INSERT INTO gift_certificate_tag VALUES(?, ?)";
//    public static final String DELETE_CERTIFICATE_TAG_BY_CERTIFICATE_ID = "DELETE FROM gift_certificate_tag WHERE gift_certificate_id=?";
//    public static final String DELETE_CERTIFICATE_TAG_BY_TAG_ID = "DELETE FROM gift_certificate_tag WHERE tag_id=?";
//
//    public static final String VISIT = "visit";
//    public static final String REST = "rest";
//    public static final String NATURE = "nature";
//    public static final String SCRIPT = "script";
//    public static final String NEW = "new";
//
//    private SqlQueryTest() {
//    }
//}
