package com.pribas.ws.timeline;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pribas.ws.moment.MomentService;
import com.pribas.ws.moment.dto.MomentDto;
import com.pribas.ws.timeline.dto.TimelineDto;
import com.pribas.ws.timeline.dto.TimelineMomentDto;
import com.pribas.ws.timeline.dto.TimelineSubmitDto;
import com.pribas.ws.user.User;

@Service
public class TimelineService {
	
	TimelineRepository timelineRepository;
	
	MomentService momentService;
	
	public TimelineService(TimelineRepository timelineRepository, MomentService momentService) {
		super();
		this.timelineRepository = timelineRepository;
		this.momentService = momentService;
	}

	public TimelineDto save(TimelineSubmitDto timelineSubmitDto, User user) {
		Timeline timeline = new Timeline();
		timeline.setTitle(timelineSubmitDto.getTitle());
		timeline.setDescription(timelineSubmitDto.getDescription());
		String tags = "";
		for(String tag : timelineSubmitDto.getTags()) {
			tags += tag+",";
		}
		timeline.setTags(tags);
		timeline.setUser(user);
		timeline.setCreation_date(new Date());
		timelineRepository.save(timeline);
		return new TimelineDto(timeline);
	}

	public TimelineDto updateTimeline(TimelineSubmitDto updatedTimeline, long id) {
		Timeline inDB = timelineRepository.getById(id);
		inDB.setTitle(updatedTimeline.getTitle());
		inDB.setDescription(updatedTimeline.getDescription());
		String tags = "";
		for(String tag : updatedTimeline.getTags()) {
			tags += tag+",";
		}
		inDB.setTags(tags);
		inDB.setCreation_date(new Date());
		timelineRepository.save(inDB);
		return new TimelineDto(inDB);
	}

	public Page<Timeline> getTimelines(Pageable page) {
		return timelineRepository.findAll(page);
	}
	
	public TimelineMomentDto getTimelineMoments(long id) {
		TimelineMomentDto timelineMomentDto = new TimelineMomentDto(timelineRepository.getById(id));
		List<MomentDto> momentDtoList = momentService.getMomentsOfTimeline(id);
		MomentDto[] momentDto = new MomentDto[momentDtoList.size()];
		momentDtoList.toArray(momentDto);
		timelineMomentDto.setMoments(momentDto);
		return timelineMomentDto;
	}
	
	public void delete(long id) {
		timelineRepository.deleteById(id);		
	}


}
