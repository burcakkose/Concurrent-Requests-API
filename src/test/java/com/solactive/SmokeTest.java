package com.solactive;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.solactive.controller.StatisticsRestController;

@SpringBootTest
public class SmokeTest {
	@Autowired
	private StatisticsRestController controller;
	
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}
}
