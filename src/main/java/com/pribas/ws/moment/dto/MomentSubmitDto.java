package com.pribas.ws.moment.dto;

import lombok.Data;

@Data
public class MomentSubmitDto {
	
	private String title;
	
	private String description;
	
	private String moment_date;
	
	private long timeline_id;
	
	private long[] attachments_id;

}
