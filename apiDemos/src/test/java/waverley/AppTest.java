package waverley;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import waverley.util.AppiumTest;
import waverley.util.Helpers;
import waverley.util.SwipeDirection;

import java.util.List;

public class AppTest extends AppiumTest
{
    @Test
    public void testXPath() throws Exception
    {
        // Find the 3rd cell by XPath, which is very slow, but convenient
        String text = Helpers.element(By.xpath("//android.view.View[1]/android.widget.FrameLayout[2]/android.widget.ListView[1]/android.widget.TextView[3]")).getText();
        assertEquals("App", text);
    }

    @Test
    public void testId() throws Exception
    {
        // Find android.widget.ListView by its ResourceId
        MobileElement element = Helpers.findByResourceId("android:id/list");
        // Then find its cell (android.widget.TextView) by its contents
        WebElement appElement = element.findElementByName("App");
        // Compare cell's content with expected value i.e. "App"
        String text = appElement.getText();
        assertEquals("App", text);
    }

    @Test
    public void testNavigation() throws Exception
    {
        MobileElement element = Helpers.findByResourceId("android:id/list");
        WebElement appElement = element.findElementByName("App");
        appElement.click();

        // Swipe ListView up. It is tempting to write
        // element.swipe(SwipeElementDirection.UP, 100);
        // but this will not work in all cases due to the coordinate system issue
        Helpers.swipe(element, SwipeDirection.UP, 500);

        Thread.sleep(1000);

        // Scroll to the very first cell containing element with the exact text
        Helpers.scrollToExact("Fragment");

        Thread.sleep(1000);

        // Find frame layouts inside view
        List<MobileElement> secondPage = Helpers.findElementsByClassName("android.widget.FrameLayout");

        MobileElement titleElement = (MobileElement)secondPage
                .get(0)                                             // We are looking for the very first frame layout
                .findElementByClassName("android.widget.TextView"); // which contains view's title

        // Compare title with expected text, providing descriptive error message
        assertEquals("Title should be API Demos", "API Demos", titleElement.getText());

        // Navigate back
        Helpers.back();

        // Test if navigation succeded and cell with text "App" is in place
        appElement = element.findElementByName("App");
        String text = appElement.getText();
        assertEquals("App", text);
    }

    @Test
    public void textFields() throws Exception
    {
        MobileElement element = Helpers.findByResourceId("android:id/list");
        WebElement appElement = element.findElementByName("App");
        appElement.click();

        Helpers.findByResourceId("android:id/list").findElementByName("Alert Dialogs").click();

        MobileElement textEntryButton = Helpers.element(By.id("io.appium.android.apis:id/text_entry_button"));
        textEntryButton.click();

        final String userName = "Viacheslav Karamov";
        final String password = "qwerty";
        // Find "Name" textField using more universal, but much slower strategy
        MobileElement nameField = Helpers.element(Helpers.locatorByValue("io.appium.android.apis:id/username_edit"));
        nameField.sendKeys(userName);

        // Find password field using Android UIAutomator strategy
        MobileElement passwordField = Helpers.element(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"io.appium.android.apis:id/password_edit\")"));
        passwordField.sendKeys(password);

        assertEquals(userName, nameField.getText());
        // Fortunately we can't read passwords
        assertNotEquals(password, passwordField.getText());

        /* Accept alert and close password dialog
           Unfortunately AppiumDriver.switchTo().alert().accept() has not been implemented yet in 1.4.8 and throws exception.
           So find "Ok" button manually.
        */
        Helpers.element(By.id("android:id/button1")).click();
    }
}
