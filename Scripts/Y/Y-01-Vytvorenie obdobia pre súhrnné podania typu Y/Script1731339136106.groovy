import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.logging.KeywordLogger
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

KeywordUtil util = new KeywordUtil()

KeywordLogger log = new KeywordLogger()

CISKlient cis = new CISKlient()

'Prepnutie na CIS'
Windows.startApplication(GlobalVariable.cestaCIS)

cis.prihlasCisKlient(GlobalVariable.menoCIS, GlobalVariable.hesloCIS, 'SK517700')

Windows.switchToWindowTitle('CIS klient - SK517700 (DTC_TEST)')

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/MenuItem_CUVyvozu'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Menu_EditovatObnovenieObdobiaCVY'))

Windows.delay(2)

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Button_EORI'))

Windows.setText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/EORI_filter'), "SK1010101010")

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Button_VyhladatEORI'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Button_VybratEORI'))

Windows.sendKeys(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Edit_CisloPolozkyEORI'), "SKSDEmiros")

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Button_VyhladatEORI'))

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Button_NovyZaznamEORI'))

Windows.setText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Button_PovolenieLehota'), '10')

Windows.sendKeys(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Button_PovolenieObdobie'), 'po')

Windows.setText(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Button_PovolenieDlzka'), '1')

Windows.click(findWindowsObject('Object Repository/CIS_klient/CU_vyvozu/Button_PovolenieUlozit'))
