package ca.uwaterloo.camevent;

/**
 * Created by mactang on 2016-11-17.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class Favourite extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public Favourite() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Favourite newInstance(int sectionNumber) {
        Favourite fragment = new Favourite();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sub_page02, container, false);
        String[] items={"Apple","Banana","Grape"};
        final ListView listView=(ListView) rootView.findViewById(R.id.listv);
        final ArrayList<String> arrayList=new ArrayList<>(Arrays.asList(items));

        final ArrayAdapter<String> listviewAdapter=new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item,
                R.id.txtitem,
                arrayList
        );
        listView.setAdapter(listviewAdapter);
        final EditText txtInput=(EditText)rootView.findViewById(R.id.txtinput);
        Button btAdd=(Button)rootView.findViewById(R.id.btadd);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newItem=txtInput.getText().toString();
                arrayList.add(newItem);
                listviewAdapter.notifyDataSetChanged();
            }
        });
        return rootView;

    }
}

