package com.solactive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.solactive.entity.Statistic;
import com.solactive.entity.Tick;
import com.solactive.exception.InvalidTickException;
import com.solactive.service.StatisticsService;

@SpringBootTest
public class StatisticsServiceTest {	
	@Autowired
	private StatisticsService statisticService;

	
	@Test
	public void shouldThrowInvalidTickExceptionForPast() {
		
		Tick tick = new Tick();
		tick.setPrice(5.0);
		tick.setTimestamp(System.currentTimeMillis()-62000); // before 60 sec.
		
		assertThrows(InvalidTickException.class, () -> this.statisticService.addTick(tick));
	}
	
	@Test
	public void shouldCalculateStatistic() {
		
		Tick t1 = new Tick();
		t1.setInstrument("AAA");
		t1.setPrice(20.0);
		t1.setTimestamp(System.currentTimeMillis());
		this.statisticService.addTick(t1);
	
		Statistic statistic = this.statisticService.calculateStatistic(t1);
		
		assertEquals(Long.valueOf(1l), statistic.getCount());
		assertEquals(Double.valueOf(20.0), statistic.getMin());
		assertEquals(Double.valueOf(20.0), statistic.getMax());
		assertEquals(Double.valueOf(20.0), statistic.getAvg());
	}
	
	@Test
	public void shouldGetTicksByInstrument() throws Exception {
		this.statisticService.addTick(createTestTick("BBB", 5.0));
		this.statisticService.addTick(createTestTick("BBB", 10.0));
		this.statisticService.addTick(createTestTick("DDD", 20.0));
		
		this.statisticService.updateStatistics();

		Statistic statistic1 = this.statisticService.getTicksByInstrument("BBB");
		
		assertEquals(Long.valueOf(2l), statistic1.getCount());
		assertEquals(Double.valueOf(5.0), statistic1.getMin());
		assertEquals(Double.valueOf(10.0), statistic1.getMax());
		assertEquals(Double.valueOf(7.5), statistic1.getAvg());
	}
	
	@Test
	public void shouldReturnEmptyByGetTicksByInstrument() {
		this.statisticService.addTick(createTestTick("BBB", 25.0));
		
		this.statisticService.updateStatistics();

		Statistic statistic1 = this.statisticService.getTicksByInstrument("ZZZ");
		
		assertEquals(Long.valueOf(0), statistic1.getCount());
		assertEquals(Double.valueOf(Double.MAX_VALUE), statistic1.getMin());
		assertEquals(Double.valueOf(Double.MIN_VALUE), statistic1.getMax());
		assertEquals(Double.valueOf(0.0), statistic1.getAvg());
	}
	
	
	private Tick createTestTick(String instrumentName, Double price) {
		return new Tick(instrumentName, price, System.currentTimeMillis());
	}
}