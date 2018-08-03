package zizkuz_menu.hassebnafssak;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GrapheV2 extends AppCompatActivity implements   AdapterView.OnItemSelectedListener{

    // private String extra_email;
  //  private String public_email;
    private Date  dateExtraite;
    private Date  jourExtrait;
  //  private Date jourExtraite;
    private Date  dateDebut;
    private Date dateFin;
    private int  ValeurDebut;
    private int ValeurFin;


    // private final String SELECT_data = "SELECT * FROM Data WHERE email='" + public_email + "';";
    private SQLiteDatabase db;
    private Cursor c;
    private Cursor c_Hadaf;
    private int sal = 0;
    private String email;
  //  private TextView Natija;
    private Cursor c_extract_notif_Mail ;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<LineGraphSeries>  graphlist = new ArrayList<>();
    private List<LineGraphSeries>  graphlist2 = new ArrayList<>();
    private int valeurAxeX;


  //  private GraphView GraphDuRecycler;
    LineGraphSeries<DataPoint> series3AdapterRecycler;
    LineGraphSeries<DataPoint> series4AdapterRecycler;
//    View.OnTouchListener gestureListener;
    private List<String> IIbada = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphe_v2);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbarGraphe5);
        toolbar1.setTitle("البيانات");
        toolbar1.setTitleTextColor(0xFFFFFFFF);

        setSupportActionBar(toolbar1);

        ////////////////////////Recycler View/////////////////
        ////////////////////////////////////////////
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //// voir en bas de OnCreate la suite du recycle View


        openDatabase();
        String SELECT_email = "SELECT * FROM users ;";
        c_extract_notif_Mail= db.rawQuery(SELECT_email, null);
        c_extract_notif_Mail.moveToFirst();
        email= c_extract_notif_Mail.getString(3);

        Calendar calend = Calendar.getInstance();
        SimpleDateFormat MoisEnCoursF = new SimpleDateFormat("yyyy-MM");
        final String MoisEnCours=MoisEnCoursF.format(calend.getTime());

        // Selecte des donnée score avec les contraintes email et mois en cours

        String SELECT_data = "SELECT * FROM Data WHERE email='" + email + "' and Month='" + MoisEnCours + "';";
        c = db.rawQuery(SELECT_data, null);
        c.moveToFirst();

        String SELECT_Hadaf = "SELECT * FROM Hadaf WHERE email='" + email + "';";
        c_Hadaf = db.rawQuery(SELECT_Hadaf, null);
        c_Hadaf.moveToFirst();

        //////// suite du Recycler View decalé à la fin afin d'avoir nos curseur deja pret

        DessinerGraph();
        ListOfIibada();
      //  mAdapter = new RecyclerAdapter(getApplicationContext(),graphlist,graphlist2,IIbada,dateDebut,dateFin);
        mAdapter = new RecyclerAdapter(getApplicationContext(),graphlist,graphlist2,IIbada,ValeurDebut,ValeurFin);


        mRecyclerView.setAdapter(mAdapter);

    }


    protected void openDatabase() {
        db = openOrCreateDatabase("userBase.db", Context.MODE_PRIVATE, null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_graphe_v2, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        if (id == R.id.add1) {
            Intent intent = new Intent(
                    GrapheV2.this, HissabV2.class);
            startActivity(intent);

            //   return true;
        }


            return super.onOptionsItemSelected(item);
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
      String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG).show();

      //  callGraph(item);     // pour ne pas appeler le graphe


    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private List<String> ListOfIibada () {

        IIbada.clear();
        IIbada.add("الصلاة في وقتها");
        IIbada.add("تلاوة القرآن");
        IIbada.add("الصيام");
        IIbada.add(" إخراج صدقة");
        IIbada.add("صلة رحم");
        IIbada.add("ذكر الله كثيرا");
        IIbada.add(" تعلم شيئ جديد");
        return IIbada;
    }

    private void LancerGraph(int itemNumber) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        ArrayList<Integer> result_jour = new ArrayList<Integer>();
        ArrayList<Date> result_Date = new ArrayList<Date>();

        int valeur_ibada=0;
        // valeur qui contiendra le score de l'3ibada
        int value_int_Hadaf=0;
        // valeur qui contiendra al Hadaf mensuel
        c.moveToFirst();
       // SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat curFormater = new SimpleDateFormat("dd");

        do {
            String iibada = c.getString(itemNumber);
          //  String  Date_extraite = c.getString(3);
            String Jour_extrait=c.getString(12);
            valeurAxeX =Integer.parseInt(Jour_extrait);


            try {
                jourExtrait = curFormater.parse(Jour_extrait);
            } catch (ParseException e) {
                e.printStackTrace();
            }


        if (iibada.equals("جماعة")) {
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

            // result Date est le tableau contenant les dates

           // result_Date.add(jourExtrait);
            result_jour.add(valeurAxeX);


        }while (c.moveToNext());

    //    drawGraph(result, value_int_Hadaf, result_Date);
        drawGraph(result, value_int_Hadaf, result_jour);
        result.clear();
        result_Date.clear();
        result_jour.clear();

    }

    private void drawGraph(ArrayList<Integer> tableau, int Hadaf,ArrayList<Integer> tableauDate ) {

        ArrayList<DataPoint> Chart = new ArrayList<DataPoint>();

        for (int j = 0; j < tableau.size(); j++) {

            if (j == 0) {
            //  dateDebut=  tableauDate.get(j);
                ValeurDebut=tableauDate.get(j);

            }

            if(j+1==tableau.size()){

          //       dateFin= tableauDate.get(j);
                ValeurFin=tableauDate.get(j);

            }

            DataPoint coordonne = new DataPoint(tableauDate.get(j), tableau.get(j));

            Chart.add(coordonne);
        }

        DataPoint[] dpArray = {};
        dpArray = Chart.toArray(dpArray);

        //  Toast.makeText(GraphActivity.this, "Chart "+Chart,Toast.LENGTH_LONG).show();

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dpArray);
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, Hadaf),
                new DataPoint(30, Hadaf),
        });



        series.setDrawBackground(true); // activate the background feature
        series.setBackgroundColor(Color.BLACK);

        series2.setDrawDataPoints(true);
        series2.setDataPointsRadius(15);

        db.close();

        series3AdapterRecycler=series;
        series4AdapterRecycler= series2;

    }

    ///////////  remplire le tableau des graphs à communiquer à l'adapter du recycler view

       private void DessinerGraph () {

           graphlist.clear();
           graphlist2.clear();
           for ( int j=4; j<11; j++) {

               LancerGraph(j);
               graphlist.add(series3AdapterRecycler);
               graphlist2.add(series4AdapterRecycler);

                      }
       }





    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent(
                GrapheV2.this, HissabV2.class);
        startActivity(intent);

    }
}
