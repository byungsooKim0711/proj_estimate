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

    public List<Sender> selectSenders(String search) {
        return senderMapper.selectSenders(search);
    }
    
    public Sender findSenderById(int senderId) {
        return senderMapper.findSenderById(senderId).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }

    public Sender insertSender(Sender sender) {
        senderMapper.insertSender(sender);
        return sender;
    }

    public void deleteSender(int senderId) {
        senderMapper.deleteSender(senderId);
    }

    public int updateSender(Sender sender) {
        return senderMapper.updateSender(sender);
    }
}