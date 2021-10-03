 package com.pribas.ws.timeline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pribas.ws.shared.CurrentUser;
import com.pribas.ws.shared.GenericResponse;
import com.pribas.ws.timeline.dto.TimelineDto;
import com.pribas.ws.timeline.dto.TimelineMomentDto;
import com.pribas.ws.timeline.dto.TimelineSubmitDto;
import com.pribas.ws.user.User;

@RestController
@RequestMapping("/api")
public class TimelineController {
	
	@Autowired
	TimelineService timelineService;
	
	@PostMapping("/timeline")
	TimelineDto saveTimeline(@RequestBody TimelineSubmitDto timeline, @CurrentUser User user) {
		return timelineService.save(timeline, user);
	}
	
	@PutMapping("/{username}/timeline/{id:[0-9]+}")
	@PreAuthorize("#username == principal.username")
	TimelineDto updateTimeline(@RequestBody TimelineSubmitDto updatedTimeline, @PathVariable String username, @PathVariable long id) {
		return timelineService.updateTimeline(updatedTimeline, id);
	}
	
	@GetMapping("/timeline")
	Page<TimelineDto> getTimelines(Pageable page) {
		return timelineService.getTimelines(page).map(TimelineDto::new);
	}
	
	@GetMapping("/timeline/{id:[0-9]+}")
	TimelineMomentDto getTimelineMoments(@PathVariable long id) {
		return timelineService.getTimelineMoments(id);
	}
	
	@DeleteMapping("/timeline/{id:[0-9]+}")
	@PreAuthorize("@timelineSecurity.isAllowedToDelete(#id, principal)")
	GenericResponse deleteTimeline(@PathVariable long id) {
		timelineService.delete(id);
		return new GenericResponse("Timeline removed");
	}

}
