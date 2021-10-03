package com.pribas.ws.moment;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pribas.ws.user.User;

@Service(value = "momentSecurity")
public class MomentSecurity {
	
	@Autowired
	MomentRepository momentRepository;
	
	public boolean isAllowedToDelete(long id, User user) {
		Optional<Moment> optionalMoment = momentRepository.findById(id);
		if(!optionalMoment.isPresent() ) {
			return false;
		}
		Moment inDB = optionalMoment.get();
		if(inDB.getTimeline().getUser().getUser_id() != user.getUser_id()) {
			return false;
		}
		return true;
	}
}
