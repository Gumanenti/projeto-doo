package org.example.domain.usecases.participante;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.example.domain.entities.participante.Participante;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class AttachParticipantListUseCase {
    public AttachParticipantListUseCase() {
    }
    public List<Participante> attachCsvFile(String pathCsv){

        List<Participante> participanteList = new ArrayList<>();

        if(pathCsv == null || pathCsv.isEmpty())
            throw new ParticipanteNotAllowedException("Campo anexo não pode estar vazio ou nulo");

        File file = new File(pathCsv);

        if (!(file.exists()))
            throw new ParticipanteNotAllowedException("Diretório do arquivo ou arquivo não existe!");

        try{
            FileReader fileReader = new FileReader(file);
            CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
            CSVReader csvReader = new CSVReaderBuilder(fileReader)
                    .withSkipLines(0) // Pular o numero de linhas.
                    .withCSVParser(parser)
                    .build();
            List<String[]> allData;

            allData = csvReader.readAll();

            for (String[] row : allData) {
                Participante p = new Participante(row[0], row[1], row[2]);
                participanteList.add(p);
            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return participanteList;
    }

}
