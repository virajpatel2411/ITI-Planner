package royal.com.itiplanner.fragments;

import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.util.HumanReadables;
import android.support.test.espresso.util.TreeIterables;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.android21buttons.fragmenttestrule.FragmentTestRule;
import java.util.concurrent.TimeoutException;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import royal.com.itiplanner.R;
import royal.com.itiplanner.activities.HomeActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class ProfileFragmentTest {

  @Rule
  public FragmentTestRule<HomeActivity, ProfileFragment> fragmentTestRule =
      new FragmentTestRule<>(HomeActivity.class, ProfileFragment.class);

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
  public void isListViewPresent() {
    View view = fragmentTestRule.getActivity().findViewById(R.id.list);
    assertThat(view, Matchers.instanceOf(ListView.class));
  }

  @Test
  public void isListAdapterSizeEqualsToFive() {
    View view = fragmentTestRule.getActivity().findViewById(R.id.list);
    assertThat(view, Matchers.instanceOf(ListView.class));
    ListView listView = (ListView) view;
    ListAdapter listAdapter = listView.getAdapter();
    assertThat(listAdapter, Matchers.instanceOf(ArrayAdapter.class));
    assertThat(listAdapter.getCount() + "", Matchers.equalToIgnoringWhiteSpace("5"));
  }

  @Test
  public void isLogOutButtonPresent() {
    View view = fragmentTestRule.getActivity().findViewById(R.id.btn_lo);
    assertThat(view, Matchers.instanceOf(Button.class));
  }

  @Test
  public void doesLogOutButtonRedirectsToLoginPage() {
    onView(withId(R.id.btn_lo)).perform(click());
    onView(withId(R.id.txt_login)).check(ViewAssertions.matches(isDisplayed()));
  }

  @Test
  public void isFragmentTransactionWorkingProperly() {
    View view = fragmentTestRule.getActivity().findViewById(R.id.list);
    Assert.assertThat(view, Matchers.instanceOf(ListView.class));
    ListView listView = (ListView) view;
    ListAdapter arrayAdapter = listView.getAdapter();
    Assert.assertThat(arrayAdapter, Matchers.instanceOf(ArrayAdapter.class));
    onView(withId(R.id.list)).perform(click());
    onView(withId(R.id.feedback_frag)).check(ViewAssertions.matches(isDisplayed()));
  }
}
