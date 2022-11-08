package org.example.domain.usecases.participante;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVWriter;
import org.example.domain.entities.participante.Participante;

public class GenerateCSVParticpanteListUseCase {

    private ParticipanteDAO participanteDAO;

    public GenerateCSVParticpanteListUseCase(ParticipanteDAO participanteDAO) {this.participanteDAO = participanteDAO;}

    private static final String CSV_PATH = "participantes.csv";

    public void generateCSV(List<Participante> participanteList){

        try {
            FileWriter fw = new FileWriter(new File(CSV_PATH));
            CSVWriter cw = new CSVWriter(fw);
            List<String[]> dados = new ArrayList<String[]>();

            for(Participante p : participanteList){
                p.mostrarDados();
                String[] linha = {p.getNome(),p.getEmail(), p.getCpf()};
                dados.add(linha);
            }

            cw.writeAll(dados);
            cw.close();
            fw.close();

            System.out.println("Participantes adicionados no Arquivo CSV");

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
