package com.github.ljtfreitas.job.scheduler.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

@Service
public class JobScheduler {

	private final TaskScheduler taskScheduler;
	private final Map<String, ScheduledFuture<?>> tasks;

	@Autowired
	public JobScheduler(TaskScheduler taskScheduler) {
		this.taskScheduler = taskScheduler;
		this.tasks = new HashMap<>();
	}

	public JobScheduler(TaskScheduler taskScheduler, Map<String, ScheduledFuture<?>> tasks) {
		this.taskScheduler = taskScheduler;
		this.tasks = new HashMap<>(tasks);
	}

	@EventListener
	public void onJobCreated(JobCreatedEvent jobCreatedEvent) {
		Job job = jobCreatedEvent.get();

		ScheduledFuture<?> scheduledTask = schedule(job);

		tasks.put(job.getName(), scheduledTask);
	}

	private ScheduledFuture<?> schedule(Job job) {
		return taskScheduler.schedule(new JobTask(job), new CronTrigger(job.getCronExpression()));
	}

	@EventListener
	public void onJobRemoved(JobRemovedEvent jobRemovedEvent) {
		Job job = jobRemovedEvent.get();

		Optional.ofNullable(tasks.get(job.getName()))
			.ifPresent(task -> task.cancel(false));

		tasks.remove(job.getName());
	}
}
