package com.app.ridesync.repositories;

import com.app.ridesync.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUserId(Integer userId);

    List<Notification> findByUserIdAndReadFlag(Integer userId, Integer readFlag);
}
