package com.example.gndectouch;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer;
import org.apache.commons.math3.ml.distance.EuclideanDistance;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UploadData extends Fragment {
    private ActivityResultLauncher<Intent> filePickerLauncher;
    private ActivityResultLauncher<Intent> filePickerLauncher1;
    CSVReader reader;
    CSVReader reader1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_data, container, false);


        TextView mentorcsv = view.findViewById(R.id.mentorcsv);
        TextView studentcsv = view.findViewById(R.id.studentcsv);
        TextView output=view.findViewById(R.id.output);

        Button mentorlist = view.findViewById(R.id.uploadbuttonformentor);
        Button menteelist = view.findViewById(R.id.uploadbuttonformentee);
        Button alotmentor = view.findViewById(R.id.alotmentortomentee);


        //download file after mentor alotment


        filePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Uri selectedFileUri = data.getData();
                    try {
                        InputStream inputStream = requireContext().getContentResolver().openInputStream(selectedFileUri);
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        this.reader = new CSVReader(inputStreamReader);
                        mentorcsv.setText("mentor.csv");
                    } catch (IOException e) {
                        // Handle any IO exceptions.
                    }
                }
            }
        });



        filePickerLauncher1 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Uri selectedFileUri = data.getData();
                    try {
                        InputStream inputStream = requireContext().getContentResolver().openInputStream(selectedFileUri);
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        this.reader1 = new CSVReader(inputStreamReader);
                        studentcsv.setText("student.csv");
                    } catch (IOException e) {
                        // Handle any IO exceptions.
                    }
                }
            }
        });

        mentorlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                filePickerLauncher.launch(intent);
            }
        });

        menteelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                filePickerLauncher1.launch(intent);
            }
        });

        alotmentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<DoublePoint> studentFeatures = new ArrayList<>();
                    List<String> studentNames = new ArrayList<>();
                    List<String> studentemail = new ArrayList<>();
                    List<String> studentphone = new ArrayList<>();
                    List<String> studentroll = new ArrayList<>();
                    CSVReader studentReader = reader1;
                    String[] studentRow;
                    boolean skipStudentHeader = true;
                    int rowCount = 0;
                    while ((studentRow = studentReader.readNext()) != null) {
                        if (skipStudentHeader) {
                            skipStudentHeader = false;
                            continue; // Skip the header row in the student CSV
                        }
                        rowCount++;

                        //Toast.makeText(requireContext(), studentRow[1]+"  hehe ", Toast.LENGTH_SHORT).show();
                        double cgpa = Double.parseDouble(studentRow[3]); // Assuming CGPA is in the fourth column
                        double[] point = { cgpa }; // Create a single-dimensional point
                        studentFeatures.add(new DoublePoint(point));
                        studentNames.add(studentRow[1]); // Assuming student names are in the second column
                        studentemail.add(studentRow[5]);
                        studentphone.add(studentRow[6]);
                        studentroll.add(studentRow[7]);
                    }
                    Toast.makeText(requireContext(), rowCount+" number of rows", Toast.LENGTH_SHORT).show();
                    CSVReader teacherReader = reader;
                    String[] teacherRow;
                    List<String> teacherNames = new ArrayList<>();
                    boolean skipTeacherHeader = true;
                    while ((teacherRow = teacherReader.readNext()) != null) {
                        if (skipTeacherHeader) {
                            skipTeacherHeader = false;
                            continue; // Skip the header row in the teacher CSV
                        }
                        teacherNames.add(teacherRow[1]); // Assuming teacher names are in the second column
                    }

                    int numClusters = teacherNames.size(); // Number of clusters based on the number of teachers

                    KMeansPlusPlusClusterer<DoublePoint> clusterer = new KMeansPlusPlusClusterer<>(numClusters, -1, new EuclideanDistance());
                    List<CentroidCluster<DoublePoint>> clusters = clusterer.cluster(studentFeatures);

                    HashMap<String, List<Double>> teacherAssignments = new HashMap<>();
                    
                    for (int i = 0; i < clusters.size(); i++) {
                        CentroidCluster<DoublePoint> cluster = clusters.get(i);
                        String teacherName = teacherNames.get(i);
                        List<Double> assignments = new ArrayList<>();
                        for (DoublePoint point : cluster.getPoints()) {
                            assignments.add(point.getPoint()[0]); // CGPA value
                        }
                        teacherAssignments.put(teacherName, assignments);
                    }

                    // Display the assignments
                    StringBuilder result = new StringBuilder();
                    File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    File outputFile = new File(downloadDir, "studentdata.csv");

                    CSVWriter writer = new CSVWriter(new FileWriter(outputFile));
                    try {
                            for (String teacher : teacherAssignments.keySet()) {
                            List<String[]> data = new ArrayList<>();

                            List<Double> cgpaList = teacherAssignments.get(teacher);
                            List<String> studentNameList = studentNames.subList(0, cgpaList.size());
                            List<String> studentNameList1 = studentphone.subList(0, cgpaList.size());
                            List<String> studentNameList2 = studentemail.subList(0, cgpaList.size());
                            List<String> studentNameList3 = studentroll.subList(0, cgpaList.size());


                            for (int i = 0; i < cgpaList.size(); i++) {
                                String studentName = studentNameList.get(i);
                                String studentName1 = studentNameList1.get(i);
                                String studentName2 = studentNameList2.get(i);
                                String studentName3 = studentNameList3.get(i);
                                double cgpa = cgpaList.get(i);
                                data.add(new String[]{teacher, studentName,studentName1,studentName2,studentName3, Double.toString(cgpa)});
                            }

                            // Write data to the CSV file without overwriting existing content
                            writer.writeAll(data);

                        }
                        mentorcsv.setText("");
                        studentcsv.setText("");

                        output.setText("outputFile.getPath().toString()");
// Close the writer to ensure changes are flushed to the file
                        writer.close();
                        Toast.makeText(requireContext(), "check student.csv file in downloaded section "+outputFile.getPath().toString(), Toast.LENGTH_SHORT).show();


//                        resulted.setText(result.toString());
//
//                        List<String[]> data = new ArrayList<String[]>();
//
//                        data.add(new String[]{"India", "New Delhi"});
//                        data.add(new String[]{"United States", "Washington D.C"});
//                        data.add(new String[]{"Germany", "Berlin"});
//
//                        writer.writeAll(data); // data is adding to csv

                    } catch (IOException e) {
                        Toast.makeText(requireContext(), "Upload both files", Toast.LENGTH_SHORT).show();
                        mentorcsv.setText("*");
                        studentcsv.setText("*");
                        alotmentor.setClickable(true);
                        output.setText("");
                    }



                } catch (Exception e) {
                    Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
    private void downloadFile() {
        File file = new File(getContext().getExternalFilesDir(null), "studentdata.csv");

        // Check if the file exists
        if (file.exists()) {
            // Create a DownloadManager.Request with the file Uri
            try {
                // Request WRITE_EXTERNAL_STORAGE permission


            }catch (Exception e){
                Toast.makeText(requireContext(), "File not found!    "+e, Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(requireContext(), "File not found!", Toast.LENGTH_SHORT).show();
        }
    }
}