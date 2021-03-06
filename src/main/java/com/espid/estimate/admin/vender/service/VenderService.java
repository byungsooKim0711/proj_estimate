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

    public List<Vender> selectVenders(String search) throws Exception {
        return venderMapper.selectVenders(search);
    }

    /**
     * @param venderId
     * @return Vender
     * @throws ResourceNotFoundException
      */
    public Vender findVenderById(int venderId) throws Exception {
        return venderMapper.findVenderById(venderId).orElseThrow(() -> new ResourceNotFoundException("Vender resource not found with ID: " + venderId));
    }

    public Vender insertVender(Vender vender) throws Exception {
        venderMapper.insertVender(vender);
        return vender;
    }

    public int deleteVender(int venderId) throws Exception {
        return venderMapper.deleteVender(venderId);
    }

    public int updateVender(Vender vender) throws Exception {
        return venderMapper.updateVender(vender);
    }
    
}