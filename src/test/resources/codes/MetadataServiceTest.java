package com.harmony.kindless.core.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.harmony.umbrella.context.ApplicationConfigurationBuilder;
import com.harmony.umbrella.context.ApplicationContext;

/**
 * @author wuxii@foxmail.com
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MetadataServiceTest {

	@Autowired
	private MetadataService metadataService;

	@Before
	public void before() {
		ApplicationContext.start(ApplicationConfigurationBuilder.create().addScanPackage("com.harmony").build());
	}

	@Test
	public void test() {
		metadataService.initMetadata();
	}

}
