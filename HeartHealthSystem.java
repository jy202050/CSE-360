// Title: Heart Health System UI   
// Desc: Create a UI system for heart health imaging and recording, it includes three main functionalities of 
//       patient intake (Receptionist view), CT Scan Tech View (Technician), and patient view (patient).
// Author: JIAYUAN YU
// Date: 06/04/2023

package application;

import javafx.application.Application;         // Entry point of the application in JavaFX  
import javafx.geometry.Insets;                 // Specify the amount of space or padding around the edges of a GUI component
import javafx.geometry.Pos;                    // Provides various pre-defined value for alignment,(center, top, bottom, right, etc) 
import javafx.scene.Scene;                     // To create and manage the content and properties of a graphical scene
import javafx.scene.control.*;                 // Allows user interface controls, such as button, labels, text fields, combo boxes, list view, table views...
import javafx.scene.layout.*;                  // Contains classes that represent various layout containers used for organizing and positioning UI elements within a scene
import javafx.scene.text.Font;                 // Set font size for the label or buttons
import javafx.scene.paint.Color;               // Set color for three view buttons
import javafx.stage.Stage;                     // Displaying the user interface and handling the interaction with the operating system


import java.io.File;                           // File class provide abstraction for working with files and directories in the file system, used to create,delete, rename, and query properties of Files and directories
import java.io.FileWriter;                     // Used for writing character data to file, it allows to write text data to a file in a sequential manner.
import java.io.FileReader;                     // Used to read patient's data from the file
import java.io.BufferedReader;                 // Used to read file element
import java.io.IOException;                    // Exception class that is thrown when an I/O operation encounters an error or fails
import java.util.List;                         // Provides methods for adding, removing, and accessing elements in a list, store and manipulate collections of data, such as lines read from a file.
import java.util.Random;                       // To generate random patient ID
import java.time.LocalDate;                    // To schedule a exam for the patient after collected all the required information
import java.util.Map;
import java.util.HashMap;
import java.time.format.DateTimeFormatter;


            


public class HeartHealthSystem extends Application {
	private Stage primaryStage;                                                              // Declares a private instance variable primaryStage of type Stage. The Stage represents the main window or stage of the JavaFX application
	private DateTimeFormatter dateFormatter;                                                 // The DateTimeFormatter class is used for formatting and parsing dates and times
	
	public static void main(String[] args) {
		launch(args);                                                                        // Starts the JavaFX application by invoking the start method
	}

	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;                                                    // This allows other methods in the class to access and manipulate the primary stage.
		this.dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");                      // Initializes the dateFormatter instance variable with a DateTimeFormatter object that formats dates using the pattern "MM/dd/yyyy".
		
