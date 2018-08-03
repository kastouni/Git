package zizkuz_menu.hassebnafssak;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class HadafV2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String User_emails;
    private String User_First_Connexion;
    private String Insert_Data;
    private Button insert;
    private SQLiteDatabase mDatabase;
    private UserBaseHelper mUserBaseHelper;
    private Spinner SpSalat;
    private Spinner SpQuran;
    private Spinner SpSiyam;
    private Spinner SpSadaqa;
    private Spinner SpAdkarSabah;
    private Spinner SpAdkarAlMasaa;
    private Spinner SpQiyam;
    private SQLiteDatabase db;
    private Cursor c_extract_notif_Mail;
    private int ComptageCurseurUser=0;
    private String ComptageCurseurUserString;




    private GestureDetectorCompat gestureDetectorCompat;
    // View.OnTouchListener gestureListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hadaf_v2);
        Toolbar toolbarHadaf = (Toolbar) findViewById(R.id.toolbarGrapheHadaf);
        toolbarHadaf.setTitle("الأهداف");
        toolbarHadaf.setTitleTextColor(0x000000);
       setSupportActionBar(toolbarHadaf);
        gestureDetectorCompat = new GestureDetectorCompat(this, new MyGestureListener());

          ////////////////Extraction de l 'email de la base

        openDatabase();
        String SELECT_email = "SELECT * FROM users ;";
        c_extract_notif_Mail= db.rawQuery(SELECT_email, null);
        ComptageCurseurUser= c_extract_notif_Mail.getCount();
        c_extract_notif_Mail.moveToFirst();
        User_emails= c_extract_notif_Mail.getString(3);


        c_extract_notif_Mail.moveToLast();
        User_First_Connexion=c_extract_notif_Mail.getString(4);

      // Toast.makeText(HadafV2.this, User_First_Connexion, Toast.LENGTH_LONG).show();

        insert=(Button) findViewById(R.id.Idkhal1);

        /////////////////////////////////

        // Spinner Salat
        Spinner spinnerSalat = (Spinner) findViewById(R.id.spinnerSalat);

        // Spinner click listener
        spinnerSalat.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categoriesSalat = new ArrayList<String>();
        for(int i=30; i>0;i--){
            String j=String.valueOf(i);
            categoriesSalat.add(j);
        }



        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterSalat = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesSalat);

        // Drop down layout style - list view with radio button
        dataAdapterSalat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerSalat.setAdapter(dataAdapterSalat);


        // Spinner Quran
        Spinner spinnerQuran = (Spinner) findViewById(R.id.spinnerQuran);

        // Spinner click listener
        spinnerQuran.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categoriesQuran = new ArrayList<String>();
        for(int i=30; i>0;i--){
            String j=String.valueOf(i);
            categoriesQuran.add(j);
        }


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterQuran = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesQuran);

        // Drop down layout style - list view with radio button
        dataAdapterQuran.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerQuran.setAdapter(dataAdapterQuran);

///////////////////////


