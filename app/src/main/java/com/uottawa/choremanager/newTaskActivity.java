package com.uottawa.choremanager;

import android.app.ListActivity;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class newTaskActivity extends AppCompatActivity {
    private ArrayList<SubTask> subTasks;
    private ArrayList<String> names;
    private newTaskMaterialsAdapter materialsTasksAdapter;
    private DataBase dB;
    private ArrayList<String> profileIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);
        dB = MainActivity.getDB();

        //profileIdList = dB.getProfileIds();

        names = new ArrayList<String>();
        subTasks = new ArrayList<SubTask>();
        final Spinner profiles = (Spinner) findViewById(R.id.spnProfiles);
        //Get array of Profiles, loop through and getNames->spinnerOption
        //Followed Tutorial https://developer.android.com/guide/topics/ui/controls/spinner.html#SelectListener
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //profiles.setAdapter(adapter);


        ListView test =(ListView) findViewById(R.id.listViewMaterials);
        //String testStr = test.toString();


        ListView materialsListView = (ListView) findViewById(R.id.listViewMaterials);
        //System.out.println("-------------------------------------------------> PRINTING ID" + testStr);


        materialsTasksAdapter = new newTaskMaterialsAdapter(getApplicationContext(),subTasks);
        materialsListView.setAdapter(materialsTasksAdapter);



        final Button addTaskButton = findViewById(R.id.btnAddTask);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Boolean done = false;
                String ownerID = "";
                ArrayList<SubTask> subTaskList = subTasks;
                String id = "";
                int startDate = Integer.parseInt( ((TextView)findViewById(R.id.txtStartDate)).getText().toString());
                int endDate = Integer.parseInt( ((TextView)findViewById(R.id.txtEndDate)).getText().toString() );
                String name = ((TextView)findViewById(R.id.txtTaskName)).getText().toString();
                String description = ((TextView)findViewById(R.id.txtNotes)).getText().toString();
                String status = "";

                dB.addTask(name, startDate, description, endDate, ownerID, subTaskList, status);

            }
        });
        final Button addButton = findViewById(R.id.btnAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String subTaskToAdd = ((TextView)findViewById(R.id.txtSTName)).getText().toString();


                if(!subTaskToAdd.equals("")) {
                    subTasks.add(new SubTask(subTaskToAdd, false));
                    names.add(subTaskToAdd);
                    materialsTasksAdapter.notifyDataSetChanged();

                }
            }
        });

        final Button removeButton = findViewById(R.id.btnRemove);
        removeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String subTaskToRemove = ((TextView)findViewById(R.id.txtSTName)).getText().toString();

                int indexOfSubTaskToBeRemoved = 9999999;

                //Checks list if the input is valid
                for(SubTask str: subTasks){

                    if(str.getName().equals(subTaskToRemove)){
                        indexOfSubTaskToBeRemoved = subTasks.indexOf(str);
                        System.out.println("-------------------------------> IDENTICAL TEXT HAS BEEN FOUND");
                        break;
                    }
                }

                try {
                    subTasks.remove(indexOfSubTaskToBeRemoved);
                    materialsTasksAdapter.notifyDataSetChanged();
                    System.out.println("-------------------------------> SUBTASK HAS BEEN REMOVED FROM LIST");
                } catch (IndexOutOfBoundsException e) {
                    CharSequence response = "Requested Material does not exist";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(getApplicationContext(), response, duration);
                    toast.show();
                    System.out.println("-------------------------------> TOAST HAS BEEN SHOWN");
                }

            }
        });


    }

}
