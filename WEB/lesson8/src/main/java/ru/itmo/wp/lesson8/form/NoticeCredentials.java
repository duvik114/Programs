package ru.itmo.wp.lesson8.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class NoticeCredentials {
    @NotNull
    @NotEmpty
    @Size(min = 4, max = 4444)
    private String content;
}
