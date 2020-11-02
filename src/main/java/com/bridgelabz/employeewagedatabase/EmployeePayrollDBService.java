package com.bridgelabz.employeewagedatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.sql.Driver;


import java.sql.Statement;

public class EmployeePayrollDBService {

	private Connection getConnection() throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost:3306/payroll_services?SSL=false";
		String userName = "root";
		String password = "P@13091998";
		Connection connection;
		System.out.println("Connecting to database:"+jdbcURL);
		connection = DriverManager.getConnection(jdbcURL,userName,password);
		System.out.println("Connection is successful!   "+connection);
		return connection;
		
	}
	public List<EmployeePayRollData> readData() {
		String sql = "select * from employee_payroll;";
		List<EmployeePayRollData> employeePayrollList = new ArrayList<>();
		try {
			Connection connection = this.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while(result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				double salary = result.getDouble("salary");
				LocalDate startDate = result.getDate("start").toLocalDate();
				employeePayrollList.add(new EmployeePayRollData(id, name, salary,startDate));
				}
			connection.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}

	}
