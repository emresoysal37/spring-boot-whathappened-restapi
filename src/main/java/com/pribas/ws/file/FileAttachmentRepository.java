package com.pribas.ws.file;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pribas.ws.user.User;

public interface FileAttachmentRepository extends JpaRepository<FileAttachment, Long> {

	List<FileAttachment> findByMomentTimelineUser(User user);
}
