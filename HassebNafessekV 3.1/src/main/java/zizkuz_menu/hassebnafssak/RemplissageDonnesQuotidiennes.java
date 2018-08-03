package zizkuz_menu.hassebnafssak;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RemplissageDonnesQuotidiennes extends Service {

    private SQLiteDatabase db;
    private Cursor c_extract_notif_Date;
    private Cursor  c_Recherche_Date;
    public RemplissageDonnesQuotidiennes() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat DateDuJour = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat MoisDeLAnnee = new SimpleDateFormat("yyyy-MM");
        final String MoisAnnee = MoisDeLAnnee.format(c.getTime());
        final String strDate = DateDuJour.format(c.getTime());

        openDatabase();
        String SELECT_email1 = "SELECT * FROM users ;";
        c_extract_notif_Date = db.rawQuery(SELECT_email1, null);
        c_extract_notif_Date.moveToFirst();
        String email_recupere_delabase = c_extract_notif_Date.getString(3);

        String SELECT_Recherche_Date = "SELECT * FROM Data WHERE email='" + email_recupere_delabase + "';";
        c_Recherche_Date = db.rawQuery(SELECT_Recherche_Date, null);
        c_Recherche_Date.moveToLast();

        String Extract_Date = c_Recherche_Date.getString(3);
        if (Extract_Date.equals(strDate)) {
            // Do nothing , user has alredy put his data
                    }
        else {

            ////// inserer des zero dans la journée en cours
            ContentValues values = getContentValues(email_recupere_delabase, strDate, MoisAnnee);
            db.insert(UserDbSchema.DataTable.NAME_DATA, null, values);

            db.close();

        }

    }


    protected void openDatabase() {
        db = openOrCreateDatabase("userBase.db", Context.MODE_PRIVATE, null);
    }

    private ContentValues getContentValues (String email, String myDate, String Mois) {

        ContentValues values = new ContentValues();


        values.put(UserDbSchema.DataTable.Cols.KEY_ID_USER_EMAIL, email);
        values.put(UserDbSchema.DataTable.Cols.KEY_DATE, myDate);
        values.put(UserDbSchema.DataTable.Cols.KEY_SALAT," فرد");
        values.put(UserDbSchema.DataTable.Cols.KEY_QURAN, "لاشيء");
        values.put(UserDbSchema.DataTable.Cols.KEY_SIYAM,"لا" );
        values.put(UserDbSchema.DataTable.Cols.KEY_SADAKA,"لا" );
        values.put(UserDbSchema.DataTable.Cols.KEY_ADKARASSABAH,"لا" );
        values.put(UserDbSchema.DataTable.Cols.KEY_ADKARALMASA,"لا" );
        values.put(UserDbSchema.DataTable.Cols.KEY_QIYAM,"لا" );
        values.put(UserDbSchema.DataTable.Cols.KEY_MONTH, Mois);

        return values;
    }




    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
