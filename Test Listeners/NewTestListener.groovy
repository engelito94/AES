import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

import internal.GlobalVariable as GlobalVariable

import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.windows.driver.WindowsDriverFactory
import com.kms.katalon.core.windows.keyword.helper.WindowsActionHelper

class NewTestListener {
	/**
	 * Executes after every test case ends.
	 * @param testCaseContext related information of the executed test case.
	 */
	@AfterTestCase
	def sampleAfterTestCase(TestCaseContext testCaseContext) {CucumberKW
		String testCaseId = testCaseContext.getTestCaseId()
		String tc = testCaseId.substring((testCaseId.lastIndexOf("/").toInteger()) + 1)
		String screenshotLocation = RunConfiguration.getProjectDir() + '/' + tc + '.png'
		WindowsActionHelper.create(WindowsDriverFactory.getWindowsSession()).takeScreenshot(screenshotLocation)
		if (Windows.verifyElementPresent(findWindowsObject('Object Repository/CEP/EKR2'), 10, FailureHandling.OPTIONAL)) {
			Windows.closeApplication()
			
			Windows.switchToWindowTitle('CIS klient - SK')
			
			Windows.closeApplication()
			
			Windows.click(findWindowsObject('Object Repository/CIS_klient/ZavrietAplikaciu'))
		} else if(Windows.verifyElementPresent(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/MenuItem_CUVyvozu'), 10, FailureHandling.OPTIONAL)) {
			Windows.closeApplication()
			
			Windows.click(findWindowsObject('Object Repository/CIS_klient/ZavrietAplikaciu'))
		}
	}
}