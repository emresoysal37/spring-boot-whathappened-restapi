package com.pribas.ws.moment;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pribas.ws.timeline.Timeline;

public interface MomentRepository extends JpaRepository<Moment, Long> {
	
	Page<Moment> findByTimeline(Timeline timeline, Pageable page);
	
	List<Moment> findByTimeline(Timeline timeline);
}
