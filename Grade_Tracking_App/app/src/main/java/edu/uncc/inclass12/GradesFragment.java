package edu.uncc.inclass12;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.uncc.inclass12.databinding.FragmentGradesBinding;

public class GradesFragment extends Fragment {
    List<Grade> grades;
    double totalCredit, totalGrade, gpa;
    FragmentGradesBinding binding;
    public GradesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGradesBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Grades");
        binding.textViewGPA.setText("GPA: 4.0");
        AppDatabase db = Room.databaseBuilder(getContext(),AppDatabase.class,"courses.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        grades = db.gradeDAO().getAll();

        for(int c = 0; c<grades.size(); c++){
            Grade course = grades.get(c);
            totalCredit += course.creditHours;
            if(course.letterGrade == 'A'){
                totalGrade += (course.creditHours * 4);
            }else if (course.letterGrade == 'B'){
                totalGrade += (course.creditHours * 3);
            }else if(course.letterGrade == 'C'){
                totalGrade += (course.creditHours * 2);
            }else if(course.letterGrade == 'D'){
                totalGrade += (course.creditHours);
            }

        }
        gpa = totalGrade/totalCredit;
        binding.textViewGPA.setText("GPA: " + String.format("%.2f",gpa));
        binding.textView2.setText("Hours: " + String.format("%.1f",totalCredit));

        if (binding.textView2.getText().toString().equals("Hours: 0.0")){
            binding.textViewGPA.setText("GPA: 4.0 " );
            binding.textView2.setText("Hours: 0.0");
        }

        else{

        }
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(new GradeAdapter());
    }

    public class GradeAdapter extends RecyclerView.Adapter<GradeHolder>
    {

        @NonNull
        @Override
        public GradeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.grade_row_item,parent,false);
            GradeHolder holder = new GradeHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull GradeHolder holder, int position) {
            Grade course = grades.get(position);
            Log.d(TAG, "onBindViewHolder: id " + course.id);
            holder.textViewLetterGrade.setText(String.valueOf(course.letterGrade));
            holder.textViewCourseName.setText(course.courseName);
            holder.textViewCreditHours.setText(course.creditHours+" Credit Hours");
            holder.textViewCourseNumber.setText(course.courseNumber);

            holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Delete code
                    AppDatabase db = Room.databaseBuilder(getContext(),AppDatabase.class,"courses.db")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                    db.gradeDAO().delete(course);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rootView,new GradesFragment())
                            .commit();
                }
            });
        }

        @Override
        public int getItemCount() {
            return grades.size();
        }
    }
    public class GradeHolder extends RecyclerView.ViewHolder{

        TextView textViewCourseName;
        TextView textViewCourseNumber;
        TextView textViewCreditHours;
        TextView textViewLetterGrade;
        ImageView imageViewDelete;
        public GradeHolder(@NonNull View itemView) {
            super(itemView);
            textViewCourseName =itemView.findViewById(R.id.textViewCourseName);
            textViewCourseNumber = itemView.findViewById(R.id.textViewCourseNumber);
            textViewCreditHours = itemView.findViewById(R.id.textViewCourseHours);
            textViewLetterGrade = itemView.findViewById(R.id.textViewCourseLetterGrade);
            imageViewDelete = itemView.findViewById(R.id.imageViewDelete);
        }
    }
}