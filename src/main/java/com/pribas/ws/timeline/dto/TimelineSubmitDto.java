package com.pribas.ws.timeline.dto;

import lombok.Data;

@Data
public class TimelineSubmitDto {
	
	private String title;
	
	private String description;
	
	private String[] tags;
}
