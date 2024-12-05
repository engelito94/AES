import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.model.FailureHandling.OPTIONAL
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

String filePath = GlobalVariable.cestaSubor + 'OPP13_SK515.xml'

String filePathDialog = filePath.replaceAll('/', '\\\\')

xml.replaceLrnValue(filePath, LRN)

'CEP'
Windows.startApplication(GlobalVariable.cestaCEP)

cep.prihlasCEP('barcikLP', 'FEsOd8O35eSoR2c+MauzCg==')
cep.vyberXMLDialog(filePathDialog)
cep.vyplnSKSpravu(LRN, 'SK515AES')

podpis.podpisSpravu()

Windows.switchToApplication()

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

//String datumPrijatia = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/DatumPrijatia'))

//String datumPrepustenia = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/DatumPrepustenia'))

String stavCV = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/StavCVNaCUVyvozuDohladu'))

String EC = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/EC'))

String MRN = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/MRN'))

if (!(stavCV.equals('Prijaté'))) {
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

Windows.delay(1)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vystupu/MenuItem_CUVystupu'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vystupu/Button_ManualnePredlozenie'))

Windows.setText(findWindowsObject('Object Repository/CIS_klient/CU_Podania/MRN_ManualnePredlozenie'), MRN)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/PotvrditZrusenieFinal')) //klik na Potvrdit

Windows.delay(2)

//Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_PotvrditZrusenie')) // potvrdit modal

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/RezimEditacie'))

Windows.delay(2)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_Podania/ManualnePredlozitTovar'))

Windows.delay(1)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/RezimEditacie'))

Windows.delay(2)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vystupu/Menu_AktualneAER'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vystupu/MenuItem_PovolenieOdchodu'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_PotvrditZrusenie')) // potvrdit modal ANO

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vystupu/Button_YES'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_PotvrditZrusenie')) // potvrdit modal Potvrdit

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/RezimEditacie'))

Windows.delay(2)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vystupu/Menu_AktualneAER'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vystupu/MenuItem_PotvrditVystup'))

Date date = new Date()

String datumDen = date.format('dd')

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vystupu/Edit_DatumVystupu'))

Windows.sendKeys(findWindowsObject('Object Repository/CIS_klient/CU_vystupu/Edit_DatumVystupu'), datumDen)

Windows.sendKeys(findWindowsObject('Object Repository/CIS_klient/CU_vystupu/Edit_DatumVystupu'), Keys.chord(Keys.ENTER))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vystupu/Button_PotvrditVystup'))

Windows.delay(5)

stavAER = Windows.getText(findWindowsObject('CIS_klient/CU_vystupu/Edit_AER_StavCV'))

if (stavAER.equals("Vyvezené")) {
	log.logInfo('Stav AER je :' + stavAER)
} else {
	log.logFailed("Stav AER nie je Vyvezené. Stav AER je : " + stavAER)
	util.markFailedAndStop("Stav AER nie je Vyvezené.")
}

'Prepnutie na CEP'
Windows.startApplication(GlobalVariable.cestaCEP)

//cesta k suboru na disku
filePath = (GlobalVariable.cestaSubor + 'OPP13_SK513.xml')

filePathDialog = filePath.replaceAll('/', '\\\\')

xml.replaceValues(filePathDialog, LRN, MRN, EC)

cep.prihlasCEP('barcikLP', 'FEsOd8O35eSoR2c+MauzCg==')
cep.vyberXMLDialog(filePathDialog)
cep.vyplnSKSpravu(LRN, 'SK513AES')

podpis.podpisSpravu()

Windows.switchToApplication()

Windows.closeApplication()

'Prepnutie na CIS'
Windows.switchToWindowTitle('CIS klient - SK517700 (DTC_TEST)')

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/MenuItem_CUVyvozu'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Menu_VyhladatCV'))

Windows.setText(findWindowsObject('CIS_klient/CU_vyvozu/Filter_LRN'), LRN)

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_VyhladatFilter'))

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_OtvoritDetail'))

stavCV = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/StavCVNaCUVyvozuDohladu'))

if (!(stavCV.equals('Výstup potvrdený'))) {
	log.logFailed('Stav CV nie je Výstup potvrdený. Stav CV je : ' + stavCV)
	util.markErrorAndStop("Stav CV nie je Výstup potvrdený.")
} else {
	log.logPassed('Stav CV je Výstup potvrdený')
}

Windows.delay(5)

int pocitadlo = 0

while (pocitadlo < 30 & Windows.verifyElementNotPresent(findWindowsObject('Object Repository/CIS_klient/CU_vystupu/Button__SchvalitOPP'), 5, FailureHandling.OPTIONAL)) {
	Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Aktualizovat'))
	Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vystupu/Button_YES'))
	Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vystupu/Button__PosuditNavrhPoPrepusteni'))
	Windows.delay(10)
	pocitadlo++
}

if (pocitadlo == 30) {
	util.markFailedAndStop("Tlačidlo posúdiť návrh po prepustení je Disabled")
}

Windows.delay(30)

Windows.verifyElementPresent(findWindowsObject('Object Repository/CIS_klient/CU_vystupu/Button__SchvalitOPP'), 30)
Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vystupu/Button__SchvalitOPP'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_PotvrditZrusenie')) // potvrdit modal Potvrdit

Windows.delay(2)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/ComboBox_OpravaPrepustenie'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/ListView_VyberOpravuPoPrepusteni'))

String pocetPoloziek = Windows.getText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/TabItem_tovarovePolozkyPocet'))

if (pocetPoloziek.contains("1/1")) {
	log.logPassed("CV obsahuje 1 položku")
}

'Zavretie CIS'
Windows.closeApplication()

Windows.click(findWindowsObject('Object Repository/CIS_klient/ZavrietAplikaciu'))

'Prepnutie na CEP kontrola SK599'
Windows.startApplication(GlobalVariable.cestaCEP)

cep.prihlasCEP('barcikLP', 'FEsOd8O35eSoR2c+MauzCg==')
cep.otvorHistoriuZasielok()
cep.skontrolujPrijatuSpravu('SK599AES', LRN)
cep.skontrolujPrijatuSpravu('SK504AES', LRN)

Windows.closeApplication()