package com.example.gndectouch;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer;
import org.apache.commons.math3.ml.distance.EuclideanDistance;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class UploadData extends Fragment {
    private ActivityResultLauncher<Intent> filePickerLauncher;
    private ActivityResultLauncher<Intent> filePickerLauncher1;
    CSVReader reader;
    CSVReader reader1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_upload_data, container, false);
TextView resulted=view.findViewById(R.id.resulted);
        // Find views within the fragment's layout
        Button mentorlist = view.findViewById(R.id.uploadbuttonformentor);
        Button menteelist = view.findViewById(R.id.uploadbuttonformentee);
        Button alotmentor = view.findViewById(R.id.alotmentortomentee);

        filePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Uri selectedFileUri = data.getData();
                    try {
                        InputStream inputStream = requireContext().getContentResolver().openInputStream(selectedFileUri);
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                         this.reader = new CSVReader(inputStreamReader);

                        // Process the CSV data

                    } catch (IOException e) {
                        // Handle any IO exceptions.
                    }
                    // Handle the selected file URI here.
                }
            }
        });
        filePickerLauncher1 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Uri selectedFileUri = data.getData();
                    // Handle the selected file URI here.
                    try {
                        InputStream inputStream = requireContext().getContentResolver().openInputStream(selectedFileUri);
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        this.reader1 = new CSVReader(inputStreamReader);

                        // Process the CSV data

                    } catch (IOException e) {
                        // Handle any IO exceptions.
                    }
                }
            }
        });

        // Perform further operations with the views
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
                List<String> teacherNames = new ArrayList<>();
                CSVReader studentReader = reader1;
                String[] studentRow;
                while ((studentRow = studentReader.readNext()) != null) {
                    double cgpa = Double.parseDouble(studentRow[3]); // Assuming CGPA is in the first column
                    double[] point = {cgpa}; // Create a single-dimensional point
                    studentFeatures.add(new DoublePoint(point));
                }

                // Read teacher data from teachers.csv
                CSVReader teacherReader = reader;
                String[] teacherRow;
                while ((teacherRow = teacherReader.readNext()) != null) {
                    teacherNames.add(teacherRow[1]); // Assuming teacher names are in the first column
                }

                // Number of clusters (teachers)
                int numClusters =3;

                // Perform K-Means clustering
                KMeansPlusPlusClusterer<DoublePoint> clusterer = new KMeansPlusPlusClusterer<>(numClusters, -1, new EuclideanDistance());
                List<CentroidCluster<DoublePoint>> clusters = clusterer.cluster(studentFeatures);

                // Display cluster assignments
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
                    Toast.makeText(requireContext(), "In try", Toast.LENGTH_SHORT).show();
                // Display the assignments
                StringBuilder result = new StringBuilder();
                for (String teacher : teacherAssignments.keySet()) {
                    result.append("Teacher: ").append(teacher).append("\n");
                    for (Double cgpa : teacherAssignments.get(teacher)) {
                        result.append("  Student: ").append("Student Name").append(", CGPA: ").append(cgpa).append("\n");

                    }
                }
                    resulted.setText(result.toString());
            }catch (Exception e) {
                    Toast.makeText(requireContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;

    }


}