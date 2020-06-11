package royal.com.itiplanner.activities;

import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
public class SignUpActivityTest {

  @Rule public ActivityTestRule<SignUpActivity> activityTestRule =
      new ActivityTestRule<>(SignUpActivity.class);
  ;

  @Test
  public void isTitlePresent() {
    SignUpActivity signUpActivity = activityTestRule.getActivity();
    View view = signUpActivity.findViewById(R.id.txt_su);
    assertThat(view, notNullValue());
    assertThat(view, Matchers.<View>instanceOf(TextView.class));
  }

  @Test
  public void isImagePresent() {
    SignUpActivity signUpActivity = activityTestRule.getActivity();
    View view = signUpActivity.findViewById(R.id.img_logo);
    Assert.assertThat(view, Matchers.notNullValue());
    assertThat(view, Matchers.<View>instanceOf(ImageView.class));
  }

  @Test
  public void isNameFormFieldPresent() {
    SignUpActivity signUpActivity = activityTestRule.getActivity();
    View view = signUpActivity.findViewById(R.id.edt_name);
    assertThat(view, Matchers.<View>instanceOf(EditText.class));
  }

  @Test
  public void isEmailFormFieldPresent() {
    SignUpActivity signUpActivity = activityTestRule.getActivity();
    View view = signUpActivity.findViewById(R.id.edt_email);
    assertThat(view, Matchers.<View>instanceOf(EditText.class));
  }

  @Test
  public void isMobileNumberFormFieldPresent() {
    SignUpActivity signUpActivity = activityTestRule.getActivity();
    View view = signUpActivity.findViewById(R.id.edt_number);
    assertThat(view, Matchers.<View>instanceOf(EditText.class));
  }

  @Test
  public void isPasswordFormFieldPresent() {
    SignUpActivity signUpActivity = activityTestRule.getActivity();
    View view = signUpActivity.findViewById(R.id.edt_password);
    assertThat(view, Matchers.<View>instanceOf(EditText.class));
  }

  @Test
  public void isBackButtonPresent() {
    SignUpActivity signUpActivity = activityTestRule.getActivity();
    View view = signUpActivity.findViewById(R.id.signup_back);
    assertThat(view, Matchers.<View>instanceOf(Button.class));
  }

  @Test
  public void isNextButtonPresent() {
    SignUpActivity signUpActivity = activityTestRule.getActivity();
    View view = signUpActivity.findViewById(R.id.btn_next);
    assertThat(view, Matchers.<View>instanceOf(Button.class));
  }

  @Test
  public void isLoginPageDisplayedOnClickingNextButton() {
    onView(withId(R.id.edt_number)).perform(replaceText("9979797787"));
    onView(withId(R.id.edt_name)).perform(replaceText("Test"));
    onView(withId(R.id.edt_email)).perform(replaceText("test123@test.com"));
    onView(withId(R.id.edt_password)).perform(replaceText("test@1234"));
    Instrumentation.ActivityMonitor
        am = getInstrumentation().addMonitor(MainActivity.class.getName(), null, true);
    onView(withId(R.id.btn_next)).perform(click());
    am.waitForActivityWithTimeout(5000);
    assertEquals(1, am.getHits());
  }

  @Test
  public void isToastDisplayedOnSignUpFailing() {
    onView(withId(R.id.edt_number)).perform(replaceText("9979797787"));
    onView(withId(R.id.edt_name)).perform(replaceText("Test"));
    onView(withId(R.id.edt_email)).perform(replaceText("testing@test.com"));
    onView(withId(R.id.edt_password)).perform(replaceText("test@1234"));
    onView(withId(R.id.btn_next)).perform(click());
    onView(withText(startsWith("SignUp Failed"))).
        inRoot(withDecorView(
            not(is(activityTestRule.getActivity().
                getWindow().getDecorView())))).
        check(matches(isDisplayed()));
  }

  @Test
  public void isLoginPageDisplayedOnClickingBackButton() {
    onView(withId(R.id.signup_back)).perform(click());
    onView(withId(R.id.txt_login)).check(matches(withText("Login")));
  }
}
