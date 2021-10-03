package com.pribas.ws.moment.dto;

import com.pribas.ws.file.FileAttachment;
import com.pribas.ws.file.dto.FileAttachmentDto;
import com.pribas.ws.moment.Moment;

import lombok.Data;

@Data
public class MomentDto {
	
	private long moment_id;
	
	private String title;
	
	private String description;
	
	private String moment_date;
	
	private FileAttachmentDto[] attachments;
	
	private String creation_date;
	
	public MomentDto(Moment moment) {
		this.moment_id = moment.getMoment_id();
		this.title = moment.getTitle();
		this.description = moment.getDescription();
		this.moment_date = moment.getMoment_date().toString();
		this.creation_date = moment.getCreation_date().toString();
		if(moment.getAttachments() != null) {
			FileAttachmentDto[] attchDto = new FileAttachmentDto[moment.getAttachments().size()];
			FileAttachment[] attch = new FileAttachment[moment.getAttachments().size()];
			moment.getAttachments().toArray(attch);
			for(int i=0; i<attch.length; i++) {
				attchDto[i] = new FileAttachmentDto(attch[i]);
			}
			this.attachments = attchDto;	
		}
	}
}
