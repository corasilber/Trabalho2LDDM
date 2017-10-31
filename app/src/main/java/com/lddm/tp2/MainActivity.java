package com.lddm.tp2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lddm.tp2.data.DisciplinaContract;
import com.lddm.tp2.data.DisciplinaDbHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

   // ArrayList<String> materias  = new ArrayList<>(Arrays.asList("Engenharia de Software", "LDDM", "Grafos"));



    Menu menu = null;
    SubMenu subMenu =  null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //reseta o banco de dados todo
     //   deletaSQL();

        //seta como fixo algumas matérias
        adicionaSQLFixo("Engenharia de Software");
        adicionaSQLFixo("LDDM");
        adicionaSQLFixo("Grafos");

       // displayDataBase();
        adicionaMateria();
        drawerLayout(toolbar);
        iniciaNav();


    }





    private void adicionaMateria(){

    //   final DisciplinaDbHelper mDbHelper = new DisciplinaDbHelper(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertBuilder =  new AlertDialog.Builder(MainActivity.this); // mostra um alert dialog
                View view2 =  getLayoutInflater().inflate(R.layout.tela_adiciona_materia,null);
                final EditText nomeMateria = (EditText) view2.findViewById(R.id.nomeMateria);
                Button addMateria = (Button) view2.findViewById(R.id.button);

                alertBuilder.setView(view2);

                final AlertDialog dialog = alertBuilder.create();
                dialog.show();

                addMateria.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(!nomeMateria.getText().toString().isEmpty()){
                            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                            menu = navigationView.getMenu();
                            String materia = nomeMateria.getText().toString();
                            subMenu.add(materia);
                            adicionaSQL(materia);
                            //.setOnMenuItemClickListener(clicaMenu("Disciplinas"));
                            Snackbar.make(view, "Matéria adicionada com sucesso!", Snackbar.LENGTH_LONG)
                                    .show();

                            dialog.dismiss();


                        } else{
                            nomeMateria.setError("Adicione uma materia para inserir!");
                            Snackbar.make(view, "Campo vazio!", Snackbar.LENGTH_LONG)
                                    .show();
                        }

                    }
                });
                            }
        });
    }
    private void adicionaSQLFixo(String materia){

        final DisciplinaDbHelper mDbHelper = new DisciplinaDbHelper(this);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM disciplinas", null);
        ContentValues values = new ContentValues();
        if(cursor.getCount() < 3) {
            values.put(DisciplinaContract.DisciplinaEntry.COLUMN_NAME, materia);
            db.insert(DisciplinaContract.DisciplinaEntry.TABLE_NAME, null, values);
        }
    }

    private void adicionaSQL(String materia){

        final DisciplinaDbHelper mDbHelper = new DisciplinaDbHelper(this);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(DisciplinaContract.DisciplinaEntry.COLUMN_NAME, materia);
        db.insert(DisciplinaContract.DisciplinaEntry.TABLE_NAME, null, values);

    }

    private void deletaSQL(){
        final DisciplinaDbHelper mDbHelper = new DisciplinaDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String clearDBQuery = "DELETE FROM "+ DisciplinaContract.DisciplinaEntry.TABLE_NAME;
        db.execSQL(clearDBQuery);
    }

    private void drawerLayout(Toolbar toolbar){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void iniciaNav(){

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final DisciplinaDbHelper mDbHelper = new DisciplinaDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM disciplinas", null);

        menu = navigationView.getMenu();
        subMenu = menu.addSubMenu("Disciplinas");

        if(cursor.moveToFirst()){
            do{
                String teste = cursor.getString(cursor.getColumnIndex(DisciplinaContract.DisciplinaEntry.COLUMN_NAME));
                subMenu.add(teste);

            }while(cursor.moveToNext());
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public  MenuItem.OnMenuItemClickListener clicaMenu(final String arquivo){
        return new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FragmentTransaction fragment = getSupportFragmentManager().beginTransaction();
                Bundle args = new Bundle();

                switch (arquivo) {
                    case "Disciplinas":
//                        PdfFragment pdfFragment = new PdfFragment();
//
//                        args.putString("subjectName", item.getTitle().toString());
//                        //args.putString("fileType", fileType);
//                        pdfFragment.setArguments(args);
//                        fragment.replace(R.id.placeholder_fragment, pdfFragment);
//                        break;
                }

                fragment.commit();
                return false;
            }
        };
    }

}
