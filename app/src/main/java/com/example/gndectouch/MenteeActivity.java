package com.example.gndectouch;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

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

        // Set student performance (you can update this dynamically)


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
        builder.setTitle("Chat with Mentor");

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
