package mocha.sample;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by smitt345 on 11/20/16.
 */

public class MyListAdapter extends ArrayAdapter<Integer> {

    private Context context;
    private MainActivityListener mainActivityListener;
    private ArrayList<Integer> myList = new ArrayList<>(Arrays.asList(1, 2, 3));

    public MyListAdapter(Context context, ArrayList<Integer> users, MainActivityListener listener) {
        super(context, 0, users);
        this.context = context;
        this.mainActivityListener = listener;
        myList = users;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public long getItemId(int position) {
        return myList.get(position);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.listitem, parent, false);
        }
        TextView view = (TextView) convertView.findViewById(R.id.itemid);
        view.setText("Family 1: item " + position + " inc by 1");

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }
}
