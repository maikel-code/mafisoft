package config;

/*
  Created by mrpitty on 20.06.17.
 */

public class R {

    public static class DB {
        public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
        public static final String DB_USER = "root";
    }

    public static class Pages {
        public static final String PATH_TO_MAIN_WINDOW = "gui/Homepage.fxml";
        public static final String PATH_TO_ADD_COURSE_WINDOW = "gui/AddCourse.fxml";
        public static final String PATH_TO_CHANGE_COURSE_WINDOW = "gui/CourseList.fxml";
        public static final String PATH_TO_ADD_CUSTOMER_WINDOW = "gui/AddCustomer.fxml";
        public static final String PATH_TO_CHANGE_CUSTOMER_WINDOW = "gui/ChangeCustomerData.fxml";
        public static final String FX_ID_ADD_COURSE = "newCourse";
        public static final String FX_ID_ALL_COURSES = "allCourse";
        public static final String FX_ID_ADD_CUSTOMER = "newCustomer";
        public static final String FX_ID_ALL_CUSTOMERS = "allCustomer";
    }

    public static class LogConfig {
        public static final String LOG_PROPERTIES = "./src/config/logging.properties";
        public static final String CONFIG_FILE = "java.util.logging.config.file";
    }

    public static class Language {
        public static final String RESOURCE_BUNDLE = "config/LabelsBundle";
        public static final int LANGUAGE_RU = 0;
        public static final int LANGUAGE_EN = 1;
        public static final int LANGUAGE_DE = 2;
        public static String currentCountry = "DE";
        public static String currentLanguage = "de";
    }
}
