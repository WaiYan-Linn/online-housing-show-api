package com.dinger.spring.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dinger.spring.model.dto.LoginDto;
import com.dinger.spring.model.dto.LoginResultDto;
import com.dinger.spring.model.dto.SignUpDto;
import com.dinger.spring.model.service.AccountService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class OwnerApi {

	@Autowired
	private AccountService service;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("login")
	public LoginResultDto login(@RequestBody LoginDto dto) {
		return internalSignIn(dto);
	}
	
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestParam(required = true) String userName,
                                    @Valid @RequestParam(required = true) String password,
                                    @Valid @RequestParam(required = true) String email,
                                    @Valid @RequestParam(required = true) String name
                                    ) {
    	
		 try {
	            SignUpDto dto = new SignUpDto(userName, password, email, name);
	            service.signUp(dto);
	            LoginResultDto loginResultDto = internalSignIn(dto.signIn());
	          
	            return ResponseEntity.ok(loginResultDto);
	            
		 }catch (DuplicateKeyException ex) {
	           
	            return ResponseEntity.badRequest().body("An error occurred while signing up. Please choose a different userName or Email I suggest.");
	        } catch (EntityNotFoundException ex) {
	        	
	            return ResponseEntity.badRequest().body("Error: Login user not found.");
	        }
		
	}

	private LoginResultDto internalSignIn(LoginDto loginDto) {
		var authentication = authenticationManager.authenticate(loginDto.authentication());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return service.findLoginUser(loginDto.getUserName()).orElseThrow( () -> new EntityNotFoundException());

	}
	
}
