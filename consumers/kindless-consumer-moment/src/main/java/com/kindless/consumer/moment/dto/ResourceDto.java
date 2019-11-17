package com.kindless.consumer.moment.dto;

import com.kindless.moment.domain.MomentResource;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wuxii
 */
@Getter
@Setter
public class ResourceDto {

    public static final int RESOURCE_OF_PICTURE = 1;
    public static final int RESOURCE_OF_VIDEO = 2;

    public static ResourceDto toResource(MomentResource resource) {
        ResourceDto res = new ResourceDto();
        res.setPath(resource.getPath());
        return res;
    }

    private String path;
    private Integer height;
    private Integer width;
    private Long duration;

}
