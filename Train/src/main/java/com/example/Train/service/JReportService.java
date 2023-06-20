package com.example.Train.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.example.Train.Repository.BookTicketRepo;
import com.example.Train.Repository.UserRepo;
import com.example.Train.entites.BookTicket;
import com.example.Train.entites.User;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
   
@Service
public class JReportService {
	
	@Autowired
	private UserRepo repo;
	
	  @Autowired
	  private BookTicketRepo bookRepo;
	  
	 
	    
	    public void exportJasperReport(HttpServletResponse response, String email, Long trainId) throws JRException, IOException {
	    	
	    	User user = repo.findByEmail(email);
	    	  List<BookTicket> bookTicket = bookRepo.findBookTicketByUserAndTrain(user.getId(), trainId);
	    	 
	    	  
	    	  
	        //Get file and compile it
	        File file = ResourceUtils.getFile("classpath:TrainTicket.jrxml");
	        
	        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
	        
	        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(bookTicket);
	        
	        Map<String, Object> parameters = new HashMap<>();
	        parameters.put("createdBy", "Sarvesh");
	        
	        //Fill Jasper report
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
	        
	       
	        //Export report
	        JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());
	    }
	    


}
