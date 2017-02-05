package com.github.ljtfreitas.job.scheduler.model;

public class JobTask implements Runnable {

	private final Job job;

	public JobTask(Job job) {
		this.job = job;
	}

	@Override
	public void run() {
		System.out.println(job.getMessage());
	}

}
