package com.example.owais.todolistmvp.backup;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.backupservice.Backup;
import com.example.backupservice.Params;
import com.example.owais.todolistmvp.R;
import com.example.owais.todolistmvp.data.DBUtil;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class BackupFragment extends Fragment implements View.OnClickListener {
    private static final int PICKFILE_RESULT_CODE = 1;
    RadioGroup radioGroup;
    RadioButton radioButton;
    CheckBox checkbox_monthly, checkBox_encrypt;
    Button buttonExport, buttonImport;
    EditText editTextExpiryDays, editTextPassWord;
    TextView textViewPassword;
    Boolean requiredPassword = false;
    public String TAG = "Log";
    View view;
    public BackupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_backup, container, false);
        initView();
        checkBox_encrypt.setOnClickListener(this);
        buttonExport.setOnClickListener(this);
        buttonImport.setOnClickListener(this);
        return view;
    }

    private void initView() {
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        checkbox_monthly = (CheckBox) view.findViewById(R.id.checkBox_monthly);
        checkBox_encrypt = (CheckBox) view.findViewById(R.id.checkBox_encrypt);
        buttonExport = (Button) view.findViewById(R.id.mybutton_export);
        buttonImport = (Button) view.findViewById(R.id.mybutton_import);
        editTextExpiryDays = (EditText) view.findViewById(R.id.editText_noOfExpiryDays);
        editTextPassWord = (EditText) view.findViewById(R.id.editText_password);
        textViewPassword = (TextView) view.findViewById(R.id.password);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Fix no activity available
        if (data == null)
            return;
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == RESULT_OK) {
                    //FilePath is your file as a string
                    final String FilePath = data.getData().getPath();
                    Backup backup = new Backup(getActivity());
                    backup.importDB(FilePath, DBUtil.DB_NAME);
                }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.mybutton_import:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                try {
                    startActivityForResult(intent, PICKFILE_RESULT_CODE);
                } catch (ActivityNotFoundException e) {
                    Log.d(TAG, e.toString());
                }
                break;

            case R.id.mybutton_export:
                String Password = editTextPassWord.getText().toString();
                String expiry = editTextExpiryDays.getText().toString();

                if (!(expiry.equals(""))) {
                    if (requiredPassword && Password.equals("")) {
                        Toast.makeText(getActivity(), "Please enter Password!", Toast.LENGTH_LONG).show();
                    } else {
                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        radioButton = (RadioButton) view.findViewById(selectedId);
                        String radioButtonText = (String) radioButton.getText();
                        int expiryDays = Integer.parseInt(expiry);

                        Params params = new Params();
                        params.setDbName(DBUtil.DB_NAME);
                        params.setStoragePath("//DCIM");
                        params.setNoOfExpiryDays(expiryDays);

                        if (radioButtonText.equals("Daily")) {
                            params.setSchedule(Params.Schedule.DAILY);
                        } else if (radioButtonText.equals("Weekly")) {
                            params.setSchedule(Params.Schedule.WEEKLY);
                        } else if (radioButtonText.equals("Monthly")) {
                            params.setSchedule(Params.Schedule.MONTHLY);
                        }
                        if (checkbox_monthly.isChecked()) {
                            params.setKeepMonthlyBackup(true);
                        } else {
                            params.setKeepMonthlyBackup(false);
                        }
                        if (checkBox_encrypt.isChecked()) {
                            params.setEncryptDB(true);
                            params.setPassword(Password);
                        } else {
                            params.setEncryptDB(false);
                            params.setPassword("");
                        }
                        Backup backup = new Backup(getActivity());
                        backup.setupService(params);
                    }
                } else {
                    Toast.makeText(getActivity(), "Please enter expiry days!", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.checkBox_encrypt:
                if (checkBox_encrypt.isChecked()) {
                    textViewPassword.setVisibility(View.VISIBLE);
                    editTextPassWord.setVisibility(View.VISIBLE);
                    requiredPassword = true;
                } else {
                    textViewPassword.setVisibility(View.INVISIBLE);
                    editTextPassWord.setVisibility(View.INVISIBLE);
                    requiredPassword = false;
                }
                break;
        }
    }
}
