package com.laxtech.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.security.Principal;

/**
 * Created by magnus on 04/03/15.
 */
@RestController
public class ProductApiService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductApiService.class);

    @Autowired
    private OAuth2RestTemplate restTemplate;


/*    @Autowired
    private LoadBalancerClient loadBalancer;*/

    @RequestMapping("/{productId}")
    //@HystrixCommand(fallbackMethod = "defaultProductComposite")
    public ResponseEntity<String> getProductComposite(
            @PathVariable int productId,
            @RequestHeader(value = "Authorization") String authorizationHeader,
            Principal currentUser) {

        LOG.info("ProductApi: User={}, Auth={}, called with productId={}", currentUser.getName(), authorizationHeader, productId);
        //URI uri = loadBalancer.choose("productcomposite").getUri();
        //String url = uri.toString() + "/product/" + productId;
        String url = "http://localhost:8072" + "/product/" + productId;
        LOG.debug("GetProductComposite from URL: {}", url);
        ResponseEntity<String> result = null;
        try {
            result = restTemplate.getForEntity(url, String.class);
        } catch (Exception exp) {
            System.out.println("Error when calling URL: http://localhost:8072/product/");
            exp.printStackTrace();
        }

        if (result != null) {
            LOG.info("GetProductComposite http-status: {}", result.getStatusCode());
            LOG.debug("GetProductComposite body: {}", result.getBody());
        }

        return result;
    }

    /**
     * Fallback method for getProductComposite()
     *
     * @param productId
     * @return
     */
/*    public ResponseEntity<String> defaultProductComposite(
        @PathVariable int productId,
        @RequestHeader(value="Authorization") String authorizationHeader,
        Principal currentUser) {

        LOG.warn("Using fallback method for product-composite-service. User={}, Auth={}, called with productId={}", currentUser.getName(), authorizationHeader, productId);
        return new ResponseEntity<String>("", HttpStatus.BAD_GATEWAY);
    }*/
}
