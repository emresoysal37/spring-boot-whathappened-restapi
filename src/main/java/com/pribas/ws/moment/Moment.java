package com.pribas.ws.moment;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.pribas.ws.file.FileAttachment;
import com.pribas.ws.timeline.Timeline;

import lombok.Data;

@Data
@Entity
public class Moment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long moment_id;
	
	private String title;
	
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date moment_date;
	
	@OneToMany(mappedBy = "moment", cascade = CascadeType.REMOVE)
	private List<FileAttachment> attachments;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date creation_date;
	
	@ManyToOne
	private Timeline timeline;
}
