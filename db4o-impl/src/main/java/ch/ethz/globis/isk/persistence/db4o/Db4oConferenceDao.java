package ch.ethz.globis.isk.persistence.db4o;

import ch.ethz.globis.isk.domain.*;
import ch.ethz.globis.isk.domain.db4o.Db4oConference;
import ch.ethz.globis.isk.persistence.ConferenceDao;
import ch.ethz.globis.isk.persistence.ProceedingsDao;
import ch.ethz.globis.isk.util.Filter;
import ch.ethz.globis.isk.util.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class Db4oConferenceDao extends Db4oDao<String, Conference> implements ConferenceDao {

    @Autowired
    ProceedingsDao proceedingsDao;

    @Override
    public Conference findOneByName(String name) {
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("name", new Filter(Operator.EQUAL, name));
        return findOneByFilter(filterMap);
    }

    @Override
    public Long countAuthorsForConference(String confId) {
    	return null;
    }

    @Override
    public Set<Person> findAuthorsForConference(String confId) {
    	return null;
    }

    @Override
    public Set<Publication> findPublicationsForConference(String confId) {
    	return null;
    }

    @Override
    public Long countPublicationsForConference(String confId) {
    	return null;
    }

    @Override
    public Conference createEntity() {
        return new Db4oConference();
    }

    @Override
    public Class getStoredClass() {
        return Db4oConference.class;
    }

    private Comparator personComparator() {
        return new Comparator<Person>() {

            @Override
            public int compare(Person o1, Person o2) {
                return o1.getId().compareTo(o2.getId());
            }
        };
    }

    private Comparator publicationComparator() {
        return new Comparator<Publication>() {

            @Override
            public int compare(Publication o1, Publication o2) {
                return o1.getId().compareTo(o2.getId());
            }
        };
    }
}
