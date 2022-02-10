package ru.itmo.wp.model.domain;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class User implements Serializable {
    private long id;
    private String login;
    private String email;
    private Date creationTime;
}
