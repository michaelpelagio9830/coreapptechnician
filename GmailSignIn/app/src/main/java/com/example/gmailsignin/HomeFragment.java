package com.example.gmailsignin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class HomeFragment extends Fragment {

    TextView intro;
    LinearLayout rate, ratepu;
    EditText message;
    Button submitrate;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        intro = v.findViewById(R.id.intro);
        rate = v.findViewById(R.id.rateus);
        ratepu = v.findViewById(R.id.ratepopup);
        submitrate = v.findViewById(R.id.submitRate);
        message = v.findViewById(R.id.messageRate);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (acct != null) {

            //This process is to fetch data from Gmail accounts that are signed in with the device.

            String personName = acct.getDisplayName();

            intro.setText("Hi " + personName + "! Welcome to CORE");

        }

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratepu.setVisibility(View.VISIBLE);
            }
        });

        submitrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (message.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Field cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "Thank you for rating us!", Toast.LENGTH_SHORT).show();
                    ratepu.setVisibility(View.GONE);

                    String recipient = "octaviosilva242424@gmail.com";// trim will remove space before and after the text
                    String subject = "Feedback";
                    String ratemessage = message.getText().toString().trim();

                    sendEmail(recipient, subject, ratemessage);
                }

            }
        });




        return v;

    }

    private void sendEmail(String recipient, String subject, String message) {
        //ACTION SEND action to launch an email client installed on your Android Device

        Intent mEmailIntent = new Intent(Intent.ACTION_SEND);
        mEmailIntent.setData(Uri.parse("mailto:"));
        mEmailIntent.setType("text/plain");
        // put recipient email in intent
        mEmailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {recipient});
        //put subject of email
        mEmailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        //put message of email in intent
        mEmailIntent.putExtra(Intent.EXTRA_TEXT, message);
        try{

            startActivity(Intent.createChooser(mEmailIntent, "Choose an Email Client"));

        }
        catch(Exception e){
            //If anything goes wrong e.g no internet or no email client like gmail is available
            //get and show exception message
            Toast.makeText(this.getActivity(), e.getMessage(),Toast.LENGTH_SHORT).show();


        }
    }

}
