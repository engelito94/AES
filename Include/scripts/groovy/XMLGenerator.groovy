import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.osgi.framework.util.FilePath

import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.logging.KeywordLogger

import java.io.File;
import java.nio.file.Files
import java.nio.file.StandardOpenOption


import java.util.zip.*;

import groovy.xml.XmlParser
import groovy.xml.XmlUtil
import groovy.xml.XmlSlurper

import java.nio.file.Path
import java.nio.file.Paths
import com.kms.katalon.core.configuration.RunConfiguration


public class XMLGenerator {

	//prepise existujuce LRN novym a ulozi do rovnakeho suboru
	def replaceLrnValue(String filePath, String newValue) {
		def LRN = newValue
		def file = new File(filePath)
		def fileText = file.text
		def pattern = /<LRN>(.*?)<\/LRN>/
		def newContent = fileText.replaceAll(pattern, "<LRN>"+LRN+"</LRN>")
		file.write(newContent)
	}

	//"nahodne" vygeneruje nove LRN
	public static String generateLRN() {
		KeywordLogger log = new KeywordLogger()
		Random random = new Random();
		int nahodneCislo1 = random.nextInt(9999);
		int nahodneCislo2 = random.nextInt(999999);
		//retazec obsahuje vzdy "SB24" a "_", zvysok sa generuje - moze sa nahradit/pridat lubovolny retazec
		String noveLRN = "SB24" + nahodneCislo1 + "_" + nahodneCislo2;
		
		def projectDir = RunConfiguration.getProjectDir()
		Path projectPath = Paths.get(projectDir)
		Path subor = projectPath.resolve('LRN.txt')
		
		def valueToAppend = noveLRN
		
		//Kontrola duplicity LRN
		// Open the file for reading
		def reader = new FileReader(subor.toString())
		
		try {
		  // Read the entire content of the file into a string
		  def textFileContent = reader.text
		
		  // Check if the base value "LRN" exists
		  def valueFound = textFileContent.contains(valueToAppend)
		  while (valueFound) {
			  nahodneCislo1 = random.nextInt(9999);
			  nahodneCislo2 = random.nextInt(999999);
			  noveLRN = "SB24" + nahodneCislo1 + "_" + nahodneCislo2;
			  valueToAppend = noveLRN
			  valueFound = textFileContent.contains(valueToAppend)
		  }
		  // Close the reader before opening for writing
		} finally {
		  reader.close()
		}

		//String filePathDialog = filePath.replaceAll('/', '\\\\')
		// Open the file for appending
		def writer = new FileWriter(subor.toString(), true)
		
		try {
		  // Append the value to the file
		  writer.write(noveLRN + System.getProperty('line.separator'))
		} finally {
		  writer.close()
		}
		
		log.logInfoCenter(noveLRN)
		
		return noveLRN;
	}

	//nahradi MRNL,LRN a EC v subore, hodnotami ziskanymi z CIS klienta, prepise datumy na aktualny - ak existuju
	def replaceValues(String filePath, String LRN, String MRN, String EC) {
		Date date = new Date()
		String datePart = date.format("yyyy-MM-dd")
		
		def file = new File(filePath)
		def fileText = file.text
		
		def pattern = /<LRN>(.*?)<\/LRN>/
		def newContent = fileText.replaceAll(pattern, "<LRN>"+LRN+"</LRN>")
		pattern = /<MRN>(.*?)<\/MRN>/
		if (pattern.size() > 0) {
			newContent = newContent.replaceAll(pattern, "<MRN>"+MRN+"</MRN>")
		}
		pattern = /<SADRegistrationCode>(.*?)<\/SADRegistrationCode>/
		if (pattern.size() > 0) {
			newContent = newContent.replaceAll(pattern, "<SADRegistrationCode>"+EC+"</SADRegistrationCode>")
		}
		pattern = /<exitDate>(.*?)<\/exitDate>/
		if (pattern.size() > 0) {
			newContent = newContent.replaceAll(pattern, "<exitDate>"+datePart+"</exitDate>")
		}
		pattern = /<acceptDate>(.*?)<\/acceptDate>/
		if (pattern.size() > 0) {
			newContent = newContent.replaceAll(pattern, "<acceptDate>"+datePart+"</acceptDate>")
		}
		file.write(newContent)
	}

