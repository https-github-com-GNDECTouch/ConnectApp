package com.example.gndectouch;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MenteeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee);

        // Get references to UI elements
        TextView nameTextView = findViewById(R.id.textViewName);
        TextView crnTextView = findViewById(R.id.textViewCRN);
        TextView urnTextView = findViewById(R.id.textViewURN);
        TextView collegeMailTextView = findViewById(R.id.textViewCollegeMail);
        TextView personalMailTextView = findViewById(R.id.textViewPersonalMail);
        TextView currentSemTextView = findViewById(R.id.textViewCurrentSem);

        ImageView chatButton = findViewById(R.id.chatButton);

        // Set example student details
        nameTextView.setText("John Doe");
        crnTextView.setText("CRN: 123456");
        urnTextView.setText("URN: 789012");
        collegeMailTextView.setText("college.johndoe@example.com");
        personalMailTextView.setText("john.doe@example.com");
        currentSemTextView.setText("Current Semester: 5");

        // Create a reference to the GraphView
        GraphView graphView = findViewById(R.id.GraphView);

        // Create a series for the line chart
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0, 1),
                new DataPoint(1, 3),
                new DataPoint(2, 4),
                new DataPoint(3, 9),
                new DataPoint(4, 6),
                new DataPoint(5, 3),
                new DataPoint(6, 6),
                new DataPoint(7, 1),
                new DataPoint(8, 2)
        });

        // Set a title for the graph view
        graphView.setTitle("My Graph View");

        // Set text color for the title
        graphView.setTitleColor(getResources().getColor(R.color.purple_200)); // Use your own color resource

        // Set title text size
        graphView.setTitleTextSize(18);

        // Add the data series to the graph view
        graphView.addSeries(series);

        // Handle chat button click
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChatPopup();
            }
        });
    }

    // Function to show the chat popup
    private void showChatPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chat with Mentor1");

        // Create a layout for the chat content
        View chatLayout = getLayoutInflater().inflate(R.layout.chat_popup, null);
        builder.setView(chatLayout);

        // Handle Send button click
        builder.setPositiveButton("Send", (dialog, which) -> {
            EditText chatMessageEditText = chatLayout.findViewById(R.id.editTextChatMessage);
            String chatMessage = chatMessageEditText.getText().toString();
            // Send the chat message (you can implement this)
            // Don't forget to dismiss the dialog
            dialog.dismiss();
        });

        // Handle Cancel button click
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
