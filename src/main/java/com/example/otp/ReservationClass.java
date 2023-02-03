package com.example.otp;

import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
@Service
public class ReservationClass {
	
	public ArrayList fetch(HttpServletRequest req,HttpServletResponse res) {
		ArrayList al=new ArrayList();
		String from=req.getParameter("source");
		String to=req.getParameter("destination");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3307/airlinereservation","root","humtum123@");
			System.out.println("connection establish");
			
			if(from.equals("Mumbai") && to.equals("Amritsar")) {
			PreparedStatement pst=con.prepareStatement("select * from mumtoamr");
			ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				String dep=rs.getString(1);
				String dur=rs.getString(2);
				String arr=rs.getString(3);
				
				//al.add(dep+"\t"+dur+"\t"+arr+"\n");
				
			}
			}else if(from.equals("Mumbai") && to.equals("Agra")){
				PreparedStatement pst=con.prepareStatement("select * from mumtoagr");
				ResultSet rs=pst.executeQuery();
				while(rs.next()) {
					String dep=rs.getString(1);
					String dur=rs.getString(2);
					String arr=rs.getString(3);
					
					al.add(dep+"\t"+dur+"\t"+arr+"\n");
				}	
				
			}
			else if(from.equals("Mumbai") && to.equals("Bareilly")){
				PreparedStatement pst=con.prepareStatement("select * from mumtobar");
				ResultSet rs=pst.executeQuery();
				while(rs.next()) {
					String dep=rs.getString(1);
					String dur=rs.getString(2);
					String arr=rs.getString(3);
					
					al.add(dep+"\t"+dur+"\t"+arr+"\n");
				}	
				
			}
			
			
		} catch (Exception e) {
			
		}
		
		return al;
		
	}

}
