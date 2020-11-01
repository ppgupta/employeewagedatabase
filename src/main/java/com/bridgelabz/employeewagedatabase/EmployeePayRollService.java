package com.bridgelabz.employeewagedatabase;

import java.util.ArrayList;
import java.util.List;

public class EmployeePayRollService {
	private List<EmployeePayRollData> employeePayrollList;
	private EmployeePayrollDBService employeePayrollDBService;

	public enum IOService {
		DB_IO
	}
	
	public EmployeePayRollService() {
		employeePayrollDBService = new EmployeePayrollDBService();
		employeePayrollList = new ArrayList<>();
	}
	
	public List<EmployeePayRollData> readEmployeePayrollData(IOService ioService) {
		if (ioService.equals(EmployeePayRollService.IOService.DB_IO))
			this.employeePayrollList = employeePayrollDBService.readData();
		return this.employeePayrollList;
	}
}
