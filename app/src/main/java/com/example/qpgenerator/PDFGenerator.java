package com.example.qpgenerator;

import android.content.Context;
import android.widget.Toast;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PDFGenerator {

    public static void generateQuestionPaper(Context context, File syllabusFile, File previousQPFile) {
        try {
            // Read input files
            String syllabusContent = readPDF(syllabusFile);
            String previousQPContent = readPDF(previousQPFile);

            // Extract questions
            List<String> questions = extractQuestions(syllabusContent, previousQPContent);

            // Generate output file
            File outputFile = new File(FileUtils.getDirectory(context), "GeneratedQuestionPaper.pdf");
            createPDF(outputFile, questions);

            Toast.makeText(context, "Question Paper Generated: " + outputFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error generating question paper!", Toast.LENGTH_SHORT).show();
        }
    }

    private static String readPDF(File pdfFile) throws Exception {
        PDDocument document = PDDocument.load(pdfFile);
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        document.close();
        return text;
    }

    private static List<String> extractQuestions(String syllabus, String prevQuestions) {
        List<String> questions = new ArrayList<>();
        String[] topics = syllabus.split("\n");

        for (String topic : topics) {
            if (prevQuestions.contains(topic)) {
                questions.add("Explain " + topic);
            } else {
                questions.add("What is " + topic + "?");
            }
        }

        return questions;
    }

    private static void createPDF(File file, List<String> questions) throws Exception {
        PdfWriter writer = new PdfWriter(file);
        com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("Generated Question Paper").setBold().setFontSize(18));
        document.add(new Paragraph("\n"));

        int questionNo = 1;
        for (String question : questions) {
            document.add(new Paragraph(questionNo++ + ". " + question));
        }

        document.close();
    }
}
