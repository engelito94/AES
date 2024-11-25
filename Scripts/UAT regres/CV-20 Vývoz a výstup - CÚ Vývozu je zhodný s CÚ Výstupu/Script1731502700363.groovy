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

String filePath = GlobalVariable.cestaSubor + 'CV-20 SK515.xml'

String filePathDialog = filePath.replaceAll('/', '\\\\')

xml.replaceLrnValue(filePath, LRN)

'CEP'
Windows.startApplication(GlobalVariable.cestaCEP)

cep.prihlasCEP(GlobalVariable.menoCEP, GlobalVariable.hesloCEP)
cep.vyberXMLDialog(filePathDialog)
cep.vyplnSKSpravu(LRN, 'SK515AES')

podpis.podpisSpravu()

Windows.switchToApplication()

Windows.closeApplication()

'Prepnutie na CIS'
Windows.startApplication(GlobalVariable.cestaCIS)

cis.prihlasCisKlient(GlobalVariable.menoCIS, GlobalVariable.hesloCIS, 'SK606900')

Windows.switchToWindowTitle('CIS klient - SK606900 (DTC_TEST)')

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/MenuItem_CUVyvozu'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Menu_VyhladatCV'))

Windows.setText(findWindowsObject('CIS_klient/CU_vyvozu/Filter_LRN'), LRN)

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_VyhladatFilter'))

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_OtvoritDetail'))

Windows.delay(5)

String datumPrijatia = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/DatumPrijatia'))

//String datumPrepustenia = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/DatumPrepustenia'))

String stavCV = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/StavCVNaCUVyvozuDohladu'))

String EC = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/EC'))

String MRN = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/MRN'))

if (datumPrijatia.isEmpty() || !(stavCV.equals('Prijaté'))) {
    log.logFailed('Operácie v hornej časti záznamu nie sú podľa očakávania. Stav CV je : ' + stavCV)
	util.markFailedAndStop("Operácie v hornej časti záznamu nie sú podľa očakávania.")
}

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/ZatvoritBezUlozenia'))

Windows.delay(300)

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_OtvoritDetail'))

Windows.delay(5)

stavCV = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/StavCVNaCUVyvozuDohladu'))

if (!(stavCV.equals('Tovar prepustený na vývoz'))) {
    log.logFailed('Stav CV nie je Tovar prepustený na vývoz. Stav CV je : ' + stavCV)
	util.markErrorAndStop("Stav CV nie je Tovar prepustený na vývoz.")
} else {
    log.logPassed('Stav CV je Tovar prepustený na vývoz')
}

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/ZatvoritBezUlozenia'))

'Kontrola AER'
Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vystupu/MenuItem_CUVystupu'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vystupu/Button_VyhladatAER'))

Windows.setText(findWindowsObject('Object Repository/CIS_klient/CU_vystupu/Filter_MRN'), MRN)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Button_VyhladatFilter'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Button_OtvoritDetail'))

Windows.delay(3)

String stavAER = Windows.getText(findWindowsObject('CIS_klient/CU_vystupu/Edit_AER_StavCV'))

if (stavAER.equals("Vytvorené")) {
	log.logInfo('Stav AER je :' + stavAER)
} else {
	log.logFailed("Stav AER nie je Vytvorené. Stav AER je : " + stavAER)
	util.markErrorAndStop("Stav AER nie je Vytvorené.")
}

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/ZatvoritBezUlozenia'))

'Prepnutie na CEP'
Windows.startApplication(GlobalVariable.cestaCEP)

//cesta k suboru na disku
filePath = (GlobalVariable.cestaSubor + 'CV-20 SK507.xml')

filePathDialog = filePath.replaceAll('/', '\\\\')

xml.replaceMRNDateTimeValues(filePathDialog, MRN)

cep.prihlasCEP(GlobalVariable.menoCEP, GlobalVariable.hesloCEP)
cep.vyberXMLDialog(filePathDialog)
cep.vyplnSKSpravu(LRN, 'SK507AES')

podpis.podpisSpravu()

