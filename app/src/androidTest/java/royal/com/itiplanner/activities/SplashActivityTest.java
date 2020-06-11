package royal.com.itiplanner.activities;

import android.app.Instrumentation;
import android.content.SharedPreferences;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.ImageView;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import royal.com.itiplanner.R;

import static android.content.Context.MODE_PRIVATE;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class SplashActivityTest {

  @Rule public ActivityTestRule<SplashActivity> activityTestRule =
      new ActivityTestRule<>(SplashActivity.class);

  @Test
  public void isImagePresent() {
    View view = activityTestRule.getActivity().findViewById(R.id.img_logo);
    Assert.assertThat(view, Matchers.notNullValue());
    Assert.assertThat(view, Matchers.instanceOf(ImageView.class));
  }

  @Test
  public void isRedirectedToLoginPageCorrectlyCheckingAvailabilityOfUID() {
    SharedPreferences sharedPreferences =
        activityTestRule.getActivity().getSharedPreferences("ITIPlanner", MODE_PRIVATE);

    String uId = sharedPreferences.getString("UID_KEY", "");

    if (!uId.equals("")) {
      Instrumentation.ActivityMonitor
          am = getInstrumentation().addMonitor(HomeActivity.class.getName(), null, true);
      am.waitForActivityWithTimeout(4000);
      assertEquals(1, am.getHits());
    } else {
      Instrumentation.ActivityMonitor
          am = getInstrumentation().addMonitor(MainActivity.class.getName(), null, true);
      am.waitForActivityWithTimeout(4000);
      assertEquals(1, am.getHits());
    }
  }
}
