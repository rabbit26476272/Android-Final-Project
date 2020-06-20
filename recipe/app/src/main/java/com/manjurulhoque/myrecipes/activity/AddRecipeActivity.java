package com.manjurulhoque.myrecipes.activity;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.manjurulhoque.myrecipes.R;

public class AddRecipeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private EditText mEditRecipe;
    private EditText mEditIngredients;
    private EditText mEditPrepare;
    private Button mButtonCreate;
    private ImageView mImageViewRecipe;
    private ImageView mImageViewIngredients;
    private ImageView mImageViewPrepare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initData();
        initToolbar();
        initSpinner();
        mButtonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contentRecipe = mEditRecipe.getText().toString();
                String contentIngredients = mEditIngredients.getText().toString();
                String contentPrepare = mEditPrepare.getText().toString();
                if(contentRecipe.length() == 0)
                    mImageViewRecipe.setColorFilter(getResources().getColor(R.color.redDark), PorterDuff.Mode.SRC_ATOP);
                else
                    mImageViewRecipe.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
                if(contentIngredients.length() == 0)
                    mImageViewIngredients.setColorFilter(getResources().getColor(R.color.redDark), PorterDuff.Mode.SRC_ATOP);
                else
                    mImageViewIngredients.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
                if(contentPrepare.length() == 0)
                    mImageViewPrepare.setColorFilter(getResources().getColor(R.color.redDark), PorterDuff.Mode.SRC_ATOP);
                else
                    mImageViewPrepare.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
                if(contentRecipe.length() != 0 && contentIngredients.length() != 0 && contentPrepare.length() != 0)
                    Toast.makeText(getApplicationContext(),"已新增食譜",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"食譜未填寫完整",Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void initData() {
        mEditRecipe = findViewById(R.id.editRecipe);
        mEditIngredients = findViewById(R.id.editIngredients);
        mEditPrepare = findViewById(R.id.editPrepare);
        mButtonCreate = findViewById(R.id.buttonCreate);
        mImageViewRecipe = findViewById(R.id.imageRecipe);
        mImageViewIngredients = findViewById(R.id.imageIngredients);
        mImageViewPrepare = findViewById(R.id.imagePrepare);
    }
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.tab_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ADD RECIPE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }
    private void initSpinner() {
        Spinner spinner = findViewById(R.id.spinner);
        if (spinner != null) {
            spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        }

        // Create an ArrayAdapter using the string array and default spinner
        // layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getApplicationContext(),
                R.array.labels_array,
                android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner.
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
