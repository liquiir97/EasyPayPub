package easypay.service;

import easypay.service.dto.UplatnicaDTO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PdfGeneratorService {

    @Autowired
    NbsQrCode nbsQrCode;

    public byte[] generateSimplePdf(UplatnicaDTO uplatnicaDTO) {
        String imageQr = nbsQrCode.generateQrCode(uplatnicaDTO);
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            //SLIKA
            PDImageXObject pdImage = null;
            if (!imageQr.equalsIgnoreCase("Error")) {
                byte[] imageBytes = Base64.getDecoder().decode(imageQr);
                pdImage = PDImageXObject.createFromByteArray(document, imageBytes, "Image");
            }

            //contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            //contentStream.beginText();
            //contentStream.newLineAtOffset(100, 700);
            //contentStream.showText(content);
            //contentStream.endText();

            // Draw Uplatilac
            float rectWidth = 270;
            float rectHeight = 50;
            float rectXUplatilac = 10;
            float rectYUplatilac = PDRectangle.A4.getHeight() - rectHeight - 80;

            contentStream.setLineWidth(1.5f);
            contentStream.setNonStrokingColor(255, 255, 255); // Light gray fill color
            contentStream.addRect(rectXUplatilac, rectYUplatilac, rectWidth, rectHeight);
            contentStream.fill();

            // Draw Border of Uplatilac
            contentStream.setNonStrokingColor(0, 0, 0); // Black border color
            contentStream.addRect(rectXUplatilac, rectYUplatilac, rectWidth, rectHeight);
            contentStream.stroke();

            /*Second - Sifra Placanja*/
            float rectWidthSifraPlacanja = 50;
            float rectHeightSifraPlacanja = 25;
            float rectXSifraPlacanja = rectXUplatilac + rectWidth + 50;
            float rectYSifraPlacanja = rectYUplatilac;

            contentStream.setLineWidth(1.5f);
            contentStream.setNonStrokingColor(255, 255, 255);
            contentStream.addRect(rectXSifraPlacanja, rectYSifraPlacanja, rectWidthSifraPlacanja, rectHeightSifraPlacanja);
            contentStream.fill();

            /*Border for the second*/
            contentStream.setLineWidth(1.5f);
            contentStream.setNonStrokingColor(0, 0, 0); // Black border color
            contentStream.addRect(rectXSifraPlacanja, rectYSifraPlacanja, rectWidthSifraPlacanja, rectHeightSifraPlacanja);
            contentStream.stroke();

            /*Draw Valuta*/
            float rectWidthValuta = 50;
            float rectHeightValuta = 25;
            float rectXValuta = rectXSifraPlacanja + rectWidthSifraPlacanja + 25;
            float rectYValuta = rectYSifraPlacanja;

            contentStream.setLineWidth(1.5f);
            contentStream.setNonStrokingColor(255, 255, 255);
            contentStream.addRect(rectXValuta, rectYValuta, rectWidthValuta, rectHeightValuta);
            contentStream.fill();

            /*Border for the third*/
            contentStream.setLineWidth(1.5f);
            contentStream.setNonStrokingColor(0, 0, 0); // Black border color
            contentStream.addRect(rectXValuta, rectYValuta, rectWidthValuta, rectHeightValuta);
            contentStream.stroke();

            /*Draw Iznos*/
            float rectWidthIznos = 80;
            float rectHeightIznos = 25;
            float rectXIznos = rectXValuta + rectWidthValuta + 25;
            float rectYIznos = rectYValuta;

            contentStream.setLineWidth(1.5f);
            contentStream.setNonStrokingColor(255, 255, 255);
            contentStream.addRect(rectXIznos, rectYIznos, rectWidthIznos, rectHeightIznos);
            contentStream.fill();

            /*Border for the fourth*/
            contentStream.setLineWidth(1.5f);
            contentStream.setNonStrokingColor(0, 0, 0); // Black border color
            contentStream.addRect(rectXIznos, rectYIznos, rectWidthIznos, rectHeightIznos);
            contentStream.stroke();

            /*Draw Svrha Uplate*/
            float rectXSvrhaUplate = 10;
            float rectYSvrhaUplate = rectYUplatilac - 120;

            contentStream.setLineWidth(1.5f);
            contentStream.setNonStrokingColor(255, 255, 255); // Light gray fill color
            contentStream.addRect(rectXSvrhaUplate, rectYSvrhaUplate, rectWidth, rectHeight);
            contentStream.fill();

            // Draw Border of Svrha Uplate
            contentStream.setNonStrokingColor(0, 0, 0); // Black border color
            contentStream.addRect(rectXSvrhaUplate, rectYSvrhaUplate, rectWidth, rectHeight);
            contentStream.stroke();

            /*Draw Racun Primaoca*/
            float rectXRacunPrimaoca = rectXUplatilac + rectWidth + 50;
            float rectYRacunPrimaoca = rectYSifraPlacanja - 120;

            contentStream.setLineWidth(1.5f);
            contentStream.setNonStrokingColor(255, 255, 255); // Light gray fill color
            contentStream.addRect(rectXRacunPrimaoca, rectYRacunPrimaoca, rectWidth, rectHeight);
            contentStream.fill();

            // Draw Border of Racun Primaoca
            float rectWidthRacunPrimaoca = 230;
            contentStream.setNonStrokingColor(0, 0, 0); // Black border color
            contentStream.addRect(rectXRacunPrimaoca, rectYRacunPrimaoca, rectWidthRacunPrimaoca, rectHeight);
            contentStream.stroke();

            /*Draw Primalac*/
            float rectXPrimalac = 10;
            float rectYPrimalac = rectYSvrhaUplate - 120;

            contentStream.setLineWidth(1.5f);
            contentStream.setNonStrokingColor(255, 255, 255); // Light gray fill color
            contentStream.addRect(rectXPrimalac, rectYPrimalac, rectWidth, rectHeight);
            contentStream.fill();

            // Draw Border of Primalac
            contentStream.setNonStrokingColor(0, 0, 0); // Black border color
            contentStream.addRect(rectXPrimalac, rectYPrimalac, rectWidth, rectHeight);
            contentStream.stroke();

            /*Draw Model*/
            float rectXModel = rectXSifraPlacanja;
            float rectYModel = rectYRacunPrimaoca - 120;
            float rectWidthModel = rectWidthSifraPlacanja;
            float rectHeightModel = rectHeightSifraPlacanja;

            contentStream.setLineWidth(1.5f);
            contentStream.setNonStrokingColor(255, 255, 255); // Light gray fill color
            contentStream.addRect(rectXModel, rectYModel, rectWidthModel, rectHeightModel);
            contentStream.fill();

            // Draw Border of Model
            contentStream.setNonStrokingColor(0, 0, 0); // Black border color
            contentStream.addRect(rectXModel, rectYModel, rectWidthModel, rectHeightModel);
            contentStream.stroke();

            /*Draw Poziv na broj*/
            float rectXPozivNaBroj = rectXValuta;
            float rectYPozivNaBroj = rectYModel;
            float rectWidthPozivNaBroj = 150;
            contentStream.setLineWidth(1.5f);
            contentStream.setNonStrokingColor(255, 255, 255); // Light gray fill color
            contentStream.addRect(rectXPozivNaBroj, rectYPozivNaBroj, rectWidthPozivNaBroj, rectHeightModel);
            contentStream.fill();

            // Draw Border of Poziv na broj
            contentStream.setNonStrokingColor(0, 0, 0); // Black border color
            contentStream.addRect(rectXPozivNaBroj, rectYPozivNaBroj, rectWidthPozivNaBroj, rectHeightModel);
            contentStream.stroke();

            /*Draw vertical line*/
            contentStream.setLineWidth(1.5f);
            contentStream.setNonStrokingColor(0, 0, 0);

            float startX = rectXUplatilac + rectWidth + 25;
            float startY = rectYUplatilac + rectHeight + 10;

            float lineLength = 305;

            contentStream.moveTo(startX, startY);
            contentStream.lineTo(startX, startY - lineLength);
            contentStream.stroke();
            /*------------------------------------------------------------------------------------------*/

            /*Draw line signature*/
            contentStream.setLineWidth(1.5f);
            contentStream.setNonStrokingColor(0, 0, 0);

            float startXSignature = rectXPrimalac;
            float startYSignature = rectYPrimalac - 50;

            float lineLengthSignature = rectWidth - 25;

            contentStream.moveTo(startXSignature, startYSignature);
            contentStream.lineTo(startXSignature + lineLengthSignature, startYSignature);
            contentStream.stroke();
            /*------------------------------------------------------------------------------------------*/

            /*Draw line date*/
            contentStream.setLineWidth(1.5f);
            contentStream.setNonStrokingColor(0, 0, 0);

            float startXDate = startXSignature + lineLengthSignature + 15;
            float startYDate = startYSignature;

            float lineLengthDate = rectWidthModel + 30;

            contentStream.moveTo(startXDate, startYDate);
            contentStream.lineTo(startXDate + lineLengthDate, startYDate);
            contentStream.stroke();

            //Draw qr image
            if (!imageQr.equalsIgnoreCase("Error")) {
                float xI = startXDate + lineLengthDate + 50;
                float yI = startYDate - 60;
                float widthI = 100;
                float heightI = 100;

                contentStream.drawImage(pdImage, xI, yI, widthI, heightI);
            }

            /*------------------------------------------------------------------------------------------*/

            /*Label on top*/
            float textXTopLabel = rectXIznos;
            float textYTopLabel = rectYIznos + rectHeightIznos + 80;

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
            contentStream.newLineAtOffset(textXTopLabel, textYTopLabel);
            contentStream.showText("NALOG ZA UPLATU");
            contentStream.endText();

            /*Label forSignature*/
            float textXSignature = startXSignature;
            float textYSignature = startYSignature - 10;

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 6);
            contentStream.newLineAtOffset(textXSignature, textYSignature);
            contentStream.showText("pecat i potpis uplatioca");
            contentStream.endText();

            /*Label for Date*/
            float textXDate = startXDate;
            float textYDate = startYDate - 10;

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 6);
            contentStream.newLineAtOffset(textXDate, textYDate);
            contentStream.showText("datum uplate");
            contentStream.endText();

            /*Text on line for date*/
            float textXOnDate = startXDate;
            float textYOnDate = startYDate + 5;

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(textXOnDate, textYOnDate);
            contentStream.showText(uplatnicaDTO.getDatumKreiranja().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            contentStream.endText();
            // Draw Text inside Rectangle 1
            float textXLabel = rectXUplatilac + 10;
            float textYLabel = rectYUplatilac + rectHeight + 10;

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 8);
            contentStream.setStrokingColor(1f, 0f, 0f);
            contentStream.newLineAtOffset(textXLabel, textYLabel);
            contentStream.showText("Uplatilac");
            contentStream.endText();

            float textX = rectXUplatilac + 10;
            float textY = rectYUplatilac + rectHeight - 20;

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.setStrokingColor(1f, 0f, 0f);
            contentStream.newLineAtOffset(textX, textY);
            contentStream.showText(uplatnicaDTO.getUplatilac());
            contentStream.endText();

            /*--------------------*/
            /*Second Rectangle*/
            /*float rectX2 = rectX + rectWidth + 50;
            float rectY2 = rectY;

            contentStream.setLineWidth(1.5f);
            contentStream.setNonStrokingColor(255, 255, 255);
            contentStream.addRect(rectX2, rectY2, rectWidth, rectHeight);
            contentStream.fill();*/

            /*Border for the second*/
            /*contentStream.setLineWidth(1.5f);
            contentStream.setNonStrokingColor(0, 0, 0); // Black border color
            contentStream.addRect(rectX2, rectY2, rectWidth, rectHeight);
            contentStream.stroke();*/

            /*Text for the second*/

            float textXSifraPlacanjaLabel = rectXSifraPlacanja;
            float textYSifraPlacanjaLabel = rectYSifraPlacanja + rectHeightSifraPlacanja + 10;

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 8);
            contentStream.newLineAtOffset(textXSifraPlacanjaLabel, textYSifraPlacanjaLabel);
            contentStream.showText("Sifra placanja");
            contentStream.endText();

            float textXSifraPlacanja = rectXSifraPlacanja + 10;
            float textYSifraPlacanja = rectYSifraPlacanja + rectHeightSifraPlacanja - 15;

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(textXSifraPlacanja, textYSifraPlacanja);
            contentStream.showText(uplatnicaDTO.getSifraPlacanja());
            contentStream.endText();

            /*Text for the third*/

            float textXValutaLabel = rectXValuta;
            float textYValutaLabel = rectYValuta + rectHeightValuta + 10;

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 8);
            contentStream.newLineAtOffset(textXValutaLabel, textYValutaLabel);
            contentStream.showText("Valuta");
            contentStream.endText();

            float textXValuta = rectXValuta + 10;
            float textYValuta = rectYValuta + rectHeightValuta - 15;

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(textXValuta, textYValuta);
            contentStream.showText(uplatnicaDTO.getValuta());
            contentStream.endText();

            /*Text for the fourth*/

            float textXIznosLabel = rectXIznos;
            float textYIznosLabel = rectYIznos + rectHeightIznos + 10;

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 8);
            contentStream.newLineAtOffset(textXIznosLabel, textYIznosLabel);
            contentStream.showText("Iznos");
            contentStream.endText();

            float textXIznos = rectXIznos + 10;
            float textYIznos = rectYIznos + rectHeightIznos - 15;

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(textXIznos, textYIznos);
            contentStream.showText(uplatnicaDTO.getIznos() + ",00".toString());
            contentStream.endText();
            /*--------------------*/

            /*Draw Text for the Svrha Uplate*/
            float textXSvrhaUplateLabel = rectXSvrhaUplate;
            float textYSvrhaUplateLabel = rectYSvrhaUplate + rectHeight + 10;

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 8);
            contentStream.newLineAtOffset(textXSvrhaUplateLabel, textYSvrhaUplateLabel);
            contentStream.showText("Svrha uplate");
            contentStream.endText();

            float textXSvrhaUplate = rectXSvrhaUplate + 10;
            float textYSvrhaUplate = rectYSvrhaUplate + rectHeight - 20;

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(textXSvrhaUplate, textYSvrhaUplate);
            contentStream.showText(uplatnicaDTO.getSvrhaUplate());
            contentStream.endText();
            /*-----------------------------------------------------------*/

            /*Draw Text for the Racun Primaoca*/
            float textXRacunPrimaocaLabel = rectXRacunPrimaoca;
            float textYRacunPrimaocaLabel = rectYRacunPrimaoca + rectHeight + 10;

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 8);
            contentStream.newLineAtOffset(textXRacunPrimaocaLabel, textYRacunPrimaocaLabel);
            contentStream.showText("Racun primaoca");
            contentStream.endText();

            float textXRacunPrimaoca = rectXRacunPrimaoca + 10;
            float textYRacunPrimaoca = rectYRacunPrimaoca + rectHeight - 20;

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(textXRacunPrimaoca, textYRacunPrimaoca);
            contentStream.showText(uplatnicaDTO.getRacunPrimaoca());
            contentStream.endText();
            /*-----------------------------------------------------------*/

            /*Draw Text for the Primalac*/
            float textXPrimalacLabel = rectXPrimalac;
            float textYPrimalacLabel = rectYPrimalac + rectHeight + 10;

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 8);
            contentStream.newLineAtOffset(textXPrimalacLabel, textYPrimalacLabel);
            contentStream.showText("Primalac");
            contentStream.endText();

            float textXPrimalac = rectXPrimalac + 10;
            float textYPrimalac = rectYPrimalac + rectHeight - 20;

            /*contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(textXPrimalac, textYPrimalac);*/
            if (uplatnicaDTO.getPrimalac().length() > 40) {
                String firstPart = uplatnicaDTO.getPrimalac().substring(0, 38);
                String seccondPart = uplatnicaDTO.getPrimalac().substring(38, uplatnicaDTO.getPrimalac().length());
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(textXPrimalac, textYPrimalac);
                contentStream.showText(firstPart);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(textXPrimalac, textYPrimalac - 20);
                contentStream.showText(seccondPart);
                contentStream.endText();
            } else {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(textXPrimalac, textYPrimalac);
                contentStream.showText(uplatnicaDTO.getPrimalac());
                contentStream.endText();
            }

            /*-----------------------------------------------------------------------*/

            /*Draw Text for the Model*/
            float textXModelLabel = rectXModel;
            float textYModelLabel = rectYModel + rectHeightModel + 10;

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 8);
            contentStream.newLineAtOffset(textXModelLabel, textYModelLabel);
            contentStream.showText("Model");
            contentStream.endText();

            float textXModel = rectXModel + 10;
            float textYModel = rectYModel + rectHeightModel - 15;

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(textXModel, textYModel);
            contentStream.showText(uplatnicaDTO.getModel() != null ? uplatnicaDTO.getModel() : "");
            contentStream.endText();
            /*-----------------------------------------------------------------------*/

            /*Draw Text for the Poziv na broj*/
            float textXPozivNaBrojLabel = rectXPozivNaBroj;
            float textYPozivNaBrojLabel = rectYPozivNaBroj + rectHeightModel + 10;

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 8);
            contentStream.newLineAtOffset(textXPozivNaBrojLabel, textYPozivNaBrojLabel);
            contentStream.showText("Poziv na broj");
            contentStream.endText();

            float textXPozivNaBroj = rectXPozivNaBroj + 10;
            float textYPozivNaBroj = rectYPozivNaBroj + rectHeightModel - 15;

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(textXPozivNaBroj, textYPozivNaBroj);
            contentStream.showText(uplatnicaDTO.getPozivNaBroj() != null ? uplatnicaDTO.getPozivNaBroj() : "");
            contentStream.endText();
            /*-----------------------------------------------------------------------*/

            contentStream.close();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
