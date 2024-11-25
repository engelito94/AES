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

String filePath = GlobalVariable.cestaSubor + 'PCV02 -SK615EXS - generuje CD601C a SK628EXS EXS.State = 1 - UAT2 - DTC test.xml'

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

cis.prihlasCisKlient(GlobalVariable.menoCIS, GlobalVariable.hesloCIS, 'SK606900')

Windows.switchToWindowTitle('CIS klient - SK606900 (DTC_TEST)')

Windows.click(findWindowsObject('CIS_klient/CU_Podania/MenuItem_CUPodania'))

Windows.click(findWindowsObject('CIS_klient/CU_Podania/Button_VyhladatEXS'))

Windows.setText(findWindowsObject('CIS_klient/CU_vyvozu/Filter_LRN'), LRN)

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_VyhladatFilter'))

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_OtvoritDetail'))

Windows.delay(5)

String stavPCV = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_Podania/stavPCV'))

String MRN = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_Podania/MRN_PCV'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/ZatvoritBezUlozenia'))

'Prepnutie na CEP'
filePath = GlobalVariable.cestaSubor + 'PCV02 - SK607EXS -UAT2 - DTC test.xml'

filePathDialog = filePath.replaceAll('/', '\\\\')

xml.replaceMRNDateTimeValues(filePath, MRN)

Windows.startApplication(GlobalVariable.cestaCEP)

cep.prihlasCEP(GlobalVariable.menoCEP, GlobalVariable.hesloCEP)
cep.vyberXMLDialog(filePathDialog)
cep.vyplnSKSpravu(LRN, 'SK607EXS')

podpis.podpisSpravu()

Windows.switchToApplication()

Windows.closeApplication()

'Prepnutie na CIS'
Windows.switchToWindowTitle('CIS klient - SK606900 (DTC_TEST)')

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_OtvoritDetail'))

Windows.delay(5)

stavPCV = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_Podania/stavPCV'))

if (!(stavPCV.equals('Tovar pripravený na výstup'))) {
	log.logFailed('Stav nevyhovuje ' + stavPCV)
	util.markErrorAndStop('Stav nevyhovuje')
}

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/RezimEditacie'))

Windows.delay(2)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/MenuItem_AktualneCV'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_Podania/Button_RozhodnutKontrolaPCV'))

Windows.doubleClick(findWindowsObject('Object Repository/CIS_klient/CU_Podania/Edit_StavKontroly'))
Windows.sendKeys(findWindowsObject('Object Repository/CIS_klient/CU_Podania/Edit_StavKontroly'), 'u')

Windows.doubleClick(findWindowsObject('Object Repository/CIS_klient/CU_Podania/Edit_VysledokKontroly'))
Windows.sendKeys(findWindowsObject('Object Repository/CIS_klient/CU_Podania/Edit_VysledokKontroly'), 'v')

Windows.doubleClick(findWindowsObject('Object Repository/CIS_klient/CU_Podania/Edit_SposobVykonaniaKontroly'))
Windows.sendKeys(findWindowsObject('Object Repository/CIS_klient/CU_Podania/Edit_SposobVykonaniaKontroly'), '4')

Windows.setText(findWindowsObject('Object Repository/CIS_klient/CU_Podania/Edit_NalezKontroly'), 'test')

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/PotvrditZrusenieFinal')) //klik na potvrdit

'Podpis SK561EXS'
Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_SpustitPodpisovanie'))

podpis.podpisSpravu()

Windows.switchToApplication()

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_SpustitOverenie'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_OKPodpisovanie'))

log.logInfo('SK561 Podpis OK')

Windows.delay(2)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/ZatvoritBezUlozenia'))

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_OtvoritDetail'))

Windows.delay(5)

stavPCV = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_Podania/stavPCV'))

if (!(stavPCV.equals('Kontrolované'))) {
	log.logFailed('Stav nevyhovuje ' + stavPCV)
	util.markErrorAndStop('Stav nevyhovuje')
}

Windows.delay(900)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/RezimEditacie'))

Windows.delay(2)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/MenuItem_AktualneCV'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_Podania/Button_PovolitOdchod')) //klik na prepustit/povolit odchod PCV

'Podpis SK525EXS'
Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_SpustitPodpisovanie'))

podpis.podpisSpravu()

Windows.switchToApplication()

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_SpustitOverenie'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_OKPodpisovanie'))

log.logInfo('SK561 Podpis OK')

//Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/PotvrditZrusenieFinal')) //klik na Potvrdit

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_PotvrditZrusenie')) //potvrdit modal

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/ZatvoritBezUlozenia'))

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_OtvoritDetail'))

Windows.delay(5)

stavPCV = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_Podania/stavPCV'))

if (!(stavPCV.equals('Tovar uvoľnený na okamžitý výstup'))) {
	log.logFailed('Stav nevyhovuje ' + stavPCV)
	util.markErrorAndStop('Stav nevyhovuje')
}

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/ZatvoritBezUlozenia'))

'Prepnutie na CEP'
filePath = GlobalVariable.cestaSubor + 'PCV02 - SK590EXS - UAT2 - DTC test.xml'

filePathDialog = filePath.replaceAll('/', '\\\\')

xml.replaceMRNDateTimeValues(filePath, MRN)

Windows.startApplication(GlobalVariable.cestaCEP)

cep.prihlasCEP(GlobalVariable.menoCEP, GlobalVariable.hesloCEP)
cep.vyberXMLDialog(filePathDialog)
cep.vyplnSKSpravu(LRN, 'SK590EXS')

podpis.podpisSpravu()

Windows.switchToApplication()

Windows.closeApplication()

'Prepnutie na CIS'
Windows.switchToWindowTitle('CIS klient - SK606900 (DTC_TEST)')

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_OtvoritDetail'))

Windows.delay(5)

stavPCV = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_Podania/stavPCV'))

if (!(stavPCV.equals('Vyvezené'))) {
	log.logFailed('Stav nevyhovuje ' + stavPCV)
	util.markErrorAndStop('Stav nevyhovuje')
}

'Zavretie CIS'
Windows.closeApplication()

Windows.click(findWindowsObject('Object Repository/CIS_klient/ZavrietAplikaciu'))