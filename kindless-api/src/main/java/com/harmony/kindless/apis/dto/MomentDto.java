package com.harmony.kindless.apis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author wuxii
 */
@Getter
@Setter
@NoArgsConstructor
public class MomentDto {

    private String content;
    private List<ResourceDto> pictures;
    private ResourceDto video;
    private String source;

    public int getType() {
        // TODO: 2019/1/26 根据值获取moment的类型
        if (pictures.isEmpty() && video == null) {
            return 0;
        }
        return 1;
    }

}
