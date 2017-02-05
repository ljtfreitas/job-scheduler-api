package com.github.ljtfreitas.job.scheduler.model;

import static org.hamcrest.CoreMatchers.containsString;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.rule.OutputCapture;

public class JobTaskTest {

	@Rule
	public OutputCapture outputCapture = new OutputCapture();

	@Test
	public void shouldPrintJobMessage() {
		Job job = new Job("jobName", "I'm a job!", "* * * * * *");

		JobTask jobTask = new JobTask(job);
		jobTask.run();

		outputCapture.expect(containsString(job.getMessage()));
	}

}
