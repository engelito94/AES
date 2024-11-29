
import internal.GlobalVariable;
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint;
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase;
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData;
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject;
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject;

import org.openqa.selenium.Keys
import org.openqa.selenium.interactions.Actions

import com.kms.katalon.core.annotation.Keyword;
import com.kms.katalon.core.checkpoint.Checkpoint;
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords;
import com.kms.katalon.core.logging.KeywordLogger
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords;
import com.kms.katalon.core.model.FailureHandling;
import com.kms.katalon.core.testcase.TestCase;
import com.kms.katalon.core.testdata.TestData;
import com.kms.katalon.core.testobject.TestObject;
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords;
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords;
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords;
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows


public class CEPovac {
	KeywordUtil util = new KeywordUtil()
	KeywordLogger log = new KeywordLogger()


	def prihlasCEP(String meno, String heslo) {
		Windows.click(findWindowsObject('CEP/Meno a heslo'))

		Windows.waitForElementPresent(findWindowsObject('CEP/prihlMeno'), 30)

		Windows.setText(findWindowsObject('CEP/prihlMeno'), meno)

		Windows.setEncryptedText(findWindowsObject('CEP/prihlHeslo'), heslo)

		Windows.click(findWindowsObject('CEP/prihlButton'))

		Windows.delay(3)

		Windows.waitForElementNotPresent(findWindowsObject('CEP/prihlButton'), 0)

		Windows.click(findWindowsObject('CEP/EKR2'))

		Windows.click(findWindowsObject('CEP/Odoslať podanie EKR2'))

		Windows.click(findWindowsObject('CEP/Jednoduche podanie'))

		log.logInfo("Prihlásenie úspešné")
	}

	def vyplnSKSpravu(String LRN, String typSpravy) {

		Windows.sendKeys(findWindowsObject('CEP/typSpravyEdit'), typSpravy)

		Windows.sendKeys(findWindowsObject('CEP/typSpravyEdit'), Keys.chord(Keys.ENTER))

		Windows.setText(findWindowsObject('CEP/LRNEdit'), LRN)

		Windows.setText(findWindowsObject('CEP/SenderIDEdit'), 'ico://sk/43771531')

		Windows.click(findWindowsObject('CEP/PodpisatOdoslat'))
	}

	def vyberXMLDialog(String filePathDialog) {
		Windows.click(findWindowsObject('CEP/XMLButton'))

		Windows.click(findWindowsObject('CEP_Dialog/DialogFileName'))

		Windows.setText(findWindowsObject('CEP_Dialog/DialogFileName'), filePathDialog) //'C:\\Users\\barcik\\Desktop\\CEP XML\\AES\\Ditec xml\\SK515_01.xml')

		Windows.sendKeys(findWindowsObject('CEP_Dialog/DialogFileName'), Keys.chord(Keys.ENTER))
	}

	def otvorHistoriuZasielok() {
		Windows.click(findWindowsObject('Object Repository/CEP/EKR2'))
		Actions builder = new Actions(Windows.getDriver())
		builder.click(Windows.findElement(findWindowsObject('Object Repository/CEP/HistoriaPodaniZasielokEKR2')))
		builder.click(Windows.findElement(findWindowsObject('Object Repository/CEP/PrijateZasielky')))
		builder.perform()
	}

