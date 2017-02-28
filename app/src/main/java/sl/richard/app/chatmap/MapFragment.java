package sl.richard.app.chatmap;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Richard on 30.12.2016.
 */
public class MapFragment extends android.support.v4.app.Fragment{

    MapFragmentListener mMainActivity;

    private Button button;
    private EditText editText;
    int viewId;

    public interface MapFragmentListener{
        void setTextView(String string);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.map_fragment_layout,container,false);

        button = (Button) view.findViewById(R.id.button2);
        editText = (EditText) view.findViewById(R.id.editText);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getContext(), editText.getText().toString(), Toast.LENGTH_SHORT).show();
                mMainActivity.setTextView(editText.getText().toString());
            }
        });
        viewId = view.getId();
        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Toast.makeText(context, "yoyo", Toast.LENGTH_SHORT).show();
        if(context instanceof MapFragmentListener){
            mMainActivity = (MapFragmentListener)context;

        }

    }

}






//


