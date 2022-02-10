package ru.itmo.wp.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class Event implements Serializable {
    public enum Type {
        LOGOUT, ENTER
    }
    private long id;
    private long userId;
    private Type type;
    private Date creationTime;
}
