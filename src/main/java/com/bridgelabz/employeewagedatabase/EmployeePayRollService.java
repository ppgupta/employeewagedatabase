package com.bridgelabz.employeewagedatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayRollService {
	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	}

	private List<EmployeePayRollData> employeePayRollList;

	public EmployeePayRollService() {
		this.employeePayRollList = new ArrayList<>();
	}

	public List<EmployeePayRollData> getEmployeePayRollList() {
		return employeePayRollList;
	}

	public void setEmployeePayRollList(List<EmployeePayRollData> employeePayRollList) {
		this.employeePayRollList = employeePayRollList;
	}

	public static void main(String args[]) {
		EmployeePayRollService employeePayrollService = new EmployeePayRollService();
		Scanner consoleInputReader = new Scanner(System.in);
		employeePayrollService.readEmployeePayrollData(consoleInputReader);
		employeePayrollService.writeEmployeePayrollData(IOService.CONSOLE_IO);
	}

	private void readEmployeePayrollData(Scanner consoleInputReader) {
		System.out.println("Enter Employee ID: ");
		int id = consoleInputReader.nextInt();
		System.out.println("Enter Employee Name: ");
		String name = consoleInputReader.next();
		System.out.println("Enter Employee Salary: ");
		Double salary = consoleInputReader.nextDouble();
		employeePayRollList.add(new EmployeePayRollData(id, name, salary));
	}

	public void writeEmployeePayrollData(IOService ioService) {
		if (ioService.equals(IOService.CONSOLE_IO)) {
			System.out.println("Employee PayRoll Data :" + employeePayRollList);
		}
		else if(ioService.equals(IOService.FILE_IO)) {
			new EmployeePayRollFileIOService().writeData(employeePayRollList);
		}
	}

	public void printData(IOService ioService) {
		if(ioService.equals(IOService.FILE_IO)) {
			new EmployeePayRollFileIOService().printData();
		}
	}

	public long countEntries(IOService ioService) {
		if(ioService.equals(IOService.FILE_IO)) {
			return new EmployeePayRollFileIOService().countEntries();
		}
		return 0;
	}

	public List<EmployeePayRollData> readData(IOService ioService) {
		if(ioService.equals(IOService.FILE_IO)) {
			return new EmployeePayRollFileIOService().readData();
		}
		if(ioService.equals(IOService.DB_IO)) {
			return new EmployeePayrollDBService().readData();
		}
		return null;
	}
}
