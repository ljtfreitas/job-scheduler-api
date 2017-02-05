package com.github.ljtfreitas.job.scheduler.api;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.ljtfreitas.job.scheduler.model.Job;
import com.github.ljtfreitas.job.scheduler.model.JobRepository;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

	private final JobRepository jobRepository;

	@Autowired
	public JobController(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Job create(@Valid @RequestBody Job job) {
		return jobRepository.add(job);
	}

	@GetMapping
	public Collection<Job> list() {
		return jobRepository.all();
	}

	@DeleteMapping("/{jobName}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable("jobName") String jobName) {
		jobRepository.delete(jobName);
	}
}
