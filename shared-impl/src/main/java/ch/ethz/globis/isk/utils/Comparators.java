package ch.ethz.globis.isk.utils;

import ch.ethz.globis.isk.domain.ConferenceEdition;
import ch.ethz.globis.isk.domain.JournalEdition;
import ch.ethz.globis.isk.domain.Person;
import ch.ethz.globis.isk.domain.Publication;
import ch.ethz.globis.isk.persistence.PersonDao;
import ch.ethz.globis.isk.persistence.PublicationDao;
import ch.ethz.globis.isk.util.Filter;
import ch.ethz.globis.isk.util.Operator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Comparators {

    public static Comparator<ConferenceEdition> conferenceEditionComparator() {
        return new Comparator<ConferenceEdition>() {
            @Override
            public int compare(ConferenceEdition o1, ConferenceEdition o2) {
                int yearResult = o1.getYear().compareTo(o2.getYear());
                if (yearResult == 0) {
                    return o1.getId().compareTo(o2.getId());
                } else {
                    return yearResult;
                }
            }
        };
    }

    public static Comparator<JournalEdition> journalEditionComparator() {
        return new Comparator<JournalEdition>() {
            @Override
            public int compare(JournalEdition o1, JournalEdition o2) {
                int yearResult = o1.getYear().compareTo(o2.getYear());
                if (yearResult == 0) {
                    int volumeResult = o1.getVolume().compareTo(o2.getVolume());
                    if (volumeResult == 0) {
                        return o1.getNumber().compareTo(o2.getNumber());
                    } else {
                        return volumeResult;
                    }
                } else {
                    return yearResult;
                }
            }
        };
    }

    public static Comparator<Publication> publicationYearComparator() {
        return new Comparator<Publication>() {
            @Override
            public int compare(Publication o1, Publication o2) {
                int yearResult = o1.getYear().compareTo(o2.getYear());
                if (yearResult == 0) {
                    return o1.getTitle().compareTo(o2.getTitle());
                } else {
                    return yearResult;
                }
            }
        };
    }
    
    public static Long searchBreadthFirstCoAuthors(PersonDao personDao, Person departure, Person target) {
    	Long distance = new Long(0);
    	Set<Person> set = new HashSet<Person>();
    	set.add(departure);
    	while(!set.contains(target)) {
    		distance++;
    		for (Person p : set) {
    			set.addAll(personDao.getCoauthors(p.getId()));
    		}
    	}
    	return distance;
    }
    
    public static Set<Person> getCoAuthors(Person author) {
    	Set<Person> set = new HashSet<Person>();
    	Set<Publication> publications = author.getAuthoredPublications();
    	publications.addAll(author.getEditedPublications());
    	for (Publication p : publications) {
    		set.addAll(p.getAuthors());
    		set.addAll(p.getEditors());
    	}
    	return set;
    }
    
    public static Double getAverageNumberOfAuthors(PublicationDao pubDao) {
    	Iterable<Publication> publications = pubDao.findAll();
    	Double numbAuthors = new Double(0);
    	for (Publication p : publications) {
    		numbAuthors += p.getAuthors().size();
    	}
    	return numbAuthors / pubDao.count();
    }
    
    public static Map<Long, Long> countPerYear(PublicationDao pubDao, Long startYear, Long endYear) {
    	Map<Long, Long> map = new HashMap<Long, Long>();
    	for (Long year = startYear;year <= endYear; year++) {
    		Map<String, Filter> filterMap = new HashMap<>();
    		filterMap.put("year", new Filter(Operator.EQUAL, year));
    		map.put(year, new Long(pubDao.countAllByFilter(filterMap)));
    	}
    	return map;
    }
}
