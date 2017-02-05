package com.github.ljtfreitas.job.scheduler.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Job {

	@NotNull
	private final String name;

	@JsonProperty("msg")
	private final String message;

	@NotNull
	@JsonProperty("cron")
	private final String cronExpression;

	@JsonCreator
	public Job(@JsonProperty("name") String name, @JsonProperty("msg") String message,
			@JsonProperty("cron") String cronExpression) {
		this.name = name;
		this.message = message;
		this.cronExpression = cronExpression;
	}

	public String getName() {
		return name;
	}

	public String getMessage() {
		return message;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	@Override
	public String toString() {
		return "[Name: " + name + ", Message: " + message + ", Cron: " + cronExpression + "]";
	}
}
