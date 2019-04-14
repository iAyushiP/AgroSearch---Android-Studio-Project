package ap.efficient_farming;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

public class AddEquipment extends AppCompatActivity //implements AdapterView.OnItemSelectedListener
        {
            private String IMAGE_SELECTED="IMAGE SELECTED";
            URLs urlObject = new URLs();
            String BASE_URL = urlObject.URL_ADD_LISTING;
            int fyear,fmonth,fday,tyear,tmonth,tday;
            Calendar calendar1,calendar2;
            DatePickerDialog datePickerDialog1,datePickerDialog2;
    private Spinner spinnerType, spinnerMode,spinnerList,spinnerAdType;
    EditText ETitle, EDesc, ERate;
    Button UploadImage, RegisterButton,availableFrom,availableTo;
    public static final int GET_FROM_GALLERY = 3;
    private static final String[] type = {"Select the equipment type..","Vehicles", "Irrigation Equipment", "Cultivation Tools", "Fertilization Tools","Consumables","Pest Control"};
    private static final String[] mode = {"Select pricing mode","per hour","per day","per week","per month"};
    private static final String[] list={"Select listing type..","Share","Rent","Sell"};
    private static final String[] AdType={"Select Ad type..","Normal","Featured","Boosted"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_equipment);
        availableFrom = (Button) findViewById(R.id.AvailableFrom);
        availableTo = (Button) findViewById(R.id.AvailableTo);
        ETitle = findViewById(R.id.EquipmentTitle);
        EDesc = findViewById(R.id.EquipmentDesc);
        ERate = findViewById(R.id.EquipmentRate);
        spinnerType = findViewById(R.id.typeSpinner);
        spinnerMode = findViewById(R.id.SpinnerMode);
        spinnerList = findViewById(R.id.SpinnerList);
        spinnerAdType = findViewById(R.id.SpinnerAdType);
        UploadImage = findViewById(R.id.UploadImage);
        RegisterButton = findViewById(R.id.RegisterEquipment);
        availableFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar1 = Calendar.getInstance();
                fyear = calendar1.get(Calendar.YEAR);
                fmonth = calendar1.get(Calendar.MONTH);
                fday = calendar1.get(Calendar.DAY_OF_MONTH);
                datePickerDialog1 = new DatePickerDialog(AddEquipment.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                availableFrom.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, 0, 0, 0);
                datePickerDialog1.show();
            }
        });
        availableTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar2 = Calendar.getInstance();
                tyear = calendar2.get(Calendar.YEAR);
                tmonth = calendar2.get(Calendar.MONTH);
                tday = calendar2.get(Calendar.DAY_OF_MONTH);
                datePickerDialog2 = new DatePickerDialog(AddEquipment.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                availableFrom.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, 0, 0, 0);
                datePickerDialog2.show();
            }
        });
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttemptAddEquipment();
            }
        });
        UploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });
        //Coding for spinner items
        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(this,
                R.layout.spinner_item, type) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) return false; //disable the first item on spinner
                else return true;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    //set the hint color as gray
                    tv.setTextColor(Color.GRAY);
                } else tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        ArrayAdapter<String> adapterMode = new ArrayAdapter<String>(this,
                R.layout.spinner_item, mode) {
                @Override
                public boolean isEnabled(int position) {
                if (position == 0) return false; //disable the first item on spinner
                else return true;
            }

                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    //set the hint color as gray
                    tv.setTextColor(Color.GRAY);
                } else tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        ArrayAdapter<String> adapterList = new ArrayAdapter<String>(this,
                R.layout.spinner_item, list) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) return false; //disable the first item on spinner
                else return true;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    //set the hint color as gray
                    tv.setTextColor(Color.GRAY);
                } else tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        ArrayAdapter<String> adapterAdType = new ArrayAdapter<String>(this,
                R.layout.spinner_item, AdType) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) return false; //disable the first item on spinner
                else return true;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    //set the hint color as gray
                    tv.setTextColor(Color.GRAY);
                } else tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterMode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterAdType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapterType);
        spinnerMode.setAdapter(adapterMode);
        spinnerList.setAdapter(adapterList);
        spinnerAdType.setAdapter(adapterAdType);
        spinnerType.setSelection(0);
        spinnerMode.setSelection(0);
        spinnerList.setSelection(0);
        spinnerAdType.setSelection(0);
        //spinnerType.setOnItemSelectedListener(this);
        //spinnerMode.setOnItemSelectedListener(this);
            }
    /*@Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the third item gets selected
                break;
            case 3:
                //
                break;
            case 4:
                //
                break;
            case 5:
                //
                break;
            case 6:
                //
                break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                UploadImage.setText(IMAGE_SELECTED);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    protected void AttemptAddEquipment() {
        // Reset errors
        ETitle.setError(null);
        EDesc.setError(null);
        ERate.setError(null);

        // Store values at the time of the login attempt.
        String title = ETitle.getText().toString();
        String desc = EDesc.getText().toString();
        String rate = ERate.getText().toString();
        Boolean cancel=false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(title)) {
            ETitle.setError(getString(R.string.error_empty_field));
            focusView = ETitle;
            cancel = true;
        }
            else if(TextUtils.isEmpty(desc)) {
            EDesc.setError(getString(R.string.error_empty_field));
            focusView = EDesc;
            cancel = true;
            }
            else if (TextUtils.isEmpty(rate)){
            ERate.setError(getString(R.string.error_empty_field));
            focusView = ERate;
            cancel = true;
        }
        if (!cancel) AddNewEquipment();
    }
    private void AddNewEquipment() {
        try {
            HashMap<String, String> param = new HashMap<>();
            param.put("listing_title", ETitle.getText().toString());
            param.put("listing_desc", EDesc.getText().toString());
            param.put("listing_rate", ERate.getText().toString());
            //add remaining parameters

            AQuery aq = new AQuery(this);
            aq.ajax(BASE_URL, param,  JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject json, AjaxStatus status) {
                    super.callback(url, json, status);

                    Log.v("CALLBACK RECEIVED" , String.valueOf(json) + status);

                    try {
                        if (json != null) {
                            JSONObject h = json.getJSONObject("success");

                            Log.v("SUCCESS" ,"SUCCESS" );
                            Toast.makeText(AddEquipment.this, "New Equipment Added", Toast.LENGTH_LONG).show();
                            exitEquipmentForm();
                        } else {
                            Log.v("ERROR", "ERROR");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }.method(AQuery.METHOD_POST));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
            public void exitEquipmentForm() {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
    }

