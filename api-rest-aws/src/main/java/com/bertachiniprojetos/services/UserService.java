package com.bertachiniprojetos.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bertachiniprojetos.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService{
	
	private Logger logger = Logger.getLogger(UserService.class.getName());
	
	@Autowired
	UserRepository repository;
	
	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Finding one user by name " + username + "!");
		var user = repository.findByUsername(username);
		if (user != null) {
			return user;
		} else {
			throw new UsernameNotFoundException("Username " + username + " not found!");
		}
	}

	/*
	public UserVO findById(Long id) throws Exception {
		logger.info("finding user");
		
		var userEntity = userRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("NO FINDINGS FOR THIS ID"));
	    
		UserVO userVO = DozerMapper.parseObject(userEntity, UserVO.class);
		
		userVO.add(linkTo(methodOn(UserController.class).findById(id)).withSelfRel());
		
		return userVO;
	}
	
	public List<UserVO> findAll() {
		logger.info("Listing user");

		var userVO = DozerMapper.parseListObject(userRepository.findAll(), UserVO.class);
		
		userVO.stream().forEach(p -> //
				{
					try {
						p.add(linkTo(methodOn(UserController.class).findById(p.getKey())).withSelfRel());
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				
		return userVO;
	}
	
	public UserVO create(UserVO userVO) throws Exception {
		
		if(Objects.isNull(userVO)) throw new RequiredObjectIsNullException();
		
		logger.info("Creating user");
		
		var entity = userRepository.save(
				DozerMapper.parseObject(userVO, User.class));
		
		userVO = DozerMapper.parseObject(entity, UserVO.class);
		
		userVO.add(linkTo(methodOn(UserController.class).findById(userVO.getKey())).withSelfRel());
		
		return userVO;
	}

	public UserVO update(UserVO userVO, Long id) throws Exception {
		
		if(Objects.isNull(userVO)) throw new RequiredObjectIsNullException();
		
		logger.info("Updating user");
		
		var entity = userRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("No FINDINGS FOR THIS ID"));
		
		entity.setFirstName(userVO.getFirstName());
		entity.setLastName(userVO.getLastName());
		entity.setAddress(userVO.getAddress());
		entity.setGender(userVO.getGender());
		
		userVO = DozerMapper.parseObject(userRepository.save(entity), UserVO.class);
		
		userVO.add(linkTo(methodOn(UserController.class).findById(userVO.getKey())).withSelfRel());

		return userVO;
	}

	public void delete(Long id) {
		
		logger.info("Deleting user");
		
		var entity = userRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("No FINDINGS FOR " + id));
		
		userRepository.delete(entity);
	}
	*/
}
