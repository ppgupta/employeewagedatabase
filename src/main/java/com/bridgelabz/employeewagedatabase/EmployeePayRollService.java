package com.bridgelabz.employeewagedatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.bridgelabz.employeewagedatabase.EmployeePayrollDBService.StatementType;

public class EmployeePayRollService {
	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	}

	private EmployeePayrollDBService employeePayrollDBService;
	private List<EmployeePayRollData> employeePayRollList;

	public EmployeePayRollService() {
		employeePayrollDBService = EmployeePayrollDBService.getInstance();
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
		} else if (ioService.equals(IOService.FILE_IO)) {
			new EmployeePayRollFileIOService().writeData(employeePayRollList);
		}
	}

	public void printData(IOService ioService) {
		if (ioService.equals(IOService.FILE_IO)) {
			new EmployeePayRollFileIOService().printData();
		}
	}

	public long countEntries(IOService ioService) {
		if (ioService.equals(IOService.FILE_IO)) {
			return new EmployeePayRollFileIOService().countEntries();
		}
		return 0;
	}

	public List<EmployeePayRollData> readData(IOService ioService) {
		if (ioService.equals(IOService.FILE_IO)) {
			employeePayRollList = new EmployeePayRollFileIOService().readData();
			return employeePayRollList;
		}
		if (ioService.equals(IOService.DB_IO)) {
			employeePayRollList = employeePayrollDBService.readData();
			return employeePayRollList;
		}
		return null;
	}

	public List<EmployeePayRollData> getEmployeeListInStartDateRange(String date1, String date2, IOService ioService) {
		if (ioService.equals(IOService.DB_IO)) {
			return employeePayrollDBService.getEmployeeListInRange(date1,date2);
		}
		return null;
	}

	public void updateEmployeeSalary(String name, double salary, StatementType type) {
		// check above
		int result = employeePayrollDBService.updateEmployeeData(name, salary, type);
		if (result == 0)
			return;
		EmployeePayRollData employeePayRollData = this.getEmployeePayRollData(name);
		if (employeePayRollData != null)
			employeePayRollData.setSalary(salary);
	}

	private EmployeePayRollData getEmployeePayRollData(String name) {
		EmployeePayRollData employeePayRollData;
		employeePayRollData = this.employeePayRollList.stream()
				.filter(employeePayrollDataItem -> employeePayrollDataItem.getName().equals(name)).findFirst()
				.orElse(null);
		return employeePayRollData;
	}

	public boolean checkEmployeePayrollInSyncWithDB(String name) {
		List<EmployeePayRollData> checkList = employeePayrollDBService.getEmployeePayrollData(name);
		return checkList.get(0).equals(getEmployeePayRollData(name));
	}
	public Map<String, Double> readAverageSalaryByGender(IOService ioService) {
		if(ioService.equals(IOService.DB_IO))
			return employeePayrollDBService.getAverageSalaryByGender();
		return null;
	}
}
