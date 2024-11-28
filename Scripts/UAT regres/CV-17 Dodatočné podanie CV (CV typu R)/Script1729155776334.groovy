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

//cesta k suboru na disku
String filePath = GlobalVariable.cestaSubor + 'SK515_R.xml'

String filePathDialog = filePath.replaceAll('/', '\\\\')

xml.replaceLrnValue(filePath, LRN)

'CEP'
Windows.startApplicationWithTitle(GlobalVariable.cestaCEP, 'CEP')

cep.prihlasCEP(GlobalVariable.menoCEP, GlobalVariable.hesloCEP)

cep.vyberXMLDialog(filePathDialog)

cep.vyplnSKSpravu(LRN, 'SK515AES')

podpis.podpisSpravu()

Windows.switchToApplication()

cep.otvorHistoriuZasielok()

cep.skontrolujPrijatuSpravu('SK516AES', LRN)

Windows.closeApplication()

'Prepnutie na CISKlient'
Windows.startApplicationWithTitle(GlobalVariable.cestaCIS, 
    'CIS')

cis.prihlasCisKlient(GlobalVariable.menoCIS, GlobalVariable.hesloCIS, 'SK607600')

Windows.switchToWindowTitle('CIS klient - SK607600 (DTC_TEST)')

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/MenuItem_CUVyvozu'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Menu_VyhladatCV'))

Windows.setText(findWindowsObject('CIS_klient/CU_vyvozu/Filter_LRN'), LRN)

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_VyhladatFilter'))

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_OtvoritDetail'))

Windows.delay(5)

//EC = Windows.getText(findWindowsObject('CIS_klient/CU_vyvozu/VyhladaneCV/EC'))
//MRN = Windows.getText(findWindowsObject('CIS_klient/CU_vyvozu/VyhladaneCV/MRN'))
Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/RezimEditacie'))

Windows.delay(2)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/MenuItem_AktualneCV'))

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/VyhladaneCV/Button_SpracovanieCVTypuR'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_PrijatTypR'))

'Podpis SK528'
if (Windows.verifyElementPresent(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_SpustitPodpisovanie'), 10, FailureHandling.OPTIONAL)) {
	Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_SpustitPodpisovanie'))
	
	podpis.podpisSpravu()
	
	Windows.switchToApplication()
	
	Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_SpustitOverenie'))
	
	Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_OKPodpisovanie'))
	
	log.logInfo('SK528 Podpis OK')
}

Windows.delay(2)

'Podpis SK529'
if (Windows.verifyElementPresent(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_SpustitPodpisovanie'), 10, FailureHandling.OPTIONAL)) {
	Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_SpustitPodpisovanie'))
	
	podpis.podpisSpravu()
	
	Windows.switchToApplication()
	
	Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_SpustitOverenie'))
	
	Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_OKPodpisovanie'))
	
	log.logInfo('SK529 Podpis OK')
}

Windows.delay(2)

'Podpis SK599'
if (Windows.verifyElementPresent(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_SpustitPodpisovanie'), 10, FailureHandling.OPTIONAL)) {
	Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_SpustitPodpisovanie'))
	
	podpis.podpisSpravu()
	
	Windows.switchToApplication()
	
	Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_SpustitOverenie'))
	
	Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_OKPodpisovanie'))
	
	log.logInfo('SK599 Podpis OK')
}

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_PotvrditSpracovanieR'))

String datumPrijatia = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/DatumPrijatia'))

String datumPrepustenia = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/DatumPrepustenia'))

String stavCV = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/StavCVNaCUVyvozuDohladu'))

if ((datumPrijatia.isEmpty() || datumPrepustenia.isEmpty()) || !(stavCV.equals('Výstup potvrdený'))) {
    log.logFailed('Operácie v hornej časti záznamu nie sú podľa očakávania')

    util.markFailedAndStop('Operácie v hornej časti záznamu nie sú podľa očakávania')
}

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/TabItem_HistoriaZmien'))

Windows.delay(5)

Windows.closeApplication()

Windows.click(findWindowsObject('Object Repository/CIS_klient/ZavrietAplikaciu'))

'Prepnutie na CEP'
Windows.startApplicationWithTitle(GlobalVariable.cestaCEP, 'CEP')

cep.prihlasCEP(GlobalVariable.menoCEP, GlobalVariable.hesloCEP)

cep.otvorHistoriuZasielok()

cep.skontrolujPrijatuSpravu('SK528AES', LRN)

cep.skontrolujPrijatuSpravu('SK529AES', LRN)

cep.skontrolujPrijatuSpravu('SK599AES', LRN)

Windows.closeApplication()

