
import internal.GlobalVariable;
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint;
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase;
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData;
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject;
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject;

import com.kms.katalon.core.annotation.Keyword;
import com.kms.katalon.core.checkpoint.Checkpoint;
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords;
import com.kms.katalon.core.logging.KeywordLogger
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords;
import com.kms.katalon.core.model.FailureHandling;
import com.kms.katalon.core.testcase.TestCase;
import com.kms.katalon.core.testdata.TestData;
import com.kms.katalon.core.testobject.TestObject;
import com.kms.katalon.core.util.internal.KeywordLoader
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords;
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords;
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords;
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

public class CISKlient {
	KeywordLogger log = new KeywordLogger()

	def prihlasCisKlient(String meno, String heslo, String colnyUrad) {
		String objekt = "Object Repository/CIS_klient/Prihlasovanie/"
		switch(colnyUrad) {
			case "SK517700":
				objekt += "CU_517700"
				break;
			case "SK607600":
				objekt += "CU_607600"
				break;
			case "SK606900":
				objekt += "CU_606900"
				break;
			case "SK526700":
				objekt += "CU_526700"
				break;
			case "SK607600":
				objekt += "CU_607600_Predlozenia"
				break;
			default:
				println("The value is unknown");
				break;
		}

		Windows.setText(findWindowsObject('Object Repository/CIS_klient/Prihlasovanie/pouz_meno'), meno)

		Windows.setEncryptedText(findWindowsObject('Object Repository/CIS_klient/Prihlasovanie/heslo'), heslo)

		Windows.setText(findWindowsObject('Object Repository/CIS_klient/Prihlasovanie/domena'), 'DITEC')

		Windows.click(findWindowsObject('Object Repository/CIS_klient/Prihlasovanie/colny_urad_dropdown'))

		Windows.click(findWindowsObject(objekt))

		Windows.click(findWindowsObject('Object Repository/CIS_klient/Prihlasovanie/Prihlasit_button'))

		Windows.click(findWindowsObject('CIS_klient/Prihlasovanie/OK_button'), FailureHandling.STOP_ON_FAILURE)

		log.logInfo("Prihlásenie úspešné")
	}
}