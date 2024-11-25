import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.logging.KeywordLogger as KeywordLogger
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable

import org.apache.ivy.plugins.namespace.MRIDRule
import org.openqa.selenium.Keys as Keys

KeywordUtil util = new KeywordUtil()

KeywordLogger log = new KeywordLogger()

CISKlient cis = new CISKlient()

Podpisovac podpis = new Podpisovac()

CEPovac cep = new CEPovac()

XMLGenerator xml = new XMLGenerator()

String MRN = '24CZ10290708178AC2'

'Prepnutie na CIS'
Windows.startApplication(GlobalVariable.cestaCIS)

cis.prihlasCisKlient(GlobalVariable.menoCIS, GlobalVariable.hesloCIS, 'SK517700')

Windows.switchToWindowTitle('CIS klient - SK517700 (DTC_TEST)')

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/MenuItem_CUVyvozu'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Button_VyziadanieInformaciiExPripady'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Button_NewItem'))

Windows.setText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Edit_VyzvaNaPodanieDoplnkovehoCV'), MRN)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Button_PotvrditOdoslat'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Button_VyhladatFilter'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Data_DatumVlozenia'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Data_DatumVlozenia'))

String dataMRN = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Data_MRNRow0'))

if (dataMRN.equals(MRN)) {
	log.logPassed("MRN sa zhodujú")
} else {
	log.logFailed("MRN sa nezhodujú")
}

'Zavretie CIS'
Windows.closeApplication()

Windows.click(findWindowsObject('Object Repository/CIS_klient/ZavrietAplikaciu'))