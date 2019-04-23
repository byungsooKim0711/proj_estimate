package com.espid.estimate.admin.sender.mapper;

import java.util.List;
import java.util.Optional;

import com.espid.estimate.admin.sender.model.Sender;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SenderMapper {
    public List<Sender> selectSenders(@Param("search") String search);

    public Optional<Sender> findSenderById(int senderId);
    
    public int insertSender(Sender sender);

    public int deleteSender(int senderId);

    public int updateSender(Sender sender);
}