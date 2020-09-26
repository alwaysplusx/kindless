package com.kindless.domain.todo;

import com.kindless.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_todo")
public class Todo extends BaseEntity {

    private long shortId;
    private String title;
    private String message;
    private Date deadline;
    private boolean done;
    private Date doneAt;

    private Long userId;

}
