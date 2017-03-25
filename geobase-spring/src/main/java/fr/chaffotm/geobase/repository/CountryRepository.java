package fr.chaffotm.geobase.repository;

import fr.chaffotm.geobase.model.Country;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CountryRepository extends PagingAndSortingRepository<Country, Long> {

}
