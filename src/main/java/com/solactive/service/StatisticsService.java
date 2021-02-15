package com.solactive.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.solactive.entity.Statistic;
import com.solactive.entity.Tick;
import com.solactive.exception.InvalidTickException;

@Service
public class StatisticsService {

	@Value("${statisticSeconds}")
	private long statisticSeconds;
	private static final List<Tick> TICKS_LIST = new ArrayList<>();
	private static final ConcurrentHashMap<String,Statistic> STATISTICS = new ConcurrentHashMap<String, Statistic>();	
	private Statistic allStatistics = new Statistic(); 


	public void addTick(Tick tick) {

		if ((System.currentTimeMillis() - tick.getTimestamp()) > statisticSeconds) {
			throw new InvalidTickException();
		}

		synchronized (this) {
			TICKS_LIST.add(tick);
		}
	}

	public Statistic getStatistics() {
		return allStatistics;
	} 


	public Statistic getTicksByInstrument(String instrument) {
		
		Statistic tic = STATISTICS.get(instrument);
		if(tic == null) {
			tic = createInitStatistic();
		}
	    return tic;   	
	}

	@Scheduled(fixedRate = 1000, initialDelay = 1000)
	public void updateStatistics() {

		synchronized (this) {		
			TICKS_LIST.removeIf(
					tick -> (System.currentTimeMillis() - tick.getTimestamp()) > statisticSeconds);
			refreshStatistics();
		}
	}		


	@Async
	public void refreshStatistics() {

		STATISTICS.clear();
		allStatistics = this.createInitStatistic();

		for(Tick tick : TICKS_LIST) {
			STATISTICS.put(tick.getInstrument(), calculateMapStatistics(tick));
			calculateAllStatistics(tick);
		}	 
	}

	
	private Statistic calculateMapStatistics(Tick tick) {

		if(STATISTICS.containsKey(tick.getInstrument())) {

			Statistic mapStatistics = STATISTICS.get(tick.getInstrument());

			if (tick.getPrice() > mapStatistics.getMax()) mapStatistics.setMax(tick.getPrice());
			if (tick.getPrice() < mapStatistics.getMin()) mapStatistics.setMin(tick.getPrice());

			mapStatistics.setSum( mapStatistics.getSum() + tick.getPrice() );
			mapStatistics.setCount( mapStatistics.getCount() + 1);
			mapStatistics.setAvg( mapStatistics.getSum() / mapStatistics.getCount() );

		} else {
			STATISTICS.put(tick.getInstrument(), calculateStatistic(tick));
		}

		return STATISTICS.get(tick.getInstrument());
	}


	public Statistic calculateStatistic(Tick tick) {

		Statistic s = this.createInitStatistic();

		if (tick.getPrice() > s.getMax()) s.setMax(tick.getPrice());
		if (tick.getPrice() < s.getMin()) s.setMin(tick.getPrice());

		s.setSum( s.getSum() + tick.getPrice() );
		s.setCount( s.getCount() + 1);
		s.setAvg( s.getSum() / s.getCount() );

		return s;
	}

	private void calculateAllStatistics(Tick tick) {

		if (tick.getPrice() > allStatistics.getMax()) allStatistics.setMax(tick.getPrice());
		if (tick.getPrice() < allStatistics.getMin()) allStatistics.setMin(tick.getPrice());

		allStatistics.setSum( allStatistics.getSum() + tick.getPrice() );
		allStatistics.setCount( allStatistics.getCount() + 1);
		allStatistics.setAvg( allStatistics.getSum() / allStatistics.getCount() );
	}

	public Statistic createInitStatistic() {
		Statistic statistic = new Statistic();
		statistic.setAvg(0.0);
		statistic.setMax(Double.MIN_VALUE);
		statistic.setMin(Double.MAX_VALUE);
		statistic.setCount(0l);
		statistic.setSum(0.0);
		return statistic;
	}


}
