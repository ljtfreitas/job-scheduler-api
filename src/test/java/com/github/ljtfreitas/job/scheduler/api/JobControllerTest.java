package com.github.ljtfreitas.job.scheduler.api;

import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.github.ljtfreitas.job.scheduler.model.Job;
import com.github.ljtfreitas.job.scheduler.model.JobRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(JobController.class)
public class JobControllerTest {

	@MockBean
	private JobRepository jobRepository;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldCreateNewJobOnPost() throws Exception {
		String requestBody = "{\"name\": \"job-name\", \"msg\": \"Hello World\", \"cron\": \"* * * * *\"}";

		mockMvc.perform(post("/api/jobs")
				.content(requestBody)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());

		verify(jobRepository).add(notNull(Job.class));
	}

	@Test
	public void shouldListAllJobsOnGet() throws Exception {
		String expectedResponseBody = "[{\"name\": \"job-name\", \"msg\": \"Hello World\", \"cron\": \"* * * * *\"}]";

		when(jobRepository.all())
			.thenReturn(Arrays.asList(new Job("job-name", "Hello World", "* * * * *")));

		mockMvc.perform(get("/api/jobs"))
			.andExpect(status().isOk())
			.andExpect(content().json(expectedResponseBody));

		verify(jobRepository).all();
	}

	@Test
	public void shouldRemoveJobOnDelete() throws Exception {
		mockMvc.perform(delete("/api/jobs/{jobName}", "job-name"))
			.andExpect(status().isOk());

		verify(jobRepository).delete("job-name");
	}
}
