package com.pribas.ws.file.dto;

import com.pribas.ws.file.FileAttachment;

import lombok.Data;

@Data
public class FileAttachmentDto {
	
	private String name;
	
	private String fileType;
	
	public FileAttachmentDto(FileAttachment fileAttachment) {
		this.name = fileAttachment.getName();
		this.fileType = fileAttachment.getFileType();
	}
}
