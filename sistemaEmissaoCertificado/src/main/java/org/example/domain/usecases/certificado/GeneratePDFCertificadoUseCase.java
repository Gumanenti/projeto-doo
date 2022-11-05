package org.example.domain.usecases.certificado;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import org.example.domain.entities.certificado.Certificado;
import org.example.domain.usecases.utils.EntityNotFoundException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
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
        Document document = new Document(PageSize.A4.rotate());
        //Document document = new Document(PageSize.A4_LANDSCAPE);

        try {

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pathRelatorio + codigo + ".pdf"));
            document.open();
            document.add(new Paragraph("Certificado!"));

            PdfContentByte canvas = writer.getDirectContentUnder();
            Image image = Image.getInstance(imagem);
            image.scaleAbsolute(PageSize.A4.rotate());
            image.setAbsolutePosition(0, 0);
            canvas.addImage(image);
            
            document.add(new Paragraph("Adicionando outro paragrafo", FontFactory.getFont(FontFactory.COURIER, 12)));

        } catch(DocumentException de) {
            System.err.println(de.getMessage());
        } catch(IOException ioe) {
            System.err.println(ioe.getMessage());
        } finally {
            document.close();
        }


    }
}
