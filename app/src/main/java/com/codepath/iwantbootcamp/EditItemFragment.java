package com.codepath.iwantbootcamp;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditItemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditItemFragment extends DialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private EditText elEditName;
    protected ArrayList passItemList;
    private  ImageView ivSaveBtn;
    private  ImageView ivCancelBtn;

    public EditItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment EditItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditItemFragment newInstance(String param1) {
        EditItemFragment fragment = new EditItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public interface EditNameDialogListener {
        void onFinishEditDialog(ArrayList passItemList);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        elEditName = (EditText)  view.findViewById(R.id.etFragEditName);

        ivSaveBtn = (ImageView) view.findViewById(R.id.ivFragSave);
        ivSaveBtn.setOnClickListener(this);

        ivCancelBtn = (ImageView) view.findViewById(R.id.ivFragCancel);
        ivCancelBtn.setOnClickListener(this);

        elEditName.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        Bundle passDataList = getArguments();
        passItemList = passDataList.getStringArrayList(GlobalInfo.KEY_PASS_DATA);
        String passItemStr = passItemList.get(0).toString();
        elEditName.setText(passItemStr);
    }

    @Override
    public void onClick(View v) {
        int getId = v.getId();

        EditNameDialogListener listener = (EditNameDialogListener) getActivity();

        if(getId == ivSaveBtn.getId()) {
            String editName = elEditName.getText().toString();

            passItemList.clear();
            passItemList.add(editName);
            listener.onFinishEditDialog(passItemList);
        }
        else if(getId == ivCancelBtn.getId()){
            passItemList.clear();
            listener.onFinishEditDialog(passItemList);
        }

        dismiss();
    }

    @Override
    public void onResume() {
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();

        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        // Call super onResume after sizing

        super.onResume();
    }
}
