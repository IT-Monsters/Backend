package com.example.itmonster.socket;

import com.amazonaws.services.connect.model.ChatMessage;
import com.example.itmonster.domain.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findTop100ByChannelIdOrderByCreatedAtDesc(Long roomId);
}
