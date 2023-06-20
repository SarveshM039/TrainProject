package com.example.Train.Controller;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.Train.Repository.UserRepo;
import com.example.Train.entites.User;


@Controller
@SpringBootApplication
public class AppController {
	
	@Autowired
	private UserRepo repo;
	
	
	

	@GetMapping(" ")
	public String index() {
		return "All/coffee";
		
	}
	
	@GetMapping("/about1")
	public String aboutus1() {
		return "All/about1";
	}
	
	@GetMapping("/home")
	public String homepage() {
		return "All/home";
		
	}
	
	@GetMapping("/about")
	public String aboutus() {
		return "All/about";
	}
	
	@GetMapping("/contact")
	public String contactus() {
		return "All/contact";
	}
	
	@GetMapping("/contact1")
	public String contactus1() {
		return "All/contact1";
	}
	
	@GetMapping("/login")
	public String login() {
		return "All/userlogin";
	}
	

	
	@GetMapping("/register")
	public String showSignupForm(Model model) {
	model.addAttribute("user", new User());	
	return"User/register";	
	}
	
	
	@PostMapping("/process_register")
	public String processRegistration(User user, HttpSession session) {
		BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
        String encodedPassword= encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole("ROLE_USER");
        session.setAttribute("message", "You have Succesfully Registered");
			repo.save(user);
		return "User/register";
	}
	
//	@GetMapping("/user/list_users")
//	public String viewUsersList(Model model) {
//		List<User> listUsers= repo.findAll();
//		model.addAttribute("listUsers", listUsers);
//		return "User/users";
//	}
//	
	
	
 
    
 
         
    
	

	
}
