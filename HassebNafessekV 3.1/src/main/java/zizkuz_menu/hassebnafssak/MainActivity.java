package zizkuz_menu.hassebnafssak;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;
import java.util.GregorianCalendar;

import zizkuz_menu.hassebnafssak.UserDbSchema.UserTable;

public class MainActivity extends AppCompatActivity {

    private EditText Nom;
    private EditText eMail;
    private EditText MobilePhone;
    private SQLiteDatabase mDatabase;
    private Button newUser;
    private   UserBaseHelper mUserBaseHelper;
    private String User_email ;
    public static int number_connexion=0;
    private Boolean flag ;
    private SQLiteDatabase db;
    private Cursor c;
    private int mId=0;
    private String constante;
    private String email_recupere_delabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


         loadSavedPreferences();
         Intent myIntent;


        /////////////insertion des infos clients initial pour peuplé la base et eviter d'avoir des pbm si le user n'introduit pas ses données.
        mUserBaseHelper= new UserBaseHelper(getApplicationContext());
        mDatabase=mUserBaseHelper.getWritableDatabase();


         if(flag){

             savePreferences();

             ContentValues values2 = getContentValues1();
             mDatabase.insert(UserTable.NAME, null, values2);

             ContentValues values3= getContentValues_initial();
             mDatabase.insert(UserDbSchema.HadafTable.NAME_HADAF, null, values3);


             setContentView(R.layout.activity_main);
        Nom= (EditText) findViewById(R.id.usernameET);
        eMail = (EditText) findViewById(R.id.passwordET);
      //  MobilePhone=(EditText)findViewById(R.id.PhoneNumber);
        newUser=(Button)findViewById(R.id.loginBtn);

         mUserBaseHelper= new UserBaseHelper(getApplicationContext());
         mDatabase=mUserBaseHelper.getWritableDatabase();

          ContentValues values_initiales= getContentValues1();

         mDatabase.insert(UserDbSchema.UserTable.NAME, null, values_initiales);

          newUser.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  //   Users user = new Users(String.valueOf(Nom.getText()), String.valueOf(eMail.getText()), String.valueOf(MobilePhone.getText()));
                  Users user = new Users(String.valueOf(Nom.getText()), String.valueOf(eMail.getText()), constante);
                  ContentValues values = getContentValues(user);
                  mDatabase.insert(UserTable.NAME, null, values);

               //   mDatabase.update(UserDbSchema.UserTable.NAME, values, "where _id = 1", null);


                  number_connexion++;

                  //  Intent intent2= new Intent(MainActivity.this,TahdidAlAhdaf.class);
                  Intent intent2 = new Intent(MainActivity.this, HadafV2.class);
                  String userEmail = user.getEmail(user);
                  intent2.putExtra("User_email", userEmail);
                  startActivity(intent2);

                  settingAlarm();
              }
          });


         }else {

             // si c'est pas le premier lancement, on recupere l'adresse mail de la base et on lance l'activité Hissab avec l'email en EXTRA
             // mUserBaseHelper= new UserBaseHelper(getApplicationContext());
            //   db=mUserBaseHelper.getReadableDatabase();
           //  Toast.makeText(MainActivity.this," ! " , Toast.LENGTH_SHORT).show();
             openDatabase();
             String SELECT_email = "SELECT * FROM users ;";

             c = db.rawQuery(SELECT_email, null);
             c.moveToFirst();
              email_recupere_delabase= c.getString(3);
             settingAlarm();


           //  myIntent = new Intent(MainActivity.this, HissabPage.class);
             myIntent = new Intent(MainActivity.this, HissabV2.class);
             myIntent.putExtra("User_email", email_recupere_delabase);
             startActivity(myIntent);
            // settingAlarm();
            // c.close();
         }


    }

    private void loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        flag = sharedPreferences.getBoolean("FirstLaunch", true);

    }

    private void savePreferences() {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("FirstLaunch", false);
        editor.apply();


    }


    protected void openDatabase() {
        db = openOrCreateDatabase("userBase.db", Context.MODE_PRIVATE, null);
    }

    private static ContentValues getContentValues (Users user){

        ContentValues values = new ContentValues();
        values.put(UserTable.Cols.KEY_NOM_USER, user.getNom(user));
        values.put(UserTable.Cols.KEY_EMAIL_USER, user.getEmail(user));
        //values.put(UserTable.Cols.KEY_PHONE_USER, user.getTelephone(user));
        values.put(UserTable.Cols.KEY_PHONE_USER, "Premiere_connexion");
        values.put(UserTable.Cols.KEY_Connexion, "Free");

        return values;
    }


    private static ContentValues getContentValues1 (){

        ContentValues values = new ContentValues();
        values.put(UserTable.Cols.KEY_NOM_USER, "Free");
        values.put(UserTable.Cols.KEY_EMAIL_USER, "Free");
        values.put(UserTable.Cols.KEY_PHONE_USER, "Premiere_connexion");
        values.put(UserTable.Cols.KEY_Connexion, "Free");

        return values;
    }

    private  ContentValues getContentValues_initial () {

        ContentValues values = new ContentValues();

        values.put(UserDbSchema.HadafTable.Cols.KEY_ID_USER_EMAIL_HADAF, "30");
        values.put(UserDbSchema.HadafTable.Cols.KEY_DATE_HADAF, "60");
        values.put(UserDbSchema.HadafTable.Cols.KEY_SALAT_HADAF, "30");
        values.put(UserDbSchema.HadafTable.Cols.KEY_QURAN_HADAF, "30");
        values.put(UserDbSchema.HadafTable.Cols.KEY_SIYAM_HADAF, "30");
        values.put(UserDbSchema.HadafTable.Cols.KEY_SADAKA_HADAF, "30");
        values.put(UserDbSchema.HadafTable.Cols.KEY_ADKARASSABAH_HADAF, "30");
        values.put(UserDbSchema.HadafTable.Cols.KEY_ADKARALMASA_HADAF, "30");
        values.put(UserDbSchema.HadafTable.Cols.KEY_QIYAM_HADAF, "30");


        return values;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private  void settingAlarm (){


// Creates an explicit intent for an Activity in your app

        openDatabase();
        String SELECT_email = "SELECT * FROM users ;";
        c = db.rawQuery(SELECT_email, null);
        c.moveToFirst();
        String email_recupere= c.getString(3);

       // Intent resultIntent = new Intent(MainActivity.this, HissabPage.class);
        Intent resultIntent = new Intent(MainActivity.this, HissabV2.class);
          resultIntent.putExtra("User_email", email_recupere);


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//////////////////////////////////////////////////////////////////:
       // stackBuilder.addParentStack(TahdidAlAhdaf.class);
        stackBuilder.addParentStack(HadafV2.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher2)
                        .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                                R.mipmap.ic_launcher2))
                        .setContentTitle("حاسب نفسك")
                        .setContentText("لاتنسى ذكر الله")
                        .setSound(sound)
                        .setAutoCancel(true)
                        .setContentIntent(resultPendingIntent)
                         ;

   // mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(0, mBuilder.build());


        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, 22);
        cal.set(Calendar.MINUTE, 1);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        //check if we want to wake up tomorrow


        if (System.currentTimeMillis() > cal.getTimeInMillis()){
          cal.setTimeInMillis(cal.getTimeInMillis() + 24 * 60 * 60 * 1000);// Okay, then tomorrow ...
      }



        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // Set the alarm to start at 22:00 AM
        manager.setRepeating(AlarmManager.RTC_WAKEUP,
                cal.getTimeInMillis(), 86400000, // for repeating
                // in every 24
                // hours
                resultPendingIntent);

        ////////  Remplissage des données de la journée par des 0 si l'utilsateur ne l'a pas fait:


        /*




        Intent IntentRemplissageDonnes = new Intent(MainActivity.this, RemplissageDonnesQuotidiennes.class);
        NotificationCompat.Builder mBuilder2 =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.abc_ab_share_pack_mtrl_alpha)
                        .setContentTitle("حاسب نفسك")
                        .setContentText("إدخال المعلومات")
                        .setSound(Uri.EMPTY)
                ;



        TaskStackBuilder stackBuilder2 = TaskStackBuilder.create(this);
//////////////////////////////////////////////////////////////////:
        // stackBuilder.addParentStack(TahdidAlAhdaf.class);
        stackBuilder2.addParentStack(HadafV2.class);
        stackBuilder2.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent2 =
                stackBuilder2.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager2 =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Calendar cal_remplissage = new GregorianCalendar();
        cal_remplissage.setTimeInMillis(System.currentTimeMillis());
        cal_remplissage.set(Calendar.HOUR_OF_DAY, 23);
        cal_remplissage.set(Calendar.MINUTE, 59);
        cal_remplissage.set(Calendar.SECOND, 0);
        cal_remplissage.set(Calendar.MILLISECOND, 0);
        //check if we want to wake up tomorrow
        if (System.currentTimeMillis() > cal_remplissage.getTimeInMillis()){
            cal_remplissage.setTimeInMillis(cal.getTimeInMillis()+ 24*60*60*1000);// Okay, then tomorrow ...
        }

        AlarmManager manager_remplissage_quotidien_donnees = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // Set the alarm to start at 23:59 AM
        manager_remplissage_quotidien_donnees.setRepeating(AlarmManager.RTC_WAKEUP,
                cal_remplissage.getTimeInMillis(), 86400000, // for repeating
                // in every 24
                // hours
                resultPendingIntent2);

        */

    }




}
