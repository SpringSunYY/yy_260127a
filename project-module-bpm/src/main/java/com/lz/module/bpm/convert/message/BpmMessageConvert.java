package com.lz.module.bpm.convert.message;

import com.lz.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Map;

@Mapper
public interface BpmMessageConvert {

    BpmMessageConvert INSTANCE = Mappers.getMapper(BpmMessageConvert.class);

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "templateCode", target = "templateCode")
    @Mapping(source = "templateParams", target = "templateParams")
    NotifySendSingleToUserReqDTO convert(Long userId, String templateCode, Map<String, Object> templateParams);

}
