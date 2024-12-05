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

String filePath = GlobalVariable.cestaSubor + 'CV-19 SK515 2024824_OpravaCV.xml'

String filePathDialog = filePath.replaceAll('/', '\\\\')

xml.replaceLrnValue(filePath, LRN)

'CEP'
Windows.startApplication(GlobalVariable.cestaCEP)

cep.prihlasCEP('barcikLP', 'FEsOd8O35eSoR2c+MauzCg==')
cep.vyberXMLDialog(filePathDialog)
cep.vyplnSKSpravu(LRN, 'SK515AES')

podpis.podpisSpravu()

Windows.switchToApplication()

cep.otvorHistoriuZasielok()
cep.skontrolujPrijatuSpravu('SK528AES', LRN)

Windows.closeApplication()

'Prepnutie na CIS'
Windows.startApplication(GlobalVariable.cestaCIS)

cis.prihlasCisKlient('barcik', 'MswRUieWN0roLhNOfsIdHQ==', 'SK517700')

Windows.switchToWindowTitle('CIS klient - SK517700 (DTC_TEST)')

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/MenuItem_CUVyvozu'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Menu_VyhladatCV'))

Windows.setText(findWindowsObject('CIS_klient/CU_vyvozu/Filter_LRN'), LRN)

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_VyhladatFilter'))

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_OtvoritDetail'))

Windows.delay(5)

String datumPrijatia = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/DatumPrijatia'))

String stavCV = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/StavCVNaCUVyvozuDohladu'))

String EC = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/EC'))

String MRN = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/MRN'))

if (datumPrijatia.isEmpty() || !(stavCV.equals('Prijaté'))) {
    log.logFailed('Operácie v hornej časti záznamu nie sú podľa očakávania. Stav CV je : ' + stavCV)
}

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/ZatvoritBezUlozenia'))

'Prepnutie na CEP'
Windows.startApplication(GlobalVariable.cestaCEP)

//cesta k suboru na disku
filePath = (GlobalVariable.cestaSubor + 'CV-19 LS_2024824_OpravaCV1_sk513.xml')

filePathDialog = filePath.replaceAll('/', '\\\\')

xml.replaceValues(filePath, LRN, MRN, EC)

cep.prihlasCEP('barcikLP', 'FEsOd8O35eSoR2c+MauzCg==')
cep.vyberXMLDialog(filePathDialog)
cep.vyplnSKSpravu(LRN, 'SK513AES')

podpis.podpisSpravu()

Windows.switchToApplication()

cep.otvorHistoriuZasielok()
cep.skontrolujPrijatuSpravu('SK556AES', LRN)

Windows.closeApplication()

'Prepnutie na CIS'
Windows.switchToWindowTitle('CIS klient - SK517700 (DTC_TEST)')

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_OtvoritDetail'))

Windows.delay(5)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/TabItem_ElektronickaKomunikaciaSK - Copy'))


'Zavretie CIS'
Windows.closeApplication()

Windows.click(findWindowsObject('Object Repository/CIS_klient/ZavrietAplikaciu'))


