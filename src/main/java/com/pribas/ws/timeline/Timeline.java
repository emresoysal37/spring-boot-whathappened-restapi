package com.pribas.ws.timeline;

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

import com.pribas.ws.moment.Moment;
import com.pribas.ws.user.User;

import lombok.Data;

@Data
@Entity
public class Timeline {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long timeline_id;
	
	private String title;
	
	private String description;
	
	@ManyToOne
	private User user;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date creation_date;
	
	private String tags;
	
	@OneToMany(mappedBy = "timeline", cascade = CascadeType.REMOVE)
	private List<Moment> moments;
}
