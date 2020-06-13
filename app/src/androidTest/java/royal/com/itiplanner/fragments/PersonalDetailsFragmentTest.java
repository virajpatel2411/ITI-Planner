package royal.com.itiplanner.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android21buttons.fragmenttestrule.FragmentTestRule;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import royal.com.itiplanner.R;
import royal.com.itiplanner.activities.HomeActivity;

public class PersonalDetailsFragmentTest {
  @Rule
  public FragmentTestRule<HomeActivity, PersonalDetailsFragment> fragmentTestRule =
      new FragmentTestRule<>(HomeActivity.class, PersonalDetailsFragment.class);

  @Test
  public void isEmailPresent() {
    View view = fragmentTestRule.getActivity().findViewById(R.id.p_email);
    Assert.assertThat(view, Matchers.instanceOf(TextView.class));
    Assert.assertThat(view, Matchers.notNullValue());
  }

  @Test
  public void isPhonePresent() {
    View view = fragmentTestRule.getActivity().findViewById(R.id.p_phone);
    Assert.assertThat(view, Matchers.instanceOf(TextView.class));
    Assert.assertThat(view, Matchers.notNullValue());
  }

  @Test
  public void isNamePresent() {
    View view = fragmentTestRule.getActivity().findViewById(R.id.p_name);
    Assert.assertThat(view, Matchers.instanceOf(TextView.class));
    Assert.assertThat(view, Matchers.notNullValue());
  }

  @Test
  public void isBackButtonPresent() {
    View view = fragmentTestRule.getActivity().findViewById(R.id.btn_personal_details);
    Assert.assertThat(view, Matchers.instanceOf(Button.class));
  }
}