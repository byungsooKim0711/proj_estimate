package com.espid.estimate.admin.sender.service;

import java.util.List;

import com.espid.estimate.admin.sender.mapper.SenderMapper;
import com.espid.estimate.admin.sender.model.Sender;
import com.espid.estimate.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SenderService {

    @Autowired
    private SenderMapper senderMapper;

    public List<Sender> selectSenders(String search) throws Exception {
        return senderMapper.selectSenders(search);
    }
    
    /**
     * @param senderId
     * @return Sender
     * @throws ResourceNotFoundException
      */
    public Sender findSenderById(int senderId) throws Exception {
        return senderMapper.findSenderById(senderId).orElseThrow(() -> new ResourceNotFoundException("Sender resource not found with ID: " + senderId));
    }

    public Sender insertSender(Sender sender) throws Exception {
        senderMapper.insertSender(sender);
        return sender;
    }

    public int deleteSender(int senderId) throws Exception {
        return senderMapper.deleteSender(senderId);
    }

    public int updateSender(Sender sender) throws Exception {
        return senderMapper.updateSender(sender);
    }
}