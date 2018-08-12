//noch ergänzen: in setupListView muss das Rezeot bei einem langen Click auch aus der datenbank gelöscht werden


package rechnrer.cookies.com.rezeptapp;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Recipes extends AppCompatActivity {
    private ArrayList<String> recipes = new ArrayList<String>();
    private ArrayAdapter<String> recipesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        setupListView();
        getExtrasToList();
        setupButtons();
    }

    private void setupButtons() {
    }

    private void setupListView(){
        ListView recipeListView = (ListView) findViewById(R.id.recipeList);
        recipesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recipes);
        recipeListView.setAdapter(recipesAdapter);
        //bei langem Klick wird ein Rezept gelöscht
        //ergänzen: auch aus der Datenbank löschen
        recipeListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                remove(position);
                return false;
            }
        });
    }
    //so wird ein neuer Rezepttitel hinzugefügt
    private void getExtrasToList(){
        String newRecipe = getIntent().getStringExtra("Rezeptname");
        recipes.add(newRecipe);
        recipesAdapter.notifyDataSetChanged();
    }
    private void remove(int position){
        recipes.remove(position);
        recipesAdapter.notifyDataSetChanged();
    }
}