// Spinner Siyam
        Spinner spinnerSiyam = (Spinner) findViewById(R.id.spinnerSiyam);

        // Spinner click listener
        spinnerSiyam.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categoriesSiyam = new ArrayList<String>();
        for(int i=30; i>0;i--){
            String j=String.valueOf(i);
            categoriesSiyam.add(j);
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterSiyam = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesSiyam);

        // Drop down layout style - list view with radio button
        dataAdapterSiyam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerSiyam.setAdapter(dataAdapterSiyam);



        //////////////////////////////

        // Spinner Sadaqa
        Spinner spinnerSadaqa = (Spinner) findViewById(R.id.spinnerSadaqa);

        // Spinner click listener
        spinnerSadaqa.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categoriesSadaqa = new ArrayList<String>();
        for(int i=30; i>0;i--){
            String j=String.valueOf(i);
            categoriesSadaqa.add(j);
        }


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterSadaqa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesSadaqa);

        // Drop down layout style - list view with radio button
        dataAdapterSadaqa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerSadaqa.setAdapter(dataAdapterSadaqa);

        //////////////////////////////
        // Spinner Adkar Assabah
        Spinner spinnerAdkarAssabah = (Spinner) findViewById(R.id.spinnerAdkarAssabah);

        // Spinner click listener
        spinnerAdkarAssabah.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categoriesAdakarAssabah = new ArrayList<String>();
        for(int i=30; i>0;i--){
            String j=String.valueOf(i);
            categoriesAdakarAssabah.add(j);
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterAdkarAssabah = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesAdakarAssabah);

        // Drop down layout style - list view with radio button
        dataAdapterAdkarAssabah.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerAdkarAssabah.setAdapter(dataAdapterAdkarAssabah);

        //////////////////////////////
        // Spinner Adkar AlMasaa
        Spinner spinnerAdkarAlmasaa = (Spinner) findViewById(R.id.spinnerAdkarAlmassa);

        // Spinner click listener
        spinnerAdkarAlmasaa.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categoriesAdakarAlmasa = new ArrayList<String>();
        for(int i=30; i>0;i--){
            String j=String.valueOf(i);
            categoriesAdakarAlmasa.add(j);
        }


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterAdkarAlmasa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesAdakarAlmasa);

        // Drop down layout style - list view with radio button
        dataAdapterAdkarAlmasa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerAdkarAlmasaa.setAdapter(dataAdapterAdkarAlmasa);


        //////////////////////////////
        // Spinner Adkar AlQiyam
        Spinner spinnerQiyam = (Spinner) findViewById(R.id.spinnerQiyam);

        // Spinner click listener
        spinnerQiyam.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categoriesQiyam = new ArrayList<String>();
        for(int i=30; i>0;i--){
            String j=String.valueOf(i);
            categoriesQiyam.add(j);
        }


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterQiyam = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesQiyam);

        // Drop down layout style - list view with radio button
        dataAdapterQiyam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerQiyam.setAdapter(dataAdapterQiyam);

        mUserBaseHelper= new UserBaseHelper(getApplicationContext());
        mDatabase=mUserBaseHelper.getWritableDatabase();


        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //  db.execSQL("delete from "+ TABLE_NAME);
                ContentValues values[] = getContentValues(User_emails);

                mDatabase.insert(UserDbSchema.HadafTable.NAME_HADAF, null, values[0]);

                //// Bloquer les insertions dans la BD user
                if (ComptageCurseurUser <9 ) {

                    mDatabase.insert(UserDbSchema.UserTable.NAME, null, values[1]);

                }
                Intent intent2 = new Intent(HadafV2.this,HissabV2.class);
                // String userEmail= user.getEmail(user);

                intent2.putExtra("extra_email", User_emails) ;
                startActivity(intent2);


            }
        });


    }
    private  ContentValues[] getContentValues (String email) {

        ContentValues [] ChartValues= new ContentValues[2];

        SpSalat = (Spinner) findViewById(R.id.spinnerSalat);
        SpQuran = (Spinner) findViewById(R.id.spinnerQuran);
        SpSiyam = (Spinner) findViewById(R.id.spinnerSiyam);
        SpSadaqa = (Spinner) findViewById(R.id.spinnerSadaqa);
        SpAdkarSabah = (Spinner) findViewById(R.id.spinnerAdkarAssabah);
        SpAdkarAlMasaa = (Spinner) findViewById(R.id.spinnerAdkarAlmassa);
        SpQiyam = (Spinner) findViewById(R.id.spinnerQiyam);

        String SalatChoice = SpSalat.getSelectedItem().toString();
        String QuranChoice = SpQuran.getSelectedItem().toString();
        String SiyamChoice = SpSiyam.getSelectedItem().toString();
        String SadaqaChoice = SpSadaqa.getSelectedItem().toString();
        String AdkarAssabahChoice = SpAdkarSabah.getSelectedItem().toString();
        String AdkarAlMasaaChoice = SpAdkarAlMasaa.getSelectedItem().toString();
        String QiyamChoice = SpQiyam.getSelectedItem().toString();
        String myDate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());


        ContentValues values = new ContentValues();

        values.put(UserDbSchema.HadafTable.Cols.KEY_ID_USER_EMAIL_HADAF, email);
        values.put(UserDbSchema.HadafTable.Cols.KEY_DATE_HADAF, myDate);
        values.put(UserDbSchema.HadafTable.Cols.KEY_SALAT_HADAF, SalatChoice);
        values.put(UserDbSchema.HadafTable.Cols.KEY_QURAN_HADAF, QuranChoice);
        values.put(UserDbSchema.HadafTable.Cols.KEY_SIYAM_HADAF, SiyamChoice);
        values.put(UserDbSchema.HadafTable.Cols.KEY_SADAKA_HADAF, SadaqaChoice);
        values.put(UserDbSchema.HadafTable.Cols.KEY_ADKARASSABAH_HADAF, AdkarAssabahChoice);
        values.put(UserDbSchema.HadafTable.Cols.KEY_ADKARALMASA_HADAF, AdkarAlMasaaChoice);
        values.put(UserDbSchema.HadafTable.Cols.KEY_QIYAM_HADAF, QiyamChoice);

        ContentValues values1 = new ContentValues();
        values1.put(UserDbSchema.UserTable.Cols.KEY_PHONE_USER, "False");
        values1.put(UserDbSchema.UserTable.Cols.KEY_Connexion, "Free");

        ChartValues[0]= values;
        ChartValues[1]= values1;
        return ChartValues;
    }






    protected void openDatabase() {
        db = openOrCreateDatabase("userBase.db", Context.MODE_PRIVATE, null);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item

        String item = parent.getItemAtPosition(position).toString();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //   Toast.makeText(HissabV2.this,User_First_Connexion , Toast.LENGTH_LONG).show();

        if ( ComptageCurseurUser < 5  ) {

            Toast.makeText(HadafV2.this, "المرجو إدخال المعلومات", Toast.LENGTH_LONG).show();
        }
        else {

            if (id == R.id.Target) {
                Intent intent = new Intent(
                        HadafV2.this, HadafV2.class);
                startActivity(intent);

                //   return true;
            }

            if (id == R.id.add) {
                Intent intent = new Intent(
                        HadafV2.this, HissabV2.class);
                startActivity(intent);

                //   return true;
            }

            if (id == R.id.stats) {
                Intent intent = new Intent(
                        HadafV2.this, NatijaV2.class);
                startActivity(intent);

                //   return true;
            }

            if (id == R.id.graph) {
                Intent intent = new Intent(
                        HadafV2.this, GrapheV2.class);
                startActivity(intent);

                //   return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hadaf_v2, menu);
        return true;
    }




    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        HadafV2.this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        //handle 'swipe left' action only

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

//// La variable du ComptageCurseurUser   me permet de pousser l'user a inserer ses objectifs lorsque la table objectif et Hissab est toujours vide durant la connexion et eviter un plantage de l'application.

            if (ComptageCurseurUser <4 ) {

                Toast.makeText(HadafV2.this, "المرجو تحديد الاهداف", Toast.LENGTH_LONG).show();

            }
            else {

                if (event2.getX() > event1.getX()) {

                    Intent intent = new Intent(HadafV2.this, HissabV2.class);

                    startActivity(intent);
                } else {

                    Intent intent2 = new Intent(HadafV2.this, HadafV2.class);
                    startActivity(intent2);
                }

            }
            return true;
        }


        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

    }

}


