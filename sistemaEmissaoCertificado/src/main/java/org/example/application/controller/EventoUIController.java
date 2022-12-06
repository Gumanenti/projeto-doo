package org.example.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.application.view.WindowLoader;
import org.example.domain.entities.evento.Evento;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.example.application.main.Main.createEventoUseCase;
import static org.example.application.main.Main.updateEventoUseCase;

public class EventoUIController {

    public DatePicker dpData;
    public Button btnFileCSV;
    @FXML
    private TextField txtNome;
    @FXML
    private Spinner<Integer> cargaHoraria;
    private SpinnerValueFactory<Integer> valueCargaHoraria = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0);
    @FXML
    private TextField txtNomePalestrante;
    @FXML
    private Button btnConfirm;
    @FXML
    private Button btnCancel;


    private Evento evento;
    private File selectedFile;

    @FXML
    private void initialize(){
        cargaHoraria.setValueFactory(valueCargaHoraria);
    }

    private void getEntityToView(){
        if(evento == null){
            evento = new Evento();
        }
        evento.setNome(txtNome.getText());
        evento.setData(LocalDateTime.now());
        evento.setCargaHoraria(valueCargaHoraria.getValue());
        evento.setNomePalestrante(txtNomePalestrante.getText());
    }

    private void setEntityIntoView(){
        txtNome.setText(evento.getNome());
        valueCargaHoraria.setValue((int) evento.getCargaHoraria());
        txtNomePalestrante.setText(evento.getNomePalestrante());

    }

    public void saveOrUpdate(ActionEvent actionEvent) throws IOException {
        getEntityToView();

        try{
        if (evento.getId() == null){
            createEventoUseCase.insert(evento);
        } else {
            updateEventoUseCase.update(evento);
        }}
        catch (Exception e){
            e.printStackTrace();
        }
        WindowLoader.setRoot("EventoManagementUI");

    }

    public void backToPreviewScenne(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("EventoManagementUI");
    }

    public void setEvento(Evento evento, UIMode mode) {
        if (evento == null){
            throw new IllegalArgumentException("Participante não pode ser nulo");
        } else {
            this.evento = evento;
            setEntityIntoView();
        }
        if (mode == UIMode.VIEW){
            configureViewMode();
        }
    }

    private void configureViewMode() {
        btnCancel.setLayoutX(btnConfirm.getLayoutX());
        btnCancel.setLayoutX(btnConfirm.getLayoutY());
        btnCancel.setText("Fechar");

        btnConfirm.setVisible(false);
        cargaHoraria.setDisable(true);
        dpData.setDisable(true);
        txtNome.setDisable(true);
        txtNomePalestrante.setDisable(true);
    }

    public void anexarCSV(ActionEvent actionEvent) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        selectedFile = fileChooser.showOpenDialog(stage);
    }
}
