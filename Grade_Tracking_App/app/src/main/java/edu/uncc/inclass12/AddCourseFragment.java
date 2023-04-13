package edu.uncc.inclass12;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import edu.uncc.inclass12.databinding.FragmentAddCourseBinding;


public class AddCourseFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentAddCourseBinding binding;

    public AddCourseFragment() {
        // Required empty public constructor
    }

    public static AddCourseFragment newInstance(String param1, String param2) {
        AddCourseFragment fragment = new AddCourseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddCourseBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        getActivity().setTitle("Add Course");
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String courseName = binding.editTextCourseName.getText().toString();
                String courseNumber = binding.editTextCourseNumber.getText().toString();
                int creditHours = Integer.parseInt(binding.editTextCourseHours.getText().toString());
                int rb = binding.radioGroupGrades.getCheckedRadioButtonId();
                char letterGrade;
                if (binding.editTextCourseName.getText().toString()==""||binding.editTextCourseNumber.getText().toString()==""||binding.editTextCourseHours.getText().toString()==""||rb==-1){
                    Toast.makeText(getContext(),"Please enter all the fields",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(binding.radioButtonA.getId()==rb){
                        letterGrade = 'A';
                    }
                    else if(binding.radioButtonB.getId()==rb){
                        letterGrade = 'B';
                    }
                    else if(binding.radioButtonC.getId()==rb){
                        letterGrade = 'C';
                    }
                    else if(binding.radioButtonD.getId()==rb){
                        letterGrade = 'D';
                    }
                    else{
                        letterGrade = 'F';
                    }
                    AppDatabase db = Room.databaseBuilder(getContext(),AppDatabase.class,"courses.db")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();

                    db.gradeDAO().insertAll(new Grade(courseName,courseNumber,creditHours,letterGrade));

                }

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rootView,new GradesFragment()).commit();
            }
        });
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.rootView,new GradesFragment())
                        .commit();
            }
        });
    }
}