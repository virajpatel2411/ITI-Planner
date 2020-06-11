package royal.com.itiplanner.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.util.HumanReadables;
import android.support.test.espresso.util.TreeIterables;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.android21buttons.fragmenttestrule.FragmentTestRule;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import royal.com.itiplanner.R;
import royal.com.itiplanner.activities.HomeActivity;
import royal.com.itiplanner.adapters.RecyclerDisplayAdapter;
import royal.com.itiplanner.models.FinalModel;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class ItineraryDisplayFragmentTest {

  @Rule
  public FragmentTestRule<HomeActivity, SearchFragment> fragmentTestRule =
      new FragmentTestRule<>(HomeActivity.class, SearchFragment.class);
  FirebaseDatabase database;
  DatabaseReference myRef;
  ArrayList<String> states;
  ArrayList<FinalModel> finalModels;

  public static ViewAction waitId(final int viewId, final long millis) {
    return new ViewAction() {
      @Override
      public Matcher<View> getConstraints() {
        return isRoot();
      }

      @Override
      public String getDescription() {
        return "wait for a specific view with id <" + viewId + "> during " + millis + " millis.";
      }

      @Override
      public void perform(final UiController uiController, final View view) {
        uiController.loopMainThreadUntilIdle();
        final long startTime = System.currentTimeMillis();
        final long endTime = startTime + millis;
        final Matcher<View> viewMatcher = withId(viewId);

        do {
          for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
            // found view with required ID
            if (viewMatcher.matches(child)) {
              return;
            }
          }

          uiController.loopMainThreadForAtLeast(50);
        }
        while (System.currentTimeMillis() < endTime);

        // timeout happens
        throw new PerformException.Builder()
            .withActionDescription(this.getDescription())
            .withViewDescription(HumanReadables.describe(view))
            .withCause(new TimeoutException())
            .build();
      }
    };
  }

  @Test
  public void isImageSliderPresent() {

    database = FirebaseDatabase.getInstance();
    myRef = database.getReference("Itinerary");
    myRef = myRef.child("Gujarat");

    states = new ArrayList<>();
    finalModels = new ArrayList<>();
    onView(isRoot()).perform(waitId(R.id.rec_display, TimeUnit.SECONDS.toMillis(20)));
    myRef.addValueEventListener(new ValueEventListener() {
      @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

          FinalModel finalModel = new FinalModel();
          finalModel = dataSnapshot1.getValue(FinalModel.class);
          finalModels.add(finalModel);
          states.add(dataSnapshot1.getKey());
        }

        Fragment fragment = new ItineraryDisplayFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("FINAL", (Serializable) finalModels);
        bundle.putStringArrayList("CITY", states);

        bundle.putString("STATE", "Gujarat");

        fragment.setArguments(bundle);

        fragmentTestRule.getActivity()
            .getSupportFragmentManager().beginTransaction()
            .replace(R.id.frame, fragment)
            .addToBackStack("homeFragment")
            .commit();

        View view = fragmentTestRule.getActivity().findViewById(R.id.img_slider);
        assertThat(view, Matchers.instanceOf(ViewPager.class));
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
  }

  @Test
  public void isRecyclerViewPresent() {

    database = FirebaseDatabase.getInstance();
    myRef = database.getReference("Itinerary");
    myRef = myRef.child("Gujarat");

    states = new ArrayList<>();
    finalModels = new ArrayList<>();
    onView(isRoot()).perform(waitId(R.id.rec_display, TimeUnit.SECONDS.toMillis(20)));
    myRef.addValueEventListener(new ValueEventListener() {
      @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

          FinalModel finalModel = new FinalModel();
          finalModel = dataSnapshot1.getValue(FinalModel.class);
          finalModels.add(finalModel);
          states.add(dataSnapshot1.getKey());
        }

        Fragment fragment = new ItineraryDisplayFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("FINAL", (Serializable) finalModels);
        bundle.putStringArrayList("CITY", states);

        bundle.putString("STATE", "Gujarat");

        fragment.setArguments(bundle);

        fragmentTestRule.getActivity()
            .getSupportFragmentManager().beginTransaction()
            .replace(R.id.frame, fragment)
            .addToBackStack("homeFragment")
            .commit();

        View view = fragmentTestRule.getActivity().findViewById(R.id.rec_display);
        assertThat(view, Matchers.instanceOf(RecyclerView.class));
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
  }

  @Test
  public void isRecyclerViewAdapterNotEmpty() {

    database = FirebaseDatabase.getInstance();
    myRef = database.getReference("Itinerary");
    myRef = myRef.child("Gujarat");

    states = new ArrayList<>();
    finalModels = new ArrayList<>();
    onView(isRoot()).perform(waitId(R.id.rec_display, TimeUnit.SECONDS.toMillis(20)));
    myRef.addValueEventListener(new ValueEventListener() {
      @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

          FinalModel finalModel = new FinalModel();
          finalModel = dataSnapshot1.getValue(FinalModel.class);
          finalModels.add(finalModel);
          states.add(dataSnapshot1.getKey());
        }

        Fragment fragment = new ItineraryDisplayFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("FINAL", (Serializable) finalModels);
        bundle.putStringArrayList("CITY", states);

        bundle.putString("STATE", "Gujarat");

        fragment.setArguments(bundle);

        fragmentTestRule.getActivity()
            .getSupportFragmentManager().beginTransaction()
            .replace(R.id.frame, fragment)
            .addToBackStack("homeFragment")
            .commit();

        View view = fragmentTestRule.getActivity().findViewById(R.id.rec_display);
        assertThat(view, Matchers.instanceOf(RecyclerView.class));
        RecyclerView recyclerView = (RecyclerView) view;
        RecyclerView.Adapter recyclerViewAdapter = recyclerView.getAdapter();
        Assert.assertThat(recyclerViewAdapter, Matchers.instanceOf(RecyclerDisplayAdapter.class));
        Assert.assertThat(recyclerViewAdapter.getItemCount(), Matchers.greaterThan(0));
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
  }
}
