package com.mintusharma.newsapp.dbhandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mintusharma.newsapp.NewsDao;
import com.mintusharma.newsapp.models.Article;

@Database(entities = {Article.class}, version = 1, exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {
    private static final String DB_NAME = "news_database";
    private static NewsDatabase instance;

    public static synchronized NewsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            NewsDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract NewsDao newsDao();
}