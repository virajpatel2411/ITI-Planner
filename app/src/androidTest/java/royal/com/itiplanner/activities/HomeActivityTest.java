package royal.com.itiplanner.activities;

import android.support.design.widget.CoordinatorLayout;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.FrameLayout;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import royal.com.itiplanner.R;

@RunWith(JUnit4.class)
public class HomeActivityTest {

  @Rule public ActivityTestRule<HomeActivity> activityTestRule =
      new ActivityTestRule<>(HomeActivity.class);

  @Test
  public void isFrameLayoutForFragmentsPresent() {
    View view = activityTestRule.getActivity().findViewById(R.id.frame);
    Assert.assertThat(view, Matchers.instanceOf(FrameLayout.class));
  }

  @Test
  public void isBottomNavigationBarPresent() {
    View view = activityTestRule.getActivity().findViewById(R.id.btmbar);
    Assert.assertThat(view, Matchers.instanceOf(CoordinatorLayout.class));
  }
}
