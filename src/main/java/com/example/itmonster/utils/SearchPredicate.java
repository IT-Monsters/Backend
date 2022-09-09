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
            .where( containsTitle( allParameters.get("title" ) ),
                containsContent( allParameters.get("content") ),
                loeDuration( allParameters.get("duration") ),
                containsClass( allParameters.get("classType") ),
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

    // 프.백.풀.디 클래스 타입 필터
    private static BooleanExpression containsClass(List<String> classTypes ){
        BooleanExpression expression = null;
        if( classTypes != null ){
            for( String classType : classTypes ){
                expression = expression == null ?
                    chkClassType( classType ) : expression.or( chkClassType( classType) );
            }
        }
        return expression;
    }

    private static BooleanExpression chkClassType( String classTypeName ){
        if( classTypeName.equals("frontend") ) return quest.frontend.goe( 1 );
        else if( classTypeName.equals("backend") ) return quest.backend.goe( 1 );
        else if( classTypeName.equals("fullstack") ) return quest.fullstack.goe( 1 );
        else return quest.designer.goe( 1 );
    }

    // 스택 필터
    private static BooleanExpression inStacks( List<String> stacks ){
        if( stacks == null ) return null;
        return quest.in(
            JPAExpressions.select( stackOfQuest.quest )
                .from( stackOfQuest )
                .where( stackOfQuest.stackName.in( stacks ) )
                .groupBy( stackOfQuest.quest )
                .orderBy( stackOfQuest.count().desc() )
        );
    }

}
