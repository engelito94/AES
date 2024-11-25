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
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

KeywordUtil util = new KeywordUtil()

KeywordLogger log = new KeywordLogger()

CISKlient cis = new CISKlient()

Podpisovac podpis = new Podpisovac()

CEPovac cep = new CEPovac()

XMLGenerator xml = new XMLGenerator()

String LRN = xml.generateLRN()

String filePath = GlobalVariable.cestaSubor + 'PCV-04 SK615EXS - DTC.xml'

String filePathDialog = filePath.replaceAll('/', '\\\\')

xml.replaceLrnValue(filePath, LRN)

'CEP'
Windows.startApplication(GlobalVariable.cestaCEP)

cep.prihlasCEP(GlobalVariable.menoCEP, GlobalVariable.hesloCEP)

cep.vyberXMLDialog(filePathDialog)

cep.vyplnSKSpravu(LRN, 'SK615EXS')

podpis.podpisSpravu()

Windows.switchToApplication()

cep.otvorHistoriuZasielok()

cep.skontrolujPrijatuSpravu('SK628EXS', LRN)

Windows.closeApplication()

'Prepnutie na CIS'
Windows.startApplication(GlobalVariable.cestaCIS)

cis.prihlasCisKlient(GlobalVariable.menoCIS, GlobalVariable.hesloCIS, 'SK517700')

Windows.switchToWindowTitle('CIS klient - SK517700 (DTC_TEST)')

Windows.click(findWindowsObject('CIS_klient/CU_Podania/MenuItem_CUPodania'))

Windows.click(findWindowsObject('CIS_klient/CU_Podania/Button_VyhladatEXS'))

Windows.setText(findWindowsObject('CIS_klient/CU_vyvozu/Filter_LRN'), LRN)

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_VyhladatFilter'))

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_OtvoritDetail'))

Windows.delay(5)

String stavPCV = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_Podania/stavPCV'))

String MRN = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_Podania/MRN_PCV'))

if (!(stavPCV.equals('Registrované'))) {
    log.logFailed('Stav nevyhovuje' + stavPCV)

    util.markErrorAndStop('Stav nevyhovuje')
}

//Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/TabItem_HistoriaZmien'))

Windows.delay(2)

Windows.click(findWindowsObject('CIS_klient/CU_Podania/MenuItem_CUPodania'))

Windows.click(findWindowsObject('CIS_klient/CU_Podania/Button_ManualPredlozenieTovaru'))

Windows.setText(findWindowsObject('Object Repository/CIS_klient/CU_Podania/MRN_ManualnePredlozenie'), MRN)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/PotvrditZrusenieFinal')) //klik na Potvrdit

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/RezimEditacie'))

Windows.delay(2)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_Podania/ManualnePredlozitTovar'))

Windows.delay(2)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/ZatvoritBezUlozenia'))

Windows.delay(2)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/ZatvoritBezUlozenia'))

Windows.delay(2)

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_OtvoritDetail'))

Windows.delay(5)

stavPCV = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_Podania/stavPCV'))

if ((stavPCV.equals('Tovar pripravený na výstup')) || (stavPCV.equals('Tovar predložený'))) {
	log.logPassed('Stav ' + stavPCV)
} else {
	util.markErrorAndStop('Stav nevyhovuje ' + stavPCV)
}

Windows.delay(900)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/RezimEditacie'))

Windows.delay(2)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/MenuItem_AktualneCV'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_Podania/Button_PovolitOdchod'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/PotvrditZrusenieFinal')) //klik na Potvrdit

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_PotvrditZrusenie')) //potvrdit modal

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/ZatvoritBezUlozenia'))

Windows.delay(2)

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_OtvoritDetail'))

stavPCV = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_Podania/stavPCV'))

if (!(stavPCV.equals('Tovar uvoľnený na okamžitý výstup'))) {
	log.logFailed('Stav nevyhovuje' + stavPCV)
	util.markErrorAndStop('Stav nevyhovuje')
}

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/RezimEditacie'))

Windows.delay(2)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/MenuItem_AktualneCV'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_Podania/Button_PotvrditVystup'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/PotvrditZrusenieFinal')) //klik na Potvrdit

Date date = new Date()
String actualDate = date.format("dd")

Windows.setText(findWindowsObject('Object Repository/CIS_klient/CU_Podania/MRN_DatumVystupu'), actualDate)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_Podania/Button_PotvrditModalVystup')) //potvrdit modal

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_PotvrditZrusenie')) //potvrdit modal

stavPCV = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_Podania/stavPCV'))

if (!(stavPCV.equals('Vyvezené'))) {
	log.logFailed('Stav nevyhovuje' + stavPCV)
	util.markErrorAndStop('Stav nevyhovuje')
}

'Zavretie CIS'
Windows.closeApplication()

Windows.click(findWindowsObject('Object Repository/CIS_klient/ZavrietAplikaciu'))

