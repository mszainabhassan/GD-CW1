package gendev.hw1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SubmissionCheck {

	public static void main(String[] args) {
		System.out.println(
				"This is a check to tell you whether you are handling files in the correct way. This does not guarantee marks - this \nis simply to help detect obvious mistakes. It DOES NOT mean anything about the mark you will receive.\n\n\n");
		contribution();
		task1();
		task2uc();
		task2ad();
		task3();
		task4();
		
		System.out.println("\n\nEND OF SUBMISSION CHECK: Please read the output above carefully.");
	}

	private static void contribution() {
		System.out.println("Checking Contribution File Submission");
		System.out.println("------------------------------");
		String fname = "contribution/contribution.txt";
		File f = new File(fname);
		if (!f.exists()) {
			System.out.println("   ERROR: The contribution file " + fname + " appears to be missing. WARNING: This will result in a penalty.");
			System.out.println();
			return;
		}
		System.out.println("   Found contribution file " + fname);
		String content = "";
		try {
			content = Files.readString(f.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		int numWords = countWordsUsingSplit(content);
		System.out.println("   It looks like your contribution file contains " + numWords + " words.");
		if (numWords == 0) {
			System.out.println("   ERROR There are no words given to explain contribution.");
		}
		else if (numWords < 40) {
			System.out.println("   WARNING The number of words is a bit low.");
		}
		System.out.println("\n\n");
	}
	
	private static void task1() {
		System.out.println("Checking Task 1 Submission");
		System.out.println("------------------------------");
		String fname = "task1-Description/description.txt";
		File f = new File(fname);
		if (!f.exists()) {
			System.out.println("   ERROR: The file " + fname + " appears to be missing. WARNING: This results in 0 marks.");
			System.out.println();
			return;
		}
		System.out.println("   Found file " + fname);
		String content = "";
		try {
			content = Files.readString(f.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		int numWords = countWordsUsingSplit(content);
		System.out.println("   It looks like your description file contains " + numWords + " of max 400 words.");
		if (numWords < 300) {
			System.out.println("   WARNING The number of words is a bit low.");
		}
		else if (numWords < 350) {
			System.out.println("   WARNING Add more words to ensure enough detail is given.");
		}
		if (numWords > 440) {
			System.out.println("   WARNING 10% over the wordlimit will incurr a penalty.");
		}
		System.out.println("\n\n");
	}

	private static void task2ad() {
		System.out.println("Checking Task 2 Activity Diagram Submission");
		System.out.println("------------------------------");
		String fname = "task2-Activity-Diagram/activity.drawio";
		File f = new File(fname);
		if (!f.exists()) {
			System.out.println("   The file " + fname + " appears to be missing. WARNING: This results in 0 marks.");
			System.out.println("\n\n");
			return;
		}
		System.out.println("   Found file " + fname);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		Document doc = null;
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(f);
		} catch (Exception e) {
			System.out.println("   WARNING: Document parsing failed. This results in 0 marks.");
			return;
		}
		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementsByTagName("mxCell");
		if (nList.getLength() == 0) {
			System.out.println(
					"   WARNING: It looks like it is compressed for exporting. Export it uncompressed from app.diagrams.net. This will result in 0 marks.");
			System.out.println("\n\n");
			return;
		}
		int actions = 0;
		for (int i = 0; i < nList.getLength(); i++) {
			Node n = nList.item(i);
			String style = "";
			try {
				style = n.getAttributes().getNamedItem("style").getNodeValue();
			} catch (Exception e) {
			}
			if (style.contains("rounded=1")) {
				actions++;
			}
		}

		System.out.println("   It looks like you have " + actions + " actions in your activity diagram.");
		if (actions < 6) {
			System.out.println("   WARNING the number of actions looks too low for full marks.");
		}
		System.out.println("\n\n");
	}

	private static void task2uc() {
		System.out.println("Checking Task 2 Use Case Diagram Submission");
		System.out.println("------------------------------");
		String fname = "task2-Usecase-Diagram/usecase.drawio";
		try {
			Files.write(Paths.get("build.properties"), ("#" + System.currentTimeMillis()).getBytes(),
					StandardOpenOption.APPEND);
		} catch (IOException e) {
		}
		File f = new File(fname);
		if (!f.exists()) {
			System.out.println("   The file " + fname + " appears to be missing. WARNING: This results in 0 marks.");
			System.out.println("\n\n");
			return;
		}
		System.out.println("   Found file " + fname);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		Document doc = null;
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(f);
		} catch (Exception e) {
			System.out.println("   WARNING: Document parsing failed. This results in 0 marks.");
			System.out.println("\n\n");
			return;
		}
		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementsByTagName("mxCell");
		if (nList.getLength() == 0) {
			System.out.println(
					"   WARNING: It looks like it is compressed for exporting. Export it uncompressed from app.diagrams.net. This results in 0 marks.");
			System.out.println("\n\n");
			return;
		}

		int usecases = 0;
		for (int i = 0; i < nList.getLength(); i++) {
			Node n = nList.item(i);
			String style = "";
			try {
				style = n.getAttributes().getNamedItem("style").getNodeValue();
			} catch (Exception e) {
			}
			if (style.contains("ellipse")) {
				usecases++;
			}
		}

		System.out.println("   It looks like you have " + usecases + " usecases in your usecase diagram.");
		if (usecases < 6) {
			System.out.println("   WARNING the number of usecases looks to low for full marks.");
		}
		System.out.println("\n\n");
	}

	private static void task3() {
		System.out.println("Checking Task 3 Submission");
		System.out.println("------------------------------");
		String fname = "model/hw1.ecore";
		File f = new File(fname);
		if (!f.exists()) {
			System.out.println("   The file " + fname + " appears to be missing. WARNING: This results in 0 marks.");
			return;
		}
		System.out.println("   Found file " + fname);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		Document doc = null;
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(f);
		} catch (Exception e) {
			System.out.println("   WARNING: Document parsing failed. This results in 0 marks.");
			return;
		}
		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementsByTagName("eClassifiers");
		int classes = nList.getLength();

		System.out.println("   It looks like you have " + classes + " classes in your class diagram.");
		if (classes < 6) {
			System.out.println("   WARNING the number of classes looks too low for full marks.");
		}
		System.out.println("\n\n");
	}

	private static void task4() {
		System.out.println("Checking Task 4 Submission");
		System.out.println("------------------------------");
		String fname = "task4-Instances/instance.xmi";
		File f = new File(fname);
		if (!f.exists()) {
			System.out.println("   The file " + fname + " appears to be missing. WARNING: This results in 0 marks.");
		} else {
			System.out.println("   Found file " + fname);
		}

		for (int i = 1; i <= 3; i++) {
			fname = "task4-Instances/inv" + i + "_sat.xmi";
			if (!Files.exists(Paths.get(fname))) {
				System.out.println("   The file " + fname + " appears to be missing. WARNING: This results in 0 marks.");
			} else {
				System.out.println("   Found file " + fname);
			}
			fname = "task4-Instances/inv" + i + "_fail.xmi";
			if (!Files.exists(Paths.get(fname))) {
				System.out.println("   The file " + fname + " appears to be missing. WARNING: This results in 0 marks.");
			} else {
				System.out.println("   Found file " + fname);
			}
		}

		System.out.println("\n\n");
	}

	public static int countWordsUsingSplit(String input) {
		if (input == null || input.isEmpty()) {
			return 0;
		}

		String[] words = input.split("\\s+");
		return words.length;
	}
}
