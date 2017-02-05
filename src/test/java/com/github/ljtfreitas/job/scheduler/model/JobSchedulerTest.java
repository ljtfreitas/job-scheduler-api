package com.github.ljtfreitas.job.scheduler.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

@RunWith(MockitoJUnitRunner.class)
public class JobSchedulerTest {

	@Mock
	private TaskScheduler taskScheduler;

	@Mock
	private ScheduledFuture<?> scheduledTask;

	private JobScheduler jobScheduler;

	@Captor
	private ArgumentCaptor<CronTrigger> cronTriggerCaptor;

	private Job job;

	@Before
	public void setup() {
		job = new Job("jobName", "I'm a job!", "* * * * * *");

		jobScheduler = new JobScheduler(taskScheduler);
	}

	@Test
	public void shouldScheduleNewJobWhenJobCreatedEventOcurred() {
		JobCreatedEvent jobCreatedEvent = new JobCreatedEvent(job);

		jobScheduler.onJobCreated(jobCreatedEvent);

		verify(taskScheduler).schedule(notNull(JobTask.class), cronTriggerCaptor.capture());

		CronTrigger cronTrigger = cronTriggerCaptor.getValue();

		assertThat(cronTrigger.getExpression(), equalTo(job.getCronExpression()));
	}

	@Test
	public void shouldCancelScheduledTaskWhenJobRemovedEventOcurred() {
		Map<String, ScheduledFuture<?>> tasks = new HashMap<>();
		tasks.put("jobName", scheduledTask);

		jobScheduler = new JobScheduler(taskScheduler, tasks);

		JobRemovedEvent jobRemovedEvent = new JobRemovedEvent(job);
		jobScheduler.onJobRemoved(jobRemovedEvent);

		verify(scheduledTask).cancel(false);
	}
}
