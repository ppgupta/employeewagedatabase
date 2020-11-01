package com.bridgelabz.employeewagedatabase;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class EmployeePayRollServiceTest 
{
	@Test
	public void givenEmployeePayrolInDBWhenRetrievedShouldMatchEmployeeCount() {
		EmployeePayRollService employeePayrollService = new EmployeePayRollService();
		List<EmployeePayRollData> employeePayrollList = employeePayrollService
				.readEmployeePayrollData(EmployeePayRollService.IOService.DB_IO);
		Assert.assertEquals(4, employeePayrollList.size());
	}
}
