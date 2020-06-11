package royal.com.itiplanner.activities;

import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.common.SignInButton;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import royal.com.itiplanner.R;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class MainActivityTest {

  @Rule
  public ActivityTestRule<MainActivity> activityTestRule =
      new ActivityTestRule<>(MainActivity.class);

  @Test
  public void isTitlePresent() {
    MainActivity mainActivity = activityTestRule.getActivity();
    View view = mainActivity.findViewById(R.id.txt_login);
    assertThat(view, notNullValue());
    assertThat(view, Matchers.<View>instanceOf(TextView.class));
  }

  @Test
  public void isImagePresent() {
    MainActivity mainActivity = activityTestRule.getActivity();
    View view = mainActivity.findViewById(R.id.img_logo);
    Assert.assertThat(view, Matchers.notNullValue());
    assertThat(view, Matchers.<View>instanceOf(ImageView.class));
  }

  @Test
  public void isEmailFormFieldPresent() {
    MainActivity mainActivity = activityTestRule.getActivity();
    View view = mainActivity.findViewById(R.id.edt_email);
    assertThat(view, Matchers.<View>instanceOf(EditText.class));
  }

  @Test
  public void isPasswordFormFieldPresent() {
    MainActivity mainActivity = activityTestRule.getActivity();
    View view = mainActivity.findViewById(R.id.edt_password);
    assertThat(view, Matchers.<View>instanceOf(EditText.class));
  }

  @Test
  public void isSignInButtonPresent() {
    MainActivity mainActivity = activityTestRule.getActivity();
    View view = mainActivity.findViewById(R.id.btn_sn);
    assertThat(view, Matchers.<View>instanceOf(Button.class));
  }

  @Test
  public void isGoogleSignInButtonPresent() {
    MainActivity mainActivity = activityTestRule.getActivity();
    View view = mainActivity.findViewById(R.id.google_sn);
    assertThat(view, Matchers.<View>instanceOf(SignInButton.class));
  }

  @Test
  public void isSignUpButtonPresent() {
    MainActivity mainActivity = activityTestRule.getActivity();
    View view = mainActivity.findViewById(R.id.btn_su);
    assertThat(view, Matchers.<View>instanceOf(Button.class));
  }

  @Test
  public void isSignUpPageDisplayedOnClickingSignUpButton() {
    onView(withId(R.id.btn_su)).perform(click());
    onView(withId(R.id.txt_su)).check(matches(withText("Sign Up")));
  }

  @Test
  public void isToastDisplayedForIncorrectCredentials() {
    onView(withId(R.id.edt_email)).perform(replaceText("testing@test.com"));
    onView(withId(R.id.edt_password)).perform(replaceText("test@12345678"));
    onView(withId(R.id.btn_sn)).perform(click());
    onView(withText(startsWith("Login Failed."))).
        inRoot(withDecorView(
            not(is(activityTestRule.getActivity().
                getWindow().getDecorView())))).
        check(matches(isDisplayed()));
  }

  @Test
  public void isHomePageDisplayedOnClickingSignInButton() {
    onView(withId(R.id.edt_email)).perform(replaceText("testing@test.com"));
    onView(withId(R.id.edt_password)).perform(replaceText("test@1234"));
    Instrumentation.ActivityMonitor
        am = getInstrumentation().addMonitor(HomeActivity.class.getName(), null, true);
    onView(withId(R.id.btn_sn)).perform(click());
    am.waitForActivityWithTimeout(5000);
    assertEquals(1, am.getHits());
  }
}
