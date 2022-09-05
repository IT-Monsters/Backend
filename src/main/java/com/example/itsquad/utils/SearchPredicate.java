package com.example.itsquad.utils;

import com.example.itsquad.domain.QQuest;
import com.example.itsquad.domain.QStackOfQuest;
import com.example.itsquad.domain.Quest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.util.MultiValueMap;


public class SearchPredicate {

    public static BooleanBuilder filter( MultiValueMap<String, String> allParameters , JPAQueryFactory jpaQueryFactory ){


        BooleanBuilder searchBuilder = new BooleanBuilder();

        QQuest quest = QQuest.quest;
        QStackOfQuest stackOfQuest = QStackOfQuest.stackOfQuest;

       // 시간나면 BooleanExpress 형태로 리팩토링할 예정

        //stack 필터
        if( allParameters.get("stack") != null ){
            List<String> stacks = allParameters.get("stack");
            List<Quest> quests = jpaQueryFactory.select( stackOfQuest.quest )
                .from( stackOfQuest )
                .where( stackOfQuest.stackName.in( stacks ) )
                .groupBy( stackOfQuest.quest )
                .having( stackOfQuest.count().eq((long) stacks.size()) ).fetch();

            searchBuilder.and( quest.in( quests ) );
        }

        // duration 필터
        if (allParameters.get("duration") != null) {
            long duration = Long.parseLong(allParameters.get("duration").get(0));
            searchBuilder.and(quest.duration.loe( duration ) );
        }

        // 프.백.풀.디 인원수
        if (allParameters.get("frontend") != null) {
            long frontend = Long.parseLong(allParameters.get("frontend").get(0));
            searchBuilder.and(quest.frontend.loe( frontend ) );
        }
        if (allParameters.get("backend") != null) {
            long backend = Long.parseLong(allParameters.get("backend").get(0));
            searchBuilder.and(quest.backend.loe( backend ) );
        }
        if (allParameters.get("fullstack") != null) {
            long fullstack = Long.parseLong(allParameters.get("fullstack").get(0));
            searchBuilder.and(quest.fullstack.loe( fullstack ) );
        }
        if (allParameters.get("designer") != null) {
            long designer = Long.parseLong(allParameters.get("designer").get(0));
            searchBuilder.and(quest.designer.loe( designer ) );
        }

        // 제목 필터링
        if (allParameters.get("title") != null) {
            String title = allParameters.get("title").get(0);
            searchBuilder.and(quest.title.contains( title ) );
        }

        // 내용 필터링
        if (allParameters.get("content") != null) {
            String content = allParameters.get("content").get(0);
            searchBuilder.and(quest.content.contains( content ) );
        }


        return searchBuilder;
    }

}
