package com.example.android.multipletable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Database Helper
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(getApplicationContext());

        //creating tags
        Tags tag1 = new Tags("Shooping");
        Tags tag2 = new Tags("Important");
        Tags tag3 = new Tags("WatchList");
        Tags tag4 = new Tags("AndroidHive");

        //inserting tag to the database
        long tag1_id = db.createTag(tag1);
        long tag2_id = db.createTag(tag2);
        long tag3_id = db.createTag(tag3);
        long tag4_id = db.createTag(tag4);

        Log.d("Tag Count","Tag Count:"+db.getAllTags().size());


        // Creating ToDos
        Todo todo1 = new Todo("iPhone 5S", 0);
        Todo todo2 = new Todo("Galaxy Note II", 0);
        Todo todo3 = new Todo("Whiteboard", 0);

        Todo todo4 = new Todo("Riddick", 0);
        Todo todo5 = new Todo("Prisoners", 0);
        Todo todo6 = new Todo("The Croods", 0);
        Todo todo7 = new Todo("Insidious: Chapter 2", 0);

        Todo todo8 = new Todo("Don't forget to call MOM", 0);
        Todo todo9 = new Todo("Collect money from John", 0);

        Todo todo10 = new Todo("Post new Article", 0);
        Todo todo11 = new Todo("Take database backup", 0);

        // Inserting todos in db
        // Inserting todos under "Shopping" Tag
        long todo1_id = db.CreateTodo(todo1, new long[] { tag1_id });
        long todo2_id = db.CreateTodo(todo2, new long[] { tag1_id });
        long todo3_id = db.CreateTodo(todo3, new long[] { tag1_id });

        // Inserting todos under "Watchlist" Tag
        long todo4_id = db.CreateTodo(todo4, new long[] { tag3_id });
        long todo5_id = db.CreateTodo(todo5, new long[] { tag3_id });
        long todo6_id = db.CreateTodo(todo6, new long[] { tag3_id });
        long todo7_id = db.CreateTodo(todo7, new long[] { tag3_id });

        // Inserting todos under "Important" Tag
        long todo8_id = db.CreateTodo(todo8, new long[] { tag2_id });
        long todo9_id = db.CreateTodo(todo9, new long[] { tag2_id });

        // Inserting todos under "Androidhive" Tag
        long todo10_id = db.CreateTodo(todo10, new long[] { tag4_id });
        long todo11_id = db.CreateTodo(todo11, new long[] { tag4_id });

       Log.e("Todo Count", "Todo count: " + db.getTodoCount());

        // "Post new Article" - assigning this under "Important" Tag
        // Now this will have - "Androidhive" and "Important" Tags
        db.createTodoTag(todo10_id, tag2_id);

        // Getting all tag names
        Log.d("Get Tags", "Getting All Tags");

        List<Tags> allTags = db.getAllTags();
        for (Tags tag : allTags) {
            Log.d("Tag Name", tag.getTag_name());
        }

        // Getting all Todos
        Log.d("Get Todos", "Getting All ToDos");

        List<Todo> allToDos = db.getallTodos();
        for (Todo todo : allToDos) {
            Log.d("ToDo", todo.getNote());
        }

        // Getting todos under "Watchlist" tag name
        Log.d("ToDo", "Get todos under single Tag name");

        List<Todo> tagsWatchList = db.getAllTodosByTag(tag3.getTag_name());
        for (Todo todo : tagsWatchList) {
            Log.d("ToDo Watchlist", todo.getNote());
        }

        // Deleting a ToDo
        Log.d("Delete ToDo", "Deleting a Todo");
       Log.d("Tag Count", "Tag Count Before Deleting: " + db.getTodoCount());

       db.deleteTodo(todo8_id);

        Log.d("Tag Count", "Tag Count After Deleting: " + db.getTodoCount());

        // Deleting all Todos under "Shopping" tag
        Log.d("Tag Count",
                "Tag Count Before Deleting 'Shopping' Todos: "
                        + db.getTodoCount());

        db.deleteTag(tag1, true);

        Log.d("Tag Count",
                "Tag Count After Deleting 'Shopping' Todos: "
                        + db.getTodoCount());

        // Updating tag name
        tag3.setTag_name("Movies to watch");
        db.updateTag(tag3);

        // Don't forget to close database connection
       db.closeDB();
    }
}
