package zizkuz_menu.hassebnafssak;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import zizkuz_menu.hassebnafssak.UserDbSchema.UserTable;

import static zizkuz_menu.hassebnafssak.UserDbSchema.*;
import static zizkuz_menu.hassebnafssak.UserDbSchema.DataTable;

/**
 * Created by zouheir_kastouni on 18/02/2016.
 */

public class UserBaseHelper extends SQLiteOpenHelper {

    private static  final String DATABASE_NAME = "userBase.db";
    private static final int VERSION = 1;

    public UserBaseHelper(Context context) {
        super(context,DATABASE_NAME,null,VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table " + DataTable.NAME_DATA + "("  +
                        "_id integer primary key autoincrement, " +
                        DataTable.Cols.KEY_ID_USER_DATA + ", " +
                        DataTable.Cols.KEY_ID_USER_EMAIL + ", " +
                        DataTable.Cols.KEY_DATE + ", " +
                        DataTable.Cols.KEY_SALAT  + ", " +
                        DataTable.Cols.KEY_QURAN + ", " +
                        DataTable.Cols.KEY_SIYAM + ", " +
                        DataTable.Cols.KEY_SADAKA + ", " +
                        DataTable.Cols.KEY_ADKARASSABAH + ", " +
                        DataTable.Cols.KEY_ADKARALMASA+ ", " +
                        DataTable.Cols.KEY_QIYAM + ", " +
                        DataTable.Cols.KEY_MONTH + ", " +
                        DataTable.Cols.KEY_DAY +  ", " +
                        DataTable.Cols.KEY_YEAR +
                        ")"
        );

       db.execSQL("create table " + UserTable.NAME + "(" +
               "_id integer primary key autoincrement, " +
               UserTable.Cols.KEY_ID_USER + ", " +
               UserTable.Cols.KEY_NOM_USER + ", " +
               UserTable.Cols.KEY_EMAIL_USER + ", " +
               UserTable.Cols.KEY_PHONE_USER + ", " +
               UserTable.Cols.KEY_Connexion +
               ")"
       );


        db.execSQL("create table " + HadafTable.NAME_HADAF + "(" +
                        "_id integer primary key autoincrement, " +
                        HadafTable.Cols.KEY_ID_USER_HADAF + ", " +
                        HadafTable.Cols.KEY_ID_USER_EMAIL_HADAF + ", " +
                        HadafTable.Cols.KEY_DATE_HADAF + ", " +
                        HadafTable.Cols.KEY_SALAT_HADAF + ", " +
                        HadafTable.Cols.KEY_QURAN_HADAF + ", " +
                        HadafTable.Cols.KEY_SIYAM_HADAF + ", " +
                        HadafTable.Cols.KEY_SADAKA_HADAF + ", " +
                        HadafTable.Cols.KEY_ADKARASSABAH_HADAF + ", " +
                        HadafTable.Cols.KEY_ADKARALMASA_HADAF + ", " +
                        HadafTable.Cols.KEY_QIYAM_HADAF +
                        ")"
        );

        db.execSQL("create table " + FirstConnection.NAME_First + "(" +
                        "_id integer primary key autoincrement, " +
                        FirstConnection.Cols.KEY_Indicateur_Premiere_connexion +
                        ")"
        );


     //   db.execSQL(  "INSERT INTO "+ FirstConnection.NAME_First + "  VALUES(yes)" );
        /////////////////////Remplissage de la ligne First connection pour savoir si c'est une premiere connexion ou non ///////////

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataTable.NAME_DATA);
        onCreate(db);

    }
}
