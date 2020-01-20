package com.example.gmailsignin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

public class ComplaintFragment extends Fragment {

    RadioGroup subjectGroup;
    RadioButton selectedButton;
    EditText mMessageET;
    Button mSendEmailBtn;
    LinearLayout complaintform;
    TextView thankyou;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_complaint, container, false);


        subjectGroup = v.findViewById(R.id.subject);
        mMessageET = v.findViewById(R.id.message);
        mSendEmailBtn = v.findViewById(R.id.btnEmail);
        complaintform = v.findViewById(R.id.complaintform);
        thankyou = v.findViewById(R.id.thankyou);


        String[] items = {"LAPTOP", "DESKTOP",};
        String[] laptopModels = {"Dell XPS 13", "Alienware Area-51m", "Dell XPS 15 2-in-1", "Dell Latitude 7490", "Dell Alienware 17 R5",
                "Dell Alienware 15 R3", "Dell Inspiron Chromebook 11 (3181) 2-in-1", "Dell G5 15 5590",};
        String[] desktopModels = {"Inspiron Zino 300", " Inspiron Zino HD 400", "Inspiron Zino HD 410",};

        final Spinner itemSpinner = (Spinner) v.findViewById(R.id.spinnerItem);
        final Spinner modelSpinner = (Spinner) v.findViewById(R.id.spinnerModel);


        final ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, items);
        itemAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        itemSpinner.setAdapter(itemAdapter);

//        FOR LAPTOP
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, laptopModels);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, desktopModels);
        adapter3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        itemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    modelSpinner.setAdapter(adapter2);
                }
                else if (position == 1){
                    modelSpinner.setAdapter(adapter3);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });

        mSendEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //get input from Items
                String item = itemSpinner.getSelectedItem().toString();
                String model = modelSpinner.getSelectedItem().toString();

                // get selected radio button from radioGroup
                int selectedId = subjectGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                selectedButton = (RadioButton) v.findViewById(selectedId);

                String subject = selectedButton.getText().toString().trim();

                //get input from EditTexts and save in variables

                String recipient = "octaviosilva242424@gmail.com";// trim will remove space before and after the text (companyA)
                //String recipient = "winnerjinwoo01234567@gmail.com"; // Company(B)

//                String subject = mSubjectET.getText().toString().trim();



                String message = mMessageET.getText().toString().trim();

                //method call for email intent with these inputs as parameters
                sendEmail (item, model, recipient, subject, message);

            }

        });

        return v;

    }

    private void sendEmail(String item, String model, String recipient, String subject, String message) {


        if(mMessageET.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else {

            //ACTION SEND action to launch an email client installed on your Android Device

            complaintform.setVisibility(View.GONE);
            thankyou.setVisibility(View.VISIBLE);

            Intent mEmailIntent = new Intent(Intent.ACTION_SEND);

            mEmailIntent.setData(Uri.parse("mailto:"));
            mEmailIntent.setType("text/plain");
            // put recipient email in intent
            mEmailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
            //put subject of email
            mEmailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            //put message of email in intent
            mEmailIntent.putExtra(Intent.EXTRA_TEXT, "ITEM DESCRIPTION\n" + "Item: " + item + "\n" + "Model: " + model + "\n\n" + "COMPLAINT\n" + message);
            try {

                startActivity(Intent.createChooser(mEmailIntent, "Choose an Email Client"));

            } catch (Exception e) {
                //If anything goes wrong e.g no internet or no email client like gmail is available
                //get and show exception message
                Toast.makeText(this.getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();


            }
        }
    }


}

