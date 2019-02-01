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

	private List<MomentDto> moments = Collections.emptyList();

}
