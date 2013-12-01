package local;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import android.util.Log;

import entities.Course;
import entities.Student;
import entities.SubType;
import entities.Submission;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LocalUtil {
	// TODO all reading/writing to android storage will be implemented here

	public static void ImportCourseData(Student student, String filename) {

		/* Use DOM XML Parser to parse Course data */
		File fXmlFile = new File(filename);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("course");

		// Create a new Course
		Course c = new Course();
		Node nNode = nList.item(0);

		// Parse Course-specific fields
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) nNode;

			if (eElement.getElementsByTagName("course_name").item(0).getTextContent() != null) {
				String name = eElement.getElementsByTagName("course_name")
						.item(0).getTextContent();
				c.setCourseName(name);
			}

			if (eElement.getElementsByTagName("univ").item(0).getTextContent() != null) {
				String univ = eElement.getElementsByTagName("univ").item(0)
						.getTextContent();
				c.setUniv(univ);
			}

			if (eElement.getElementsByTagName("course_number").item(0).getTextContent() != null) {
				String num = eElement.getElementsByTagName("course_number")
						.item(0).getTextContent();
				c.setCourseNumber(num);
			}

			if (eElement.getElementsByTagName("semester").item(0).getTextContent() != null) {
				String sem = eElement.getElementsByTagName("semester").item(0)
						.getTextContent();
				c.setSemester(sem);
			}
			
			if (eElement.getElementsByTagName("email").item(0).getTextContent() != null) {
				String email = eElement.getElementsByTagName("email").item(0)
						.getTextContent();
				c.setEmail(email);
			}
			
			// Get list of all submissions
			NodeList subList = eElement.getElementsByTagName("submission");

			/* Loop through all Submissions */
			for (int i = 0; i < subList.getLength(); i++) {
				// Create a new Submission
				Submission s = new Submission();
				Node sub = subList.item(i);

				// Parse Submission-specific fields
				if (sub.getNodeType() == Node.ELEMENT_NODE) {
					Element subElement = (Element) sub;

					if (subElement.getElementsByTagName("subName").item(0).getTextContent() != null) {
						String subName = subElement
								.getElementsByTagName("subName").item(0)
								.getTextContent();
						s.setSubName(subName);
						Log.d("XML", "subm: " + subName);
					}

					int subId = Integer.parseInt(subElement.getAttribute("id"));
					s.setSubId(subId);

					if (subElement.getElementsByTagName("subType").item(0).getTextContent() != null) {
						SubType subType = SubType.valueOf(subElement
								.getElementsByTagName("subType").item(0)
								.getTextContent().toUpperCase());
						s.setSubType(subType);
					}

					//Log.d("XML", "relDate: " + subElement.getElementsByTagName("releaseDate").item(0).getTextContent());
					if (subElement.getElementsByTagName("releaseDate").item(0) != null) {
						DateFormat formatter = new SimpleDateFormat(
								"MM/dd/yyyy hh:mm");
						Date releaseDate = null;
						Date dueDate = null;
						try {
							releaseDate = formatter.parse(subElement
									.getElementsByTagName("releaseDate")
									.item(0).getTextContent());
							dueDate = formatter.parse(subElement
									.getElementsByTagName("dueDate").item(0)
									.getTextContent());
						} catch (DOMException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						s.setReleaseDate(releaseDate);
						s.setDueDate(dueDate);
					}
					
					if (subElement.getElementsByTagName("weightPercent").item(0).getTextContent() != null) {
						int weightPercent = Integer.parseInt(subElement
								.getElementsByTagName("weightPercent").item(0)
								.getTextContent());
						s.setWeightPercent(weightPercent);
					}

					if (subElement.getElementsByTagName("weightPoints").item(0).getTextContent() != null) {
						int weightPoints = Integer.parseInt(subElement
								.getElementsByTagName("weightPoints").item(0)
								.getTextContent());
						s.setWeightPoints(weightPoints);
					}

					if (subElement.getElementsByTagName("description").item(0).getTextContent() != null) {
						String desc = subElement
								.getElementsByTagName("description").item(0)
								.getTextContent();
						s.setDescription(desc);
					}

					// Add this submission to this Course's submissions
					c.submissions.add(s);
				}
			}
		}
		student.courses.add(c);
	}

	public static boolean checkUsersFile(String email, String filename) {

		Log.d("LUTIL", "fname: " + filename);
		/* Use DOM XML Parser to parse Users data */
		File fXmlFile = new File(filename);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("allowed_users");

		Node nNode = nList.item(0);

		// Parse Course-specific fields
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) nNode;

			String user = eElement.getElementsByTagName("user")
					.item(0).getTextContent();
			if ((user != null) && (user.equalsIgnoreCase(email))) {
				return true;
			}
		}
		return false;
	}
	
}
