package com.pribas.ws.timeline.dto;

import com.pribas.ws.moment.dto.MomentDto;
import com.pribas.ws.timeline.Timeline;

import lombok.Data;

@Data
public class TimelineMomentDto {

	private String title;

	private String description;

	private long user_id;

	private String creation_date;

	private String[] tags;

	private MomentDto[] moments;

	public TimelineMomentDto(Timeline timeline) {
		this.title = timeline.getTitle();
		this.description = timeline.getDescription();
		this.user_id = timeline.getUser().getUser_id();
		this.creation_date = timeline.getCreation_date().toString();
		if (timeline.getTags().length() > 0) {
			this.tags = timeline.getTags().split(",");
		}
	}
}
