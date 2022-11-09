package org.example.domain.usecases.certificado;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.domain.entities.certificado.Certificado;
import org.example.domain.entities.certificado.CertificadoStatus;
import org.example.domain.usecases.utils.EntityNotFoundException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

public class GeneratePDFCertificadoUseCase {
    private final String pathRelatorio;

    private final CertificadoDAO certificadoDAO;

    public GeneratePDFCertificadoUseCase(CertificadoDAO certificadoDAO, String pathRelatorio) {
        this.certificadoDAO = certificadoDAO;
        this.pathRelatorio = pathRelatorio;
    }

    public void generatePDF(String code) {
        Optional<Certificado> certificado;
        certificado = certificadoDAO.findOne(code);
        String pathImage = certificado.get().getEvento().getPathTemplateImage();

        if (certificado.isEmpty()) {
            throw new EntityNotFoundException("Certificado não encontrado.");
        }

        certificado.get().getParticipante();
        Document document = new Document(PageSize.A4.rotate(), 180, 180, 90, 100);

        try {

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pathRelatorio + code + ".pdf"));
            document.open();

            document.add(createParagraph("CERTIFICADO",
                    FontFactory.getFont(FontFactory.TIMES_ROMAN, 60, BaseColor.BLACK), Element.ALIGN_JUSTIFIED_ALL));


            document.add(createParagraph(certificado.get().getEvento().getNome().toUpperCase(),
                    FontFactory.getFont(FontFactory.TIMES, 20, BaseColor.GRAY), Element.ALIGN_CENTER, 25));

            // document.add(createParagraph("______________",
            //         FontFactory.getFont(FontFactory.TIMES, 20, BaseColor.GRAY), Element.ALIGN_CENTER, 0, 0));

            document.add(createParagraph("Este certificado está conferido a",
                    FontFactory.getFont(FontFactory.TIMES_ROMAN, 16), Element.ALIGN_CENTER, 30));

            int value = 50, aux;
            String name = certificado.get().getParticipante().getNome();
            if (name.length() > 44 ){
                String[] nameV = name.split(" ");
                aux = nameV.length-1;
                for(int i=nameV.length; i<1; i--){
                    System.out.println(nameV[i-1]);
                    if ((i != 1) && (nameV[aux].length() > nameV[i-1].length())){
                        aux = i-1;
                    }
                    if ((nameV[0].length() + nameV[i-1].length() + 1) <= 15){
                        name = nameV[0] + " " + nameV[i-1];
                        break;
                    }
                }
                if (name.length() > 15){
                    if((nameV[0].length() + nameV[aux].length() + 1) <= 44){
                        name = nameV[0] + " " + nameV[aux];
                    }
                    else{
                        name = name.substring(0, 14);
                        certificado.get().setCertificadoStatus(new CertificadoStatus(false));
                    }
                }
            } else{
                if (name.length() > 15) {
                    value = 30;
                } else {
                    value = 50;
                }

            }

            document.add(createParagraph(name,
                    FontFactory.getFont(FontFactory.TIMES_ITALIC, value, BaseColor.RED), Element.ALIGN_CENTER, value));

            String text = "incrito sob o número do CPF " + certificado.get().getParticipante().getCpf()
                    + " por participar do evento " +
                    certificado.get().getEvento().getNome() + ", ministrado por " + certificado.get().getEvento().getNomePalestrante() + " na data de " + certificado.get().getEvento().getData()
                    + ", com carga horária de " +
                    certificado.get().getEvento().getCargaHoraria() + " horas.";

            document.add(createParagraph(text,
                    FontFactory.getFont(FontFactory.TIMES_ROMAN, 16), Element.ALIGN_CENTER, 30));

            Date today = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
            String strDate = "Data de emissão: " + dateFormat.format(today);

            document.add(createParagraph(strDate,
                    FontFactory.getFont(FontFactory.TIMES_ROMAN, 12), Element.ALIGN_CENTER, 30));

            document.add(createParagraph("VALIDAÇÃO ONLINE",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10), Element.ALIGN_RIGHT, 60));

            document.add(createParagraph(certificado.get().getCodigo(),
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10), Element.ALIGN_RIGHT));

            PdfContentByte canvas = writer.getDirectContentUnder();
            Image image = Image.getInstance(pathImage);
            image.scaleAbsolute(PageSize.A4.rotate());
            image.setAbsolutePosition(0, 0);
            canvas.addImage(image);

        } catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
        } finally {
            document.close();
        }
    }

    private Paragraph createParagraph(String mesage, Font font, int alignment, int leading) {
        Paragraph paragraph = new Paragraph(mesage, font);
        paragraph.setAlignment(alignment);
        paragraph.setPaddingTop(0);
        paragraph.setLeading(leading);
        return paragraph;
    }

    private Paragraph createParagraph(String mesage, Font font, int alignment) {
        Paragraph paragraph = new Paragraph(mesage, font);
        paragraph.setAlignment(alignment);
        return paragraph;
    }
}