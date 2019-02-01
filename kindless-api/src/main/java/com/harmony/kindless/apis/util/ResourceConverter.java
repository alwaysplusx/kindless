package com.harmony.kindless.apis.util;

import com.harmony.kindless.apis.domain.moment.MomentResource;
import com.harmony.kindless.apis.dto.ResourceDto;

/**
 * @author wuxii
 */
public class ResourceConverter {

	public static MomentResource momentPicture(ResourceDto resource) {
		MomentResource res = new MomentResource();
		res.setType(MomentResource.RESOURCE_OF_PICTURE);
		res.setPath(resource.getPath());
		return res;
	}

	public static MomentResource momentVideo(ResourceDto resource) {
		MomentResource res = new MomentResource();
		res.setType(MomentResource.RESOURCE_OF_VIDEO);
		res.setPath(resource.getPath());
		return res;
	}

}
