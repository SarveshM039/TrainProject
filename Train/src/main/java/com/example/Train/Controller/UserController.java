  package com.example.Train.Controller;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.Train.Repository.AddTrainRepo;
import com.example.Train.Repository.BookTicketRepo;
import com.example.Train.Repository.CancelRepo;
import com.example.Train.Repository.PaymentBookRepo;
import com.example.Train.Repository.UserRepo;
import com.example.Train.entites.AddTrain;
import com.example.Train.entites.BookTicket;
import com.example.Train.entites.Cancel;
import com.example.Train.entites.PaymentBook;
import com.example.Train.entites.User;
import com.example.Train.entites.UserBookingDTO;
import com.example.Train.service.JReportService;
import com.razorpay.*;

import net.sf.jasperreports.engine.JRException;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
    private JReportService service;
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private AddTrainRepo tRepo;
	
	@Autowired
	private BookTicketRepo bookRepo;
	
	@Autowired
	private PaymentBookRepo payRepo;
	 
	@Autowired
	private CancelRepo cRepo;
	
	
	@GetMapping("/searchtrain")
    public String searchtrainn() {
		      
   	 return "User/searchtrain2";
    }

	@RequestMapping("/searchtrains")
	public String searchtrain(@RequestParam("trainFrom") String trainFrom , @RequestParam("trainTo") String trainTo, ModelMap model) {
		 List<AddTrain> findtrains = tRepo.findtrains(trainFrom, trainTo);
		 model.addAttribute("findtrains", findtrains);
	return "User/display_trains";	
	}
	
	
	@RequestMapping("/booking/{trainId}")
	public String book(@PathVariable("trainId") Long trainId , Model model) {
		   Optional<AddTrain> bookid = tRepo.findById(trainId);
		        AddTrain addTrain = bookid.get();
		        model.addAttribute("addTrain", addTrain);
		return "User/index1";
	}
	
	@PostMapping("/booksave")
	public String booksave(@ModelAttribute("book") BookTicket book,@RequestParam("trainId") Long trainId, Model model, Principal principal) {
		String name = principal.getName();
		User user = this.repo.findByEmail(name);
		
		book.setUser(user);
		user.getBookTickets().add(book);
		
		this.repo.save(user);
		
	  model.addAttribute("book", book);
		return "User/paycheck";
		
	}
	

	
	@GetMapping("/userbook")
	public String userbook(Model model, Principal principal) {
	    String userName = principal.getName();
	    User user = this.repo.findByEmail(userName);

	    List<PaymentBook> paymentBookList = this.payRepo.findPaymentByUser(user.getId());
	    List<BookTicket> bookTicketList = this.bookRepo.findBooktTicketByUser(user.getId());

	    List<UserBookingDTO> userBookingList = new ArrayList<>();

	    // Combine the data from PaymentBook and BookTicket entities into UserBookingDTO objects
	    for (int i = 0; i < paymentBookList.size(); i++) {
	        UserBookingDTO userBookingDTO = new UserBookingDTO();
	        userBookingDTO.setPaymentBook(paymentBookList.get(i));
	        userBookingDTO.setBookTicket(bookTicketList.get(i));

	        userBookingList.add(userBookingDTO);
	    }

	    model.addAttribute("userBookingList", userBookingList);
	    return "User/booking";
	}
	
	@GetMapping("/cancelticket") 
	public String cancelticket(Model model, Principal principal) {
		String userName = principal.getName();
	    User user = this.repo.findByEmail(userName);
		 List<Cancel> cancel = cRepo.findCancelByUser(user.getId());
		model.addAttribute("cancel", cancel);
		return "User/cancel"; 
	}
	
	@PostMapping("/delete")
	public String deleteUserBooking(@RequestParam("payBookId") Long payBookId, @RequestParam("trainId") Long trainId) {
	BookTicket train = this.bookRepo.findById(trainId).get();      
		
		Cancel cancel= new Cancel();
	     cancel.setTrainId(train.getTrainId());
	     cancel.setTrainName(train.getTrainName());
	     cancel.setTrainFrom(train.getTrainFrom());
	     cancel.setTrainTo(train.getTrainTo());
	     cancel.setPrice(train.getPrice());
	     cancel.setFirstName(train.getFirstName());
	     cancel.setEmail(train.getEmail());
	     cancel.setPhone(train.getPhone());
	     cancel.setUser(train.getUser());
	     this.cRepo.save(cancel);
	    
		payRepo.deleteById(payBookId);
		bookRepo.deleteById(trainId);
	    // Redirect back to the booking list page
	    return "redirect:/user/userbook";
	}
	
	
	@GetMapping("/showTrain")
	public String showTrain() {
		return "Admin/showTrain";
	}

	
	@GetMapping("/change")
	public String change() {
		
		return "User/change";
	}
	
	@PostMapping("/change-password")
	public String changePassword(@RequestParam("oldPassword") String oldPassword , @RequestParam("newPassword") String newPassword, Principal principal , HttpSession session) {
	 
	System.out.println("OLD PASSWORD: "+oldPassword);	
	System.out.println("NEW PASSWORD: "+newPassword);	
	
	String email = principal.getName();
     User currentUser = this.repo.findByEmail(email);
	 System.out.println(currentUser.getPassword());	
	 
	 if(this.bCryptPasswordEncoder.matches(oldPassword, currentUser.getPassword())) {
		 
		 currentUser.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
		 this.repo.save(currentUser);
		 session.setAttribute("message", "Your password is successfully changed...!!!");
	 }else {
		 
		 session.setAttribute("message", "Please Enter correct old password !!!");
		 return "redirect:/user/change";
	 }
	 
	
	return "User/searchtrain2";	
	}
	
	@GetMapping("/profile")
	public String yourprofile( Model model, Principal principal) {
		
		String username = principal.getName();
		User user = repo.findByEmail(username);
		model.addAttribute("user", user);   
		return"User/profile";
	}
	
	
	
