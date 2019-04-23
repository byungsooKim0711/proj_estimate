package com.espid.estimate.admin.price.controller;

import java.util.List;

import com.espid.estimate.admin.price.model.Price;
import com.espid.estimate.admin.price.service.PriceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/admin")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @GetMapping("/price")
    public List<Price> getPrices(@RequestParam(name = "search", required = false) String search) {
        return priceService.selectPrices(search);
    }

    @PostMapping("/price")
    public ResponseEntity<Price> insertPrice(@RequestBody final Price price, final UriComponentsBuilder uriBuilder) {
        priceService.insertPrice(price);

        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.path("/admin/price/{id}").buildAndExpand(price.getPriceId()).toUri());

        return new ResponseEntity<>(price, headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/price/{priceId}")
    public ResponseEntity<Void> deletePrice(@PathVariable(name = "priceId") int priceId) {
        Price deleted = priceService.findPriceById(priceId);
        
        priceService.deletePrice(deleted.getPriceId());
        
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/price/{priceId}")
    public ResponseEntity<Price> updatePrice(@PathVariable(name = "priceId") int priceId, @RequestBody final Price price) {
        Price updated = priceService.findPriceById(priceId);

        price.setPriceId(updated.getPriceId());
        priceService.updatePrice(price);

        return new ResponseEntity<>(price, HttpStatus.OK);
    }
}