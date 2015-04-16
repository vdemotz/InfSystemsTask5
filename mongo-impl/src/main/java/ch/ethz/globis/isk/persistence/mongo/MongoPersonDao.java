package ch.ethz.globis.isk.persistence.mongo;

import ch.ethz.globis.isk.domain.Person;
import ch.ethz.globis.isk.domain.mongo.MongoPerson;
import ch.ethz.globis.isk.persistence.PersonDao;
import ch.ethz.globis.isk.util.Filter;
import ch.ethz.globis.isk.util.Operator;
import ch.ethz.globis.isk.utils.Comparators;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MongoPersonDao extends MongoDao<String, Person> implements PersonDao {

    @Override
    public Person findOneByName(String name) {
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("name", new Filter(Operator.EQUAL, name));
        return findOneByFilter(filterMap);
    }

    @Override
    public Set<Person> getCoauthors(String id) {
    	return Comparators.getCoAuthors(this.findOne(id));
    }

    @Override
    public Long computeAuthorDistance(String firstId, String secondId) {
    	return Comparators.searchBreadthFirstCoAuthors(this, this.findOne(firstId), this.findOne(secondId));
    }

    @Override
    public Person createEntity() {
        return new MongoPerson();
    }

    @Override
    protected Class<MongoPerson> getStoredClass() {
        return MongoPerson.class;
    }

    @Override
    protected String collection() {
        return "person";
    }

    private Set<Person> getCoauthors(Person person) {
    	return null;
    }
}
