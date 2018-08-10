// Noch ergänzen: Rezept muss in einer Datenbank gespeichert werden! (in saveClicked)


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


public class New_Recipe extends AppCompatActivity {
    private ArrayList<String> ingredients = new ArrayList<String>();
    private ArrayAdapter<String> ingredients_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__recipe);
        setupButton();
        setupListView();
    }

    private void setupButton(){
        Button addButton = (Button)findViewById(R.id.newRecipeButtonAdd);
        Button saveButton = (Button) findViewById(R.id.newRecipeButtonSave);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addClicked();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveClicked();
            }
        });
    }
    private void addClicked(){
        EditText newEntry = (EditText) findViewById(R.id.newRecipeEditIngredients);
        String newEntryText = newEntry.getText().toString();
        if(newEntryText != null && ! TextUtils.isEmpty(newEntryText.trim())) {
            ingredients.add(newEntry.getText().toString());
            newEntry.setText("");
            ingredients_adapter.notifyDataSetChanged();
        }else{
            // wenn man nichts eingegeben hat, kommt ein Toast
            newEntry.setText("");
            Toast toast = Toast.makeText(this, R.string.no_ingredient_toast, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    private void saveClicked(){
        //das Rezept muss in einer Datenbank gespeichert werden
        EditText recipeTitle = (EditText)findViewById(R.id.newRecipeEditRecipeTitle);
        String recipeTitleText = recipeTitle.getText().toString();
        EditText recipeDiscription = (EditText)findViewById(R.id.newRecipeEditRecipeDescription);
        String recipeDiscriptionText = recipeDiscription.toString();
        EditText recipeIngredients = (EditText)findViewById(R.id.newRecipeEditIngredients);
        String recipeIngredientsText = recipeIngredients.getText().toString();
        //nur wenn das Rezept vollständig ist, wird der Intent ausgeführt
        if(recipeTitleText != null && ! TextUtils.isEmpty(recipeTitleText.trim()) && recipeDiscriptionText != null && ! TextUtils.isEmpty(recipeDiscriptionText.trim()) && recipeIngredientsText != null && ! TextUtils.isEmpty(recipeIngredientsText.trim())){
            Intent intent = new Intent(this, Recipes.class);
            intent.putExtra("Rezeptname", recipeTitle.toString());
        }
        //Wenn Titel/Beschreibung/Zutaten des Rezeptes fehlern, wird ein Toast angezeigt
        else {
            Toast toast = Toast.makeText(this, R.string.not_full_recipe, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    private void setupListView(){
        ListView itemListView = (ListView) findViewById(R.id.newRecipeList);
        ingredients_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredients);
        itemListView.setAdapter(ingredients_adapter);

        //bei einem langen Klick kann man eine Zutat auch wieder löschen
        itemListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                remove(position);
                return false;
            }
        });
    }
    private void remove(int position){
        ingredients.remove(position);
        ingredients_adapter.notifyDataSetChanged();
    }
}