	def skontrolujPrijatuSpravu(String typ, String LRN) {

		Windows.setText(findWindowsObject('Object Repository/CEP/Edit_IdentifikatorEKR'), '26575977')

		Windows.setText(findWindowsObject('Object Repository/CEP/Edit_IDPripadu'), LRN)

		Windows.setText(findWindowsObject('Object Repository/CEP/Edit_DropdownTypZasielky'), typ)

		Windows.sendKeys(findWindowsObject('Object Repository/CEP/Edit_DropdownTypZasielky'), Keys.chord(Keys.ENTER))

		//Windows.click(findWindowsObject('Object Repository/CEP/Button_Vyhladat'))

		Windows.sendKeys(findWindowsObject('Object Repository/CEP/EKR2'), Keys.chord(Keys.LEFT_ALT, 'm'))
		Windows.sendKeys(findWindowsObject('Object Repository/CEP/EKR2'), Keys.chord('v'))

		int pocitadlo = 0
		while (Windows.waitForElementNotPresent(findWindowsObject('Object Repository/CEP/DataItem_NazovZasielky'), 10, FailureHandling.OPTIONAL) & pocitadlo < 50) {
			Windows.sendKeys(findWindowsObject('Object Repository/CEP/EKR2'), Keys.chord(Keys.LEFT_ALT, 'm'))
			Windows.sendKeys(findWindowsObject('Object Repository/CEP/EKR2'), Keys.chord('v'))
			pocitadlo ++
		}

		String sprava = Windows.getText(findWindowsObject('Object Repository/CEP/DataItem_NazovZasielky'))


		switch(typ) {
			case "SK516AES":
				if (sprava.equals("SK516AES.001 - Potvrdenie o registrácii vývozného CV")) {
					log.logPassed("SK516 nájdená")
					util.markPassed("SK516 nájdená")
				}
				break;
			case "SK528AES":
				if (sprava.equals("SK528AES.001 - Rozhodnutie o prijatí vývozného CV")) {
					log.logPassed("SK528 nájdená")
					util.markPassed("SK528 nájdená")
				}
				break;
			case "SK529AES":
				if (sprava.equals("SK529AES.001 - Rozhodnutie o prepustení do colného režimu vývoz")) {
					log.logPassed("SK529 nájdená")
					util.markPassed("SK529 nájdená")
				}
				break;
			case "SK599AES":
				if (sprava.equals("SK599AES.001 - Potvrdenie o výstupe tovaru")) {
					log.logPassed("SK999 nájdená")
					util.markPassed("SK599 nájdená")
				}
				break;
			case "SK509AES":
				if (sprava.equals("SK509AES.001 - Rozhodnutie o zrušení/zneplatnení vývozného CV")) {
					log.logPassed("SK509 nájdená")
					util.markPassed("SK509 nájdená")
				}
				break;
			case "SK525AES":
				if (sprava.equals("SK525AES.001 - Rozhodnutie o povolení výstupu tovaru")) {
					log.logPassed("SK525 nájdená")
					util.markPassed("SK525 nájdená")
				}
				break;
			case "SK525EXS":
				if (sprava.equals("SK525EXS.001 - Rozhodnutie o povolení výstupu tovaru")) {
					log.logPassed("SK525 nájdená")
					util.markPassed("SK525 nájdená")
				}
				break;
			case "SK531AES":
				if (sprava.equals("SK531AES.001 - Oznámenie o uplynutí/predĺžení lehoty na podanie dodatočného CV")) {
					log.logPassed("SK531 nájdená")
					util.markPassed("SK531 nájdená")
				}
				break;
			case "SK504AES":
				if (sprava.equals("SK504AES.001 - Rozhodnutie o prijatí opravy vývozného CV")) {
					log.logPassed("SK504 nájdená")
					util.markPassed("SK504 nájdená")
				}
				break;
			case "SK556AES":
				if (sprava.equals("SK556AES.001 - Zamietnutie z CÚ vývozu")) {
					log.logPassed("SK556 nájdená")
					util.markPassed("SK556 nájdená")
				}
				break;
			case "SK628EXS":
				if (sprava.equals("SK628EXS.001 - Potvrdenie podania predbežného colného vyhlásenia")) {
					log.logPassed("SK628 nájdená")
					util.markPassed("SK628 nájdená")
				}
				break;
			case "SK604EXS":
				if (sprava.equals("SK604EXS.001 - Povolenie opravy predbežného colného vyhlásenia")) {
					log.logPassed("SK604 nájdená")
					util.markPassed("SK604 nájdená")
				}
				break;
			case "SK557EXS":
				if (sprava.equals("SK557EXS.001 - Odmietnutie colného vyhlásenia na CÚ výstupu/podania")) {
					log.logPassed("SK557 nájdená")
					util.markPassed("SK557 nájdená")
				}
				break;
			case "SK548EXS":
				if (sprava.equals("SK548EXS.001 - Schválenie/zamietnutie manifestu")) {
					log.logPassed("SK548 nájdená")
					util.markPassed("SK548 nájdená")
				}
				break;
			case "SK561AES":
				if (sprava.equals("SK561AES.001 - Oznámenie o kontrole na CÚ Výstupu")) {
					log.logPassed("SK561 nájdená")
					util.markPassed("SK561 nájdená")
				}
				break;
			case "SK504AES":
				if (sprava.equals("SK504AES.001 - Rozhodnutie o prijatí opravy vývozného CV")) {
					log.logPassed("SK504 nájdená")
					util.markPassed("SK504 nájdená")
				}
				break;
			case "SK560AES":
				if (sprava.contains("SK560AES.001")) {
					log.logPassed("SK560 nájdená")
					util.markPassed("SK560 nájdená")
				}
				break;
			case "SK548AES":
				if (sprava.equals("SK548AES.001 - Schválenie/zamietnutie manifestu")) {
					log.logPassed("SK548 nájdená")
					util.markPassed("SK548 nájdená")
				}
				break;
			default:
				log.logFailed("Neočakávaná správa")
				util.markFailedAndStop("Neočakávaná správa")
				break;
		}
	}

	def skontrolujPrijatuSpravu599(String typ, String LRN) {

		Windows.setText(findWindowsObject('Object Repository/CEP/Edit_IdentifikatorEKR'), '26575977')

		Windows.setText(findWindowsObject('Object Repository/CEP/Edit_IDPripadu'), LRN)

		Windows.setText(findWindowsObject('Object Repository/CEP/Edit_DropdownTypZasielky'), typ)

		Windows.sendKeys(findWindowsObject('Object Repository/CEP/Edit_DropdownTypZasielky'), Keys.chord(Keys.ENTER))

		//Windows.click(findWindowsObject('Object Repository/CEP/Button_Vyhladat'))

		Windows.sendKeys(findWindowsObject('Object Repository/CEP/EKR2'), Keys.chord(Keys.LEFT_ALT, 'm'))
		Windows.sendKeys(findWindowsObject('Object Repository/CEP/EKR2'), Keys.chord('v'))

		int pocitadlo = 0
		while (Windows.waitForElementNotPresent(findWindowsObject('Object Repository/CEP/DataItem_NazovZasielky'), 10, FailureHandling.OPTIONAL) & pocitadlo < 50) {
			Windows.sendKeys(findWindowsObject('Object Repository/CEP/EKR2'), Keys.chord(Keys.LEFT_ALT, 'm'))
			Windows.sendKeys(findWindowsObject('Object Repository/CEP/EKR2'), Keys.chord('v'))
			pocitadlo ++
		}

		String sprava = Windows.getText(findWindowsObject('Object Repository/CEP/DataItem_NazovZasielky'))
		String sprava2 = Windows.getText(findWindowsObject('Object Repository/CEP/DataItem_NazovZasielky2'))

		if(sprava.equals("SK599AES.001 - Potvrdenie o výstupe tovaru z územia Únie") && sprava2.equals("SK599AES.001 - Potvrdenie o výstupe tovaru z územia Únie")) {
			log.logPassed("Nájdené 2 správy SK599")
		}
	}
}