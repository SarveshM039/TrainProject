package com.example.Train.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Train.Repository.AddTrainRepo;
import com.example.Train.Repository.BookTicketRepo;
import com.example.Train.Repository.MasterRepo;
import com.example.Train.Repository.NoOfStopRepo;
import com.example.Train.entites.AddTrain;
import com.example.Train.entites.BookTicket;
import com.example.Train.entites.Master;
import com.example.Train.entites.No_of_stops;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AddTrainRepo tRepo;

	@Autowired
	private MasterRepo masterRepo; 
	

	@Autowired
	private NoOfStopRepo stopRepo;
	
	@Autowired
	private BookTicketRepo bookRepo;
	
	@GetMapping("/adminlogin/{page}")
	public String  aadminlogin(@PathVariable("page") Integer page,Model model) {
		Pageable  pageable = PageRequest.of(page, 5);
	    Page<AddTrain> findAllTrains = tRepo.findAll(pageable);
		model.addAttribute("listtrains", findAllTrains);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", findAllTrains.getTotalPages());
		return"Admin/admin";
	}
	
	@GetMapping("/adminhome/{page}")
	public String adminHome(@PathVariable("page") Integer page,Model model) {
		Pageable  pageable = PageRequest.of(page, 5);
	    Page<AddTrain> findAllTrains = tRepo.findAll(pageable);
		model.addAttribute("listtrains", findAllTrains);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", findAllTrains.getTotalPages());
		return "Admin/admin";
	}

	
	@GetMapping("/addTrain")
	public String addTrain( Model model) {
		AddTrain addTrain= new AddTrain();
		model.addAttribute("addTrain", addTrain);
		model.addAttribute("listofstop", stopRepo.findAll());
		return "Admin/add";
	}
	
	@PostMapping("/savetrains")
	public String savetrain(AddTrain addTrain) {
		tRepo.save(addTrain);
		return "Admin/add";
	}
	
	@GetMapping("/listtrains/{page}")
	public String viewtrainList(@PathVariable("page") Integer page, Model model) {
		Pageable  pageable = PageRequest.of(page, 5);
	    Page<AddTrain> findAllTrains = tRepo.findAll(pageable);
		model.addAttribute("listtrains", findAllTrains);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", findAllTrains.getTotalPages());
		return "Admin/showtrain";
	}
	
	@GetMapping("/traindetails/delete/{trainId}")
	public String deletetrain(@PathVariable("trainId") Long trainId) {
		tRepo.deleteById(trainId);
		return"redirect:/admin/listtrains/0";
	}

	@GetMapping("/traindetails/update/{trainId}")
	public String update(@PathVariable("trainId") Long trainId ,  Model model) {
	     Optional<AddTrain> update = tRepo.findById(trainId);
	     AddTrain addTrain= update.get();
		 model.addAttribute("traindetails", addTrain);
	      return "Admin/update";	
	}
	
	
	
	@PostMapping("/savetraindetails")
	public String updatetrain(@ModelAttribute AddTrain addTrain) {
		 tRepo.save(addTrain);
		 return"redirect:/admin/listtrains/0";
	}
	
	@RequestMapping("/trainmaster")
	public String trainhome(Model model) {
		List<Master> listtrains = masterRepo.findAll();
		model.addAttribute("listtrains", listtrains);
		return "trainMaster/TrainMaster";
	}
		
	@GetMapping("/master")
	public String showmaster(Model model) {
     Master master=new Master();
     model.addAttribute("master", master);
    return "trainMaster/master";
	}
	
	@PostMapping("/savemaster")
	public String saveMaster(Master master , Model model) {
			masterRepo.save(master);
	List<Master> listtrains = masterRepo.findAll();
	model.addAttribute("listtrains", listtrains);
		return "trainMaster/Alltrain";
	}
	
	
	@GetMapping("/displayTrainPassngr/{page}")
	public String diplaytp(@PathVariable("page") Integer page,Model model) {
		
		Pageable  pageable = PageRequest.of(page, 5);
	    Page<BookTicket> displaytp = bookRepo.findAll(pageable);
		model.addAttribute("displaytp", displaytp);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", displaytp.getTotalPages());	
        return "Admin/displaytp";
	}
	
	@GetMapping("/traindetails/view/{trainId}")
	public String view(@PathVariable("trainId") Long trainId ,  Model model) {
		
		  Optional<BookTicket> displaytp = bookRepo.findById(trainId);
		  BookTicket displaytpp = displaytp.get();
		/*
		 * Optional<AddTrain> update = tRepo.findById(trainId); AddTrain addTrain=
		 * update.get();
		 */
		 model.addAttribute("displaytpp", displaytpp);
	      return "Admin/view";	
	}
	
	@GetMapping("/noofstops")
	public String noofstops (Model model ) {
    No_of_stops stop= new No_of_stops();		
	model.addAttribute("stop", stop);
	return "trainMaster/stops";
		
	}
	
	@PostMapping("/savestop")
	public String savestop(No_of_stops stop, Model model) {
		stopRepo.save(stop);
		List<No_of_stops> liststop = stopRepo.findAll();
		model.addAttribute("liststop", liststop);
		
		return "trainMaster/stopview";
		
	}

	@GetMapping("/stopview")
	public String stoplist(Model model) {
		List<No_of_stops> liststop = stopRepo.findAll();
		model.addAttribute("liststop", liststop);
		return "trainMaster/stopview";
	}
	
	
	@GetMapping("/viewMasterTrains")
	public String viewMaster(Model model) {
		List<Master> listtrains = masterRepo.findAll();
		model.addAttribute("listtrains", listtrains);
			return "trainMaster/Alltrain";
	}
	
	@GetMapping("/trainMaster/details/{trainId}")
	public String getTrainMasterdataByTrainMasterId(@PathVariable Long trainId, Model model) {
		Optional<Master>   trainMaster = this.masterRepo.findById(trainId);
		 
		model.addAttribute("trainmaster", trainMaster);
		model.addAttribute("listOfNoOfStop", stopRepo.findAll());
		return "admin/add";
	}
}
