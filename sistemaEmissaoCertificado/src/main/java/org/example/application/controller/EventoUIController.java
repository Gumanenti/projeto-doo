package org.example.application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.application.view.WindowLoader;
import org.example.domain.entities.evento.Evento;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.example.application.main.Main.createEventoUseCase;
import static org.example.application.main.Main.updateEventoUseCase;

public class EventoUIController {

    @FXML
    private DatePicker dpData;
    @FXML
    private Spinner<Integer> sHora;

    private final SpinnerValueFactory<Integer> valueHora = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23,0);
    @FXML
    private Spinner<Integer> sMinutos;
    private final SpinnerValueFactory<Integer> valueMinutos = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,0);

    @FXML
    private TextField txtNome;
    @FXML
    private Spinner<Integer> cargaHoraria;
    private final SpinnerValueFactory<Integer> valueCargaHoraria = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0);
    @FXML
    private TextField txtNomePalestrante;
    @FXML
    private Button btnConfirm;
    @FXML
    private Button btnCancel;

    private Evento evento;

    @FXML
    private void initialize(){
        cargaHoraria.setValueFactory(valueCargaHoraria);
        sHora.setValueFactory(valueHora);
        sMinutos.setValueFactory(valueMinutos);
    }

    private void getEntityToView(){
        if(evento == null){
            evento = new Evento();
        }
        evento.setNome(txtNome.getText());
        evento.setData(LocalDateTime.now());
        evento.setCargaHoraria(valueCargaHoraria.getValue());
        evento.setNomePalestrante(txtNomePalestrante.getText());
        evento.setData(LocalDateTime.of(
                dpData.getValue().getYear(),
                dpData.getValue().getMonthValue(),
                dpData.getValue().getDayOfMonth(),
                sHora.getValue(),
                sMinutos.getValue()
        ));
    }

    private void setEntityIntoView(){
        txtNome.setText(evento.getNome());
        valueCargaHoraria.setValue(evento.getCargaHoraria());
        txtNomePalestrante.setText(evento.getNomePalestrante());
        dpData.setValue(LocalDate.parse(evento.getData().format(DateTimeFormatter.ISO_DATE)));
        valueHora.setValue(evento.getData().getHour());
        valueMinutos.setValue(evento.getData().getMinute());
    }

    public void saveOrUpdate() throws IOException {
        getEntityToView();

        try{
        if (evento.getId() == null){
            createEventoUseCase.insert(evento);
        } else {
                updateEventoUseCase.update(evento);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        WindowLoader.setRoot("EventoManagementUI");

    }

    public void backToPreviewScenne() throws IOException {
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
        sMinutos.setDisable(true);
        sHora.setDisable(true);
    }

    public void attachImage() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG Files", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG Files", "*.png")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null && !selectedFile.getAbsolutePath().isEmpty()) {
            if (evento == null)
                    evento = new Evento();
            evento.setPathTemplateImage(selectedFile.getAbsolutePath());

        }
    }
}
