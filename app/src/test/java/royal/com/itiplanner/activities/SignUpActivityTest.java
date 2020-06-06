package royal.com.itiplanner.activities;

import android.text.TextUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ TextUtils.class })
public class SignUpActivityTest {

  SignUpActivity signUpActivity;

  @Before
  public void init() {
    PowerMockito.mockStatic(TextUtils.class);
    signUpActivity = new SignUpActivity();
  }

  @Test
  public void nameBlankTest() {
    Assert.assertFalse(signUpActivity.validateName(""));
  }

  @Test
  public void nameCorrectTest() {
    Assert.assertTrue(signUpActivity.validateName("Viraj"));
  }

  @Test
  public void emailBlankTest() {
    Assert.assertFalse(signUpActivity.validateEmail(""));
  }

  @Test
  public void emailValidateTest1() {
    Assert.assertFalse(signUpActivity.validateEmail("abc"));
  }

  @Test
  public void emailValidateTest2() {
    Assert.assertFalse(signUpActivity.validateEmail("abc@gmail"));
  }

  @Test
  public void emailValidateTest3() {
    Assert.assertFalse(signUpActivity.validateEmail("abcd@yahoo."));
  }

  @Test
  public void emailCorrectTest() {
    Assert.assertTrue(signUpActivity.validateEmail("abcd@gmail.com"));
  }

  @Test
  public void mobNoTest1() {
    Assert.assertFalse(signUpActivity.validateMob(""));
  }

  @Test
  public void mobNoCorrectTest() {
    Assert.assertTrue(signUpActivity.validateMob("9979787037"));
  }

  @Test
  public void passWordTest1() {
    Assert.assertFalse(signUpActivity.validatePassEmpty(""));
  }

  @Test
  public void passWordTest2() {
    Assert.assertFalse(signUpActivity.validatePassAlphabet("abcdefgh"));
  }

  @Test
  public void passWordTest3() {
    Assert.assertFalse(signUpActivity.validatePassNumeric("1234567"));
  }

  @Test
  public void passWordCorrectTes() {
    Assert.assertTrue(signUpActivity.validatePassNumeric("abcd1234"));
    Assert.assertTrue(signUpActivity.validatePassEmpty("abcd1234"));
    Assert.assertTrue(signUpActivity.validatePassAlphabet("abcd1234"));
  }
}
