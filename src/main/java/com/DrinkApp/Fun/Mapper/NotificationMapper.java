package com.DrinkApp.Fun.Mapper;

import com.DrinkApp.Fun.Dto.NotificationDto;
import com.DrinkApp.Fun.Entity.NotificationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationEntity dtoToEntity(NotificationDto notificationDto);

    NotificationDto entityToDto(NotificationEntity notificationEntity);

}
