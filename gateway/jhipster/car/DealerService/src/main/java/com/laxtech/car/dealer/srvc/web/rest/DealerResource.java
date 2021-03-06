package com.laxtech.car.dealer.srvc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.laxtech.car.dealer.srvc.domain.Dealer;

import com.laxtech.car.dealer.srvc.repository.DealerRepository;
import com.laxtech.car.dealer.srvc.repository.search.DealerSearchRepository;
import com.laxtech.car.dealer.srvc.web.rest.errors.BadRequestAlertException;
import com.laxtech.car.dealer.srvc.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Dealer.
 */
@RestController
@RequestMapping("/api")
public class DealerResource {

    private final Logger log = LoggerFactory.getLogger(DealerResource.class);

    private static final String ENTITY_NAME = "dealer";

    private final DealerRepository dealerRepository;

    private final DealerSearchRepository dealerSearchRepository;

    public DealerResource(DealerRepository dealerRepository, DealerSearchRepository dealerSearchRepository) {
        this.dealerRepository = dealerRepository;
        this.dealerSearchRepository = dealerSearchRepository;
    }

    /**
     * POST  /dealers : Create a new dealer.
     *
     * @param dealer the dealer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dealer, or with status 400 (Bad Request) if the dealer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dealers")
    @Timed
    public ResponseEntity<Dealer> createDealer(@RequestBody Dealer dealer) throws URISyntaxException {
        log.debug("REST request to save Dealer : {}", dealer);
        if (dealer.getId() != null) {
            throw new BadRequestAlertException("A new dealer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dealer result = dealerRepository.save(dealer);
        dealerSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/dealers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dealers : Updates an existing dealer.
     *
     * @param dealer the dealer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dealer,
     * or with status 400 (Bad Request) if the dealer is not valid,
     * or with status 500 (Internal Server Error) if the dealer couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dealers")
    @Timed
    public ResponseEntity<Dealer> updateDealer(@RequestBody Dealer dealer) throws URISyntaxException {
        log.debug("REST request to update Dealer : {}", dealer);
        if (dealer.getId() == null) {
            return createDealer(dealer);
        }
        Dealer result = dealerRepository.save(dealer);
        dealerSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dealer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dealers : get all the dealers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dealers in body
     */
    @GetMapping("/dealers")
    @Timed
    public List<Dealer> getAllDealers() {
        log.debug("REST request to get all Dealers");
        return dealerRepository.findAll();
        }

    /**
     * GET  /dealers/:id : get the "id" dealer.
     *
     * @param id the id of the dealer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dealer, or with status 404 (Not Found)
     */
    @GetMapping("/dealers/{id}")
    @Timed
    public ResponseEntity<Dealer> getDealer(@PathVariable Long id) {
        log.debug("REST request to get Dealer : {}", id);
        Dealer dealer = dealerRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dealer));
    }

    /**
     * DELETE  /dealers/:id : delete the "id" dealer.
     *
     * @param id the id of the dealer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dealers/{id}")
    @Timed
    public ResponseEntity<Void> deleteDealer(@PathVariable Long id) {
        log.debug("REST request to delete Dealer : {}", id);
        dealerRepository.delete(id);
        dealerSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/dealers?query=:query : search for the dealer corresponding
     * to the query.
     *
     * @param query the query of the dealer search
     * @return the result of the search
     */
    @GetMapping("/_search/dealers")
    @Timed
    public List<Dealer> searchDealers(@RequestParam String query) {
        log.debug("REST request to search Dealers for query {}", query);
        return StreamSupport
            .stream(dealerSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
