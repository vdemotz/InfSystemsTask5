package ch.ethz.globis.isk.persistence.jpa;

import ch.ethz.globis.isk.domain.Publication;
import ch.ethz.globis.isk.domain.jpa.JpaPublication;
import ch.ethz.globis.isk.persistence.PublicationDao;
import ch.ethz.globis.isk.util.Filter;
import ch.ethz.globis.isk.util.Operator;
import ch.ethz.globis.isk.utils.Comparators;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JpaPublicationDao extends JpaDao<String, Publication> implements PublicationDao {

    private static String AVERAGE_NUMBER_OF_AUTHORS = "select avg(convert(count, double)) as average " + "from (select  publication_author.id, count(*) as count " + "from publication_author inner join person " + "on publication_author.person_id = person.id " + "group by publication_author.id)";

    private static String COUNT_PUBLICATIONS_PER_YEAR_INTERVAL = "select year, count(*) from publication " + "where year >= :startYear and year <= :endYear group by year";

    @Override
    protected Class<JpaPublication> getStoredClass() {
        return JpaPublication.class;
    }

    @Override
    public Publication createEntity() {
        return new JpaPublication();
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
    	return Comparators.getAverageNumberOfAuthors(this);
    }

    @Override
    public List<Publication> findByAuthorIdOrderedByYear(String authorId) {
        return queryByReferenceIdOrderByYear("Publication", "authors", authorId);
    }

    @Override
    public List<Publication> findByEditorIdOrderedByYear(String editorId) {
        return queryByReferenceIdOrderByYear("Publication", "editors", editorId);
    }

    @Override
    public List<Publication> findByPublisherOrderedByYear(String publisherId) {
        return queryByReferenceIdOrderByYear("Publication", "publisher", publisherId);
    }

    @Override
    public List<Publication> findBySchoolOrderedByYear(String schoolId) {
        return queryByReferenceIdOrderByYear("Publication", "school", schoolId);
    }

    @Override
    public List<Publication> findBySeriesOrderedByYear(String seriesId) {
        return queryByReferenceIdOrderByYear("Publication", "series", seriesId);
    }
}
