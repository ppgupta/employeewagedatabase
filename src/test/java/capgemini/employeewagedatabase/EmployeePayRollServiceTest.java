package capgemini.employeewagedatabase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import capgemini.employeewagedatabase.EmployeePayRollService.IOService;
import capgemini.employeewagedatabase.EmployeePayrollDBService.StatementType;


/**
 * Unit test for simple App.
 */
public class EmployeePayRollServiceTest 
{
	@Test
	public void given3EmployeesWhenWrittenToFileShouldMatchEmployeeEntries() {
		List<EmployeePayRollData> employeeList = new ArrayList<>();
		employeeList.add(new EmployeePayRollData(1,"Jeff Bezos",10000.20));
		employeeList.add(new EmployeePayRollData(2,"Bill gates",8000.60));
		employeeList.add(new EmployeePayRollData(3,"Mark Zuckerberg",6000.80));
		EmployeePayRollService employeePayrollService = new EmployeePayRollService();
		employeePayrollService.setEmployeePayRollList(employeeList);
		employeePayrollService.writeEmployeePayrollData(IOService.FILE_IO);
		employeePayrollService.printData(IOService.FILE_IO);
		long numberOfEntries = employeePayrollService.countEntries(IOService.FILE_IO);
		List<EmployeePayRollData> analysisList = employeePayrollService.readData(IOService.FILE_IO);
		if(analysisList!=null) {
		System.out.println(analysisList);
		}
		else {
			System.out.println("null");
		}
		assertEquals(3,numberOfEntries);
	}
	
	//UC2
	@Test
	public void givenEmployeePayrollInDbWhenRetrievedShouldMatchEmployeeCount() {
		EmployeePayRollService employeePayrollService = new EmployeePayRollService();
		List<EmployeePayRollData> employeePayrollData = employeePayrollService.readData(IOService.DB_IO);
		assertEquals(3,employeePayrollData.size());
	}
	//UC3
	@Test
			public void givenNewSalaryForEmployeeWhenUpdatedShouldSyncWithDB() {
				EmployeePayRollService employeePayrollService = new EmployeePayRollService();
				List<EmployeePayRollData> employeePayRollData = employeePayrollService.readData(IOService.DB_IO);
				employeePayrollService.updateEmployeeSalary("Teressa",2000000.00,StatementType.STATEMENT);
				boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Teressa");
				assertTrue(result);
			}
			
		//UC4
			@Test
			public void givenNewSalaryForEmployeeWhenUpdatedShouldSyncWithDBUsingPreparedStatement() {
				EmployeePayRollService employeePayrollService = new EmployeePayRollService();
				List<EmployeePayRollData> employeePayRollData = employeePayrollService.readData(IOService.DB_IO);
				employeePayrollService.updateEmployeeSalary("Teressa",2000000.00,StatementType.PREPARED_STATEMENT);
				boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Teressa");
				assertTrue(result);
			}
			//UC5
			@Test
			public void givenDateRangeWhenRetrievedShouldMatchEmployeeCount() {
				EmployeePayRollService employeePayrollService = new EmployeePayRollService();
				List<EmployeePayRollData> employeePayRollData = employeePayrollService.readData(IOService.DB_IO);
				List<EmployeePayRollData> employeePayRollList = employeePayrollService.getEmployeeListInStartDateRange("2010-01-01","2020-12-12",IOService.DB_IO);
				 System.out.println(employeePayRollList.size());
				assertEquals(3, employeePayRollList.size());
			}
			//UC6
			@Test
			public void givenPayrollDataWhenAverageSalaryRetrievedByGenderShouldReturnProperValue() {
				EmployeePayRollService employeePayrollService = new EmployeePayRollService();
				List<EmployeePayRollData> employeePayRollData = employeePayrollService.readData(IOService.DB_IO);
				Map<String,Double> averageSalaryByGender = employeePayrollService.readAverageSalaryByGender(IOService.DB_IO);
				assertTrue(averageSalaryByGender.get("M").equals(3000000.00)&&averageSalaryByGender.get("F").equals(2000000.00));
			}
			//UC7
			@Test
			public void givenNewEmployeeWhenAddedShouldSyncWithDB() {
				EmployeePayRollService employeePayrollService = new EmployeePayRollService();
				List<EmployeePayRollData> employeePayRollData = employeePayrollService.readData(IOService.DB_IO);
				employeePayrollService.addEmployeeToPayroll("Mark",5000000.00,LocalDate.now(),"M");
				boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Mark");
				assertTrue(result);
			}
	
}
