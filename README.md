# MaFiSoft

## Setup

    IDE used: IntelliJ IDEA
    - Project export to eclipse is added in ".project" & ".classpath" & "mafisoft4.eml" & "mafisoft4.userlibraries"
    - Java Version 1.8 

    Setup MySql DB with sql Dump. And change DB credentials in DBHelper class
    Database should be named "mafisoftBD",  but can be changed in DBHelper as well.
    
    Make sure the mysql connector is included as a library (version 5.1.40 worked)
     
    For running JUnit test the Library´s "Hamcrest" and "junit" should be added

## Description for "SWT2 Praktikum" 

* Logic: 
    1. AddCourse:
    
            Add both courses: video and physical.

    2. AddCustomer:
        
            Add Customer with complex Datatype zipCode

    3. ChangeCustomerData:
    
            Holds logic for Listing all customer
            + Searching through customer List
            + Editing customer Data & Listing their joined courses
            + Add new courses to a customer
            
    4. CourseList:
            
            List either all Physical or Video Courses
            + Edit Physical or Video Courses

*  DBHelper
    
        Singelton class
        Holds connection to DB
        Is our default global class for all DB Operations and accesses DAO fucntions for DB Operation execution

* GUI
    
        FXML layout
        
* JUnit Test
   
        Located in "src/tests.MafisoftTest", Following is tested
        - Add Customer and check if stored in Database
        - Add Physical Course and check if stored in Database
        - Add Video Course and check if stored in Database
        - Add Course to customer and check if it was added in DB + Check if removing course form customer removes entry from DB
         
* DAO / DTO Pattern

        Located in package "dao", "service"
        CourseDAO -> All related Course DB operations
        CustomerDAO -> All related Customer DB operations
        
        
* Singleton pattern

        Is used in DBHelper class, DBHelper is initialized as Singelton class by getInstance 

* Facades pattern

        Is implemented with Interface Navigable, The GUI has the only acces through interface Navigable. 
        With this lose coupling, is the exchange between packages easier possible.


