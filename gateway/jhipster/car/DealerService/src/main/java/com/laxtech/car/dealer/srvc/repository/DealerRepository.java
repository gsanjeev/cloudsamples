package com.laxtech.car.dealer.srvc.repository;

import com.laxtech.car.dealer.srvc.domain.Dealer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Dealer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DealerRepository extends JpaRepository<Dealer, Long> {

}
