package com.kindless.moment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author wuxii
 */
@Getter
@Setter
@NoArgsConstructor
public class MomentDto {

    private String content;
    private Integer type;
    private List<ResourceDto> pictures;
    private List<ResourceDto> videos;
    private String source;
    private Date createdAt;

}
