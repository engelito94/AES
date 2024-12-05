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

String filePath = GlobalVariable.cestaSubor + 'PCV05 - 1. SK615EXS - automat. prepustenie + SK525 partially exited-ditec test.xml'

String filePathDialog = filePath.replaceAll('/', '\\\\')

xml.replaceLrnValue(filePath, LRN)

'CEP - SK615'
Windows.startApplication('C:\\Program Files (x86)\\Ditec\\Ditec.Cep.Ekr.Interface\\Cep.Ekr.App.exe')

cep.prihlasCEP('barcikLP', 'FEsOd8O35eSoR2c+MauzCg==')
cep.vyberXMLDialog(filePathDialog)
cep.vyplnSKSpravu(LRN, 'SK615EXS')

podpis.podpisSpravu()

Windows.switchToApplication()

cep.otvorHistoriuZasielok()
cep.skontrolujPrijatuSpravu('SK628EXS', LRN)

Windows.closeApplication()

'Prepnutie na CIS'
Windows.startApplication('C:\\Users\\barcik\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\CIS klient(DTC_TEST).lnk')

cis.prihlasCisKlient('barcik', 'MswRUieWN0roLhNOfsIdHQ==', 'SK606900')

Windows.switchToWindowTitle('CIS klient - SK606900 (DTC_TEST)')

Windows.click(findWindowsObject('CIS_klient/CU_Podania/MenuItem_CUPodania'))

Windows.click(findWindowsObject('CIS_klient/CU_Podania/Button_VyhladatEXS'))

Windows.setText(findWindowsObject('CIS_klient/CU_vyvozu/Filter_LRN'), LRN)

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_VyhladatFilter'))

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_OtvoritDetail'))

Windows.delay(5)

String stavPCV = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_Podania/stavPCV'))

String MRN = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_Podania/MRN_PCV'))

if ((stavPCV.equals('Tovar pripravený na výstup'))) {
	log.logPassed('Stav vyhovuje ' + stavPCV)
} else {
	util.markErrorAndStop('Stav nevyhovuje')
}

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/ZatvoritBezUlozenia'))

Windows.delay(300)

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_OtvoritDetail'))

Windows.delay(1)

stavPCV = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_Podania/stavPCV'))

if ((stavPCV.equals('Tovar držaný na skladovanie'))) {
	log.logPassed('Stav vyhovuje ' + stavPCV)
} else {
	util.markErrorAndStop('Stav nevyhovuje')
}

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/ZatvoritBezUlozenia'))

'Prepnutie na CEP - SK547'
filePath = GlobalVariable.cestaSubor + 'PCV05 - 2. SK547EXS - generuje SK548 + Activity 31 partially exited-ditec test.xml'

filePathDialog = filePath.replaceAll('/', '\\\\')

xml.replaceMRNDateTimeValues(filePath, MRN)

Windows.startApplication('C:\\Program Files (x86)\\Ditec\\Ditec.Cep.Ekr.Interface\\Cep.Ekr.App.exe')

cep.prihlasCEP('barcikLP', 'FEsOd8O35eSoR2c+MauzCg==')
cep.vyberXMLDialog(filePathDialog)
cep.vyplnSKSpravu(LRN, 'SK547EXS')

podpis.podpisSpravu()

Windows.switchToApplication()

cep.otvorHistoriuZasielok()
cep.skontrolujPrijatuSpravu('SK525EXS', LRN)
cep.skontrolujPrijatuSpravu('SK548EXS', LRN)

Windows.closeApplication()

'Prepnutie na CIS'
Windows.switchToWindowTitle('CIS klient - SK606900 (DTC_TEST)')

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_OtvoritDetail'))

Windows.delay(3)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/TabItem_ElektronickaKomunikaciaSK'))

Windows.delay(1)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_Podania/Aktualizovat_ElKomSK'))

Windows.doubleClick(findWindowsObject('Object Repository/CIS_klient/CU_Podania/Item_SK547'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_Podania/OtvoritUlozitSKSpravu'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_Podania/UlozitSpravuSK'))

filePath = GlobalVariable.cestaSubor + 'PCV05 - SK548.xml'

filePathDialog = filePath.replaceAll('/', '\\\\')

Windows.setText(findWindowsObject('Object Repository/CIS_klient/CIS_DIalog/FIleName'), filePathDialog)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CIS_DIalog/SaveButton'))

