import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import static org.mockito.Mockito.RETURNS_DEFAULTS

import org.eclipse.osgi.framework.util.FilePath
import org.openqa.selenium.Keys as Keys

Podpisovac podpis = new Podpisovac()

XMLGenerator xml = new XMLGenerator()

def LRN = "SB244489_v793910"

String filePath = GlobalVariable.cestaSubor + 'SK515_01.xml'

String filePathDialog = filePath.replaceAll("/", "\\\\\\\\")

xml.replaceLrnValue(filePath, LRN)

Windows.startApplication(GlobalVariable.cestaCEP)

Windows.click(findWindowsObject('CEP/Meno a heslo'))

Windows.waitForElementPresent(findWindowsObject('CEP/prihlMeno'), 30)

Windows.setText(findWindowsObject('CEP/prihlMeno'), GlobalVariable.menoCEP)

Windows.setEncryptedText(findWindowsObject('CEP/prihlHeslo'), GlobalVariable.hesloCEP)

Windows.click(findWindowsObject('CEP/prihlButton'))

WebUI.delay(3)

Windows.waitForElementNotPresent(findWindowsObject('CEP/prihlButton'), 0)

Windows.click(findWindowsObject('CEP/EKR2'))

Windows.click(findWindowsObject('CEP/Odoslať podanie EKR2'))

Windows.click(findWindowsObject('CEP/Jednoduche podanie'))

Windows.sendKeys(findWindowsObject('CEP/typSpravyEdit'), 'SK515AES')

Windows.sendKeys(findWindowsObject('CEP/typSpravyEdit'), Keys.chord(Keys.ENTER))

'Vybratie cesty suboru'
Windows.click(findWindowsObject('CEP/XMLButton'))

Windows.click(findWindowsObject('CEP_Dialog/DialogFileName'))

Windows.setText(findWindowsObject('CEP_Dialog/DialogFileName'), filePathDialog) //'C:\\Users\\barcik\\Desktop\\CEP XML\\AES\\Ditec xml\\SK515_01.xml')

Windows.sendKeys(findWindowsObject('CEP_Dialog/DialogFileName'), Keys.chord(Keys.ENTER))

Windows.setText(findWindowsObject('CEP/LRNEdit'), LRN)

Windows.setText(findWindowsObject('CEP/SenderIDEdit'), 'ico://sk/43771531')

Windows.click(findWindowsObject('CEP/PodpisatOdoslat'))

Windows.switchToDesktop()

'Podpisovanie cep'
podpis.podpisSpravu()

Windows.delay(2)

Windows.switchToApplication()

Windows.closeApplication()

'Prepnutie na CIS klienta'
Windows.startApplicationWithTitle(GlobalVariable.cestaCIS, 
    '')

Windows.setText(findWindowsObject('Object Repository/CIS_klient/Prihlasovanie/pouz_meno'), GlobalVariable.menoCIS)

Windows.setEncryptedText(findWindowsObject('Object Repository/CIS_klient/Prihlasovanie/heslo'), GlobalVariable.hesloCIS)

Windows.setText(findWindowsObject('Object Repository/CIS_klient/Prihlasovanie/domena'), 'DITEC')

Windows.click(findWindowsObject('Object Repository/CIS_klient/Prihlasovanie/colny_urad_dropdown'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/Prihlasovanie/CU_607600'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/Prihlasovanie/Prihlasit_button'))

Windows.click(findWindowsObject('CIS_klient/Prihlasovanie/OK_button'), FailureHandling.STOP_ON_FAILURE)

Windows.switchToWindowTitle('CIS klient - SK607600 (DTC_TEST)')

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/MenuItem_CUVyvozu'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Menu_VyhladatCV'))

Windows.setText(findWindowsObject('CIS_klient/CU_vyvozu/Filter_LRN'), LRN)

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_VyhladatFilter'))

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/Button_OtvoritDetail'))

Windows.delay(5)

EC = Windows.getText(findWindowsObject('CIS_klient/CU_vyvozu/VyhladaneCV/EC'))

MRN = Windows.getText(findWindowsObject('CIS_klient/CU_vyvozu/VyhladaneCV/MRN'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/RezimEditacie'))

Windows.delay(2)

Windows.switchToApplication()

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Menu_AktualneCV'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Menu_ZrusitZamietnut'))

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/VyhladaneCV/Button_PotvrditZrusenie'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Dropdown_KodDovoduZrusenia'))

Windows.doubleClick(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/ListItem_ZrusenieKod2'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Dropdown_DovodZrusenia'))

Windows.setText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/TextArea_DovodZrusenia'), 'test zrusenia')

Windows.doubleClick(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_OKDovodZrusenia'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/VyhladaneCV/Button_ZrusitCV'))

Windows.click(findWindowsObject('CIS_klient/CU_vyvozu/VyhladaneCV/PotvrditZrusenieFinal'))

Windows.closeApplication()

Windows.click(findWindowsObject('CIS_klient/ZavrietAplikaciu'))

