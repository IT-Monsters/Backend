package com.example.itmonster.utils;

import com.amazonaws.services.dynamodbv2.xspec.B;
import com.example.itmonster.domain.QQuest;
import com.example.itmonster.domain.QStackOfQuest;
import com.example.itmonster.domain.Quest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;


public class SearchPredicate {

    private static QQuest quest = QQuest.quest;
    private static QStackOfQuest stackOfQuest = QStackOfQuest.stackOfQuest;

    public static List<Quest> filterSearch( MultiValueMap<String, String> allParameters , JPAQueryFactory jpaQueryFactory){
        return jpaQueryFactory.selectFrom( QQuest.quest )
            .where( containsTitle( allParameters.get("title") ),
                containsContent( allParameters.get("content") ),
                loeDuration( allParameters.get("duration") ),
                loeBackend( allParameters.get("backend") ),
                loeFrontend( allParameters.get("frontend") ),
                loeFullstack( allParameters.get("fullstack") ),
                loeDesigner( allParameters.get("designer") ),
                inStacks( allParameters.get("stack")) )
            .fetch();
    }

    // 제목 필터
    private static BooleanExpression containsTitle(List<String> title){
        return title != null ?
            quest.title.contains( title.get(0) ) : null;
    }

    // 내용 필터
    private static BooleanExpression containsContent(List<String> content){
        return content != null ?
            quest.content.contains( content.get(0) ) : null;
    }

    // 기간 필터
    private static BooleanExpression loeDuration(List<String> duration){
        return duration != null ?
            quest.duration.loe( Long.parseLong(duration.get(0)) ) : null;
    }

    // 프.백.풀.디 인원수 필터
    private static BooleanExpression loeFrontend(List<String> frontend_num){
        return frontend_num != null ?
            quest.frontend.between( 1,  Long.parseLong(frontend_num.get(0)) ) : null;
    }

    private static BooleanExpression loeBackend(List<String> backend_num ){
        return backend_num != null ?
            quest.backend.between( 1, Long.parseLong(backend_num.get(0)) ) : null;
    }

    private static BooleanExpression loeFullstack(List<String> fullstack_num ){
        return fullstack_num != null ?
            quest.fullstack.between( 1, Long.parseLong(fullstack_num.get(0)) ) : null;
    }

    private static BooleanExpression loeDesigner(List<String> designer_num ){
        return designer_num != null ?
            quest.designer.between( 1, Long.parseLong(designer_num.get(0)) ) : null;
    }

    // 스택 필터
    private static BooleanExpression inStacks( List<String> stacks ){
        if( stacks == null ) return null;
        return quest.in(
            JPAExpressions.select( stackOfQuest.quest )
                .from( stackOfQuest )
                .where( stackOfQuest.stackName.in( stacks ) )
                .groupBy( stackOfQuest.quest )
                .having( stackOfQuest.count().eq((long) stacks.size())) ) ;
    }


}
