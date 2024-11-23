package com.example.qpgenerator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText subjectName, subjectCode;
    private Button uploadSyllabusBtn, uploadPrevQPBtn, generateQPBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        subjectName = findViewById(R.id.subject_name);
        subjectCode = findViewById(R.id.subject_code);
        uploadSyllabusBtn = findViewById(R.id.upload_syllabus_btn);
        uploadPrevQPBtn = findViewById(R.id.upload_prev_qp_btn);
        generateQPBtn = findViewById(R.id.generate_qp_btn);

        // Set up button click listeners
        uploadSyllabusBtn.setOnClickListener(v -> selectFile("Select Syllabus PDF", 100));
        uploadPrevQPBtn.setOnClickListener(v -> selectFile("Select Previous QP PDF", 200));
        generateQPBtn.setOnClickListener(v -> generateQuestionPaper());
    }

    // Method to select a file
    private void selectFile(String title, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent, title), requestCode);
    }

    // Handle file selection result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case 100:
                    Toast.makeText(this, "Syllabus uploaded!", Toast.LENGTH_SHORT).show();
                    break;
                case 200:
                    Toast.makeText(this, "Previous QP uploaded!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    // Method to generate the question paper
    private void generateQuestionPaper() {
        String name = subjectName.getText().toString();
        String code = subjectCode.getText().toString();

        if (name.isEmpty() || code.isEmpty()) {
            Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Generating Question Paper...", Toast.LENGTH_LONG).show();
            // Logic to analyze and generate question paper goes here
        }
    }
}
