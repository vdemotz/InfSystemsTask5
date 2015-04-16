package ch.ethz.globis.isk.persistence.mongo;

import ch.ethz.globis.isk.domain.Publication;
import ch.ethz.globis.isk.domain.mongo.MongoPublication;
import ch.ethz.globis.isk.persistence.PublicationDao;
import ch.ethz.globis.isk.util.Filter;
import ch.ethz.globis.isk.util.Operator;
import com.mongodb.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public class MongoPublicationDao extends MongoDao<String, Publication> implements PublicationDao {

    @Override
    protected Query basicQuery() {
        return new Query();
    }

    @Override
    public Publication findOneByTitle(String title) {
        Map<String, Filter> filterMap = new TreeMap<>();
        filterMap.put("title", new Filter(Operator.EQUAL, title));
        return findOneByFilter(filterMap);
    }

    @Override
    public Map<Long, Long> countPerYears(Long startYear, Long endYear) {
    	return null;
    }

    @Override
    public Double getAverageNumberOfAuthors() {
    	return null;
    }

    @Override
    public Publication createEntity() {
        return new MongoPublication();
    }

    @Override
    protected Class<MongoPublication> getStoredClass() {
        return MongoPublication.class;
    }

    @Override
    protected String collection() {
        return "publication";
    }

    @Override
    public List<Publication> findByAuthorIdOrderedByYear(String authorId) {
        return queryByReferenceIdOrderByYear("authors.$id", authorId);
    }

    @Override
    public List<Publication> findByEditorIdOrderedByYear(String editorId) {
        return queryByReferenceIdOrderByYear("editors.$id", editorId);
    }

    @Override
    public List<Publication> findByPublisherOrderedByYear(String publisherId) {
        return queryByReferenceIdOrderByYear("publisher.$id", publisherId);
    }

    @Override
    public List<Publication> findBySchoolOrderedByYear(String schoolId) {
        return queryByReferenceIdOrderByYear("school.$id", schoolId);
    }

    @Override
    public List<Publication> findBySeriesOrderedByYear(String seriesId) {
        return queryByReferenceIdOrderByYear("series.$id", seriesId);
    }
}

class KeyValuePair {

    @Id
    String key;

    String value;

    KeyValuePair() {
    }

    String getValue() {
        return value;
    }

    void setValue(String value) {
        this.value = value;
    }

    String getKey() {
        return key;
    }

    void setKey(String key) {
        this.key = key;
    }
}
