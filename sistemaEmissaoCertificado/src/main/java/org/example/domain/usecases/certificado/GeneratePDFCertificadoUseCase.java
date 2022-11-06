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
import org.example.domain.usecases.utils.EntityNotFoundException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class GeneratePDFCertificadoUseCase {
    private final String pathRelatorio = "relatorios/";
    
    private CertificadoDAO certificadoDAO;

    public GeneratePDFCertificadoUseCase(CertificadoDAO certificadoDAO) {
        this.certificadoDAO = certificadoDAO;
    }

    public void generatePDF(String codigo, String imagem){
        Optional<Certificado> certificado;
        certificado = certificadoDAO.findOne(codigo);
        
        if(certificado.isEmpty()){
            throw new EntityNotFoundException("Certificado não encontrado.");
        }

        certificado.get().getParticipante();
        Document document = new Document(PageSize.A4.rotate(), 200, 200, 130, 130);
        //Document document = new Document(PageSize.A4_LANDSCAPE);

        try {

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pathRelatorio + codigo + ".pdf"));
            document.open();

            Paragraph titulo = new Paragraph("CERTIFICADO", FontFactory.getFont(FontFactory.TIMES_ROMAN,45,BaseColor.GRAY));
            titulo.setAlignment(Element.ALIGN_CENTER);            
            document.add(titulo);

            Paragraph subtitulo = new Paragraph(certificado.get().getEvento().getNome().toUpperCase(), FontFactory.getFont(FontFactory.TIMES,25,BaseColor.DARK_GRAY));
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            subtitulo.setLeading(20);
            subtitulo.setSpacingBefore(0);
            document.add(subtitulo);

            Paragraph separador = new Paragraph("______________", FontFactory.getFont(FontFactory.HELVETICA,20));
            separador.setAlignment(Element.ALIGN_CENTER);
            separador.setPaddingTop(0);
            separador.setLeading(0);
            document.add(separador);

            Paragraph paragraph1 = new Paragraph("Este certificado está conferido a", FontFactory.getFont(FontFactory.TIMES_ROMAN,16));
            paragraph1.setAlignment(Element.ALIGN_CENTER);        
            paragraph1.setPaddingTop(0);
            paragraph1.setLeading(30);        
            document.add(paragraph1);

            Paragraph participante = new Paragraph(certificado.get().getParticipante().getNome(), FontFactory.getFont(FontFactory.TIMES_ITALIC,70,BaseColor.RED));
            participante.setPaddingTop(0);
            participante.setLeading(50);
            participante.setAlignment(Element.ALIGN_CENTER);            
            document.add(participante);

            String textoCorpo = "incrito sob o número do CPF " + certificado.get().getParticipante().getCpf() + " por participar do evento " +
            certificado.get().getEvento().getNome() + " na data de " + certificado.get().getEvento().getData() + ", com carga horária de " + 
            certificado.get().getEvento().getCargaHoraria() + " horas.";

            Paragraph paragraph2 = new Paragraph(textoCorpo, FontFactory.getFont(FontFactory.TIMES_ROMAN,16));
            paragraph2.setAlignment(Element.ALIGN_CENTER);        
            document.add(paragraph2);

            
            Date today = Calendar.getInstance().getTime();  
            DateFormat dateFormat =  new  SimpleDateFormat("dd MMMM yyyy");  
            String strDate = dateFormat.format(today);

            Paragraph paragraph3 = new Paragraph("Data de emissão: " + strDate, FontFactory.getFont(FontFactory.TIMES_ROMAN,16));
            paragraph3.setAlignment(Element.ALIGN_CENTER);        
            paragraph3.setPaddingTop(0);
            paragraph3.setLeading(30);  
            document.add(paragraph3);

            Paragraph paragraph4 = new Paragraph("VALIDAÇÃO ONLINE:" , FontFactory.getFont(FontFactory.HELVETICA_BOLD,12));
            paragraph4.setAlignment(Element.ALIGN_CENTER);  
            paragraph4.setPaddingTop(0);
            paragraph4.setLeading(30);      
            document.add(paragraph4);

            Paragraph paragraph5 = new Paragraph(certificado.get().codigo, FontFactory.getFont(FontFactory.HELVETICA_BOLD,12));
            paragraph5.setPaddingTop(0);
            //paragraph5.setLeading(50);
            paragraph5.setAlignment(Element.ALIGN_CENTER);        
            document.add(paragraph5);

            PdfContentByte canvas = writer.getDirectContentUnder();
            Image image = Image.getInstance(imagem);
            image.scaleAbsolute(PageSize.A4.rotate());
            image.setAbsolutePosition(0, 0);
            canvas.addImage(image);
            
        } catch(DocumentException de) {
            System.err.println(de.getMessage());
        } catch(IOException ioe) {
            System.err.println(ioe.getMessage());
        } finally {
            document.close();
        }


    }
}
