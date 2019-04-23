package com.espid.estimate.admin.sender.controller;

import java.util.List;

import com.espid.estimate.admin.sender.model.Sender;
import com.espid.estimate.admin.sender.service.SenderService;

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
public class SenderController {

    @Autowired
    private SenderService senderService;

    @GetMapping("/sender")
    public List<Sender> getSender(@RequestParam(name = "search", required = false) String search) {
        return senderService.selectSenders(search);
    }

    @PostMapping("/sender")
    public ResponseEntity<Sender> insertSender(@RequestBody final Sender sender, final UriComponentsBuilder uriBuilder) {
        senderService.insertSender(sender);

        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.path("/admin/sender/{id}").buildAndExpand(sender.getSpidId()).toUri());

        return new ResponseEntity<>(sender, headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/sender/{senderId}")
    public ResponseEntity<?> deleteSender(@PathVariable(name = "senderId") int senderId) {
        Sender deleted = senderService.findSenderById(senderId);

        senderService.deleteSender(deleted.getSpidId());

        return ResponseEntity.accepted().build();
    }

    @PutMapping("/sender/{senderId}")
    public ResponseEntity<Sender> updateSender(@PathVariable(name = "senderId") int senderId, @RequestBody final Sender sender) {
        Sender updated = senderService.findSenderById(senderId);

        sender.setSpidId(updated.getSpidId());
        senderService.updateSender(sender);

        return new ResponseEntity<>(sender, HttpStatus.OK); 
    }
}