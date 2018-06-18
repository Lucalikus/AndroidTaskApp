package com.example.luiscastillo.task_app;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Task.class}, version = 1)
public abstract class TaskDB extends RoomDatabase {

    public abstract TaskDAO taskDAO();
    private static TaskDB INSTANCE;
    public static TaskDB getTaskDB(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(), TaskDB.class, "task-database").build();
        }
        return INSTANCE;
    }
    public static void destroyInstance() {
        INSTANCE = null;
    }
}