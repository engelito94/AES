
import internal.GlobalVariable;
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint;
import static com.kms.katalon.core.model.FailureHandling.CONTINUE_ON_FAILURE
import static com.kms.katalon.core.model.FailureHandling.OPTIONAL
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase;
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData;
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject;
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject;

import com.kms.katalon.core.annotation.Keyword;
import com.kms.katalon.core.checkpoint.Checkpoint;
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords;
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords;
import com.kms.katalon.core.model.FailureHandling;
import com.kms.katalon.core.testcase.TestCase;
import com.kms.katalon.core.testdata.TestData;
import com.kms.katalon.core.testobject.TestObject;
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords;
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords;
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords;
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

public class Podpisovac {

	public static void podpisSpravu() {
		Windows.switchToDesktop()

		Windows.waitForElementPresent(findWindowsObject('Podpisovac/ButtonPodpisat'), 0)
		Windows.click(findWindowsObject('Podpisovac/ButtonPodpisat'))

		Windows.click(findWindowsObject('Podpisovac/ZoznamCertifikatov'))

		Windows.click(findWindowsObject('Podpisovac/Filter'))

		Windows.doubleClick(findWindowsObject('Podpisovac/Certifikat'))

		Windows.delay(2)

		Windows.waitForElementPresent(findWindowsObject('Podpisovac/ButtonOK'), 0)
		Windows.click(findWindowsObject('Podpisovac/ButtonOK'))

		if (Windows.verifyElementPresent(findWindowsObject('Podpisovac/ButtonOK'), 10, OPTIONAL)) {
			Windows.click(findWindowsObject('Podpisovac/ButtonOK'))
		}
		
	}
}
