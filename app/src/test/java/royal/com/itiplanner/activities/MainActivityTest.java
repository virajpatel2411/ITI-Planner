package royal.com.itiplanner.activities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MainActivityTest {

  MainActivity mainActivity;

  @Before
  public void init()
  {
    mainActivity = new MainActivity();
  }

  @Test
  public void emailBlankTest()
  {
    Assert.assertFalse(mainActivity.validateEmail(""));
  }

  @Test
  public void emailValidateTest1()
  {
   Assert.assertFalse(mainActivity.validateEmail("abc@gmail"));
  }

  @Test
  public void emailValidateTest2()
  {
    Assert.assertFalse(mainActivity.validateEmail("abc@"));
  }

  @Test
  public void emailCorrectTest()
  {
    Assert.assertTrue(mainActivity.validateEmail("abc@gmail.com"));
  }

  @Test
  public void passwordIncorrect()
  {
    Assert.assertFalse(mainActivity.validatePassword(""));
  }

  @Test
  public void passwordIsNotBlankTest()
  {
    Assert.assertTrue(mainActivity.validatePassword("abcd"));
  }

  @Test
  public void passwordNotAppropriateTest1()
  {
    Assert.assertFalse(mainActivity.validateEmailAndPassword("abcd@gmail.com","abcdefgh"));
  }

  @Test
  public void passwordNotAppropriateTest2()
  {
    Assert.assertFalse(mainActivity.validateEmailAndPassword("abcd@gmail.com","1234567"));
  }

  @Test
  public void passwordAppropriateTest()
  {
    Assert.assertTrue(mainActivity.validateEmailAndPassword("abcd@gmail.com","abcd1234"));
  }

}
