package org.example.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.example.application.view.WindowLoader;
import org.example.domain.entities.evento.Evento;
import org.example.domain.entities.participante.Participante;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.example.application.main.Main.*;

public class PreGerarCertificadosUIManagement {
    @FXML
    private Button btnAnexarCSV;
    @FXML
    private Button btnClearTable;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnRemoveParticipant;
    @FXML
    private Button btnEditParticipant;
    @FXML
    private Button btnDeleteParticipant;
    @FXML
    private Button btnGenerateCertificados;
    @FXML
    private TableView<Participante> tableView;
    @FXML
    private TableColumn<Participante, String> cCpf;
    @FXML
    private TableColumn<Participante, String> cName;
    @FXML
    private TableColumn<Participante, String> cEmail;
    @FXML
    private ComboBox<Evento> cbEvento;
    private ObservableList<Evento> listViewEvent;

    private ObservableList<Participante> tableData;
    private List<Participante> participanteList;
    private Evento evento;

    @FXML
    private void initialize(){
        if (participanteList == null)
            participanteList = new ArrayList<>();
        bindTableViewToItemsList();
        bindColumnsToValueSources();
        loadDataAndShow();
        loadComboBoxEvent();
    }

    private void loadDataAndShow() {
        tableData.clear();
        tableData.addAll(participanteList);
    }

    private void bindColumnsToValueSources() {
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);
    }

    private void bindTableViewToItemsList() {
        cCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        cName.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    public void backToPreView() throws IOException {
        WindowLoader.setRoot("MainUI");
    }
    public void removeParticipant() {
        Participante selectedItem = tableView.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            participanteList.remove(selectedItem);
        }
        loadDataAndShow();
    }
    public void editParticipant() throws IOException {
        showParticipantInMode(UIMode.UPDATE);
    }

    public void showParticipantInMode(UIMode mode) throws IOException {
        Participante selectedItem = tableView.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            WindowLoader.setRoot("ParticipanteUI");
            ParticipanteUIController controller = (ParticipanteUIController) WindowLoader.getController();
            controller.setParticipante(selectedItem, mode);
        }
    }

    public void addParticipant() throws IOException {
        showParticipantInMode(UIMode.CREATE);
    }
    public void generateCertificados() throws IOException {
        evento = cbEvento.getValue();
        for (Participante p : participanteList){
            if(findParticipanteUseCase.findOne(p.getCpf()).isEmpty()) {
                createParticipanteUseCase.insert(p);
            }else{
                updateParticipanteUseCase.update(p);
            }
            generateCertificadoUseCase.createCertificado(evento.getId(), p.getCpf());
        }
        WindowLoader.setRoot("MainUI");
    }

    public void anexarCSV() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null && !selectedFile.getAbsolutePath().isEmpty()) {
            participanteList = attachParticipantListUseCase.attachCsvFile(selectedFile.getAbsolutePath());
            tableData.addAll(attachParticipantListUseCase.attachCsvFile(selectedFile.getAbsolutePath()));
        }
    }

    private void loadComboBoxEvent(){
        listViewEvent = FXCollections.observableArrayList(findEventoUseCase.findAll());
        cbEvento.setItems(listViewEvent);
        cbEvento.setConverter(new StringConverter<Evento>() {
            @Override
            public String toString(Evento object) {
                if (object != null){
                    return object.getNome();
                }
                return null;
            }

            @Override
            public Evento fromString(String string) {
                return null;
            }
        });
    }

    public void clearTable() {
        tableData.clear();
    }

    public void loadCSVFile() {
        anexarCSV();
    }
}
