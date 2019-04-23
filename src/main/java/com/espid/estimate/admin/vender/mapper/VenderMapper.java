package com.espid.estimate.admin.vender.mapper;

import java.util.List;
import java.util.Optional;

import com.espid.estimate.admin.vender.model.Vender;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VenderMapper {
    public List<Vender> selectVenders(@Param("search") String search);

    public Optional<Vender> findVenderById(int venderId);
    
    public int insertVender(Vender vender);

    public int deleteVender(int venderId);

    public int updateVender(Vender vender);
}