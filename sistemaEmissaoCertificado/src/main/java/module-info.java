 module org.example {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.example to javafx.fxml;
     requires itextpdf;
     requires java.desktop;
     requires opencsv;
     exports org.example;
 }
