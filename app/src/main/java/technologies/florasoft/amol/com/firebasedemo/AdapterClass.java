package technologies.florasoft.amol.com.firebasedemo;

import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdapterClass extends BaseAdapter {

    MainActivity mainActivity;
    ArrayList<User> massageArray;
    String androidDeviceId;

    public AdapterClass(MainActivity mainActivity, ArrayList<User> massageArray, String androidDeviceId) {
        this.mainActivity = mainActivity;
        this.massageArray = massageArray;
        this.androidDeviceId = androidDeviceId;

    }

    @Override
    public int getCount() {
        return massageArray.size();
    }

    @Override
    public Object getItem(int position) {
        return massageArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = mainActivity.getLayoutInflater();

        if (view == null) {
            view = inflater.inflate(R.layout.name_list, null);
            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final User item = massageArray.get(position);

        holder.name.setText(item.getName());

        if(androidDeviceId.equals(item.getAndroidDeviceId())){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.FILL_PARENT);
            params.gravity = Gravity.RIGHT;
            holder.name.setLayoutParams(params);
            holder.name.setBackgroundResource(R.drawable.background2);
        }
        else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.FILL_PARENT);
            params.gravity = Gravity.LEFT;
            holder.name.setLayoutParams(params);
            holder.name.setBackgroundResource(R.drawable.background1);

        }


        return view;
    }




    private class ViewHolder{
        View view_line;
        TextView name,area,amt,regno,stbno,stb_details;
    }
}
