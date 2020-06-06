package royal.com.itiplanner.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import royal.com.itiplanner.R;
import royal.com.itiplanner.models.FeedbackModel;

public class FeedbackFragment extends Fragment {

  EditText edtFeedback;
  Button btnSubmit, btnBack;
  RatingBar ratingBar;
  private DatabaseReference myRef;
  private FirebaseAuth mAuth;

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.fragment_feedback,container,false);

    edtFeedback = rootView.findViewById(R.id.edt_feedback);
    btnSubmit = rootView.findViewById(R.id.btn_feedback);
    btnBack = rootView.findViewById(R.id.btn_back_feedback);
    ratingBar = rootView.findViewById(R.id.rating_feedback);

    btnBack.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        getActivity().onBackPressed();
      }
    });

    btnSubmit.setOnClickListener(new View.OnClickListener() {
      @RequiresApi(api = Build.VERSION_CODES.O) @Override public void onClick(View v) {
        String feedbackText = edtFeedback.getText().toString();
        int ratings = ratingBar.getNumStars();
        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getUid();

        Calendar today = Calendar.getInstance();
        today.clear(Calendar.HOUR); today.clear(Calendar.MINUTE); today.clear(Calendar.SECOND);
        Date todayDate = today.getTime();

        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);

        String date = df.format(todayDate);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Feedback");

        FeedbackModel feedbackModel = new FeedbackModel();
        feedbackModel.setRatings(ratings);
        feedbackModel.setFeedbackMessage(feedbackText);
        feedbackModel.setUser_id(mAuth.getUid());
        feedbackModel.setDate(date);
        String key = myRef.push().getKey();
        myRef.child(key).setValue(feedbackModel);

        edtFeedback.setText("");
        ratingBar.setRating(0);

      }
    });

    return rootView;
  }
}
