package waverley.util;

import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;


public class AppiumTest implements SauceOnDemandSessionIdProvider
{
    private AndroidDriver mDriver;

    @Rule
    public TestRule printTests = new TestWatcher()
    {
        protected void starting(Description description)
        {
            System.out.print("  test: " + description.getMethodName());
        }

        protected void finished(Description description)
        {
            final String session = getSessionId();

            if (session != null)
            {
                System.out.println(" " + "https://saucelabs.com/tests/" + session);
            }
            else
            {
                System.out.println();
            }
        }
    };

    /**
     * Run before each test *
     */
    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium-version", "1.4.8");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Android");
        capabilities.setCapability("platformVersion", "4.0");
        capabilities.setCapability("autoAcceptAlerts", true);

        mDriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

        Helpers.init(mDriver);
    }

    /**
     * Run after each test *
     */
    @After
    public void tearDown() throws Exception
    {
        if (mDriver != null)
        {
            mDriver.quit();
        }
    }

    /**
     * If we're not on Sauce then return null otherwise SauceOnDemandTestWatcher will error. *
     */
    public String getSessionId()
    {
        return null;
    }
}

