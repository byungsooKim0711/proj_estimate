package com.espid.estimate.admin.vender.controller;

import com.espid.estimate.admin.vender.model.Vender;
import com.espid.estimate.admin.vender.service.VenderService;

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

import java.util.List;

@RestController
@RequestMapping("/admin")
public class VenderController {

    @Autowired
    private VenderService venderService;

    @GetMapping("/vender")
    public List<Vender> getVenders(@RequestParam(name = "search", required = false) String search) throws Exception {
        return venderService.selectVenders(search);

    }

    @PostMapping("/vender")
    public ResponseEntity<Vender> insertVender(@RequestBody final Vender vender, final UriComponentsBuilder uriBuilder) throws Exception {
        venderService.insertVender(vender);

        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.path("/admin/vender/{id}").buildAndExpand(vender.getVenderId()).toUri());

        return new ResponseEntity<>(vender, headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/vender/{venderId}")
    public ResponseEntity<?> deleteVender(@PathVariable(name = "venderId") int venderId) throws Exception {
        Vender deleted = venderService.findVenderById(venderId);

        venderService.deleteVender(deleted.getVenderId());

        return ResponseEntity.accepted().build();
    }

    @PutMapping("/vender/{venderId}")
    public ResponseEntity<Vender> updateVender(@PathVariable(name = "venderId") int venderId, @RequestBody final Vender vender) throws Exception {
        Vender updated = venderService.findVenderById(venderId);

        vender.setVenderId(updated.getVenderId());
        venderService.updateVender(vender);

        return new ResponseEntity<>(vender, HttpStatus.OK);
    }
}