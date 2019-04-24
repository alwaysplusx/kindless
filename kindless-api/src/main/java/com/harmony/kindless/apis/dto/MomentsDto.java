package com.harmony.kindless.apis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

/**
 * @author wuxii
 */
@Getter
@Setter
@NoArgsConstructor
public class MomentsDto {

    private Long nextCursor;
    private Long previousCursor;
    private List<MomentDto> moments = Collections.emptyList();

    public MomentsDto(List<MomentDto> moments) {
        this.moments = moments;
        if (!moments.isEmpty()) {
            this.nextCursor = moments.get(moments.size() - 1).getCreatedAt().getTime();
            this.previousCursor = moments.get(0).getCreatedAt().getTime();
        }
    }

}
