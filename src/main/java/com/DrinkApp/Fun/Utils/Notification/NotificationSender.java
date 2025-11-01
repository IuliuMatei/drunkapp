package com.DrinkApp.Fun.Utils.Notification;

import com.DrinkApp.Fun.Entity.UserEntity;

import java.util.List;

public interface NotificationSender {

    String getType();

    void send(UserEntity sender, String message, List<UserEntity> recipients);
}
