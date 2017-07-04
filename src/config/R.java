package config;

/*
  Created by mrpitty on 20.06.17.
 */

public class R {

    public static class DB {
        public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    }

    public static class Pages {
        public static final String PATH_TO_MAIN_WINDOW = "gui/Homepage.fxml";
        public static final String PATH_TO_ADD_COURSE_WINDOW = "gui/AddCourse.fxml";
        public static final String PATH_TO_CHANGE_COURSE_WINDOW = "gui/CourseList.fxml";
        public static final String PATH_TO_ADD_CUSTOMER_WINDOW = "gui/AddCustomer.fxml";
        public static final String PATH_TO_CHANGE_CUSTOMER_WINDOW = "gui/ChangeCustomerData.fxml";

    }

    public static class LogConfig {
        public static final String LOG_PROPERTIES = "./src/config/logging.properties";
    }

    public static class Language {
        public static final String RESOURCE_BUNDLE = "config/LabelsBundle";
        public static final String LANGUAGE_RU = "Russian";
        public static final String LANGUAGE_EN = "English";
        public static final String LANGUAGE_DE = "German";
        public static String currentCountry = "DE";
        public static String currentLanguage = "de";
    }
}
