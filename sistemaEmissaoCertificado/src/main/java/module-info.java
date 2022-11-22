 module org.example {
    requires javafx.controls;
    requires javafx.fxml;

    //opens org.example.application.controller to javafx.fxml;  ---nao sei se precisa pq tem na linha 13
    opens org.example.application.view to javafx.fxml;
     requires itextpdf;
     requires java.desktop;
     requires opencsv;
     requires mail;
     exports org.example.application.view;
     exports org.example.application.controller;
     opens org.example.application.controller to javafx.fxml;
 }
