package com.example.otp;

import java.io.PrintWriter;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class ControllerClass {

	
	@Autowired
	Search s;
	
	@Autowired
	mumtoamrRepo mumtoamr1;
	
	@Autowired
	mumtobarRepo mumtobar;
	
	
	
	/*
	 * @Autowired ReservationClass reg;
	 */
	@Autowired
	FlightBook flightbookrepo;
	
	@Autowired
	GateWayRepo repo;
	
	@Autowired
	RepositaryInterface repo1;
	
	@RequestMapping(value="/home",method = RequestMethod.GET)
	public String home() {
		
		return "home";
		
	}
	
	@RequestMapping(value="/about")
	public String about() {
		
		return "about";
		
	}
	
	@RequestMapping(value="/registration")
	public String registration() {
		
		return "registration";
		
	}
	
	@RequestMapping(value="/register")
	public String register(@ModelAttribute RegistrationClass rc,@RequestParam("email") String email,HttpServletResponse res) throws Exception{
		
		Optional<RegistrationClass> o=repo1.findById(email);
		System.out.println(o);
		/*
		 * if(email1.equals(email)) {
		 * 
		 * 
		 * PrintWriter out=res.getWriter(); out.print("Email already Register"); }else {
		 * repo1.save(rc); return "success"; }
		 */
		
		repo1.save(rc);

		
		return "success";
		
	}
	
	
	@RequestMapping(value="/blog-home")
	public String bloghome() {
		
		return "blog-home";
		
	}
	
	@RequestMapping(value="/blog-single")
	public String blogsingle() {
		
		return "blog-single";
		
	}
	
	/*
	 * @RequestMapping(value="/details") public String flight12(@ModelAttribute
	 * mumtobar m) {
	 * 
	 * m.setDep("16:50"); m.setDur("07h 15min"); m.setArr("00:05");
	 * m.setSource("Mumbai"); m.setDestination("Bareilly"); mumtobar.save(m);
	 * 
	 * return "about";
	 * 
	 * }
	 */
	@RequestMapping(value="/flight")
	public String flight() {
		
		return "flight";
		
	}
	
	
	@RequestMapping("/show")
	public String showFlights(Model model, @RequestParam("source") String source,@RequestParam("destination") String destination) {
		//for mum to amritsar data
		List<mumtoamr> li=mumtoamr1.findAll();
		//for mum to bareilly data
		List<mumtobar> li1=mumtobar.findAll();
		if(source.equals("Mumbai") && destination.equals("Amritsar")) {
			model.addAttribute("mum", li);
			return "display";
		}
		else if(source.equals("Mumbai") && destination.equals("Bareilly")) {
			model.addAttribute("mum", li1);
			return "display";
		}
		
		
		return "flight";
		
	}
	
	@RequestMapping("/book")
	public String book() {
		return "Book";
	}
	
	@RequestMapping("/flightbook")
	public String flightbook(@ModelAttribute FlightEntity fe) {
		flightbookrepo.save(fe);
		return "gateway";
	}
	
	@RequestMapping("/gateway")
	public String flightbook(@ModelAttribute Gateway gateway1) {
		
		repo.save(gateway1);
		return "response";
	}
	
	@RequestMapping("/Login")
	public String LoginApp() {
		return "Login";
	}
	
	
	@RequestMapping("/login")
	public String Login(Model model,mumtoamr m,HttpServletRequest req,@RequestParam("uname") String uname,@RequestParam("pass") String pass,HttpServletResponse res,HttpSession session,@ModelAttribute SearchEntityClass sf,@RequestParam("id") int id) {
		
		System.out.println(sf.getSource());
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3307/airlinereservation","root","humtum123@");
			System.out.println("connection establish");
			PreparedStatement pst1=con.prepareStatement("select * from flight_entity where email=?");
			PreparedStatement pst2=con.prepareStatement("select * from gateway where pass=?");
			PreparedStatement pst3=con.prepareStatement("select * from search_entity_class");
			PreparedStatement pst4=con.prepareStatement("select * from mum_to_amr where id=?");
			
			pst4.setInt(1, id);
			pst1.setString(1, uname);
			pst2.setString(1, pass);
			//pst3.setString(1, )
			//execute query-1
			ResultSet rs=pst1.executeQuery();
			//execute query-1
			ResultSet rs2=pst2.executeQuery();
			//execute query-3
			ResultSet rs3=pst3.executeQuery();
			ResultSet rs4=pst4.executeQuery();
			while(rs.next()) {
				//long reservationno=rs.getLong(1);
				String eml=rs.getString(1);
				String address=rs.getString(2);
				String fullname=rs.getString(5);
				if(uname.equals(eml)) {
				session.setAttribute("eml", eml);
				session.setAttribute("address", address);
				session.setAttribute("fullname", fullname);
			}
			}
			while(rs2.next()) {
				String pass1=rs2.getString(1);
				String user=rs2.getString(2);
				if(pass.equals(pass1)) {
					session.setAttribute("secondMail", user);
					System.out.println(pass1);
					System.out.println(user);
				}
			}
			while(rs3.next()) {
				String adults=rs3.getString(2);
				String childrens=rs3.getString(3);
				String classes=rs3.getString(4);
				String date=rs3.getString(5);
				String des=rs3.getString(6);
				String flightType=rs3.getString(7);
				String source=rs3.getString(8);
				
				session.setAttribute("adults", adults);
				session.setAttribute("childrens", childrens);
				session.setAttribute("classes", classes);
				session.setAttribute("date", date);
				session.setAttribute("des", des);
				session.setAttribute("flightType", flightType);
				session.setAttribute("source", source);
				
				while(rs4.next()) {
					int id1=rs4.getInt(1);
					String arr=rs4.getString(2);
					String dep=rs4.getString(3);
					String dur=rs4.getString(5);
					session.setAttribute("dep", dep);
					session.setAttribute("dur", dur);
					session.setAttribute("arr", arr);
					session.setAttribute("id1", id1);
					
					System.out.println(id);
					
					/*
					 * if(source.equals("Mumbai") && des.equals("Amritsar")) {
					 * 
					 * 
					 * Optional<mumtoamr> li=mumtoamr1.findById(id); model.addAttribute("mum",li); }
					 * else if(source.equals("Mumbai") && des.equals("Bareilly")) { List<mumtobar>
					 * li1=mumtobar.findAll(); model.addAttribute("mum", li1);
					 * 
					 * }
					 */
				
				}	
				
			}
		} catch (Exception e) {
			
		}
		
		System.out.println(uname);
		System.out.println(pass);
		
		return "LoginPage";
		
	}

	
}