Windows.switchToApplication()

cep.otvorHistoriuZasielok()
cep.skontrolujPrijatuSpravu('SK528AES', LRN)
cep.skontrolujPrijatuSpravu('SK529AES', LRN)

Windows.closeApplication()

'Prepnutie na CIS'
Windows.switchToWindowTitle('CIS klient - SK606900 (DTC_TEST)')

'Kontrola AER'
Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Button_OtvoritDetail'))

stavAER = Windows.getText(findWindowsObject('CIS_klient/CU_vystupu/Edit_AER_StavCV'))

if (stavAER.equals("Tovar predložený")) {
	log.logInfo('Stav AER je :' + stavAER)
} else {
	log.logFailed("Stav AER nie je Tovar predložený. Stav AER je : " + stavAER)
	util.markErrorAndStop("Stav AER nie je Tovar predložený.")
}

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/ZatvoritBezUlozenia'))

Windows.delay(300)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Button_OtvoritDetail'))

stavAER = Windows.getText(findWindowsObject('CIS_klient/CU_vystupu/Edit_AER_StavCV'))

if (stavAER.equals("Tovar uvoľnený na okamžitý výstup")) {
	log.logInfo('Stav AER je :' + stavAER)
} else {
	log.logFailed("Stav AER nie je Tovar uvoľnený na okamžitý výstup. Stav AER je : " + stavAER)
	util.markErrorAndStop("Stav AER nie je Tovar uvoľnený na okamžitý výstup.")
}

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/ZatvoritBezUlozenia'))

'Prepnutie na CEP'
Windows.startApplication(GlobalVariable.cestaCEP)

//cesta k suboru na disku
filePath = (GlobalVariable.cestaSubor + 'CV-20 SK590.xml')

filePathDialog = filePath.replaceAll('/', '\\\\')

xml.replaceMRNDateTimeValues(filePathDialog, MRN)

cep.prihlasCEP(GlobalVariable.menoCEP, GlobalVariable.hesloCEP)
cep.vyberXMLDialog(filePathDialog)
cep.vyplnSKSpravu(LRN, 'SK590AES')

podpis.podpisSpravu()

Windows.switchToApplication()

cep.otvorHistoriuZasielok()
cep.skontrolujPrijatuSpravu('SK525AES', LRN)

Windows.closeApplication()

'Prepnutie na CIS'
Windows.switchToWindowTitle('CIS klient - SK606900 (DTC_TEST)')

'Kontrola AER'
Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Button_OtvoritDetail'))

stavAER = Windows.getText(findWindowsObject('CIS_klient/CU_vystupu/Edit_AER_StavCV'))

if (stavAER.equals("Vyvezené")) {
	log.logInfo('Stav AER je :' + stavAER)
} else {
	log.logFailed("Stav AER nie je Vyvezené. Stav AER je : " + stavAER)
	util.markFailedAndStop("Stav AER nie je Vyvezené.")
}

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/ZatvoritBezUlozenia'))

'Kontrola CV'
Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/MenuItem_CUVyvozu'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Menu_VyhladatCV'))

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_OtvoritDetail'))

Windows.delay(5)

stavCV = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/StavCVNaCUVyvozuDohladu'))

if (!(stavCV.equals('Výstup potvrdený'))) {
	log.logFailed('Stav CV nie je Výstup potvrdený. Stav CV je : ' + stavCV)
	util.markErrorAndStop("Stav CV nie je Výstup potvrdený.")
} else {
	log.logPassed('Stav CV je Výstup potvrdený')
}

'Zavretie CIS'
Windows.closeApplication()

Windows.click(findWindowsObject('Object Repository/CIS_klient/ZavrietAplikaciu'))

'Prepnutie na CEP kontrola SK599'
Windows.startApplication(GlobalVariable.cestaCEP)

cep.prihlasCEP(GlobalVariable.menoCEP, GlobalVariable.hesloCEP)
cep.otvorHistoriuZasielok()
cep.skontrolujPrijatuSpravu('SK599AES', LRN)

Windows.closeApplication()

