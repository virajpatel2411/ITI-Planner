package royal.com.itiplanner.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
      @Override public void onClick(View v) {
        String feedbackText = edtFeedback.getText().toString();
        int ratings = ratingBar.getNumStars();
        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Feedback");

        FeedbackModel feedbackModel = new FeedbackModel();
        feedbackModel.setRatings(ratings);
        feedbackModel.setFeedbackMessage(feedbackText);

        myRef.child(uid).setValue(feedbackModel);

        edtFeedback.setText("");
        ratingBar.setRating(0);

      }
    });

    return rootView;
  }
}