//	@GetMapping("/list_users")
//	public String viewUsersList(Model model) {
//		List<User> listUsers= repo.findAll();
//		model.addAttribute("listUsers", listUsers);
//		return "User/users";
//	}
//	
	
	//creating order for payment
	
		@PostMapping("/create_order")
		@ResponseBody
		public String createOrder(@RequestBody Map<String, Object> data, Principal principal, Long trainId ) throws Exception  {
		
			System.out.println(data);
			
			int amt= Integer.parseInt(data.get("amount").toString());
			
	    RazorpayClient client = new RazorpayClient("rzp_test_y5N0aQVLs4HQFj", "A66DO0AV7Ny6xDHA6uU3QQZt");
		         	
	      JSONObject ob= new JSONObject();
		  ob.put("amount", amt*100);
		  ob.put("currency", "INR");
		  ob.put("receipt", "txn_235425");
		  
		  Order order = client.orders.create(ob);
		  System.out.println(order);   
		  
		  
		  //save the order in database
	    
		  PaymentBook paymentBooking = new PaymentBook();
		  
			 paymentBooking.setAmount(order.get("amount")+"");
			 paymentBooking.setOrderId(order.get("id"));
			 paymentBooking.setPaymentId(null);
			 paymentBooking.setStatus("created");
			 paymentBooking.setUser(this.repo.findByEmail(principal.getName()));
			 paymentBooking.setReceipt(order.get("receipt"));
			 
			 this.payRepo.save(paymentBooking);
			  
			return order.toString();
			

		  
}
		@PostMapping("/update_payment")
		public ResponseEntity<?> update_payment(@RequestBody Map<String , Object> data){
			
			PaymentBook paymentBook = this.payRepo.findByOrderId(data.get("order_id").toString());
			
			paymentBook.setPaymentId(data.get("payment_id").toString());
			paymentBook.setStatus(data.get("status").toString());
			
			this.payRepo.save(paymentBook);
			System.out.println(data);
			Map<String, String> response = Collections.singletonMap("msg", "updated");
			return ResponseEntity.ok(response);
		}
		
//		 @GetMapping("/getAddress")
//		    public List<BookTicket> getAddress() {
//		        List<BookTicket> address = (List<BookTicket>) bookRepo.findAll();
//		        return address;
//		    }
		 
		         
//		    @GetMapping("/jasperpdf/export/{trainId}/{payBookId}")
//		    public void createPDF(HttpServletResponse response, Principal principal, 
//		    		@PathVariable("trainId") Long trainId,
//		    		@PathVariable("payBookId") Long payBookId) throws IOException, JRException {
//		        response.setContentType("application/pdf");
//		        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
//		        String currentDateTime = dateFormatter.format(new Date());
//		  
//		        String headerKey = "Content-Disposition";
//		        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
//		        response.setHeader(headerKey, headerValue);
//		  
//		        service.exportJasperReport(response, principal.getName(), trainId, payBookId);
//		    }
	 
		@GetMapping("/jasperpdf/export/{trainId}")
	    public void createPDF(HttpServletResponse response, Principal principal,@PathVariable("trainId") Long trainId) throws IOException, JRException {
	        response.setContentType("application/pdf");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	  
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=TrainTicket_" + currentDateTime + ".pdf";
	        response.setHeader(headerKey, headerValue);
	  
	        service.exportJasperReport(response, principal.getName(), trainId);
	    }

	 
		
}
