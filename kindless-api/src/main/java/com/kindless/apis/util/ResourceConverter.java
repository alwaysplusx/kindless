package com.kindless.apis.util;

import com.kindless.moment.domain.MomentResource;
import com.kindless.apis.dto.ResourceDto;

/**
 * @author wuxii
 */
public class ResourceConverter {

	public static MomentResource momentPicture(ResourceDto resource) {
		MomentResource res = new MomentResource();
		res.setType(ResourceDto.RESOURCE_OF_PICTURE);
		res.setPath(resource.getPath());
		return res;
	}

	public static MomentResource momentVideo(ResourceDto resource) {
		MomentResource res = new MomentResource();
		res.setType(ResourceDto.RESOURCE_OF_VIDEO);
		res.setPath(resource.getPath());
		return res;
	}

}
