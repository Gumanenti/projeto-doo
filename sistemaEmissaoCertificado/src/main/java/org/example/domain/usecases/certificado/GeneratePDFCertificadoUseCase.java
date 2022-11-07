package org.example.domain.usecases.certificado;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import javax.swing.text.ParagraphView;

import org.example.domain.entities.certificado.Certificado;
import org.example.domain.entities.certificado.CertificadoStatus;
import org.example.domain.usecases.utils.EntityNotFoundException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.RomanList;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class GeneratePDFCertificadoUseCase {
    private final String pathRelatorio = "relatorios/";

    private CertificadoDAO certificadoDAO;

    public GeneratePDFCertificadoUseCase(CertificadoDAO certificadoDAO) {
        this.certificadoDAO = certificadoDAO;
    }

    public void generatePDF(String code, String pathImage) {
        Optional<Certificado> certificado;
        certificado = certificadoDAO.findOne(code);

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
                    FontFactory.getFont(FontFactory.TIMES, 20, BaseColor.GRAY), Element.ALIGN_CENTER, 0, 25));

            // document.add(createParagraph("______________",
            //         FontFactory.getFont(FontFactory.TIMES, 20, BaseColor.GRAY), Element.ALIGN_CENTER, 0, 0));

            document.add(createParagraph("Este certificado está conferido a",
                    FontFactory.getFont(FontFactory.TIMES_ROMAN, 16), Element.ALIGN_CENTER, 0, 30));

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
                        certificado.get().setCertificadoStatus(new CertificadoStatus(false));;
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
                    FontFactory.getFont(FontFactory.TIMES_ITALIC, value, BaseColor.RED), Element.ALIGN_CENTER, 0, value));

            String text = "incrito sob o número do CPF " + certificado.get().getParticipante().getCpf()
                    + " por participar do evento " +
                    certificado.get().getEvento().getNome() + ", ministrado por " + certificado.get().getEvento().getNomePalestrante() + " na data de " + certificado.get().getEvento().getData()
                    + ", com carga horária de " +
                    certificado.get().getEvento().getCargaHoraria() + " horas.";

            document.add(createParagraph(text,
                    FontFactory.getFont(FontFactory.TIMES_ROMAN, 16), Element.ALIGN_CENTER,0,30));

            Date today = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
            String strDate = "Data de emissão: " + dateFormat.format(today);

            document.add(createParagraph(strDate,
                    FontFactory.getFont(FontFactory.TIMES_ROMAN, 12), Element.ALIGN_CENTER, 0, 30));

            document.add(createParagraph("VALIDAÇÃO ONLINE",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10), Element.ALIGN_RIGHT, 0, 60));

            document.add(createParagraph(certificado.get().codigo,
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10), Element.ALIGN_RIGHT));
                    
            PdfContentByte canvas = writer.getDirectContentUnder();
            Image image = Image.getInstance(pathImage);
            image.scaleAbsolute(PageSize.A4.rotate());
            image.setAbsolutePosition(0, 0);
            canvas.addImage(image);

        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } finally {
            document.close();
        }
    }

    private Paragraph createParagraph(String mesage, Font font, int alignment, int paddingTop, int leading) {
        Paragraph paragraph = new Paragraph(mesage, font);
        paragraph.setAlignment(alignment);
        paragraph.setPaddingTop(paddingTop);
        paragraph.setLeading(leading);
        return paragraph;
    }

    private Paragraph createParagraph(String mesage, Font font, int alignment) {
        Paragraph paragraph = new Paragraph(mesage, font);
        paragraph.setAlignment(alignment);
        return paragraph;
    }
}
