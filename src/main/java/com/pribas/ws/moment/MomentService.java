package com.pribas.ws.moment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pribas.ws.file.FileAttachment;
import com.pribas.ws.file.FileAttachmentRepository;
import com.pribas.ws.file.FileService;
import com.pribas.ws.moment.dto.MomentDto;
import com.pribas.ws.moment.dto.MomentSubmitDto;
import com.pribas.ws.timeline.Timeline;
import com.pribas.ws.timeline.TimelineRepository;

@Service
public class MomentService {

	MomentRepository momentRepository;

	TimelineRepository timelineRepository;

	FileAttachmentRepository fileAttachmentRepository;

	FileService fileService;

	public MomentService(MomentRepository momentRepository, TimelineRepository timelineRepository,
			FileAttachmentRepository fileAttachmentRepository, FileService fileService) {
		this.momentRepository = momentRepository;
		this.timelineRepository = timelineRepository;
		this.fileAttachmentRepository = fileAttachmentRepository;
		this.fileService = fileService;
	}

	public void save(MomentSubmitDto momentSubmitDto) throws ParseException {
		Moment moment = new Moment();
		moment.setTitle(momentSubmitDto.getTitle());
		moment.setDescription(momentSubmitDto.getDescription());
		moment.setCreation_date(new Date());
		Date date = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(momentSubmitDto.getMoment_date().substring(0, 16));
		moment.setMoment_date(date);
		moment.setTimeline(timelineRepository.getById(momentSubmitDto.getTimeline_id()));
		if (momentSubmitDto.getAttachments_id() != null) {
			for (long attachment_id : momentSubmitDto.getAttachments_id()) {
				Optional<FileAttachment> optionalFileAttachment = fileAttachmentRepository.findById(attachment_id);
				if (optionalFileAttachment.isPresent()) {
					FileAttachment fileAttachment = optionalFileAttachment.get();
					fileAttachment.setMoment(moment);
					fileAttachmentRepository.save(fileAttachment);
				}
			}
		}
		momentRepository.save(moment);
	}

	public MomentDto updateMoment(MomentSubmitDto updatedMoment, long id) throws ParseException {
		Moment inDB = momentRepository.getById(id);
		inDB.setTitle(updatedMoment.getTitle());
		inDB.setDescription(updatedMoment.getDescription());
		inDB.setCreation_date(new Date());
		Date date = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(updatedMoment.getMoment_date().substring(0, 16));
		inDB.setMoment_date(date);
		if (updatedMoment.getAttachments_id() != null) {
			for (long attachment_id : updatedMoment.getAttachments_id()) {
				Optional<FileAttachment> optionalFileAttachment = fileAttachmentRepository.findById(attachment_id);
				if (optionalFileAttachment.isPresent()) {
					FileAttachment fileAttachment = optionalFileAttachment.get();
					fileAttachment.setMoment(inDB);
					fileAttachmentRepository.save(fileAttachment);
				}
			}
		}
		momentRepository.save(inDB);
		return new MomentDto(inDB);
	}

	public Page<Moment> getMoments(Pageable page) {
		return momentRepository.findAll(page);
	}

	public Page<Moment> getMomentsOfTimelineOfUser(Pageable page, long timeline_id) {
		Timeline timeline = timelineRepository.getById(timeline_id);
		return momentRepository.findByTimeline(timeline, page);
	}
	
	public List<MomentDto> getMomentsOfTimeline(long timeline_id) {
		Timeline inDB = timelineRepository.getById(timeline_id);
		List<Moment> moments = momentRepository.findByTimeline(inDB);
		List<MomentDto> momentDto = new ArrayList<>();
		for(int i=0; i<moments.size(); i++) {
			momentDto.add(new MomentDto(moments.get(i)));
		}
		return momentDto;
	}
	
	public void delete(long id) {
		Moment inDB = momentRepository.getById(id);
		if (inDB.getAttachments() != null) {
			for (int i = 0; i < inDB.getAttachments().size(); i++) {
				String fileName = inDB.getAttachments().get(i).getName();
				fileService.deleteAttachmentFile(fileName);
			}
			momentRepository.deleteById(id);
		}
	}

}
