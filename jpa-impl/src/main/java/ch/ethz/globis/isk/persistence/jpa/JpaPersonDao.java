package ch.ethz.globis.isk.persistence.jpa;

import ch.ethz.globis.isk.domain.Person;
import ch.ethz.globis.isk.domain.jpa.JpaPerson;
import ch.ethz.globis.isk.persistence.PersonDao;
import ch.ethz.globis.isk.util.Filter;
import ch.ethz.globis.isk.util.Operator;
import ch.ethz.globis.isk.utils.Comparators;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Repository
public class JpaPersonDao extends JpaDao<String, Person> implements PersonDao {

    @Override
    protected Class<JpaPerson> getStoredClass() {
        return JpaPerson.class;
    }

    @Override
    public Person createEntity() {
        return new JpaPerson();
    }

    @Override
    public Person findOneByName(String name) {
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("name", new Filter(Operator.EQUAL, name));
        return findOneByFilter(filterMap);
    }

    @Override
    public Set<Person> getCoauthors(String id) {
    	return null;
    }

    @Override
    public Long computeAuthorDistance(String firstId, String secondId) {
    	return Comparators.searchBreadthFirstCoAuthors(this, this.findOne(firstId), this.findOne(secondId));
    }

    private Set<Person> getCoauthors(Person person) {
    	return null;
    }
}
