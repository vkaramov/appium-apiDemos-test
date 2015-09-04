package waverley.util;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class Helpers
{
    private static AndroidDriver mDriver;
    private static long mTimeoutInSeconds = 30;
    private static long mCurrentTimeout = mTimeoutInSeconds;

    /**
     * Initialize the webdriver. Must be called before using any helper methods. *
     */
    public static void init(AndroidDriver webDriver)
    {
        mDriver = webDriver;
        setWait(mTimeoutInSeconds);
    }

    public static void setWait(long seconds)
    {
        mDriver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
        mCurrentTimeout = seconds;
    }

    public static long getWait()
    {
        return mCurrentTimeout;
    }

    /**
     * Convenience method for swiping across the screen
     *
     * @param startx
     *            starting x coordinate
     * @param starty
     *            starting y coordinate
     * @param endx
     *            ending x coordinate
     * @param endy
     *            ending y coordinate
     * @param duration
     *            amount of time in milliseconds for the entire swipe action to
     *            take
     */
    public static void swipe(int startx, int starty, int endx, int endy, int duration)
    {
        mDriver.swipe(startx, starty, endx, endy, duration);
    }

    /**
     * Convenience method for swiping element of choice in four possible directions
     * @param element   element to swipe
     * @param direction swipe direction
     * @param duration  amount of time in milliseconds for the entire swipe action to
     *            take
     */
    public static void swipe(MobileElement element, SwipeDirection direction, int duration)
    {
        direction.swipe(mDriver, element, duration);
    }

    /***
     * Scroll forward to the element which has a description or name which exactly matches the input text.
     * The scrolling is performed on the first scrollView present on the UI
     * @param text input text
     */
    public static void scrollToExact(final String text)
    {
        mDriver.scrollToExact(text);
    }

    public static WebDriver.Window getWindow()
    {
        return  mDriver.manage().window();
    }
    /**
     * Wrap WebElement in MobileElement *
     */
    private static MobileElement w(WebElement element)
    {
        return (MobileElement) element;
    }

    /**
     * Wrap WebElement in MobileElement
     */
    private static List<MobileElement> w(List<WebElement> elements)
    {
        List list = new ArrayList(elements.size());
        for (WebElement element : elements)
        {
            list.add(w(element));
        }

        return list;
    }

    /**
     * Return an element by locator *
     */
    public static MobileElement element(By locator)
    {
        WebElement webElement = mDriver.findElement(locator);
        return w(webElement);
    }

    /**
     * Return a list of elements by locator *
     */
    public static List<MobileElement> elements(By locator)
    {
        return w(mDriver.findElements(locator));
    }

    /**
     * Press the back button *
     */
    public static void back()
    {
        mDriver.navigate().back();
    }

    public static List<MobileElement> findElementsByClassName(String className)
    {
        return elements(locatorByClass(className));
    }

    /**
     * Return a tag name locator *
     */
    public static By locatorByClass(String tagName)
    {
        return By.className(tagName);
    }
    /**
     * Return a static text locator by xpath index *
     */
    public static By locatorByIndex(int xpathIndex)
    {
        return By.xpath("//android.widget.TextView[" + xpathIndex + "]");
    }

    /**
     * Return a static text element that contains text *
     */
    public static MobileElement text(final String text)
    {
        return element(locatorByText(text));
    }

    /**
     * Return a static text locator that contains text *
     */
    public static By locatorByText(final String text)
    {
        String up = text.toUpperCase();
        String down = text.toLowerCase();
        return By.xpath("//android.widget.TextView[contains(translate(@text,\"" + up
                                + "\",\"" + down + "\"), \"" + down + "\")]");
    }

    /**
     * Return a static text element by exact text *
     */
    public static MobileElement findByTextExact(final String text)
    {
        return element(locatorByTextExact(text));
    }

    /**
     * Return a static text locator by exact text *
     */
    public static By locatorByTextExact(final String text)
    {
        return By.xpath("//android.widget.TextView[@text='" + text + "']");
    }


    public static By locatorByValue(final String value)
    {
        return By.xpath("//*[@content-desc=\"" + value + "\" or @resource-id=\"" + value +
                                "\" or @text=\"" + value + "\"] | //*[contains(translate(@content-desc,\"" + value +
                                "\",\"" + value + "\"), \"" + value + "\") or contains(translate(@text,\"" + value +
                                "\",\"" + value + "\"), \"" + value + "\") or @resource-id=\"" + value + "\"]");
    }

    public static MobileElement findByResourceId(final String resourceId)
    {
        return element(By.id(resourceId));
    }

    /***
     * Wait for visible element to disappear during 60 seconds.
     * @param locator locator of element whose visibility is tracked
     */
    public static void waitLocatorToHide(By locator)
    {
        final int timeout = 5;
        final int waitTime = 60*3 / timeout;
        final long currentTimeout = getWait();
        setWait(1);
        try
        {
            for (int attempt = 0; attempt < waitTime; ++attempt)
            {
                if (element(locator).isDisplayed())
                {
                    Thread.sleep(timeout * 1000);
                }
                else
                {
                    return;
                }
            }
        }
        catch (Exception e)
        {
        }
        finally
        {
            setWait(currentTimeout);
        }
    }
}
