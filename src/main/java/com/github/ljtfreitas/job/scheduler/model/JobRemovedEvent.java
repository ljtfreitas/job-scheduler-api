package com.github.ljtfreitas.job.scheduler.model;

public class JobRemovedEvent {

	private Job job;

	public JobRemovedEvent(Job job) {
		this.job = job;
	}

	public Job get() {
		return job;
	}

}
