package com.lwkandroid.lib.core.db;

import com.lwkandroid.lib.core.context.AppContext;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * @description:
 * @author: LWK
 * @date: 2020/7/3 14:30
 */
@Database(entities = {}, version = 1)
abstract class TestDatabase extends RoomDatabase
{
    private static TestDatabase mInstance;

    public static TestDatabase getInstance()
    {
        if (mInstance == null)
        {
            synchronized (TestDatabase.class)
            {
                if (mInstance == null)
                {
                    Room.databaseBuilder(AppContext.get(), TestDatabase.class, "test")
                            .addCallback(new Callback()
                            {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db)
                                {
                                    super.onCreate(db);
                                }

                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db)
                                {
                                    super.onOpen(db);
                                }

                                @Override
                                public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db)
                                {
                                    super.onDestructiveMigration(db);
                                }
                            });
                }
            }
        }
        return mInstance;
    }


}
