package com.DrinkApp.Fun.Utils.Notification;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NotificationSenderFactory {

    private final Map<String, NotificationSender> senders = new HashMap<>();

    public NotificationSenderFactory(List<NotificationSender> senderList) { //la initializare, springboot scaneaza clasele care impl notifications sender sic reaza o lista cu ele
        for (NotificationSender sender : senderList) {
            senders.put(sender.getType(), sender);
        }
    }

    public NotificationSender getSender(String type) {
        NotificationSender sender = senders.get(type);
        if (sender == null) {
            throw new IllegalArgumentException("Unknown notification sender type: " + type);
        }
        return sender;
    }
}
