package com.moer.daydayup.mybatisdemo;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.matchers.JUnitMatchers.containsString;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MybatisDemoApplicationTests {

	@ClassRule
	public static OutputCapture out = new OutputCapture();

	@Test
	public void contextLoads() {
		out.expect(containsString("北京"));
	}
}
