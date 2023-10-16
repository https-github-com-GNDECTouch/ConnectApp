package com.example.gndectouch;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FaculityActivity extends AppCompatActivity {
    TextView mentorname;
    private ActivityResultLauncher<Intent> filePickerLauncher;
    private ActivityResultLauncher<Intent> filePickerLauncher1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculity);

        LinearLayout whole=(LinearLayout) findViewById(R.id.linearLayout);

        mentorname=(TextView)findViewById(R.id.mentorname);
        Intent intent = getIntent();
        ArrayList<String> mentorlist = (ArrayList<String> )intent.getStringArrayListExtra("data");

        // Now you can use the mentorlist in this activity
        if (mentorlist != null) {
            for (String name : mentorlist ) {
                // Do something with each name
                if(name!=null)
                      Toast.makeText(this, "hello ji "+name, Toast.LENGTH_SHORT).show();
            }
        }
        //Toast.makeText(this, mentorlist.size(), Toast.LENGTH_SHORT).show();
       // mentorname.setText(builder.toString());


        // Now you can use the mentorlist in this activity


        List<Student> books = readBooksFromCSV("books.txt");

        //take student csv
        Button btn=findViewById(R.id.selectFileButton);
        Button btn1=findViewById(R.id.selectFileButton1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilePicker1();
            }


        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilePicker();
            }


        });


        filePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri selectedFileUri = result.getData().getData();
                        if (selectedFileUri != null) {
                            displayFileContent(selectedFileUri);
                        }

                        // Handle the selected file URI (e.g., upload it to a server).
                        // For this example, we are not implementing the file upload.
                    }
                }
        );
        filePickerLauncher1 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri selectedFileUri = result.getData().getData();
                        if (selectedFileUri != null) {
                            displayFileContent1(selectedFileUri);
                        }

                        // Handle the selected file URI (e.g., upload it to a server).
                        // For this example, we are not implementing the file upload.
                    }
                }
        );

    }
    private void displayFileContent(Uri fileUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(fileUri);
            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append('\n');
                }
                inputStream.close();
                TextView data=findViewById(R.id.csvdata);
                data.setText(content.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void displayFileContent1(Uri fileUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(fileUri);
            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append('\n');
                }
                inputStream.close();
                TextView data=findViewById(R.id.csvdata1);
                data.setText(content.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        filePickerLauncher.launch(intent);
    }
    private void openFilePicker1() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        filePickerLauncher1.launch(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                Toast.makeText(FaculityActivity.this, "HELLO MONIKA 1", Toast.LENGTH_SHORT).show();
                Uri selectedFileUri = data.getData();
                TextView csv=findViewById(R.id.csvdata);
                csv.setText(selectedFileUri.toString());
                // You can use the selected file URI to access the chosen file.
                // For example, you can open and read the file's contents.
            }
            else{
                Toast.makeText(FaculityActivity.this, "Null data", Toast.LENGTH_SHORT).show();
            }
        }
    }





//selectFileButton.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            // Start an Intent to open the file picker
//            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            intent.setType("*/*");  // You can specify a MIME type if you want to filter file types.
//
//            startActivityForResult(intent, 1); // Use a unique request code
//        }
//    });
//
//









    private static List<Student> readBooksFromCSV(String fileName) {
        List<Student> books = new ArrayList<>();
        Path pathToFile = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            pathToFile = Paths.get(fileName);
        }

        // create an instance of BufferedReader
        // using try with resource, Java 7 feature to close resources
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            try (BufferedReader br = Files.newBufferedReader(pathToFile,
                    StandardCharsets.US_ASCII)) {

                // read the first line from the text file
                String line = br.readLine();

                // loop until all lines are read
                while (line != null) {

                    // use string.split to load a string array with the values from
                    // each line of
                    // the file, using a comma as the delimiter
                    String[] attributes = line.split(",");
//
//                    Student book = createBook(attributes);
//
//                    // adding book into ArrayList
//                    books.add(book);

                    // read next line before looping
                    // if end of file reached, line would be null
                    line = br.readLine();
                }

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        return books;
    }

    private static Student createBook(String[] metadata) {
        String name = metadata[0];
        int price = Integer.parseInt(metadata[1]);
        String author = metadata[2];

        // create and return book of this metadata
        return new Student();
    }


}
