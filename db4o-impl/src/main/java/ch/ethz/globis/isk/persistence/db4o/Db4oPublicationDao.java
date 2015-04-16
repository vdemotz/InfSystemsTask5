package ch.ethz.globis.isk.persistence.db4o;

import ch.ethz.globis.isk.domain.Publication;
import ch.ethz.globis.isk.domain.db4o.Db4oPublication;
import ch.ethz.globis.isk.persistence.PublicationDao;
import ch.ethz.globis.isk.util.Filter;
import ch.ethz.globis.isk.util.Operator;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class Db4oPublicationDao extends Db4oDao<String, Publication> implements PublicationDao {

    @Override
    public Publication createEntity() {
        return new Db4oPublication();
    }

    @Override
    public Publication findOneByTitle(String title) {
        Map<String, Filter> filterMap = new HashMap<>();
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
    public Class getStoredClass() {
        return Db4oPublication.class;
    }

    @Override
    public List<Publication> findByAuthorIdOrderedByYear(String authorId) {
        return queryByReferenceIdOrderByYear("authors", authorId).execute();
    }

    @Override
    public List<Publication> findByEditorIdOrderedByYear(String editorId) {
        return queryByReferenceIdOrderByYear("editors", editorId).execute();
    }

    @Override
    public List<Publication> findByPublisherOrderedByYear(String publisherId) {
        return queryByReferenceIdOrderByYear("publisher", publisherId).execute();
    }

    @Override
    public List<Publication> findBySchoolOrderedByYear(String schoolId) {
        return queryByReferenceIdOrderByYear("school", schoolId).execute();
    }

    @Override
    public List<Publication> findBySeriesOrderedByYear(String seriesId) {
        return queryByReferenceIdOrderByYear("series", seriesId).execute();
    }
}
