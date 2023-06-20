package com.example.Train.Controller;

import java.util.Random;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Train.Repository.UserRepo;
import com.example.Train.entites.User;
import com.example.Train.service.EmailService;

@Controller
public class ForgotController {
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;

	Random random = new Random(1000);
	
	//email id from open handler
	@RequestMapping("/forgot")
	public String openEmainForm() {
		
		return "All/forgot_email_form";
	}
	
	@PostMapping("/send-otp")
	public String sendOTP(@RequestParam("email") String email,HttpSession session) {
		
		System.out.println("Email"+ email);
		
		//generating otp of 4 digit
		
		int otp = random.nextInt(9999);
		
		System.out.println("OTP"+otp);
		
		
		// write code for send otp to email....
		String subject="OTP From Seurity";
		String message=" " 
				+"<div style='border:1px solid #e2e2e2; padding:20px'>"
				+"<h1>"
				+"OTP is "
				+"<b>"+otp
				+"</n>"
				+"</h1>"
				+"</div>";
		String to =email;
		
		boolean flag = this.emailService.sendEmail(subject, message, to);
		
		if(flag) {
			
			session.setAttribute("myotp", otp);
			session.setAttribute("email", email);
			return "All/verify_otp";
			
		}else {
			session.setAttribute("message", "check your email id !!");
			return "All/forgot_email_form";
			
		}
	}
	
	//verify otp
	@PostMapping("/verify-otp")
	public String verifyOtp(@RequestParam("otp") int otp, HttpSession session ) {
		
		int myOtp=(int) session.getAttribute("myotp");
		String email =(String) session.getAttribute("email");
		if(myOtp==otp) {
			//passwords change form
			
			User user = this.repo.findByEmail(email);
			
			if(user==null) {
				
				//send error message
				session.setAttribute("message", "User does not exits with email !!");
				return "All/forgot_email_form";
			}else {
				
			}

			return "All/passord_change_form";
		}else {
			session.setAttribute("message", "You have entered wrong otp !!");
			return"All/verify_otp";
		}
	}
	
	@PostMapping("/change-password")
	public String changePassword(@RequestParam("newpassword") String newpassword , HttpSession session) {
		String email =(String) session.getAttribute("email");
		User user = this.repo.findByEmail(email);
		user.setPassword(this.bcrypt.encode(newpassword));
		this.repo.save(user);
		return "redirect:/login?change=password changed Successfully...";
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
