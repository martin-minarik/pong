module pong {
    requires transitive javafx.controls;
    opens pong to javafx.fxml, jakarta.xml.bind;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;
    requires java.xml;
    requires jakarta.xml.bind;
    exports pong;
}