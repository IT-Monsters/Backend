package com.example.itmonster.repository;

import com.example.itmonster.domain.Channel;
import com.example.itmonster.domain.Quest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
    Channel getChannelByQuest(Quest quest);

}