if (Windows.verifyElementPresent(findWindowsObject('Object Repository/CIS_klient/CIS_DIalog/ExistujuciSubor'), 0, FailureHandling.OPTIONAL)) {
	Windows.click(findWindowsObject('Object Repository/CIS_klient/CIS_DIalog/ExistujuciSubor'))
}

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_PotvrditZrusenie')) // potvrdit modal

//vyber manifest number
String manifestNUmber = xml.getManifestNumberSK548(filePath)

'Zavretie CIS'
Windows.closeApplication()

Windows.click(findWindowsObject('Object Repository/CIS_klient/ZavrietAplikaciu'))

'Prepnutie na CEP - SK590'
filePath = GlobalVariable.cestaSubor + 'PCV05 - 3. SK590EXS partially exited-ditec test.xml'

filePathDialog = filePath.replaceAll('/', '\\\\')

xml.replaceMRNDateTimeValues(filePath, MRN)
xml.replaceManifestNumber(filePath, manifestNUmber)

Windows.startApplication('C:\\Program Files (x86)\\Ditec\\Ditec.Cep.Ekr.Interface\\Cep.Ekr.App.exe')

cep.prihlasCEP('barcikLP', 'FEsOd8O35eSoR2c+MauzCg==')
cep.vyberXMLDialog(filePathDialog)
cep.vyplnSKSpravu(LRN, 'SK590EXS')

podpis.podpisSpravu()

Windows.switchToApplication()

'Poslanie dalsej SK547'
Windows.click(findWindowsObject('CEP/EKR2'))

Windows.click(findWindowsObject('CEP/Odoslať podanie EKR2'))

Windows.click(findWindowsObject('CEP/Jednoduche podanie'))

filePath = GlobalVariable.cestaSubor + 'PCV05 - 4. SK547EXS - generuje SK548 + Activity 31 partially-ditec test.xml'
filePathDialog = filePath.replaceAll('/', '\\\\')

xml.replaceMRNDateTimeValues(filePath, MRN)

cep.vyberXMLDialog(filePathDialog)
cep.vyplnSKSpravu(LRN, 'SK547EXS')

podpis.podpisSpravu()

Windows.switchToApplication()

cep.otvorHistoriuZasielok()
cep.skontrolujPrijatuSpravu('SK548EXS', LRN)

Windows.closeApplication()

'Prepnutie na CIS'
Windows.startApplication('C:\\Users\\barcik\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\CIS klient(DTC_TEST).lnk')

cis.prihlasCisKlient('barcik', 'MswRUieWN0roLhNOfsIdHQ==', 'SK606900')

Windows.switchToWindowTitle('CIS klient - SK606900 (DTC_TEST)')

Windows.click(findWindowsObject('CIS_klient/CU_Podania/MenuItem_CUPodania'))

Windows.click(findWindowsObject('CIS_klient/CU_Podania/Button_VyhladatEXS'))

Windows.setText(findWindowsObject('CIS_klient/CU_vyvozu/Filter_LRN'), LRN)

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_VyhladatFilter'))

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_OtvoritDetail'))

Windows.delay(5)

stavPCV = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_Podania/stavPCV'))

if (stavPCV.equals('Tovar čiastočné vyvezený')) {
	log.logPassed('Stav vyhovuje ' + stavPCV)
} else {
	util.markErrorAndStop('Stav nevyhovuje')
}

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/RezimEditacie'))

Windows.delay(2)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/MenuItem_AktualneCV'))

//tlacidlo potvrdit vystup je stale disabled, v tomto kroku konci
Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_Podania/Button_PotvrditVystup'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_Podania/Button_PotvrditModalVystup'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_PotvrditZrusenie')) // potvrdit modal

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/ZatvoritBezUlozenia'))

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_OtvoritDetail'))

Windows.delay(3)

stavPCV = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_Podania/stavPCV'))

if (stavPCV.equals('Vyvezené')) {
	log.logPassed('Stav vyhovuje ' + stavPCV)
} else {
	util.markErrorAndStop('Stav nevyhovuje')
}

'Zavretie CIS'
Windows.closeApplication()

Windows.click(findWindowsObject('Object Repository/CIS_klient/ZavrietAplikaciu'))