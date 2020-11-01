package com.bridgelabz.employeewagedatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

public class EmployeePayrollDBService {

	private Connection getConnection() throws SQLException, ClassNotFoundException {
		String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
		String username = "root";
		String password = "P@13091998";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection;
		connection = DriverManager.getConnection(jdbcURL, username, password);
		return connection;
	}

	public List<EmployeePayRollData> readData() {
		String sql = "SELECT * FROM employee_payroll;";
		List<EmployeePayRollData> employeePayrollList = new ArrayList<>();
		try (Connection connection = getConnection();) {
			Statement statement =  connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				int empId = resultSet.getInt("id");
				String empName = resultSet.getString("name");
				double salary = resultSet.getDouble("basic_pay");
				LocalDate startDate = resultSet.getDate("start").toLocalDate();
				employeePayrollList.add(new EmployeePayRollData(empId, empName, salary, startDate));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}

}
