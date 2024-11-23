package com.example.qpgenerator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private EditText subjectName, subjectCode;
    private Button uploadSyllabusBtn, uploadPrevQPBtn, generateQPBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        subjectName = findViewById(R.id.subject_name);
        subjectCode = findViewById(R.id.subject_code);
        uploadSyllabusBtn = findViewById(R.id.upload_syllabus_btn);
        uploadPrevQPBtn = findViewById(R.id.upload_prev_qp_btn);
        generateQPBtn = findViewById(R.id.generate_qp_btn);

        // Button Actions
        uploadSyllabusBtn.setOnClickListener(v -> FileUtils.selectFile(this, 100));
        uploadPrevQPBtn.setOnClickListener(v -> FileUtils.selectFile(this, 200));
        generateQPBtn.setOnClickListener(v -> {
            File syllabusFile = new File(FileUtils.getDirectory(this), "Syllabus.pdf");
            File previousQPFile = new File(FileUtils.getDirectory(this), "PreviousQP.pdf");

            if (!syllabusFile.exists() || !previousQPFile.exists()) {
                Toast.makeText(this, "Please upload all required files", Toast.LENGTH_SHORT).show();
                return;
            }

            PDFGenerator.generateQuestionPaper(this, syllabusFile, previousQPFile);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FileUtils.handleFileSelection(this, requestCode, resultCode, data);
    }
}
