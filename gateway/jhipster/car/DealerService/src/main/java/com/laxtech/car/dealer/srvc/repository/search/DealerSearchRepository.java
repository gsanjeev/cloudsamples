package com.laxtech.car.dealer.srvc.repository.search;

import com.laxtech.car.dealer.srvc.domain.Dealer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Dealer entity.
 */
public interface DealerSearchRepository extends ElasticsearchRepository<Dealer, Long> {
}
