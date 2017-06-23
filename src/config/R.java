package config;

/*
  Created by mrpitty on 20.06.17.
 */

public class R {
    public static class Log {
        public static final String LOG_PROPERTIES_FILE = "logs/log.properties";
    }

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
}
