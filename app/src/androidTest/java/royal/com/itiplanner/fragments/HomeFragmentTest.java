package royal.com.itiplanner.fragments;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.util.HumanReadables;
import android.support.test.espresso.util.TreeIterables;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.android21buttons.fragmenttestrule.FragmentTestRule;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import royal.com.itiplanner.R;
import royal.com.itiplanner.activities.HomeActivity;

import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class HomeFragmentTest {

  @Rule
  public FragmentTestRule<HomeActivity, HomeFragment> fragmentTestRule =
      new FragmentTestRule<>(HomeActivity.class, HomeFragment.class);

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
  public void isTitleHavingUserNamePresent() {
    View view = fragmentTestRule.getActivity().findViewById(R.id.txt_name);
    Assert.assertThat(view, Matchers.notNullValue());
    Assert.assertThat(view, Matchers.instanceOf(TextView.class));
  }

  @Test
  public void isImagePresent() {
    View view = fragmentTestRule.getActivity().findViewById(R.id.img_logo);
    Assert.assertThat(view, Matchers.notNullValue());
    Assert.assertThat(view, Matchers.instanceOf(ImageView.class));
  }

  @Test
  public void isListViewPresent() {
    View view = fragmentTestRule.getActivity().findViewById(R.id.list_home);
    Assert.assertThat(view, Matchers.notNullValue());
    Assert.assertThat(view, Matchers.instanceOf(ListView.class));
  }

  @Test
  public void isListViewAdapterNotEmpty() {
    View view = fragmentTestRule.getActivity().findViewById(R.id.list_home);
    Assert.assertThat(view, Matchers.notNullValue());
    Assert.assertThat(view, Matchers.instanceOf(ListView.class));
    ListView listView = (ListView) view;
    Espresso.onView(isRoot()).perform(waitId(R.id.list_home, TimeUnit.SECONDS.toMillis(20)));
    ListAdapter arrayAdapter = listView.getAdapter();
    Assert.assertThat(arrayAdapter, Matchers.instanceOf(ArrayAdapter.class));
    Assert.assertThat(arrayAdapter.getCount(), Matchers.greaterThan(0));
  }
}

