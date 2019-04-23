package com.espid.estimate.admin.vender.service;

import java.util.List;

import com.espid.estimate.admin.vender.mapper.VenderMapper;
import com.espid.estimate.admin.vender.model.Vender;
import com.espid.estimate.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VenderService {

    @Autowired
    private VenderMapper venderMapper;

    public List<Vender> selectVenders(String search) {
        return venderMapper.selectVenders(search);
    }

    /**
     * @param venderId
     * @return Vender
     * @throws ResourceNotFoundException
      */
    public Vender findVenderById(int venderId) {
        return venderMapper.findVenderById(venderId).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }

    public Vender insertVender(Vender vender) {
        venderMapper.insertVender(vender);
        return vender;
    }

    public void deleteVender(int venderId) {
        venderMapper.deleteVender(venderId);
    }

    public int updateVender(Vender vender) {
        return venderMapper.updateVender(vender);
    }
    
}