package com.example.luiscastillo.task_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver showTaskReceiver = new ShowTaskReceiver();
    BroadcastReceiver updateTaskCountReceiver = new UpdateTaskCountReceiver();

    final static String tag = "LACM";
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main);
        Log.d(tag, "The onCreate() event");  //NOTA
    }
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onStart(){

        super.onStart();
        Log.d(tag, "The onStart() event");

        IntentFilter intentTaskReady = new IntentFilter("com.LACM.CUSTOM_INTENT.TasksReady");
        this.registerReceiver(this.showTaskReceiver, intentTaskReady);

        IntentFilter intentTaskCountReady = new IntentFilter("com.LACM.CUSTOM_INTENT.TasksCountReady");
        this.registerReceiver(updateTaskCountReceiver, intentTaskCountReady);
    }
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onResume(){

        super.onResume();
        Log.d(tag, "The onResume() event");

        TaskDB taskDBInstance = TaskDB.getTaskDB(getApplicationContext());
        //                                                  //Asking to the DB for data.
        DBUtil.DBGetAllTask(taskDBInstance, getApplicationContext());
        //                                                  //Asking to the DB to count.
        DBUtil.DBTaskCount(taskDBInstance, getApplicationContext());
    }
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onPause()
    //                                                      //Called when another activity is taking focus.
    {
        super.onPause();
        Log.d(tag, "The onPause() event");

        //                                                  //To unregister a receiver of the broadcast
        Log.d(tag, "Unregistrando....");
        this.unregisterReceiver(this.showTaskReceiver);
        this.unregisterReceiver(this.updateTaskCountReceiver);
    }
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onStop(){

        super.onStop();
        Log.d(tag, "The onStop() event");
    }
    //----------------------------------------------------------------------------------------------
    @Override
    public void onDestroy(){

        super.onDestroy();
        Log.d(tag, "The onDestroy() event");
    }
    //----------------------------------------------------------------------------------------------
    public void ShowNewTaskForm(View view){

        Log.d(tag, "click ButtonNewTask");
        //                                                  //intent, intentara empezar la nueva
        //                                                  //  actividad
        Intent intent = new Intent(
                getApplicationContext(), NewTaskFormActivity.class);
        startActivity(intent);
    }
    //----------------------------------------------------------------------------------------------
    public void ShowAllTask(View view){

        Log.d(tag, "click ButtonShowAll");
        //                                                  //intent, intentara mostrar todo
        //                                                  //
        Intent intent = new Intent(
                getApplicationContext(), TaskListActivity.class);
        startActivity(intent);
    }
    /*
    * show task receiver
    * */

    //----------------------------------------------------------------------------------------------
    public void CancelNewTask(View view) {

        //                                                  //Back to main activity.
        android.content.Intent intent = new Intent(
                getApplicationContext(), NewTaskFormActivity.class);
        startActivity(intent);
    }
    //----------------------------------------------------------------------------------------------
    private class ShowTaskReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //                                              //When com.LGF.CUSTOM_INTENT.TasksReady occurs

            List<Task> listOfTask = DBUtil.getTasks();
            for (Task task: listOfTask){
                Log.d("LGF - Tasks ", task.getShortDescription() + ", " +
                        String.valueOf(task.getPercentage()));
            }
        }
    }
    //------------------------------------------------------------------------------------------------------------------

    private class UpdateTaskCountReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //                                              //When com.LGF.CUSTOM_INTENT.ToDoTaskCountReady occurs

            int intToDoTask = DBUtil.getToDoTaskCount();
            TextView textViewTaskToDo = findViewById(R.id.TaskToDo);
            textViewTaskToDo.setText(String.valueOf(intToDoTask) + " Task To Do");

            int intDoingTask = DBUtil.getDoingTaskCount();
            TextView textViewTaskDoing = findViewById(R.id.TaskDoing);
            textViewTaskDoing.setText(String.valueOf(intDoingTask) + " Task Doing");

            int intDoneTask = DBUtil.getDoneTaskCount();
            TextView textViewTaskDone = findViewById(R.id.TaskDone);
            textViewTaskDone.setText(String.valueOf(intDoneTask) + " Task Done");
        }
    }

    /*END TASK*/
}
