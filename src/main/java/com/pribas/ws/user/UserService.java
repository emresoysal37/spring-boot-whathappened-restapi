package com.pribas.ws.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pribas.ws.error.NotFoundException;
import com.pribas.ws.file.FileService;
import com.pribas.ws.user.dto.UserUpdateDto;

@Service
public class UserService {
	
	UserRepository userRepository;
	
	PasswordEncoder passwordEncoder;
	
	FileService fileService;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			FileService fileService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.fileService = fileService;
	}

	public void save(User user) {
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	public Page<User> getUsers(Pageable page, User user) {
		if(user != null) {
			return userRepository.findByUsernameNot(user.getUsername(), page);
		}
		return userRepository.findAll(page);
	}
	
	public User getByUsername(String username) {
		User inDB = userRepository.findByUsername(username);
		if(inDB == null) {
			throw new NotFoundException();
		}
		return inDB;
	}

	public User updateUser(String username, UserUpdateDto updatedUser) {
		User inDB = getByUsername(username);
		inDB.setEmail(updatedUser.getEmail());
		inDB.setPassword(this.passwordEncoder.encode(updatedUser.getPassword()));
		return userRepository.save(inDB);
	}

	public void deleteUser(String username) {
		User inDB = getByUsername(username);
		fileService.deleteAllStoredFilesForUser(inDB);
		userRepository.delete(inDB);
	}


}
