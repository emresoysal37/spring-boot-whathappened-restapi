package com.pribas.ws.timeline.dto;

import com.pribas.ws.timeline.Timeline;

import lombok.Data;

@Data
public class TimelineDto {
	
	private long timeline_id;
	
	private String title;
	
	private String description;
	
	private long user_id;
	
	private String creation_date;
	
	private String[] tags;
	
	public TimelineDto(Timeline timeline) {
		this.timeline_id = timeline.getTimeline_id();
		this.title = timeline.getTitle();
		this.description = timeline.getDescription();
		this.user_id = timeline.getUser().getUser_id();
		this.creation_date = timeline.getCreation_date().toString();
		if (timeline.getTags().length() > 0) {
			this.tags = timeline.getTags().split(",");
		}
	}
	
}
