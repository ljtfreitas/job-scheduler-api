package com.github.ljtfreitas.job.scheduler.model;

public class JobCreatedEvent {

	private final Job job;

	public JobCreatedEvent(Job job) {
		this.job = job;
	}

	public Job get() {
		return job;
	}

}
