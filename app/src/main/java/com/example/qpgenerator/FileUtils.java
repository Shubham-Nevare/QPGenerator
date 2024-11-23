package com.example.qpgenerator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileUtils {

    public static void selectFile(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        activity.startActivityForResult(Intent.createChooser(intent, "Select PDF File"), requestCode);
    }

    public static void handleFileSelection(Activity activity, int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = activity.getContentResolver().openInputStream(uri);
                File directory = getDirectory(activity);
                File file = new File(directory, (requestCode == 100 ? "Syllabus.pdf" : "PreviousQP.pdf"));
                FileOutputStream outputStream = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }

                outputStream.close();
                inputStream.close();

                Toast.makeText(activity, file.getName() + " uploaded!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(activity, "Error uploading file!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static File getDirectory(Context context) {
        File directory = new File(context.getExternalFilesDir(null), "UploadedFiles");
        if (!directory.exists()) {
            directory.mkdir();
        }
        return directory;
    }
}
