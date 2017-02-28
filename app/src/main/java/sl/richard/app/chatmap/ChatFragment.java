package sl.richard.app.chatmap;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Richard on 30.12.2016.
 */
public class ChatFragment extends android.support.v4.app.Fragment {

    Activity mMainActivity;
    EditText editText;
    int viewId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.chat_fragment_layout,container,false);

        editText = (EditText) view.findViewById(R.id.editText);

        viewId = view.getId();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof MapFragment.MapFragmentListener){
            mMainActivity = (Activity) context;

        }


    }

    public void setEditText(String text){
        editText.setText(text);
    }

/*
    public interface PhotoFragmentInterface{
        void fragmentdata();
    }
    */
}
