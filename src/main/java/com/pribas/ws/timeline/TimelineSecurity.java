package com.pribas.ws.timeline;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pribas.ws.user.User;

@Service(value = "timelineSecurity")
public class TimelineSecurity {
	
	@Autowired
	TimelineRepository timelineRepository;
	
	public boolean isAllowedToDelete(long id, User user) {
		Optional<Timeline> optionalTimeline = timelineRepository.findById(id);
		if(!optionalTimeline.isPresent()) {
			return false;
		}
		Timeline inDB = optionalTimeline.get();
		if(inDB.getUser().getUser_id() != user.getUser_id()) {
			return false;
		}
		return true;
	}
}
