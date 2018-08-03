package zizkuz_menu.hassebnafssak;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static zizkuz_menu.hassebnafssak.UserDbSchema.DataTable;

public class HissabV2 extends AppCompatActivity {

    private static RadioGroup RadioGroupSalat;
    private static RadioGroup RadioGroupQuran;
    private  static RadioGroup RadioGroupSiyam;
    private static RadioGroup RadioGroupSadaqa;
    private static RadioGroup RadioGroupAdkarAssabah;
    private static RadioGroup RadioGroupAdkarAlmasa;
    private static RadioGroup RadioGroupQiyam;
    private static Button insertData;

    private String User_First_Connexion;
    private String User_second_Connexion;

    // ajouter la declaration des radiobutton
    private static RadioButton RadioButtonSalat;
    private static RadioButton RadioButtonQuran;
    private static RadioButton RadioButtonSiyam;
    private static RadioButton RadioButtonSadaqa;
    private static RadioButton RadioButtonAdkarAssabah;
    private static RadioButton RadioButtonAdkarAlmasaa;
    private static RadioButton RadioButtonQiyam;


    private SQLiteDatabase mDatabase1;
    private UserBaseHelper mUserBaseHelper1;
    private String User_emails;
    private String Name;
    private String Mobile;
    private String Connexion;
    private String ComptageCurseurUserString;
    private int ComptageCurseurUser=0;


    private Cursor c_Recherche_Date;
    private Cursor c_extract_notif_Date;
    private Boolean flag2 ;
    private GestureDetectorCompat gestureDetectorCompat;
//    View.OnTouchListener gestureListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hissab_v2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("الحساب");
        toolbar.setTitleTextColor(0x000000);


        setSupportActionBar(toolbar);
        gestureDetectorCompat = new GestureDetectorCompat(HissabV2.this, new My2ndGestureListener());

        insertData=(Button)findViewById(R.id.Idkhal);
        mUserBaseHelper1= new UserBaseHelper(getApplicationContext());
        mDatabase1=mUserBaseHelper1.getWritableDatabase();


        openDatabase();

        String SELECT_email1 = "SELECT * FROM users ;";
        c_extract_notif_Date= mDatabase1.rawQuery(SELECT_email1, null);
        ComptageCurseurUser= c_extract_notif_Date.getCount();
        c_extract_notif_Date.moveToFirst();

        Name = c_extract_notif_Date.getString(2);
        User_emails= c_extract_notif_Date.getString(3);


        User_First_Connexion=c_extract_notif_Date.getString(4);
        User_second_Connexion=c_extract_notif_Date.getString(5);


        insertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


/// recuperation de la date du dernier enregistrement de la base pour la comparer avec la date du jour
                /// afin de savoir si l'utilisateur a deja introduit les données du jour. Si c'est le cas
                // un Toast sera affiché pour lui dire que les données ont été deja saisie.


               loadSavedPreferences();

                Calendar c = Calendar.getInstance();
                SimpleDateFormat Year = new SimpleDateFormat("yyyy");
                SimpleDateFormat DateDuJour = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat MoisDeLAnnee = new SimpleDateFormat("yyyy-MM");
                SimpleDateFormat JourduMoisFormat = new SimpleDateFormat("dd");

                final String Annee = Year.format(c.getTime());
                final String MoisAnnee = MoisDeLAnnee.format(c.getTime());
                final String Jour= JourduMoisFormat.format(c.getTime());
                final String strDate = DateDuJour.format(c.getTime());


