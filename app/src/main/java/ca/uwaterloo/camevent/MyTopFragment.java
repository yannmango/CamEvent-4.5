package ca.uwaterloo.camevent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MyTopFragment extends PostListFragment {

    public MyTopFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // All my posts
        return databaseReference.child("user-posts")
                .child(getUid()).orderByChild("starCount");
    }
}
