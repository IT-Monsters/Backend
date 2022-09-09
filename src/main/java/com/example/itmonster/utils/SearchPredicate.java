package com.example.itmonster.utils;

import com.example.itmonster.domain.QQuest;
import com.example.itmonster.domain.QStackOfQuest;
import com.example.itmonster.domain.Quest;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.util.MultiValueMap;


public class SearchPredicate {

    private static final QQuest quest = QQuest.quest;
    private static final QStackOfQuest stackOfQuest = QStackOfQuest.stackOfQuest;

    public static List<Quest> filterSearch( MultiValueMap<String, String> allParameters , JPAQueryFactory jpaQueryFactory){
        return jpaQueryFactory.selectFrom( QQuest.quest )
            .where( containsTitle( allParameters.get("title") ),
                containsContent( allParameters.get("content") ),
                loeDuration( allParameters.get("duration") ),
                containsBackend( allParameters.get("classType") ),
                containsFrontend( allParameters.get("classType") ),
                containsFullstack( allParameters.get("classType") ),
                containsDesigner( allParameters.get("classType") ),
                inStacks( allParameters.get("stack")) )
            .orderBy( quest.modifiedAt.desc() )
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
    private static BooleanExpression containsFrontend(List<String> classType ){
        return classType != null && classType.contains( "frontend" )?
            quest.frontend.goe( 1 ) : null;
    }

    private static BooleanExpression containsBackend(List<String> classType ){
        return classType != null && classType.contains( "backend" )?
            quest.backend.goe( 1 ) : null;
    }

    private static BooleanExpression containsFullstack(List<String> classType ){
        return classType != null && classType.contains( "fullstack" )?
            quest.fullstack.goe( 1 ) : null;
    }

    private static BooleanExpression containsDesigner(List<String> classType ){
        return classType != null && classType.contains( "designer" )?
            quest.designer.goe( 1 ) : null;
    }
//
//    private static BooleanExpression containsClass(List<String> classType ){
//        return classType != null ?
//            containsFrontend( classType ).and( containsBackend(classType) )
//                .and( containsDesigner( classType ) ).and( containsFullstack( classType)) : null;
//    }
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