	def replaceMRNDateTimeValues(String filePath, String MRN) {
		Date date = new Date()
		String datePart = date.format("yyyy-MM-dd")
		String timePart = date.format("HH:mm:ss")
		String dateTIme = datePart + "T" + timePart

		Random random = new Random();
		int nahodneCislo = random.nextInt(9999);

		def file = new File(filePath)
		def fileText = file.text

		def pattern = /<MRN>(.*?)<\/MRN>/
		def newContent = fileText.replaceAll(pattern, "<MRN>"+MRN+"</MRN>")
		pattern = /<arrivalNotificationDateAndTime>(.*?)<\/arrivalNotificationDateAndTime>/
		if (pattern.size() > 0) {
			newContent = newContent.replaceAll(pattern, "<arrivalNotificationDateAndTime>"+dateTIme+"</arrivalNotificationDateAndTime>")
		}

		pattern = /<manifestReferenceNumber>(.*?)<\/manifestReferenceNumber>/
		if (pattern.size() > 0) {
			newContent = newContent.replaceAll(pattern, "<manifestReferenceNumber>"+"AMB"+nahodneCislo.toString()+"</manifestReferenceNumber>")
		}

		pattern = /<exitDate>(.*?)<\/exitDate>/
		if (pattern.size() > 0) {
			newContent = newContent.replaceAll(pattern, "<exitDate>"+datePart+"</exitDate>")
		}

		file.write(newContent)
	}

	def replaceManifestNumber(String filePath, String number) {
		def file = new File(filePath)
		def fileText = file.text

		def pattern = /<manifestNumber>(.*?)<\/manifestNumber>/
		def newContent = fileText.replaceAll(pattern, "<manifestNumber>"+number+"</manifestNumber>")
		
		file.write(newContent)
	}

	def getManifestNumberSK548(String filePath) {
		def file = new File(filePath)
		def fileText = file.text

		def i1 = fileText.indexOf("<manifestNumber>")
		def i2 = fileText.indexOf("</manifestNumber>")

		def valueOfPattern = fileText.substring(i1+"<manifestNumber>".length(), i2)
		return valueOfPattern
	}
	
	def updateY(String filePath) {
		def xmlFile = new File(filePath)
		def xmlFileText = xmlFile.text
		def xml = new XmlParser().parse(xmlFile)
		
		def mrn1 = xml.GoodsShipment.GoodsItem[0].GoodsItemPreviousDocument[0].referenceNumber.text()
		def mrn2 = xml.GoodsShipment.GoodsItem[1].GoodsItemPreviousDocument[0].referenceNumber.text()
		def mrn3 = xml.GoodsShipment.GoodsItem[2].GoodsItemPreviousDocument[0].referenceNumber.text()
		
		//nahradi najdene MRN v xml, MRN v subore C a F
		String mrnF = deleteLastLine("MRNF")
		def content = xmlFileText.replaceAll(mrn1, deleteLastLine("MRNC"))
		content = content.replaceAll(mrn2, mrnF)
		content = content.replaceAll(mrn3, mrnF)
		
		xmlFile.write(content)
	}
	
	def writeMRN(String nazovSuboru, String MRN) {
		def projectDir = RunConfiguration.getProjectDir()
		Path projectPath = Paths.get(projectDir)
		Path nazovMRN = projectPath.resolve(nazovSuboru + '.txt')
		
		//String filePathDialog = filePath.replaceAll('/', '\\\\')
		// Open the file for appending
		def writer = new FileWriter(nazovMRN.toString(), true)
		
		try {
		  // Append the value to the file
		  writer.write(MRN + System.getProperty('line.separator'))
		} finally {
		  writer.close()
		}
	}
	
	def deleteLastLine(String fileName) {
		String lastLine
		// Define the file path
		def projectDir = RunConfiguration.getProjectDir()
		Path projectPath = Paths.get(projectDir)
		Path nazovMRN = projectPath.resolve(fileName + '.txt')
		
		// Read all lines into a list
		List<String> lines
		
		try {
		  // Read lines using readLines() for better performance
		  lines = Files.readAllLines(new File(nazovMRN.toString()).toPath())
		} catch (IOException e) {
		  // Handle potential I/O exceptions (optional)
		  e.printStackTrace()
		  lines = []
		}
		
		if (lines.size() > 0) {
		  // Store the last line in a variable
		  lastLine = lines.remove(lines.size() - 1) // Removes and returns the last line
		
		  // Write the remaining lines back to the file using writeAllLines()
		  Files.write(new File(nazovMRN.toString()).toPath(), lines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)
		} else {
		  println("File " + nazovMRN.toString() + " is empty");
		}
		
		return lastLine
	}
}