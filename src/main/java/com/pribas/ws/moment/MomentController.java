package com.pribas.ws.moment;

import java.text.ParseException;

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

import com.pribas.ws.moment.dto.MomentDto;
import com.pribas.ws.moment.dto.MomentSubmitDto;
import com.pribas.ws.shared.GenericResponse;

@RestController
@RequestMapping("/api")
public class MomentController {

	@Autowired
	MomentService momentService;

	@PostMapping("/moments")
	GenericResponse saveMoment(@RequestBody MomentSubmitDto moment) throws ParseException {
		momentService.save(moment);
		return new GenericResponse("Moment is save");
	}
	
	@PutMapping("/users/{username}/moments/{id:[0-9]+}")
	@PreAuthorize("#username == principal.username")
	MomentDto updateMoment(@RequestBody MomentSubmitDto updatedMoment, @PathVariable String username, @PathVariable long id) throws ParseException {
		return momentService.updateMoment(updatedMoment, id);
	}

	@GetMapping("/moments")
	Page<MomentDto> getMoments(Pageable page) {
		return momentService.getMoments(page).map(MomentDto::new);
	}
	
	@GetMapping("/users/{username}/timeline/{id:[0-9]+}/moments")
	Page<MomentDto> getMomentsOfTimelineOfUser(Pageable page, @PathVariable String username,
			@PathVariable long id) {
		return momentService.getMomentsOfTimelineOfUser(page, id).map(MomentDto::new);
	}

	@DeleteMapping("/moments/{id:[0-9]+}")
	@PreAuthorize("@momentSecurity.isAllowedToDelete(#id, principal)")
	GenericResponse deleteMoment(@PathVariable long id) {
		momentService.delete(id);
		return new GenericResponse("Moment removed");
	}
}
