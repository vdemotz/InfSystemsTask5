package ch.ethz.globis.isk.persistence.db4o;

import ch.ethz.globis.isk.domain.Person;
import ch.ethz.globis.isk.domain.db4o.Db4oPerson;
import ch.ethz.globis.isk.persistence.PersonDao;
import ch.ethz.globis.isk.util.Filter;
import ch.ethz.globis.isk.util.Operator;
import ch.ethz.globis.isk.utils.Comparators;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class Db4oPersonDao extends Db4oDao<String, Person> implements PersonDao {

    @Override
    public Person createEntity() {
        return new Db4oPerson();
    }

    @Override
    public Person findOneByName(final String name) {
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

    @Override
    public Class getStoredClass() {
        return Db4oPerson.class;
    }

    private Set<Person> getCoauthors(Person person) {
    	return null;
    }
}
