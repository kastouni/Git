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
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class NatijaV2 extends AppCompatActivity {

    private Button Detail;
    private Button Annuel;
    private Button Totall;
    private GestureDetectorCompat gestureDetectorCompat;

    private EditText PresentValueSalat;
    private EditText TargetValueSalat;
    private EditText PourcentageSalat;

    private EditText PresentValueQuran;
    private EditText TargetValueQuran;
    private EditText PourcentageQuran;

    private EditText PresentValueSiyam;
    private EditText TargetValueSiyam;
    private EditText PourcentageSiyam;

    private EditText PresentValueSadaka;
    private EditText TargetValueSadaka;
    private EditText PourcentageSadaka;

    private EditText PresentValueAdkarAssabah;
    private EditText TargetValueAdkarAssabah;
    private EditText PourcentageAdkarAssabah;

    private EditText PresentValueAdkarAlMasaa;
    private EditText TargetValueAdkarAlMasaa;
    private EditText PourcentageAdkarAlMasaa;

    private EditText PresentValueQiyam;
    private EditText TargetValueQiyam;
    private EditText PourcentageQiyam;


    private SQLiteDatabase db;
    private UserBaseHelper mUserBaseHelper;
    private Cursor c;
    private Cursor c_Hadaf;
    private int sal=0;
    private int FinalResult=0;
    private int pourcent=0;
    private int ComptageCurseurUser=0;
    private Cursor c_extract_user;
    private String Name;
    private String User_emails;
    private String User_First_Connexion;
    private String User_second_Connexion;
    ArrayList<Float> resultats  = new ArrayList<Float>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_natija_v2);
        Toolbar toolbarHadaf = (Toolbar) findViewById(R.id.toolbarNatija);
        toolbarHadaf.setTitle("المتابعة");
        toolbarHadaf.setTitleTextColor(0x000000);
        setSupportActionBar(toolbarHadaf);

        gestureDetectorCompat = new GestureDetectorCompat(NatijaV2.this, new MyGestureListener());
        openDatabase();

        String SELECT_email1 = "SELECT * FROM users ;";
        c_extract_user = db.rawQuery(SELECT_email1, null);
        c_extract_user.moveToFirst();

        Name = c_extract_user.getString(2);
        User_emails= c_extract_user.getString(3);
        User_First_Connexion=c_extract_user.getString(4);
        User_second_Connexion=c_extract_user.getString(5);


        mUserBaseHelper= new UserBaseHelper(getApplicationContext());
        db=mUserBaseHelper.getWritableDatabase();

        ContentValues values = getContentValues();

        ComptageCurseurUser= c_extract_user.getCount();

        if ( ComptageCurseurUser < 9 ) {

            db.insert(UserDbSchema.UserTable.NAME, null, values);

        }

        Detail=(Button) findViewById(R.id.detail);
        Annuel=(Button) findViewById(R.id.Annual);
        Totall=(Button) findViewById(R.id.Total);

        PresentValueSalat=(EditText)findViewById(R.id.PresentValue1);
        TargetValueSalat =(EditText)findViewById(R.id.TargetValue1);
        PourcentageSalat=(EditText)findViewById(R.id.Pourcentage1);

        PresentValueQuran=(EditText)findViewById(R.id.PresentValueQu);
        TargetValueQuran =(EditText)findViewById(R.id.TargetValueQu);
        PourcentageQuran=(EditText)findViewById(R.id.PourcentageQu);

        PresentValueSiyam=(EditText)findViewById(R.id.PresentValueSi);
        TargetValueSiyam =(EditText)findViewById(R.id.TargetValueSi);
        PourcentageSiyam=(EditText)findViewById(R.id.PourcentageSi);

        PresentValueSadaka=(EditText)findViewById(R.id.PresentValueSd);
        TargetValueSadaka =(EditText)findViewById(R.id.TargetValueSd);
        PourcentageSadaka=(EditText)findViewById(R.id.PourcentageSd);

        PresentValueAdkarAssabah=(EditText)findViewById(R.id.PresentValueAS);
        TargetValueAdkarAssabah =(EditText)findViewById(R.id.TargetValueAS);
        PourcentageAdkarAssabah=(EditText)findViewById(R.id.PourcentageAS);

        PresentValueAdkarAlMasaa=(EditText)findViewById(R.id.PresentValueAM);
        TargetValueAdkarAlMasaa =(EditText)findViewById(R.id.TargetValueAM);
        PourcentageAdkarAlMasaa=(EditText)findViewById(R.id.PourcentageAM);

        PresentValueQiyam=(EditText)findViewById(R.id.PresentValueQi);
        TargetValueQiyam =(EditText)findViewById(R.id.TargetValueQi);
        PourcentageQiyam=(EditText)findViewById(R.id.PourcentageQi);

        openDatabase();

        Calendar calend = Calendar.getInstance();
        SimpleDateFormat MoisEnCoursF = new SimpleDateFormat("yyyy-MM");
        final String MoisEnCours=MoisEnCoursF.format(calend.getTime());

        // Selecte des donnée score avec les contraintes email et mois en cours

        String SELECT_data = "SELECT * FROM Data WHERE Month='" + MoisEnCours + "';";
        c = db.rawQuery(SELECT_data, null);
        c.moveToFirst();

        String SELECT_Hadaf = "SELECT * FROM Hadaf ;";
        c_Hadaf = db.rawQuery(SELECT_Hadaf, null);
        c_Hadaf.moveToFirst();

        ///////////Convertir les resultat du tableau de float à int pour eliminer la virgule lors de l'affichage

        ////////////Retreive Salat Data
        resultats.clear();
        resultats=CalculResultat(4);
        float Resultatfloat= resultats.get(0);
        int ResultatInt = (int)Resultatfloat;

        float Hadaffloat= resultats.get(1);
        int HadafInt = (int)Hadaffloat;

        PresentValueSalat.setText(String.valueOf(ResultatInt));
        TargetValueSalat.setText(String.valueOf(HadafInt));
        PourcentageSalat.setText(String.valueOf(resultats.get(2)));
        ////////////Retreive Quran Data
        resultats.clear();
        resultats=CalculResultat(5);
        float ResultatfloatQ= resultats.get(0);
        int ResultatIntQ = (int)ResultatfloatQ;

        float HadaffloatQ= resultats.get(1);
        int HadafIntQ = (int)HadaffloatQ;

        PresentValueQuran.setText(String.valueOf(ResultatIntQ));
        TargetValueQuran.setText(String.valueOf(HadafIntQ));
        PourcentageQuran.setText(String.valueOf(resultats.get(2)));

        ////////////Retreive Siyam Data
        resultats.clear();
        resultats=CalculResultat(6);
        float ResultatfloatS= resultats.get(0);
        int ResultatIntS = (int)ResultatfloatS;

        float HadaffloatS= resultats.get(1);
        int HadafIntS = (int)HadaffloatS;

        PresentValueSiyam.setText(String.valueOf(ResultatIntS));
        TargetValueSiyam.setText(String.valueOf(HadafIntS));
        PourcentageSiyam.setText(String.valueOf(resultats.get(2)));


        ////////////Retreive Sadaka Data
        resultats.clear();
        resultats=CalculResultat(7);
        float ResultatfloatSd= resultats.get(0);
        int ResultatIntSd = (int)ResultatfloatSd;

        float HadaffloatSd= resultats.get(1);
        int HadafIntSd = (int)HadaffloatSd;

        PresentValueSadaka.setText(String.valueOf(ResultatIntSd));
        TargetValueSadaka.setText(String.valueOf(HadafIntSd));
        PourcentageSadaka.setText(String.valueOf(resultats.get(2)));

        ////////////Retreive AdkarAssabah Data
        resultats.clear();
        resultats=CalculResultat(8);
        float ResultatfloatAS= resultats.get(0);
        int ResultatIntAS = (int)ResultatfloatAS;

        float HadaffloatAS= resultats.get(1);
        int HadafIntAS = (int)HadaffloatAS;

        PresentValueAdkarAssabah.setText(String.valueOf(ResultatIntAS));
        TargetValueAdkarAssabah.setText(String.valueOf(HadafIntAS));
        PourcentageAdkarAssabah.setText(String.valueOf(resultats.get(2)));

        ////////////Retreive AdkarALMasaa Data
        resultats.clear();
        resultats=CalculResultat(9);
        float ResultatfloatAM= resultats.get(0);
        int ResultatIntAM = (int)ResultatfloatAM;

        float HadaffloatAM= resultats.get(1);
        int HadafIntAM = (int)HadaffloatAM;

        PresentValueAdkarAlMasaa.setText(String.valueOf(ResultatIntAM));
        TargetValueAdkarAlMasaa.setText(String.valueOf(HadafIntAM));
        PourcentageAdkarAlMasaa.setText(String.valueOf(resultats.get(2)));

        ////////////Retreive Qiyam Data
        resultats.clear();
        resultats=CalculResultat(10);
        float ResultatfloatQi= resultats.get(0);
        int ResultatIntQi = (int)ResultatfloatQi;

        float HadaffloatQi= resultats.get(1);
        int HadafIntQi = (int)HadaffloatQi;

        PresentValueQiyam.setText(String.valueOf(ResultatIntQi));
        TargetValueQiyam.setText(String.valueOf(HadafIntQi));
        PourcentageQiyam.setText(String.valueOf(resultats.get(2)));

        Detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(NatijaV2.this, GrapheV2.class);

                startActivity(intent2);
            }
        });

        Annuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(NatijaV2.this, Natija_Weekly.class);

                startActivity(intent2);
            }
        });

        Totall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(NatijaV2.this, Total.class);

                startActivity(intent2);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_natija_v2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Target) {
            Intent intent = new Intent(
                    NatijaV2.this, HadafV2.class);
            startActivity(intent);

            //   return true;
        }

        if (id == R.id.add) {
            Intent intent = new Intent(
                    NatijaV2.this, HissabV2.class);
            startActivity(intent);

            //   return true;
        }

        if (id == R.id.stats) {
            Intent intent = new Intent(
                    NatijaV2.this, NatijaV2.class);
            startActivity(intent);

            //   return true;
        }

        if (id == R.id.graph) {
            Intent intent = new Intent(
                    NatijaV2.this, GrapheV2.class);
            startActivity(intent);

            //   return true;
        }


        return super.onOptionsItemSelected(item);
    }



    private  ContentValues getContentValues () {

        ContentValues values= new ContentValues();

        values.put(UserDbSchema.UserTable.Cols.KEY_NOM_USER, Name);
        values.put(UserDbSchema.UserTable.Cols.KEY_EMAIL_USER, User_emails);
        values.put(UserDbSchema.UserTable.Cols.KEY_PHONE_USER, User_First_Connexion);
        values.put(UserDbSchema.UserTable.Cols.KEY_Connexion, "False");




        return values;
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        NatijaV2.this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }





    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

            if (event2.getX() > event1.getX()) {

                Intent intent = new Intent(
                        NatijaV2.this, GrapheV2.class);
                startActivity(intent);
            } else {

                Intent intent = new Intent(
                        NatijaV2.this, HissabV2.class);
                startActivity(intent);
            }


            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }


    protected void openDatabase() {
        db = openOrCreateDatabase("userBase.db", Context.MODE_PRIVATE, null);
    }


    private ArrayList<Float> CalculResultat (int itemNumber) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        ArrayList<Float> result_jour  = new ArrayList<Float>();
        //Long[] result_jour =  new Long[] ();
       // ArrayList<Date> result_Date = new ArrayList<Date>();

        int valeur_ibada=0;
        // valeur qui contiendra le score de l'3ibada
        int value_int_Hadaf=0;
        // valeur qui contiendra al Hadaf mensuel
        c.moveToFirst();


        do {
            String iibada = c.getString(itemNumber);

            if (iibada.equals("في وقتها")) {
                sal = 1;
            }
            else if (iibada.equals("نعم")){
                sal = 1;
            }

            else if (iibada.equals("جزء")){
                sal = 1;
            }

            else  {
                sal=0;
            }

// incrementation du score Ibada

            valeur_ibada= valeur_ibada +sal;
            c_Hadaf.moveToLast();
            String value_Hadaf = c_Hadaf.getString(itemNumber);
            value_int_Hadaf= Integer.parseInt(value_Hadaf);

            // result est le tableau contenant les scores
            result.add(valeur_ibada);


        }while (c.moveToNext());

         FinalResult=result.get(result.size()-1);
        pourcent=FinalResult*100/value_int_Hadaf;
        result_jour.add((float) FinalResult);
        result_jour.add((float) value_int_Hadaf);
        result_jour.add((float) pourcent);
        result.clear();

       return result_jour;

    }





}

