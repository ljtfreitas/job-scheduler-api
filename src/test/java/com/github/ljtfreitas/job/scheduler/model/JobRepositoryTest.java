package com.github.ljtfreitas.job.scheduler.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

@RunWith(MockitoJUnitRunner.class)
public class JobRepositoryTest {

	@Mock
	private ApplicationEventPublisher eventPublisher;

	private JobRepository jobRepository;

	@Captor
	private ArgumentCaptor<JobCreatedEvent> jobCreatedEventCaptor;

	@Captor
	private ArgumentCaptor<JobRemovedEvent> jobRemovedEventCaptor;

	private Job job;

	@Before
	public void setup() {
		job = new Job("jobName", "I'm a job!", "* * * * * *");

		jobRepository = new JobRepository(eventPublisher);
	}

	@Test
	public void shouldAddNewJob() {
		jobRepository.add(job);

		assertThat(jobRepository.all(), hasSize(1));
		assertThat(jobRepository.all().iterator().next(), sameInstance(job));

		verify(eventPublisher).publishEvent(jobCreatedEventCaptor.capture());

		JobCreatedEvent jobCreatedEvent = jobCreatedEventCaptor.getValue();
		assertThat(jobCreatedEvent.get(), sameInstance(job));
	}

	@Test
	public void shouldRemoveJob() {
		Map<String, Job> jobs = new HashMap<>();
		jobs.put("jobName", job);

		jobRepository = new JobRepository(eventPublisher, jobs);

		assertThat(jobRepository.all(), hasSize(1));

		jobRepository.delete("jobName");

		assertThat(jobRepository.all(), is(empty()));

		verify(eventPublisher).publishEvent(jobRemovedEventCaptor.capture());

		JobRemovedEvent jobRemovedEvent = jobRemovedEventCaptor.getValue();
		assertThat(jobRemovedEvent.get(), sameInstance(job));
	}

	@Test
	public void shouldNotPublishJobRemovedEventWhenJobNameNotFoundOnDelete() {
		jobRepository.delete("jobName");

		verify(eventPublisher, never()).publishEvent(any());
	}
}