		showSystemMainView();                                                                // Displaying the main view of the heart health system.
	}
	
	public void showSystemMainView() {
		
		primaryStage.setTitle("Heart Health Imaging and Recording System");                  // Create the title for the UI system
		
		Label systemLabel = new Label("Welcome to Heart Health Imaging and Recording System"); // Create the title label for the main view
		systemLabel.setStyle("-fx-font-weight: bold;");                                      // Set label to bold
		
		Button patientIntakeButton = new Button("Patient Intake");                           // Create the button named Patient Intake
		patientIntakeButton.setOnAction(event -> patientIntakeView());                       // sets showPatientIntakeView method as the action to be performed when the view button is clicked
		
		Button ctScanTechViewButton = new Button("CT Scan Tech View");                       // Create the button named CT Scan Tech View
		ctScanTechViewButton.setOnAction(event -> ctScanTechView());                         // sets showCTScanTechView method as the action to be performed when the view button is clicked
		
		Button patientViewButton = new Button("Patient View");                               // Create the button named Patient View
		patientViewButton.setOnAction(event -> patientLogin());                               // sets showPatientView method as the action to be performed when the view button is clicked

	    
		// Set Font Size
		int mainViewFontSize = 22;                                                           // Assign font size 18 to variable mainViewFontSize
		systemLabel.setFont(Font.font(mainViewFontSize));                                    // Set font size of systemLabel label to 18
		patientIntakeButton.setFont(Font.font(mainViewFontSize));                            // Set font size of "Patient Intake" button to 18
		ctScanTechViewButton.setFont(Font.font(mainViewFontSize));                           // Set font size of "CT Scan Tech View" button to 18
		patientViewButton.setFont(Font.font(mainViewFontSize));                              // Set font size of "Patient View" button to 18
		
		
		// Set Blue Color Background
		String setButtonBlue = "-fx-background-color: blue; -fx-text-fill: white";            // Set the three buttons background to blue and keep the white text
		patientIntakeButton.setStyle(setButtonBlue);                                          // Set "Patient Intake" button background to blue and keep text "Patient Intake" white
		ctScanTechViewButton.setStyle(setButtonBlue);                                         // Set "CT Scan Tech View" button background to blue and keep text "CT Scan Tech View" white
		patientViewButton.setStyle(setButtonBlue);                                            // Set "Patient View" button background to blue and keep text "Patient View" white
		
		
		// Set view box spacing to 40 pixels and set all nodes to stacked vertically within the VBox
		VBox setMainView = new VBox(40, systemLabel, patientIntakeButton, ctScanTechViewButton, patientViewButton); 
		setMainView.setPadding(new Insets(40));                                               // Sets the padding around the VBox to 40 pixels on all sides.
		setMainView.setAlignment(Pos.TOP_CENTER);                                             // Set all nodes(Manipulate buttons) to top centered 
		
		
		// Set size of the box (buttons)
		// Patient Intake Button
		patientIntakeButton.setPrefWidth(330);                                                // Set patient intake button box width to 330 pixel
		patientIntakeButton.setPrefHeight(150);                                               // Set patient intake button box height to 330 pixel
		
		// CT Scan Tech View Button 
		ctScanTechViewButton.setPrefWidth(330);                                               // Set CT Scan Tech View button box width to 330 pixel
		ctScanTechViewButton.setPrefHeight(150);                                              // Set CT Scan Tech View button box height to 330 pixel
		
		// Patient View  
		patientViewButton.setPrefWidth(330);                                                  // Set patient view button width box to 330 pixel
		patientViewButton.setPrefHeight(150);                                                 // Set patient view button width height to 330 pixel
		
		
		// Set Scene length                         
		Scene mainViewScene = new Scene(setMainView, 750, 500);                               // The length of the scene is set to 750 pixels and the height is set to 500 pixels
		primaryStage.setScene(mainViewScene);                                                 // Sets the created scene as the mainViewScene to be displayed on the primaryStage stage
		primaryStage.show();                                                                  // Display the primary stage along with the configured scene
					
	}
	
	
	// A private class to hold patient's information
	private class Patient {
		private String patientID, FirstName, LastName, Email, PhoneNumber, HealthHistory, InsuranceID;  // private instance variables for storing the patient's info
		private LocalDate examDate;
		
		// constructor of the Patient class takes the necessary information to create a Patient object and initializes the instance variables with the provided values
		public Patient (String patientID, String FirstName, String LastName, String Email, String PhoneNumber, String HealthHistory, String InsuranceID, LocalDate examDate) {
			this.patientID = patientID;                                                      
			this.FirstName =  FirstName;                                                      
			this.LastName = LastName;                                                         
			this.Email = Email;                                                               
			this.PhoneNumber = PhoneNumber;                                                   
			this.HealthHistory = HealthHistory;                                               
			this.InsuranceID = InsuranceID;                                                   
			this.examDate = examDate;	                                                      
		}
		
		public String getPatientID() {                                                        // getter method that returns the patient's ID
			return patientID;
		} 
		public String getFirstName() {                                                        // getter method that returns the patient's first name
			return FirstName;
		}
		public String getLastName() {                                                         // getter method that returns the patient's last name
			return LastName;
		}
		public String getEmail() {                                                            // getter method that returns the patient's email
			return Email;
		}
		public String getPhoneNumber() {                                                      // getter method that returns the patient's phone number
			return PhoneNumber;			
		}
		public String getHealthHistory() {                                                    // getter method that returns the patient's health history
			return HealthHistory;
		}
		public String getInsuranceID() {                                                      // getter method that returns the patient's insurance ID
			return InsuranceID;
		}
		public LocalDate examDate() {                                                         // getter method that returns the patient's exam date
			return examDate;
		}
	}
	
	
	// Patient Intake (One of the views from Main system)
	private void patientIntakeView() { 
		primaryStage.setTitle("Heart Health System - Patient Intake");                        // Set UI title for Patient Intake view
		 
		// Title
	    Label patientIntakeLabel = new Label("Patient Intake Form");                          // Create patient intake label
	    patientIntakeLabel.setStyle("-fx-font-weight: bold;");                                // Set title to bold
	    patientIntakeLabel.setFont(Font.font(30));                                            // Set title font size to 30
		
	    // Save Button
		Button savePatientIntakeButton = new Button("Save");                                  // Create button named Save
		String setButtonBlue = "-fx-background-color: blue; -fx-text-fill: white";            // Set the buttons background to blue and keep the white text
		savePatientIntakeButton.setStyle(setButtonBlue);                                      // Set "Save" button background to blue and keep text "Save" white
		savePatientIntakeButton.setPrefWidth(100);                                            // Set Save button width to 100 pixels
		savePatientIntakeButton.setPrefHeight(50);                                            // Set Save button height to 50 pixels
        
		// Back button
		Button backButton = new Button("Back");                                              // Create a button named Back to Main System
		backButton.setStyle(setButtonBlue);                                                  // Set "Back" button background to blue and keep text "Back" white
		backButton.setPrefWidth(100);                                                        // Set Save button width to 100 pixels
		backButton.setPrefHeight(50);                                                        // Set Save button height to 50 pixels
		backButton.setFont(Font.font(20));                                                   // Set font size to 20
		backButton.setOnAction(event -> {                                                    // When user click on back button, it will take user back to the main system view
			showSystemMainView();
		});
		
 
        // 6 labels for patient information
		Label firstName = new Label("First Name: ");                                          // Create first name label
		TextField firstNameTextField = new TextField();                                       // Create text field for first name 
		firstNameTextField.setPrefHeight(48);                                                 // Set height of the text field to 48 pixels
		
		Label lastName = new Label("Last Name: ");                                            // Create Last Name label
		TextField lastNameTextField = new TextField();                                        // Create text field for last name
		lastNameTextField.setPrefHeight(48);                                                  // Set height of the text field to 48 pixels
		 
		Label email = new Label("Email: ");                                                   // Create email label
		TextField emailTextField = new TextField();                                           // Create text field for email
		emailTextField.setPrefHeight(48);                                                     // Set height of the text field to 48 pixels
		
		Label phoneNumber = new Label("Phone Number: ");                                      // Create phone number label
		TextField phoneNumberTextField = new TextField();                                     // Create text field for phone number
		phoneNumberTextField.setPrefHeight(48);                                               // Set height of the text field to 48 pixels
		                           
		Label healthHistory = new Label("Health History: ");                                  // Create health history label
		TextField healthHistoryTextField = new TextField();                                   // Create text field for health history
		healthHistoryTextField.setPrefHeight(48);                                             // Set height of the text field to 48 pixels
		
		
		Label insuranceID = new Label("Insurance ID: ");                                      // Create Insurance ID label
		TextField insuranceIDTextField = new TextField();                                     // Create text field for insurance ID
		insuranceIDTextField.setPrefHeight(48);                                               // Set height of the text field to 48 pixels
	
		VBox setBackButton = new VBox(20, backButton);                                        // Set box spacing to 20 pixels
		setBackButton.setPadding(new Insets(20));                                             // Sets the padding around the VBox to 20 pixels on all sides.
		setBackButton.setAlignment(Pos.BOTTOM_LEFT);                                          // Set node(Back) centered horizontally and aligned to the bottom right vertically with the box
		
		
		VBox setSaveButton = new VBox(20, savePatientIntakeButton);                           // Set box spacing to 20 pixels
		setSaveButton.setPadding(new Insets(20));                                             // Sets the padding around the VBox to 20 pixels on all sides.
		setSaveButton.setAlignment(Pos.TOP_RIGHT);                                            // Set node(Save) centered horizontally and aligned to the bottom right vertically with the box
		 
		VBox setPatientIntakeTitle = new VBox(40, patientIntakeLabel);                        // Set title spacing to 40 pixels
		setPatientIntakeTitle.setAlignment(Pos.TOP_CENTER);                                   // Set node(title) centered 
		
		// Set each eat box spacing to 35 pixels and set all nodes to stacked vertically within the box
		VBox setPatientIntakeLabel = new VBox(35, firstName,  lastName, email, phoneNumber, healthHistory, insuranceID);
		setPatientIntakeLabel.setAlignment(Pos.CENTER_LEFT);                                  // Set alignment to center left
		setPatientIntakeLabel.setPrefWidth(200);                                              // Set width of all patient intake label to 200 pixels
		
		
		// Set each eat box spacing to 15 pixels and set all nodes to stacked vertically within the box
		VBox setPatientIntakeTextField = new VBox(15, firstNameTextField, lastNameTextField, emailTextField, phoneNumberTextField, healthHistoryTextField, insuranceIDTextField);
		setPatientIntakeTextField.setAlignment(Pos.CENTER);                                  // Set nodes centered
		
		
		
		// Set the general position of each section 
		BorderPane patientIntakeLayout = new BorderPane();                                   // Create a border pane named patientIntakeLayout
		patientIntakeLayout.setTop(setPatientIntakeTitle);                                   // Set title as top section
		patientIntakeLayout.setLeft(setPatientIntakeLabel);                                  // Set all information labels as left section
		patientIntakeLayout.setCenter(setPatientIntakeTextField);                            // Set all text field as center section
		patientIntakeLayout.setRight(setSaveButton);                                         // Set save button as right section
		patientIntakeLayout.setBottom(setBackButton);
		

		// Set all font size to 20, except the title(title font size will be 30 and declared above)
		savePatientIntakeButton.setFont(Font.font(20));                                     
		firstName.setFont(Font.font(20));
		lastName.setFont(Font.font(20));
		email.setFont(Font.font(20));
		phoneNumber.setFont(Font.font(20));
		healthHistory.setFont(Font.font(20));
		insuranceID.setFont(Font.font(20));

		
		
		// Create a button action when the button is clicked
		savePatientIntakeButton.setOnAction(event -> {
			
			String firstNameText = firstNameTextField.getText();                             // Retrieves the text entered in the firstNameTextField and assigns it to the FirstName variable
			String lastNameText = lastNameTextField.getText();                               // Retrieves the text entered in the lastNameTextField and assigns it to the LastName variable
			String emailText = emailTextField.getText();                                     // Retrieves the text entered in the emailTextField and assigns it to the Email variable
			String phoneNumberText = phoneNumberTextField.getText();                         // Retrieves the text entered in the phoneNumberTextField and assigns it to the PhoneNumber variable
			String healthHistoryText = healthHistoryTextField.getText();                     // Retrieves the text entered in the healthHistoryTextField and assigns it to the HealthHistory variable
			String insuranceIDText = insuranceIDTextField.getText();                         // Retrieves the text entered in the insuranceIDTextField and assigns it to the InsuranceID variable
			
			
			// Check if all the required text fields are filled, patient ID will not generate if there is 1 or more text fields input missing 
			if (firstNameText.isEmpty() || lastNameText.isEmpty() || emailText.isEmpty() || phoneNumberText.isEmpty() || healthHistoryText.isEmpty() || insuranceIDText.isEmpty()) {
				
				// Display error message if there is missing text filled
				Alert errorAlert = new Alert(Alert.AlertType.ERROR);
				errorAlert.setTitle("Error");                                                  // Display string Error as alert title
				errorAlert.setHeaderText("Missing Information!");                              // Display Missing Information as alert header
				errorAlert.setContentText("Please fill in all the required text field!");      // Display message on the alert content
				errorAlert.showAndWait();
				return;
		
			}
	     
			
			else {
				
				// Generate 5 digit patient ID if all required text fields are filled
				String patientID = generate5DigitID();                                      // Generate 5 digit patient ID
				
				// Schedule an appointment after new patient ID is generated
				LocalDate examDate = LocalDate.now().plusDays(3);                          // Schedule an appointment 3 days from today	
				
				// Create patient information FILE based on the intake form
				Patient newPatient = new Patient(patientID, firstNameText, lastNameText, emailText, phoneNumberText, healthHistoryText, insuranceIDText, examDate);    
				
				// Save patient information to a file
				savePatientInfoToFile(newPatient);
					
				// Display success message after save button is clicked
				Alert intakeAlert = new Alert(Alert.AlertType.INFORMATION);
				intakeAlert.setTitle("Patient Intake");
				intakeAlert.setHeaderText("Saved successfully!");
				intakeAlert.setContentText("Patient Name: " + firstNameText + " " + lastNameText + "\nPatient ID: " + patientID + 
						"\nExam Date: " + examDate.format(dateFormatter) + "\nContact Number: " + phoneNumberText);
				intakeAlert.showAndWait();
					
				
				// Clear the input fields after patient info saved 
				firstNameTextField.clear();                                               // Clear first name text field
				lastNameTextField.clear();                                                // Clear last name text field
				emailTextField.clear();                                                   // Clear email text field
				phoneNumberTextField.clear();                                             // Clear phone number text field
				healthHistoryTextField.clear();                                           // Clear health history text field
				insuranceIDTextField.clear();                                             // Clear insurance ID text field

			}
		
			showSystemMainView();                                                         // Call the main system view(means the system page will jump back to the main view 
                                                                                          // after the save button is clicked	
		}); 
		
		
		// Create scene and sets it on a Stage to display the GUI
		// Create a new scene object name patientIntakeScene with specified patientIntakeLayout
		// The width of the scene is set to 1000 pixels and the height is set to 600 pixels
		Scene patientIntakeScene = new Scene(patientIntakeLayout, 1000, 600);                                              
		primaryStage.setScene(patientIntakeScene);                                         // Sets the created scene as the patientIntakeScene to be displayed on the primaryStage stage
		primaryStage.show();                                                               // Display the primary stage along with the configured scene
		

	} 
	
	// Create a method to generate 5 digit patient ID randomly (Integer)
	private String generate5DigitID() {
		Random randomPatientID = new Random();
		int patientID = randomPatientID.nextInt(90000) + 10000;
		return String.valueOf(patientID);
		
	}
	
	// Create an action for save button and display the properly message

	private void savePatientInfoToFile(Patient newPatient) {
		String patientFileName = newPatient.getPatientID() + "_PatientInfo.txt";           // Create a patient file named xxxxx_PatientInfo.txt
		
		try (FileWriter fileWriter = new FileWriter(patientFileName)) {                    // Open try block and used for exception handling and ensures fileWriter resource is used properly
			
			fileWriter.write("Patient ID: " + newPatient.getPatientID());                  // writes the patient ID string concatenated with the patient's Patient ID retrieved using the getPatientID() method to the file
			fileWriter.write("\nFirst Name: " + newPatient.getFirstName());                // writes the First Name string concatenated with the patient's first name retrieved using the getFirstName() method to the file
			fileWriter.write("\nLast Name: " + newPatient.getLastName());                  // writes the Last Name string concatenated with the patient's last name retrieved using the getLastName() method to the file
			fileWriter.write("\nEmail: " + newPatient.getEmail());                         // writes the Email string concatenated with the patient's email retrieved using the getEmail() method to the file
			fileWriter.write("\nPhone Number: " + newPatient.getPhoneNumber());            // writes the Phone Number string concatenated with the patient's phone number retrieved using the getPhoneNumber() method to the file
			fileWriter.write("\nHealth History: " + newPatient.getHealthHistory());        // writes the Health History string concatenated with the patient's health history retrieved using the getHealthHistory() method to the file
			fileWriter.write("\nInsurance ID: " + newPatient.getInsuranceID());            // writes the Insurance ID string concatenated with the patient's Insurance ID retrieved using the getInsuranceID() method to the file
			fileWriter.write("\nExam Date: " + newPatient.examDate());                     // writes the Exam Date string concatenated with the patient's exam date retrieved using the examDate() method to the file
				 
		} catch (IOException event) {                                                      // This line starts a catch block and specifies that if an IOException occurs within the try block, the following code will handle the exception
			event.printStackTrace();                                                       // prints the stack trace of the exception that occurred, providing information about where and how the exception was thrown
		}
	}
	

	
	
	    // A private class to hold CT Scan data 
		private class ctScanData {
			private String PatientID, TotalAgatstionCACScore, LM, LAD, LCX, RCA, PDA;     // private instance variables for storing the patient's CT scan data
			
			// constructor of the Patient class takes the necessary information to create a Patient object and initializes the instance variables with the provided values
			public ctScanData (String PatientID, String TotalAgatstionCACScore, String LM, String LAD, String LCX, String RCA, String PDA) {
				this.PatientID = PatientID;
				this.TotalAgatstionCACScore =  TotalAgatstionCACScore;
				this.LM = LM;
				this.LAD = LAD;
				this.LCX = LCX;
				this.RCA = RCA;
				this.PDA = PDA;	
			}
			
			public String getPatientID() {                                               // getter method that returns the patient's ID
				return PatientID;
			}
			public String getTotalAgatstionCACScore() {                                  // getter method that returns the patient's total agatstion CAC Score
				return TotalAgatstionCACScore;
			}
			public String getLM() {                                                      // getter method that returns the patient's LM Score
				return LM;
			}
			public String getLAD() {                                                     // getter method that returns the patient's LAD Score
				return LAD;
			}
			public String getLCX() {                                                     // getter method that returns the patient's LCX Score
				return LCX;			
			} 
			public String getRCA() {                                                     // getter method that returns the patient's RCA Score
				return RCA;
			}
			public String getPDA() {                                                     // getter method that returns the patient's PDA Score
				return PDA;
			}
		
		}
	
	
	// CT Scan Tech View (One of the views from Main system)
	private void ctScanTechView() {
		primaryStage.setTitle("Heart Health System - CT Scan Tech View");                    // Create the title for the UI system
		
		 // Save Button
		Button saveCTScanButton = new Button("Save");                                        // Create button named Save
		String setButtonBlue = "-fx-background-color: blue; -fx-text-fill: white";           // Set the buttons background to blue and keep the white text
		saveCTScanButton.setStyle(setButtonBlue);                                            // Set "Save" button background to blue and keep text "Save" white
		saveCTScanButton.setPrefWidth(100);                                                  // Set Save button width to 100 pixels
		saveCTScanButton.setPrefHeight(50);                                                  // Set Save button height to 50 pixels
		saveCTScanButton.setFont(Font.font(18));                                             // Set font to 18
		
		// Back button
		Button backButton = new Button("Back");                                              // Create a button named Back to Main System
		backButton.setStyle(setButtonBlue);                                                  // Set "Back" button background to blue and keep text "Back" white
		backButton.setPrefWidth(100);                                                        // Set Save button width to 100 pixels
		backButton.setPrefHeight(50);                                                        // Set Save button height to 50 pixels
		backButton.setFont(Font.font(20));                                                   // Set font size to 20
		backButton.setOnAction(event -> {                                                    // When user click on back button, it will take user back to the main system view
			showSystemMainView();
		});
		

		// Create all label for CT Scan Tech View
		Label patientIDLabel = new Label("Patient ID: ");                                     // Create Patient ID label
		TextField patientIDTextField = new TextField();                                       // Create text field for patient ID
		patientIDTextField.setPrefHeight(48);                                                 // Set height of the text field to 48 pixels
		patientIDTextField.setPrefWidth(350);                                                 // Set width of the text field to 350 pixels
		patientIDLabel.setStyle("-fx-font-weight: bold;");                  				  // Set font to bold
		patientIDLabel.setFont(Font.font(18));                                                // Set  font size to 18
		
		
		Label totalAgatstionCACScoreLabel = new Label("The total Agatston CAC score: ");      // Create total Agatstion CAC score label
		TextField totalAgatstionCACScoreTextField = new TextField();                          // Create text field for total Agatstion CAC score
		totalAgatstionCACScoreTextField.setPrefHeight(48);                                    // Set height of the text field to 48 pixels
		totalAgatstionCACScoreTextField.setPrefWidth(350);                                    // Set width of the text field to 350 pixels
		totalAgatstionCACScoreLabel.setStyle("-fx-font-weight: bold;");                       // Set font to bold
		totalAgatstionCACScoreLabel.setFont(Font.font(18));                                   // Set font size to 18
		
		Label vesselLevelAgatstonCACScoreLabel = new Label("Vessel level Agaston CAC score");
		vesselLevelAgatstonCACScoreLabel.setStyle("-fx-font-weight: bold;");                  // Set font to bold
		vesselLevelAgatstonCACScoreLabel.setFont(Font.font(20));                              // Set  font size to 20
		
		Label lmLabel = new Label("LM: ");                                                    // Create LM label
		TextField lmTextField = new TextField();                                              // Create text field for LM Score
		lmTextField.setPrefHeight(48);                                                        // Set height of the text field to 48 pixels
		lmTextField.setPrefWidth(250);                                                        // Set width of the text field to 250 pixels
		lmLabel.setStyle("-fx-font-weight: bold;");                                           // Set font to bold
		lmLabel.setFont(Font.font(18));                                                       // Set font to 18
		 
		Label ladLabel = new Label("LAD: ");                                                  // Create LAD label
		TextField ladTextField = new TextField();                                             // Create text field for LAD Score
		ladTextField.setPrefHeight(48);                                                       // Set height of the text field to 48 pixels
		ladTextField.setPrefWidth(250);                                                       // Set width of the text field to 250 pixels
		ladLabel.setStyle("-fx-font-weight: bold;");                                          // Set font to bold
		ladLabel.setFont(Font.font(18));                                                      // Set font to 18
		
		Label lcxLabel = new Label("LCX: ");                                                  // Create LCX label
		TextField lcxTextField = new TextField();                                             // Create text field for LCX Score
		lcxTextField.setPrefHeight(48);                                                       // Set height of the text field to 48 pixels
		lcxTextField.setPrefWidth(250);                                                       // Set width of the text field to 250 pixels
		lcxLabel.setStyle("-fx-font-weight: bold;");                                          // Set font to bold
		lcxLabel.setFont(Font.font(18));                                                      // Set font to 18
		
		
		Label rcaLabel = new Label("RCA: ");                                                  // Create RCA label
		TextField rcaTextField = new TextField();                                             // Create text field for RCA Score
		rcaTextField.setPrefHeight(48);                                                       // Set height of the text field to 48 pixels
		rcaTextField.setPrefWidth(250);                                                       // Set width of the text field to 250 pixels
		rcaLabel.setStyle("-fx-font-weight: bold;");                                          // Set font to bold
		rcaLabel.setFont(Font.font(18));                                                      // Set font to 18
		
		
		Label pdaLabel = new Label("PDA: ");                                                  // Create PDA label
		TextField pdaTextField = new TextField();                                             // Create text field for PDA Score
		pdaTextField.setPrefHeight(48);                                                       // Set height of the text field to 48 pixels
		pdaTextField.setPrefWidth(250);                                                       // Set width of the text field to 250 pixels
		pdaLabel.setStyle("-fx-font-weight: bold;");                                          // Set font to bold
		pdaLabel.setFont(Font.font(18));                                                      // Set font to 18
		
		
		// Create a GridPane layout 
		GridPane techViewGridPane = new GridPane();                                           // creates an empty grid pane that can be used to organize components in a grid-like layout.
		techViewGridPane.setPadding(new Insets(10));                                          // Sets the padding of the grid pane to Insets with a uniform value of 10,
		                                                                                      // the padding defines the space between the edges of the grid pane and its content.
		techViewGridPane.setVgap(10);                                                         // Sets the vertical gap between rows in the grid pane to 10 pixels. This creates a vertical spacing between the rows, 
		                                                                                      // controlling the amount of space between components vertically.
	
		
		// Manipulate all labels and text field to the GridPane layout 
		techViewGridPane.add(patientIDLabel, 0, 0);                                           // Adds a patientIDLabel to the grid pane at column 0, row 0
		techViewGridPane.add(patientIDTextField, 1, 0);                                       // Adds a patientIDTextField to the grid pane at column 1, row 0
		techViewGridPane.add(totalAgatstionCACScoreLabel, 0, 1);                              // Adds a totalAgatstionCACScoreLabel to the grid pane at column 0, row 1
		techViewGridPane.add(totalAgatstionCACScoreTextField, 1, 1);                          // Adds a totalAgatstionCACScoreTextField to the grid pane at column 1, row 1
		techViewGridPane.add(vesselLevelAgatstonCACScoreLabel, 1, 6);                         // Adds a vesselLevelAgatstonCACScoreLabel to the grid pane at column 1, row 6
		techViewGridPane.add(lmLabel, 1, 7);                                                  // Adds an lmLabel to the grid pane at column 1, row 7
		techViewGridPane.add(lmTextField, 2, 7);                                              // Adds an lmTextField to the grid pane at column 2, row 7
		techViewGridPane.add(ladLabel, 1, 8);                                                 // Adds a ladLabel to the grid pane at column 1, row 8
		techViewGridPane.add(ladTextField, 2, 8);                                             // Adds a ladTextField to the grid pane at column 2, row 8
		techViewGridPane.add(lcxLabel, 1, 9);                                                 // Adds an lcxLabel to the grid pane at column 1, row 9
		techViewGridPane.add(lcxTextField, 2, 9);                                             // Adds an lcxTextField to the grid pane at column 2, row 9
		techViewGridPane.add(rcaLabel, 1, 10);                                                // Adds an rcaLabel to the grid pane at column 1, row 10
		techViewGridPane.add(rcaTextField, 2, 10);                                            // Adds an rcaTextField to the grid pane at column 2, row 10
		techViewGridPane.add(pdaLabel, 1, 11);                                                // Adds a pdaLabel to the grid pane at column 1, row 11
		techViewGridPane.add(pdaTextField, 2, 11);                                            // Adds a pdaTextField to the grid pane at column 2, row 11
		techViewGridPane.add(backButton, 0, 14);                                              // Adds a back button to the grid pane at column 0, row 14
		techViewGridPane.add(saveCTScanButton, 3, 14);                                        // Adds a saveCTScanButton to the grid pane at column 3, row 14
		
		
		
		// Create a button action when save button is clicked
		saveCTScanButton.setOnAction(event -> {
			
			String patientIDText = patientIDTextField.getText();                               // Retrieves the text entered in the patientIDTextField and assigns it to the patientIDText variable
			String totalAgatstionCACScoreText = totalAgatstionCACScoreTextField.getText();     // Retrieves the text entered in the totalAgatstionCACScoreText and assigns it to the totalAgatstionCACScoreTextFied variable
			String lmText = lmTextField.getText();                                             // Retrieves the text entered in the lmText and assigns it to the lmTextField variable
			String ladText = ladTextField.getText();                                           // Retrieves the text entered in the ladText and assigns it to the ladTextField variable
			String lcxText = lcxTextField.getText();                                           // Retrieves the text entered in the lcxText and assigns it to the lcxTextField variable
			String rcaText = rcaTextField.getText();                                           // Retrieves the text entered in the rcaText and assigns it to the rcaTextField variable
			String pdaText = pdaTextField.getText();                                           // Retrieves the text entered in the pdaText and assigns it to the pdaTextField variable
			
			// Check if all the required text fields are filled
			if (patientIDText.isEmpty() || totalAgatstionCACScoreText.isEmpty() || lmText.isEmpty() || ladText.isEmpty() || lcxText.isEmpty() || rcaText.isEmpty() || pdaText.isEmpty()) {
				
				// Display error message if there is missing text filled
				Alert errorAlert = new Alert(Alert.AlertType.ERROR);
				errorAlert.setTitle("Error");                                                  // Display string Error as alert title
				errorAlert.setHeaderText("Missing Information!");                              // Display Missing Information as alert header
				errorAlert.setContentText("Please fill in all the required text field!");      // Display message on the alert content
				errorAlert.showAndWait();
				return;
		
			}
	     
			
			else {
				
				
				// Create patient CT Scan data FILE based on the technician's recording
				ctScanData newPatientData = new ctScanData(patientIDText, totalAgatstionCACScoreText, lmText, ladText, lcxText, rcaText, pdaText);    
				
				// Save patient CT scan data to a file
				savePatientCTScanDataToFile(newPatientData);
					
				// Display success message after save button is clicked
				Alert intakeAlert = new Alert(Alert.AlertType.INFORMATION);
				intakeAlert.setTitle("CT Scan Report");
				intakeAlert.setHeaderText("Saved");
				intakeAlert.setContentText("CT Report has been saved successfully!");
				intakeAlert.showAndWait();
				
				
				// Clear the input fields after patient info saved 
				patientIDTextField.clear();                                                    // Clear first name text field
				totalAgatstionCACScoreTextField.clear();                                       // Clear last name text field
				lmTextField.clear();                                                           // Clear email text field
				ladTextField.clear();                                                          // Clear phone number text field
				lcxTextField.clear();                                                          // Clear health history text field
				rcaTextField.clear();                                                          // Clear insurance ID text field
				pdaTextField.clear();  

			}
		
			showSystemMainView();                                                              // Call the main system view(means the system page will jump back to the main view 
                                                                                               // after the save button is clicked
			
		});
		
		// Create scene and sets it on a Stage to display the GUI
	    // Create a new scene object name patientIntakeScene with specified patientIntakeLayout
	    // The width of the scene is set to 1000 pixels and the height is set to 600 pixels
		Scene ctScanTechViewScene = new Scene(techViewGridPane, 1000, 600);
		primaryStage.setScene(ctScanTechViewScene);                                            // Sets the created scene as the ctScanTechViewScene to be displayed on the primaryStage stage
		primaryStage.show();                                                                   // Display the primary stage along with the configured scene
		    
	}
	
	private void savePatientCTScanDataToFile(ctScanData newPatientData) {
		String patientCTScanFileName = newPatientData.getPatientID() + "CTResults.txt";                         // Create a patient file named xxxxxCTResults.txt
		
		try (FileWriter fileWriter = new FileWriter(patientCTScanFileName)) {                    	            // Open try block and used for exception handling and ensures fileWriter resource is used properly
			
			fileWriter.write("Patient ID: " + newPatientData.getPatientID());                                   // writes the patient ID string concatenated with the PatientID retrieved using the getPatientID() method to the file
			fileWriter.write("\nTotal Agatston CAC score: " + newPatientData.getTotalAgatstionCACScore());      // writes the Total Agatstion CAC Score string concatenated with the getTotalAgatstionCACScore retrieved using the getTotalAgatstionCACScore() method to the file
			fileWriter.write("\nLM Score: " + newPatientData.getLM());                                          // writes the LM Score string concatenated with the patient's LM Score retrieved using the getLM() method to the file
			fileWriter.write("\nLAD Score: " + newPatientData.getLAD());                         				// writes the LAD score string concatenated with the patient's LAD score retrieved using the getLAD() method to the file
			fileWriter.write("\nLCX Score: " + newPatientData.getLCX());           								// writes the LCX score string concatenated with the patient's LCX score retrieved using the getLCX() method to the file
			fileWriter.write("\nRCA Score: " + newPatientData.getRCA());        								// writes the RCA score string concatenated with the patient's RCA score retrieved using the getRCA() method to the file
			fileWriter.write("\nPDA Score: " + newPatientData.getPDA());                                        // writes the PDA score string concatenated with the patient's PDA score retrieved using the getPDA() method to the file
			
			
		} catch (IOException event) {                                                                           // This line starts a catch block and specifies that if an IOException occurs within the try block, the following code will handle the exception
			event.printStackTrace();                                                                            // prints the stack trace of the exception that occurred, providing information about where and how the exception was thrown
		}
		
    }
	
	

	
	// Patient View (One of the views from Main system)
    private void patientView(String patientLoginID) {
    	primaryStage.setTitle("Heart Health System - Patient View");                                    // Create the title for the UI system
    	
		String patientName = getPatientName(patientLoginID);                                            // Call getPatientName method to get patient's full name based on the patient ID entered
			
    	// Create all labels for Patient View
    	Label patientViewTitle = new Label("Hello <" + patientName +">" );                              // Create label for the patient view title, display patient name
    	patientViewTitle.setFont(Font.font(30));                                                        // Set font to 30
    	patientViewTitle.setStyle("-fx-font-weight: bold;");                                            // Set font to bold
	
		
		Button logoutButton = new Button("Log Out");                                                    // Create Log Out button
		logoutButton.setOnAction(event -> {                                                             // Take user back to main system view when logout button is clicked
			showSystemMainView();
		});
		

		// Retrieve the Agatston CAC scores
		Map<String, String> agatstonScores = getAgatstonScores(patientLoginID);
		
        // Create labels for each score and set their text
        Label totalScoreLabel = new Label("Total Agatston CAC score: " + agatstonScores.get("Total Agatston CAC score"));
        Label lmLabel = new Label("LM: " + agatstonScores.get("LM Score"));
        Label ladLabel = new Label("LAD: " + agatstonScores.get("LAD Score"));
        Label lcxLabel = new Label("LCX: " + agatstonScores.get("LCX Score"));
        Label rcaLabel = new Label("RCA: " + agatstonScores.get("RCA Score"));
        Label pdaLabel = new Label("PDA: " + agatstonScores.get("PDA Score"));
		
        
		// Set all labels font to 25 and bold style
        totalScoreLabel.setFont(Font.font(30));                                                         // Set font to 20
        totalScoreLabel.setStyle("-fx-font-weight: bold;");                                             // Set font to bold
    	lmLabel.setFont(Font.font(20));                                                                 // Set font to 20
    	lmLabel.setStyle("-fx-font-weight: bold;");                                                     // Set font to bold
    	ladLabel.setFont(Font.font(20));                                                        	    // Set font to 20
    	ladLabel.setStyle("-fx-font-weight: bold;");                                            	    // Set font to bold
    	lcxLabel.setFont(Font.font(20));                                                        	    // Set font to 20
    	lcxLabel.setStyle("-fx-font-weight: bold;");                                            	    // Set font to bold
    	rcaLabel.setFont(Font.font(20));                                                        	    // Set font to 20
    	rcaLabel.setStyle("-fx-font-weight: bold;");                                            	    // Set font to bold
    	pdaLabel.setFont(Font.font(20));                                                       		    // Set font to 20
    	pdaLabel.setStyle("-fx-font-weight: bold;");                                           	        // Set font to bold
	   
		VBox setPatientViewTitle = new VBox(patientViewTitle);
		setPatientViewTitle.setPadding(new Insets(20));
		setPatientViewTitle.setAlignment(Pos.TOP_CENTER);
		
        VBox setLogoutButton = new VBox(logoutButton);
        setLogoutButton.setPadding(new Insets(30));
        setLogoutButton.setAlignment(Pos.BOTTOM_RIGHT);
        setLogoutButton.setPrefSize(40, 30);
        
        
        VBox setAllCACScores = new VBox(40, totalScoreLabel, lmLabel, ladLabel, lcxLabel, rcaLabel, pdaLabel);
        setAllCACScores.setPadding(new Insets(25));
        setAllCACScores.setAlignment(Pos.CENTER);
    	
       
	    BorderPane patientViewLayout = new BorderPane();
        patientViewLayout.setTop(setPatientViewTitle);
        patientViewLayout.setCenter(setAllCACScores);
        patientViewLayout.setBottom(setLogoutButton);
        
        
		
		// Create scene and sets it on a Stage to display the GUI
		// Create a new scene object name patientIntakeScene with specified patientIntakeLayout
		// The width of the scene is set to 1000 pixels and the height is set to 600 pixels
		Scene patientViewScene = new Scene(patientViewLayout, 1000, 600);                                              
		primaryStage.setScene(patientViewScene);                                                       // Sets the created scene as the patientIntakeScene to be displayed on the primaryStage stage
		primaryStage.show();                                                                           // Display the primary stage along with the configured scene
    	
    }
    
    private Map<String, String> getAgatstonScores(String patientLoginID) {
        Map<String, String> agatstonCACScores = new HashMap<>();
        String ctResultsFilePath = patientLoginID + "CTResults.txt";

        try (BufferedReader agatstonCACReader = new BufferedReader(new FileReader(ctResultsFilePath))) {
            String line;

            while ((line = agatstonCACReader.readLine()) != null) {
                if (line.startsWith("Total Agatston CAC score:")) {
                    String totalScore = line.substring(line.indexOf(":") + 1).trim();
                    agatstonCACScores.put("Total Agatston CAC score", totalScore);
                } else if (line.startsWith("LM Score:")) {
                    String lmScore = line.substring(line.indexOf(":") + 1).trim();
                    agatstonCACScores.put("LM Score", lmScore);
                } else if (line.startsWith("LAD Score:")) {
                    String ladScore = line.substring(line.indexOf(":") + 1).trim();
                    agatstonCACScores.put("LAD Score", ladScore);
                } else if (line.startsWith("LCX Score:")) {
                    String lcxScore = line.substring(line.indexOf(":") + 1).trim();
                    agatstonCACScores.put("LCX Score", lcxScore);
                } else if (line.startsWith("RCA Score:")) {
                    String rcaScore = line.substring(line.indexOf(":") + 1).trim();
                    agatstonCACScores.put("RCA Score", rcaScore);
                } else if (line.startsWith("PDA Score:")) {
                    String pdaScore = line.substring(line.indexOf(":") + 1).trim();
                    agatstonCACScores.put("PDA Score", pdaScore);
                }
            }
        } 
        
        catch (IOException e) {
            e.printStackTrace();
        }

        return agatstonCACScores;
    }
 
	

    // Create a getPatientName method to get patient infomation from the text file
	private String getPatientName(String patientLoginID) {
		
		String patientInfoFilePath = patientLoginID + "_PatientInfo.txt";
		
		try (BufferedReader fileReader = new BufferedReader(new FileReader(patientInfoFilePath))) {
			
			String line;
			
			// Read the first line (patient ID)
			fileReader.readLine();                                                                   
			
	        // Initialize first name and last name variables
	        String patientFirstName = "";
	        String patientLastName = "";

	        // Read the lines until the end of the file
	        while ((line = fileReader.readLine()) != null) {
	            if (line.startsWith("First Name:")) {
	            	
	                // Extract the first name by removing the "First Name:" label
	            	patientFirstName = line.substring("First Name:".length()).trim();
	                
	            } 
	            else if (line.startsWith("Last Name:")) {
	            	
	                // Extract the last name by removing the "Last Name:" label
	            	patientLastName = line.substring("Last Name:".length()).trim();
	            }
	        }
	        
	        // Combine first name and last name
			String patientFullName = patientFirstName + " " + patientLastName;                       
			
			return patientFullName.trim();				
		}
		catch (IOException event) {
			event.printStackTrace();		
		}	
		
		return " "; 
    }
    
       
    
    // Create patient login panel and check if patient id is entered correctly                                                                                                                             
	private void patientLogin() {
		primaryStage.setTitle("Heart Health System - Patient Login");                                  // Create the title for the UI system
		
		Label loginPanelTitle = new Label("Please enter the 5 digit patient ID to login");             // Create the label for patient Login panel
		loginPanelTitle.setFont(Font.font(30));                                                        // Set font size to 30
		loginPanelTitle.setStyle("-fx-font-weight: bold;");                                            // Set font to bold
		
		Label patientIDInputTextLabel = new Label("Patient ID: ");                                     // Create patient ID text field label
		patientIDInputTextLabel.setFont(Font.font(35));                                                // Set font size to 35
		patientIDInputTextLabel.setStyle("-fx-font-weight: bold;");                                    // Set font to bold
		
		TextField patientIDInputTextField = new TextField();                                           // Create patient ID text field 
		patientIDInputTextField.setPrefHeight(60);                                                     // Set height to 60 pixels
		patientIDInputTextField.setPrefWidth(100);                                                     // Set width to 100 pixels
		
		 
		Button loginButton = new Button("Login");                                                      // Create a button named Login
		String setButtonBlue = "-fx-background-color: blue; -fx-text-fill: white";                     // Set the  button background to blue and keep the white text
		loginButton.setStyle(setButtonBlue);                                                           // Set "Login" button background to blue and keep text "Login" white
		loginButton.setPrefWidth(100);                                                                 // Set Save button width to 100 pixels
		loginButton.setPrefHeight(50);                                                                 // Set Save button height to 50 pixels
		loginButton.setFont(Font.font(20));                                                            // Set font size to 20                                           
		
		
		Button backButton = new Button("Back");                                                        // Create a button named Back to Main System
		backButton.setStyle(setButtonBlue);                                                            // Set "Back" button background to blue and keep text "Back" white
		backButton.setPrefWidth(100);                                                                  // Set Save button width to 100 pixels
		backButton.setPrefHeight(50);                                                                  // Set Save button height to 50 pixels
		backButton.setFont(Font.font(20));                                                             // Set font size to 20
		backButton.setOnAction(event -> {                                                              // When user click on back button, it will take user back to the main system view
			showSystemMainView();
		});
		
	    
		// Code to set title section
		VBox setPanelTitle = new VBox(loginPanelTitle);                                                // Set box spacing to 20 pixels
		setPanelTitle.setPadding(new Insets(20));                                                      // Sets the padding around the VBox to 20 pixels on all sides.
		setPanelTitle.setAlignment(Pos.TOP_CENTER);                                                    // Set node to top centered
		
		// Code to set label and text field section
		VBox setPatientIDLabel = new VBox(80, patientIDInputTextLabel,patientIDInputTextField);        // Set box spacing to 50 pixels
		setPatientIDLabel.setPadding(new Insets(50));                                                  // Sets the padding around the VBox to 50 pixels on all sides
		setPatientIDLabel.setAlignment(Pos.CENTER);                                                    // Set node to centered
		

		// Code to set Login button
		VBox setLoginButton = new VBox(loginButton);                                                   // Set box spacing to 20 pixels
		setLoginButton.setPadding(new Insets(20));                                                     // Sets the padding around the VBox to 10 pixels on all sides
		setLoginButton.setAlignment(Pos.BOTTOM_RIGHT);                                                 // Set node to bottom right
		
		// Code to set back button 
		VBox setBackButton = new VBox(backButton);                                                     // Set box spacing to 20 pixels         
		setBackButton.setPadding(new Insets(20));                                                      // Sets the padding around the VBox to 20 pixels on all sides
		setBackButton.setAlignment(Pos.BOTTOM_LEFT);                                                   // Set node to bottom left
		
		
		// Set the general position of each section 
		BorderPane patientLoginLayout = new BorderPane();                                              // Create a border pane for patient login panel
		patientLoginLayout.setTop(setPanelTitle);                                                      // Set title as top section
		patientLoginLayout.setLeft(setBackButton);                                                     // Set all back button labels as left section
		patientLoginLayout.setCenter(setPatientIDLabel);                                               // Set all text field as center section
		patientLoginLayout.setRight(setLoginButton);                                                   // Set Login button as right section
		
		// Check if the patient ID is valid ID when users click on Login button
		loginButton.setOnAction(event -> {                                                             // When patient ID has been verified, it will take users to the patient view
			String patientLoginID = patientIDInputTextField.getText();
			
			while (patientLoginID.isEmpty()) {
				
				// Display error message if input is empty
				Alert loginAlert = new Alert(Alert.AlertType.ERROR);
				loginAlert.setTitle("Login Error");
				loginAlert.setHeaderText("Please enter your patient ID!");
				loginAlert.showAndWait();
				return;
			}
			
			// Check if patient ID is entered correctly
			if (!isValidPatientID(patientLoginID) ) {
				
				// Display error message after login is clicked
				Alert loginAlert = new Alert(Alert.AlertType.ERROR);
				loginAlert.setTitle("Login Error");
				loginAlert.setHeaderText("Wrong patient ID entered");
				loginAlert.setContentText("Please confrim your patient ID!");
				loginAlert.showAndWait();
				return;
			}
			
			else if (!isPatientReportExist(patientLoginID)) {
				
				// Display error message if patient report is not exist
				Alert reportAlert = new Alert(Alert.AlertType.ERROR);
				reportAlert.setTitle("Report Error");
				reportAlert.setHeaderText("Patient ID: " + patientLoginID);
				reportAlert.setContentText("Report unavailable!");
				reportAlert.showAndWait();
				return;
			}
			
			else {
				patientView(patientLoginID);
			}
		});   
	

		
		// controlling the amount of space between components vertically.
		// Create scene and sets it on a Stage to display the GUI
		// Create a new scene object name patientIntakeScene with specified patientIntakeLayout
		// The width of the scene is set to 1000 pixels and the height is set to 600 pixels
		Scene patientLoginScene = new Scene(patientLoginLayout, 1000, 600);                                              
		primaryStage.setScene(patientLoginScene);                                                        // Sets the created scene as the patientIntakeScene to be displayed on the primaryStage stage
		primaryStage.show();                                                                             // Display the primary stage along with the configured scene
			
	}
	
	
	
	
    private boolean isValidPatientID(String patientLoginID) {
    	
    	String patientInfoFilePath = patientLoginID + "_PatientInfo.txt";
  
        // Check if patient info file exists
        File patientInfoFile = new File(patientInfoFilePath);
        if (!patientInfoFile.exists()) {
            return false;
        }

        return true;    
    }
    
    
    private boolean isPatientReportExist(String patientLoginID) {
    	
        String ctResultsFilePath = patientLoginID + "CTResults.txt";
    	
        // Check if CT scan results file exists
        File ctResultsFile = new File(ctResultsFilePath);
        if (!ctResultsFile.exists()) {
            return false;
        }
    	return true;
    }
    		
    	
 }