                if (flag2) {

                    savePreferences();

                    ///// Recuperation de la date du jour

                    ContentValues values[] = getContentValues(User_emails, strDate, MoisAnnee,Jour, Annee);
                    mDatabase1.insert(DataTable.NAME_DATA, null, values[0]);


                    //// On limite les insertions dans la table user une fois on atteint 9 enregistrements

                    if ( ComptageCurseurUser < 9 ) {

                        mDatabase1.insert(UserDbSchema.UserTable.NAME, null, values[1]);

                    }

                  //////////////////// // Intent intent = new Intent(HissabV2.this, GrapheV2.class);
                    Intent intent = new Intent(HissabV2.this, NatijaV2.class);

                    intent.putExtra("extra_email", User_emails);
                    startActivity(intent);

                } else {
                    openDatabase();
                    String SELECT_email1 = "SELECT * FROM users ;";
                    c_extract_notif_Date = mDatabase1.rawQuery(SELECT_email1, null);
                    c_extract_notif_Date.moveToFirst();
                    String email_recupere_delabase = c_extract_notif_Date.getString(3);


                    String SELECT_Recherche_Date = "SELECT * FROM Data WHERE email='" + email_recupere_delabase + "';";
                    c_Recherche_Date = mDatabase1.rawQuery(SELECT_Recherche_Date, null);
                    c_Recherche_Date.moveToLast();

                    String Extract_Date = c_Recherche_Date.getString(3);
                    if (Extract_Date.equals(strDate)) {
                        openDatabase();

                        new AlertDialog.Builder(HissabV2.this)
                                .setTitle("تغيير المعلومات")
                                .setMessage("أأنت متأكد من إرادت تغيير معلوماتك اليومية؟")
                                .setPositiveButton(" نعم", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Suppression de la ligne des données du jours
                                        mDatabase1.execSQL("delete from  Data where Date='" + strDate + "';");
                                        ContentValues values[] = getContentValues(User_emails, strDate, MoisAnnee,Jour,Annee);
                                        mDatabase1.insert(DataTable.NAME_DATA, null, values[0]);
                                  /////////// On arrete les insertion dans la table User apres le 9 eme enregistrement
                                        if ( ComptageCurseurUser < 9 ) {
                                            mDatabase1.insert(UserDbSchema.UserTable.NAME, null, values[1]);
                                        }
                                    //    mDatabase1.insert(UserDbSchema.UserTable.NAME, null, values[1]);
                                        Toast.makeText(HissabV2.this, "ثم تغيير المعلومات ", Toast.LENGTH_SHORT).show();
                                        /////////////Intent intent = new Intent(HissabV2.this, GrapheV2.class);
                                        Intent intent = new Intent(HissabV2.this, NatijaV2.class);

                                        intent.putExtra("extra_email", User_emails);
                                        startActivity(intent);
                                        mDatabase1.close();

                                    }
                                })
                                .setNegativeButton("لا", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    /////////////////////    Intent intent = new Intent(HissabV2.this, GrapheV2.class);
                                        Intent intent = new Intent(HissabV2.this, NatijaV2.class);

                                        intent.putExtra("extra_email", User_emails);
                                        startActivity(intent);
                                        mDatabase1.close();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();


                    } else {
                        openDatabase();
                        ContentValues values[] = getContentValues(User_emails, strDate, MoisAnnee, Jour,Annee);

                        mDatabase1.insert(DataTable.NAME_DATA, null, values[0]);

                        ///////////On arrete les insertions dans la table user
                        if ( ComptageCurseurUser < 9 ) {
                            mDatabase1.insert(UserDbSchema.UserTable.NAME, null, values[1]);

                        }

                       /////////////////// Intent intent = new Intent(HissabV2.this, GrapheV2.class);
                        Intent intent = new Intent(HissabV2.this, NatijaV2.class);

                        intent.putExtra("extra_email", User_emails);
                        startActivity(intent);
                        mDatabase1.close();


                    }


                }
            }
        });

    }

    protected void openDatabase() {
        mDatabase1 = openOrCreateDatabase("userBase.db", Context.MODE_PRIVATE, null);
    }


    private  ContentValues[] getContentValues (String email, String myDate, String Mois, String day, String year) {

        ContentValues [] ChartValues= new ContentValues[2];

        RadioGroupSalat = (RadioGroup) findViewById(R.id.RadioGroupSalat);
        RadioGroupQuran = (RadioGroup) findViewById(R.id.RadioGroupQuran);
        RadioGroupSiyam = (RadioGroup) findViewById(R.id.RadioGroupSiyam);
        RadioGroupSadaqa = (RadioGroup) findViewById(R.id.RadioGroupSadaqa);
        RadioGroupAdkarAssabah = (RadioGroup) findViewById(R.id.RadioGroupAdkarAssabah);
        RadioGroupAdkarAlmasa = (RadioGroup) findViewById(R.id.RadioGroupAdkarAlmasa);
        RadioGroupQiyam = (RadioGroup) findViewById(R.id.RadioGroupQiyam);

        int selectedIdSalat = RadioGroupSalat.getCheckedRadioButtonId();
        int selectedIdQuran = RadioGroupQuran.getCheckedRadioButtonId();
        int selectedIdSiyam = RadioGroupSiyam.getCheckedRadioButtonId();
        int selectedIdSadaqa = RadioGroupSadaqa.getCheckedRadioButtonId();
        int selectedIdAdkarAssabah = RadioGroupAdkarAssabah.getCheckedRadioButtonId();
        int selectedIdAdkarAlmasa = RadioGroupAdkarAlmasa.getCheckedRadioButtonId();
        int selectedIdQiyam = RadioGroupQiyam.getCheckedRadioButtonId();

        ContentValues values = new ContentValues();


        RadioButtonSalat = (RadioButton) findViewById(selectedIdSalat);
        RadioButtonQuran = (RadioButton) findViewById(selectedIdQuran);
        RadioButtonSiyam = (RadioButton) findViewById(selectedIdSiyam);
        RadioButtonSadaqa = (RadioButton) findViewById(selectedIdSadaqa);
        RadioButtonAdkarAssabah = (RadioButton) findViewById(selectedIdAdkarAssabah);
        RadioButtonAdkarAlmasaa = (RadioButton) findViewById(selectedIdAdkarAlmasa);
        RadioButtonQiyam = (RadioButton) findViewById(selectedIdQiyam);
        //String myDate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());



        values.put(DataTable.Cols.KEY_ID_USER_EMAIL, email);
        values.put(DataTable.Cols.KEY_DATE, myDate);
        values.put(DataTable.Cols.KEY_SALAT, RadioButtonSalat.getText().toString());
        values.put(DataTable.Cols.KEY_QURAN, RadioButtonQuran.getText().toString());
        values.put(DataTable.Cols.KEY_SIYAM, RadioButtonSiyam.getText().toString());
        values.put(DataTable.Cols.KEY_SADAKA, RadioButtonSadaqa.getText().toString());
        values.put(DataTable.Cols.KEY_ADKARASSABAH, RadioButtonAdkarAssabah.getText().toString());
        values.put(DataTable.Cols.KEY_ADKARALMASA, RadioButtonAdkarAlmasaa.getText().toString());
        values.put(DataTable.Cols.KEY_QIYAM, RadioButtonQiyam.getText().toString());
        values.put(DataTable.Cols.KEY_MONTH, Mois);
        values.put(DataTable.Cols.KEY_DAY, day);
        values.put(DataTable.Cols.KEY_YEAR,year );


        ContentValues values1 = new ContentValues();
        values1.put(UserDbSchema.UserTable.Cols.KEY_NOM_USER, Name);
        values1.put(UserDbSchema.UserTable.Cols.KEY_EMAIL_USER, User_emails);
        values1.put(UserDbSchema.UserTable.Cols.KEY_PHONE_USER, "False1");
        values1.put(UserDbSchema.UserTable.Cols.KEY_Connexion, User_second_Connexion);


        ChartValues[0]=values;
        ChartValues[1]=values1;

        return ChartValues;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_hissab_v2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

     //   Toast.makeText(HissabV2.this,User_First_Connexion , Toast.LENGTH_LONG).show();

        if ( ComptageCurseurUser < 5  ) {

            Toast.makeText(HissabV2.this, "المرجو إدخال المعلومات", Toast.LENGTH_LONG).show();
        }
        else {

            if (id == R.id.Target) {
                Intent intent = new Intent(
                        HissabV2.this, HadafV2.class);
                startActivity(intent);

                //   return true;
            }

            if (id == R.id.add) {
                Intent intent = new Intent(
                        HissabV2.this, HissabV2.class);
                startActivity(intent);

                //   return true;
            }

            if (id == R.id.stats) {
                Intent intent = new Intent(
                        HissabV2.this, NatijaV2.class);
                startActivity(intent);

                //   return true;
            }

            if (id == R.id.graph) {
                Intent intent = new Intent(
                        HissabV2.this, GrapheV2.class);
                startActivity(intent);

                //   return true;
            }

        }
        return super.onOptionsItemSelected(item);

    }


    private void loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        flag2 = sharedPreferences.getBoolean("PremierLaunch", true);

    }

    private void savePreferences() {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("PremierLaunch", false);
        editor.commit();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        HissabV2.this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class My2ndGestureListener extends GestureDetector.SimpleOnGestureListener {
        //handle 'swipe right' action only

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {


            if ( ComptageCurseurUser < 5 ) {

                Toast.makeText(HissabV2.this, "المرجو إدخال المعلومات", Toast.LENGTH_LONG).show();
            }
            else {
                if (event2.getX() > event1.getX()) {

                    Intent intent = new Intent(
                     HissabV2.this, NatijaV2.class);
                    startActivity(intent);


                } else {

                    Intent intent = new Intent(
                            HissabV2.this, HadafV2.class);
                    startActivity(intent);

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


