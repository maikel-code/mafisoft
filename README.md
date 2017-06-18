# MaFiSoft


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
   
        Located in "src/MafisoftTest", Following is tested
        - Add Customer and check if stored in Database
        - Add Physical Course and check if stored in Database
        - Add Video Course and check if stored in Database
        - Add Course to customer and check if it was added in DB + Check if removing course form customer removes entry from DB
         
* DAO / DTO Pattern

        Located in package "dao"
        DAOCourse -> All related Course DB operations
        DAOCustomer -> All related Customer DB operations
        
        
* Singleton pattern

        Is used in DBHelper class, DBHelper is initialized as Singelton class by getInstance 

* Facades pattern

        Is implemented with Interface Navigable, The GUI has the only acces through interface Navigable. 
        With this lose coupling, is the exchange between packages easier possible.


