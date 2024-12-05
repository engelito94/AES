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

String filePath = GlobalVariable.cestaSubor + 'CV-14 Podanie zjednodušeného CV typu C (na základe povolenia SDE) - SK515 - DT-inTP.xml'

String filePathDialog = filePath.replaceAll('/', '\\\\')

xml.replaceLrnValue(filePath, LRN)

'CEP'
Windows.startApplication('C:\\Program Files (x86)\\Ditec\\Ditec.Cep.Ekr.Interface\\Cep.Ekr.App.exe')

cep.prihlasCEP('barcikLP', 'FEsOd8O35eSoR2c+MauzCg==')

cep.vyberXMLDialog(filePathDialog)

cep.vyplnSKSpravu(LRN, 'SK515AES')

podpis.podpisSpravu()

Windows.switchToApplication()

Windows.closeApplication()

'Prepnutie na CIS'
Windows.startApplication('C:\\Users\\barcik\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\CIS klient(DTC_TEST).lnk')

cis.prihlasCisKlient('barcik', 'MswRUieWN0roLhNOfsIdHQ==', 'SK607600')

Windows.switchToWindowTitle('CIS klient - SK607600 (DTC_TEST)')

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/MenuItem_CUVyvozu'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Menu_VyhladatCV'))

Windows.setText(findWindowsObject('CIS_klient/CU_vyvozu/Filter_LRN'), LRN)

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_VyhladatFilter'))

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_OtvoritDetail'))

Windows.delay(5)

String datumPrijatia = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/DatumPrijatia'))

String stavCV = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/StavCVNaCUVyvozuDohladu'))

if (datumPrijatia.isEmpty() || !(stavCV.equals('Prijaté'))) {
    log.logFailed('Operácie v hornej časti záznamu nie sú podľa očakávania')
	util.markErrorAndStop("Posledný krok zlyhal")
}

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/ZatvoritBezUlozenia'))

Windows.delay(250)

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_OtvoritDetail'))

String datumPrepustenia = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/DatumPrepustenia'))
stavCV = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/StavCVNaCUVyvozuDohladu'))

if (datumPrepustenia.isEmpty() || !(stavCV.equals('Tovar prepustený na vývoz'))) {
	log.logFailed('Operácie v hornej časti záznamu nie sú podľa očakávania')
	util.markErrorAndStop("Posledný krok zlyhal")
}

'Zavretie CIS'
Windows.closeApplication()

Windows.click(findWindowsObject('Object Repository/CIS_klient/ZavrietAplikaciu'))

'Prepnutie na CEP'
Windows.startApplication('C:\\Program Files (x86)\\Ditec\\Ditec.Cep.Ekr.Interface\\Cep.Ekr.App.exe')

cep.prihlasCEP('barcikLP', 'FEsOd8O35eSoR2c+MauzCg==')
cep.otvorHistoriuZasielok()
cep.skontrolujPrijatuSpravu('SK528AES', LRN)
cep.skontrolujPrijatuSpravu('SK529AES', LRN)

Windows.closeApplication()
