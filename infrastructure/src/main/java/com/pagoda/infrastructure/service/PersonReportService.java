package com.pagoda.infrastructure.service;

import com.lowagie.text.pdf.BaseFont;
import com.pagoda.application.GeneratePersonReport;
import com.pagoda.application.PersonWithID;
import com.pagoda.core.model.Gender;
import com.pagoda.core.model.Person;
import com.pagoda.core.model.PersonType;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class PersonReportService implements GeneratePersonReport {
    private final String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("'Ngày' dd 'Tháng' MM 'Năm' yyyy"));
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    @Override
    public byte[] generateSinglePersonReport(PersonWithID personWithID) {
        Person person = personWithID.person();
        String template = loadTemplate("templates/person-single.html");
        String filledTemplate = template
                .replace("{{lastName}}", person.lastName())
                .replace("{{firstName}}", person.firstName())
                .replace("{{gender}}", translateGender(person.gender()))
                .replace("{{birthDate}}", formatDate(person.birthDate()))
                .replace("{{dharmaName}}", person.dharmaName())
                .replace("{{ethnicity}}", person.ethnicity())
                .replace("{{hometown}}", person.hometown())
                .replace("{{birthPlace}}", person.birthPlace())
                .replace("{{email}}", person.email())
                .replace("{{idIssuePlace}}", person.idIssuePlace())
                .replace("{{idIssueDate}}", formatDate(person.idIssueDate()))
                .replace("{{idNumber}}", person.idNumber())
                .replace("{{phoneNumber}}", person.phoneNumber())
                .replace("{{permanentResidence}}", person.permanentResidence())
                .replace("{{temporaryResidence}}", person.temporaryResidence())
                .replace("{{nationality}}", person.nationality())
                .replace("{{ordinationDate}}", formatDate(person.ordinationDate()))
                .replace("{{currentDate}}", currentDate);

        return generatePdfFromHtml(filledTemplate);
    }

    private String translateGender(Gender gender) {
        return switch (gender) {
            case MALE -> "Nam";
            case FEMALE -> "Nữ";
        };
    }

    private String formatDate(LocalDate date) {
        return date != null ? date.format(DATE_FORMAT) : "";
    }

    @Override
    public byte[] generatePersonListReport(List<PersonWithID> persons) {
        String template = loadTemplate("templates/person-list.html");

        StringBuilder rows = new StringBuilder();
        int index = 1;
        for (PersonWithID p : persons) {
            Person person = p.person();
            rows.append("<tr>")
                    .append("<td>").append(index++).append("</td>")
                    .append("<td>").append(person.lastName()).append("</td>")
                    .append("<td>").append(person.firstName()).append("</td>")
                    .append("<td>").append(formatDate(person.birthDate())).append("</td>")
                    .append("<td>").append(person.hometown()).append("</td>")
                    .append("<td>").append(person.dharmaName()).append("</td>")
                    .append("<td>").append(translateType(person.type())).append("</td>")
                    .append("</tr>");
        }

        String filledTemplate = template.replace("{{rows}}", rows.toString());

        return generatePdfFromHtml(filledTemplate);
    }

    private String translateType(PersonType type) {
        return switch (type) {
            case BUDDHIST -> "Phật tử";
            case LAY_BROTHER -> "Chú tiểu";
            case MASTER -> "Sư thầy";
        };
    }

    private String loadTemplate(String path) {
        try (InputStream is = new ClassPathResource(path).getInputStream();
             Scanner scanner = new Scanner(is, StandardCharsets.UTF_8)) {
            return scanner.useDelimiter("\\A").next();
        } catch (Exception e) {
            throw new RuntimeException("Cannot load template: " + path, e);
        }
    }

    private byte[] generatePdfFromHtml(String htmlContent) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            // Register custom font
            renderer.getFontResolver().addFont(
                    "fonts/DejaVuSerif.ttf",
                    BaseFont.IDENTITY_H,  // Important for Unicode!
                    BaseFont.EMBEDDED
            );
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }


}
