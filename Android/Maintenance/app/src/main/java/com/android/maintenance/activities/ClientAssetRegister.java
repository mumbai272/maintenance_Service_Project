package com.android.maintenance.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.ClientAssetRegisterDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.Utility;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ClientAssetRegister extends AppCompatActivity implements View.OnClickListener {

    private static String url = ConfigConstant.url;
    private static String clientURL = url + "client/" + ConfigConstant.company_id;
    private static String machintypeURL = url + "machine/machinetype?companyId=" + ConfigConstant.company_id;
    private static String machinemadeURL= url + "machine/machinemake?companyId=" + ConfigConstant.company_id;
    private static String machinemodelURL= url + "machine/machinemodel?companyId=" + ConfigConstant.company_id;
    private static String assetclientregisterURL= url+"asset";
    private static final String TAG = "Mymessage";
    private static String token;

    private Map<Integer,String> clientAssetDTO;
    private Map<Integer,String> machine_type;
    private Map<Integer,String> machine_made;
    private Map<Integer,String> machine_model;
    ArrayAdapter<String> adapter;
    DatePickerDialog mfgDatePickerDialog;
    DatePickerDialog installDatePickerDialog;
    SimpleDateFormat dateFormatter;
    EditText dateofmfg,dateofinstall,assetNo,assetDesc,mfgSLno,insSLno,hidden_client_id,hidden_machine_type,hidden_machine_made,hidden_machine_model;
    Spinner spinner_client,spinner_machine_type,spinner_machin_made,spinner_machine_model;
    Button add_Btn;
    String client,e_machine_type,e_machine_made,e_machine_model;
    String asset_no,asset_desc,mfg_sl_no,inst_sl_no,inst_date,mfg_date;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent=getIntent();
        token=intent.getStringExtra("token");
        Log.e("Token data",""+token);
       // new GetClient().execute();
       // new GetMachineType().execute();
        new GetMachineMade().execute();
       // new GetMachineModel().execute();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_asset_register);

        assetNo=(EditText)findViewById(R.id.editText_asset_no);
        assetDesc=(EditText)findViewById(R.id.editText_asset_desc);
        mfgSLno=(EditText)findViewById(R.id.editText_mfg_sl_no);
        insSLno=(EditText)findViewById(R.id.editText_inst_sl_no);
        hidden_client_id=(EditText)findViewById(R.id.hidden_client_id);
        hidden_machine_type=(EditText)findViewById(R.id.hidden_machineType);
        hidden_machine_made=(EditText)findViewById(R.id.hidden_machineMake);
        hidden_machine_model=(EditText)findViewById(R.id.hidden_machinemodel);
        spinner_client = (Spinner) findViewById(R.id.spinner_client_id);
        spinner_machine_type =(Spinner) findViewById(R.id.spinner_asset_type);
        spinner_machin_made=(Spinner) findViewById(R.id.spinner_make_id);
        spinner_machine_model=(Spinner) findViewById(R.id.spinner_model_no);
        add_Btn=(Button)findViewById(R.id.registerbtn);

        dateFormatter = new SimpleDateFormat("yyy-MM-dd", Locale.US);
        findViewsById();
        setDateTimeField();
        clientAssetDTO=new HashMap<Integer, String>();
        machine_type=new HashMap<Integer, String>();
        machine_made=new HashMap<Integer, String>();
        machine_model=new HashMap<Integer, String>();
        addButtonClick();

    }

    private void addButtonClick() {
        add_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                asset_no = assetNo.getText().toString();
                asset_desc = assetDesc.getText().toString();
                mfg_sl_no = mfgSLno.getText().toString();
                inst_sl_no = insSLno.getText().toString();
                inst_date = dateofinstall.getText().toString();
                mfg_date = dateofmfg.getText().toString();
                if(Utility.isNotNull(asset_no)&&Utility.isNotNull(asset_desc)&&Utility.isNotNull(mfg_sl_no)&&Utility.isNotNull(inst_sl_no)&&Utility.isNotNull(inst_date)&&Utility.isNotNull(mfg_date)){
                    try {
                        String json="";
                        ClientAssetRegisterDTO register= new ClientAssetRegisterDTO(ConfigConstant.company_id,Integer.parseInt( hidden_client_id.getText().toString() ),Integer.parseInt( hidden_machine_type.getText().toString() ),Integer.parseInt( hidden_machine_made.getText().toString() ),Integer.parseInt( hidden_machine_model.getText().toString() ),asset_no,asset_desc,mfg_sl_no,inst_sl_no,mfg_date,inst_date);
                        ObjectMapper mapper = new ObjectMapper();
                        json = mapper.writeValueAsString(register);
                        new PostAssetClient().execute(json);

                    }catch (Exception e){
                       e.printStackTrace();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Please don't leave any field blank", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private class GetClient extends AsyncTask<Void, Void, Void>  {

        @Override
        protected Void doInBackground(Void... arg0) {

            Log.e(TAG, "inside doInBackground");
            ServiceHandlerWS jsonParser = new ServiceHandlerWS();
            String json = jsonParser.makeServiceGet(clientURL,token);

            Log.e(" client Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray clientData = null;

                        try {
                            clientData = jsonObj.getJSONArray("data");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for (int i = 0; i < clientData.length(); i++) {
                            JSONObject catObj = (JSONObject) clientData.get(i);
                            clientAssetDTO.put(catObj.getInt("clientId"),catObj.getString("clientName"));
                            Log.e(TAG,"items:"+catObj.getInt("clientId")+catObj.getString("clientName"));
                        }
                        Log.e(TAG,"items:"+clientAssetDTO.size());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            populateSpinner();
        }

    }

    public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }
    /*
     * Adding spinner data
     * */
    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();
        for (Object value : clientAssetDTO.values()) {
         Log.e(TAG," ///"+value);
        lables.add((String) value);
        }
          adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner_client.setAdapter(adapter);
        spinner_client.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                client =parentView.getItemAtPosition(position).toString();
                Log.e(TAG,"hidden"+client);
                Object val=getKeyFromValue(clientAssetDTO,client);
                String s = val.toString();
                Log.e(TAG,"This is val"+s);
                hidden_client_id.setText(s);

             //   Toast.makeText(parentView.getContext(), "You selected: " + client,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            //    Toast.makeText(parentView.getContext(), "You selected nothing " ,Toast.LENGTH_LONG).show();
            }

        });
        Log.e(TAG,"item set");
    }

    //data picker
    public void setDateTimeField() {
        dateofmfg.setOnClickListener(this);
        dateofinstall.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        mfgDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateofmfg.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        installDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateofinstall.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public void findViewsById() {

        dateofmfg = (EditText) findViewById(R.id.datePicker_date_mfg);
        dateofmfg.setInputType(InputType.TYPE_NULL);
        dateofmfg.requestFocus();

        dateofinstall = (EditText) findViewById(R.id.datePicker_date_install);
        dateofinstall.setInputType(InputType.TYPE_NULL);
        dateofinstall.requestFocus();
    }

    @Override
    public void onClick(View view) {
        if(view == dateofinstall) {
            installDatePickerDialog.show();
        } else if(view == dateofmfg) {
            mfgDatePickerDialog.show();
        }
    }

    private class GetMachineType extends AsyncTask<Void, Void, Void>  {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            Log.e(TAG, "inside for "+machintypeURL);
            ServiceHandlerWS jsonParser = new ServiceHandlerWS();
            String json = jsonParser.makeServiceGet(machintypeURL, token);

            Log.e("Machine type Response: ", "------- " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray clientData = null;

                        try {
                            clientData = jsonObj.getJSONArray("data");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for (int i = 0; i < clientData.length(); i++) {
                            JSONObject catObj = (JSONObject) clientData.get(i);
                            machine_type.put(catObj.getInt("machineId"),catObj.getString("machineName"));
                        }
                        Log.e(TAG,"items:"+machine_type.size());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            populateSpinner2();
        }

    }
    /*
     * Adding spinner data
     * */
    private void populateSpinner2() {
        List<String> lables = new ArrayList<String>();
        for (Object value : machine_type.values()) {
            Log.e(TAG," ///"+value);
            lables.add((String) value);
        }

        // Creating adapter for spinner
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_machine_type.setAdapter(adapter);
        spinner_machine_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                e_machine_type =parentView.getItemAtPosition(position).toString();
                Log.e(TAG,"hidden"+client);
                Object val=getKeyFromValue(machine_type,e_machine_type);
                String s = val.toString();
                Log.e(TAG,"This is val"+s);
                hidden_machine_type.setText(s);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        Log.e(TAG,"item set");
    }

    private class GetMachineMade extends AsyncTask<Void, Void, Void>  {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            Log.e(TAG, "inside doInBackground");
            ServiceHandlerWS jsonParser = new ServiceHandlerWS();
            String json = jsonParser.makeServiceGet(machinemadeURL,token);

            Log.e("machine made Response: ", "****** " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray clientData = null;

                        try {
                            clientData = jsonObj.getJSONArray("data");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for (int i = 0; i < clientData.length(); i++) {
                            JSONObject catObj = (JSONObject) clientData.get(i);
                            machine_made.put(catObj.getInt("machineId"),catObj.getString("machineName"));
                        }
                        Log.e(TAG,"items:"+machine_made.size());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            populateSpinner3();
        }

    }
    /*
     * Adding spinner data
     * */
    private void populateSpinner3() {
        List<String> lables= new ArrayList<String>();
        for (Object value : machine_made.values()) {
            Log.e(TAG," ///"+value);
            lables.add((String) value);
        }

        // Creating adapter for spinner
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_machin_made.setAdapter(adapter);
        spinner_machin_made.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                e_machine_made= parentView.getItemAtPosition(position).toString();
                Log.e(TAG,"hidden"+e_machine_made);
                Object val=getKeyFromValue(machine_made,e_machine_made);
                String s = val.toString();
                Log.e(TAG,"This is val"+s);
                hidden_machine_made.setText(s);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        Log.e(TAG,"item set");
    }

    private class GetMachineModel extends AsyncTask<Void, Void, Void>  {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            Log.e(TAG, "inside doInBackground");
            ServiceHandlerWS jsonParser = new ServiceHandlerWS();
            String json = jsonParser.makeServiceGet(machinemodelURL,token);

            Log.e("Response: ", "$$$$$$$" + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray clientData = null;

                        try {
                            clientData = jsonObj.getJSONArray("data");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for (int i = 0; i < clientData.length(); i++) {
                            JSONObject catObj = (JSONObject) clientData.get(i);
                            machine_model.put(catObj.getInt("machineId"),catObj.getString("machineName"));
                        }
                        Log.e(TAG,"items:"+machine_model.size());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            populateSpinner4();
        }

    }
    /*
     * Adding spinner data
     * */
    private void populateSpinner4() {
        List<String> lables= new ArrayList<String>();
        for (Object value : machine_model.values()) {
            Log.e(TAG," ///"+value);
            lables.add((String) value);
        }

        // Creating adapter for spinner
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_machine_model.setAdapter(adapter);
        spinner_machine_model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                e_machine_model = parentView.getItemAtPosition(position).toString();
                Log.e(TAG,"hidden"+e_machine_model);
                Object val=getKeyFromValue(machine_model,e_machine_model);
                String s = val.toString();
                Log.e(TAG,"This is val"+s);
                hidden_machine_model.setText(s);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        Log.e(TAG,"item set");
    }

    private class PostAssetClient extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... param) {
            String result="";
            ServiceHandlerWS servicepost= new ServiceHandlerWS();
            Log.e(TAG,"this input post"+param[0]);
            result= servicepost.makeServicePost(ConfigConstant.url+"asset",param[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Gson gson = new GsonBuilder().create();
            BaseResponseDTO clientResponse=gson.fromJson(result, BaseResponseDTO.class);
           if(clientResponse.getStatusCode()==1){
                Log.e("Success :",""+clientResponse.getMsg());
                Toast.makeText(getApplicationContext(),clientResponse.getMsg(), Toast.LENGTH_LONG).show();
            }else{
                Log.e("ERROR :",""+clientResponse.getMsg());
                Toast.makeText(getApplicationContext(),clientResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }

    }
}