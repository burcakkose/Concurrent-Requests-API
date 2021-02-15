package com.solactive.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Statistic {

	private Double avg;
	private Double max;
	private Double min;
	private Long count;
	private Double sum;

	@JsonIgnore
	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}

	public Statistic() {
		super();
	}

	public Statistic(Double avg, Double max, Double min, Long count, Double sum) {
		super();

		this.sum = sum;
		this.avg = avg;
		this.max = max;
		this.min = min;
		this.count = count;
	}

	public Double getAvg() {
		return avg;
	}

	public void setAvg(Double avg) {
		this.avg = avg;
	}

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}

	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

}
