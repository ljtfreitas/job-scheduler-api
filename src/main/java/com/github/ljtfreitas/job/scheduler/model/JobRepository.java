package com.github.ljtfreitas.job.scheduler.model;

import static com.github.ljtfreitas.job.scheduler.util.Preconditions.isFalse;
import static com.github.ljtfreitas.job.scheduler.util.Preconditions.isNotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

@Repository
public class JobRepository {

	private final ApplicationEventPublisher eventPublisher;
	private final Map<String, Job> jobs;

	@Autowired
	public JobRepository(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
		this.jobs = new HashMap<>();
	}

	public JobRepository(ApplicationEventPublisher eventPublisher, Map<String, Job> jobs) {
		this.eventPublisher = eventPublisher;
		this.jobs = new HashMap<>(jobs);
	}

	public Job add(Job job) {
		isFalse(jobs.containsKey(job.getName()), "A job with this name already exists.");
		isNotNull(job.getCronExpression(), "Your job should define a cron expression.");

		jobs.put(job.getName(), job);

		eventPublisher.publishEvent(new JobCreatedEvent(job));

		return job;
	}

	public Collection<Job> all() {
		return jobs.values();
	}

	public void delete(String jobName) {
		Job removed = jobs.remove(jobName);

		if (removed != null) eventPublisher.publishEvent(new JobRemovedEvent(removed));
	}
}
