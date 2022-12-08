module com.clicks.atmmachine {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.clicks.atmmachine to javafx.fxml;
    opens com.clicks.atmmachine.controller to javafx.fxml;
    opens com.clicks.atmmachine.utils to javafx.fxml;
    exports com.clicks.atmmachine;
}