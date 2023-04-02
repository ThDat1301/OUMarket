module com.ddhn.fxoumarket {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;
    requires org.apache.commons.codec;
  
    opens com.ddhn.pojo to javafx.fxml;
    opens com.ddhn.fxoumarket to javafx.fxml;
    exports com.ddhn.fxoumarket;
    exports com.ddhn.pojo;
    exports com.ddhn.services;
    exports com.ddhn.conf;
}
