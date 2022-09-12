package com.example.itmonster.controller.response;

import com.example.itmonster.domain.Quest;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClassDto implements Serializable {

    private long frontend;
    private long backend;
    private long fullstack;
    private long designer;

    public ClassDto( Quest quest ){
        frontend = quest.getFrontend();
        backend = quest.getBackend();
        fullstack = quest.getFullstack();
        designer = quest.getDesigner();
    }
}
