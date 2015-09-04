package waverley.util;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

public enum SwipeDirection
{
    /**
     * Up from the center of the lower
     */
    UP{
        @Override
        void swipe(AppiumDriver driver, MobileElement element, int duration){
            final Point location = element.getCoordinates().inViewPort();
            final Dimension size = element.getSize();
            final int centerX = location.getX() + (size.getWidth() / 2);
            driver.swipe(centerX, location.getY() + size.getHeight() - 1, centerX, location.getY(), duration);
        }
    },
    /**
     * Down from the center of the upper
     */
    DOWN{
        @Override
        void swipe(AppiumDriver driver, MobileElement element, int duration){
            final Point location = element.getCoordinates().inViewPort();
            final Dimension size = element.getSize();
            final int centerX = location.getX() + (size.getWidth() / 2);
            driver.swipe(centerX, location.getY(), centerX, location.getY() + size.getHeight() - 1, duration);
        }
    },
    /**
     * To the left from the center of the rightmost
     */
    LEFT{
        @Override
        void swipe(AppiumDriver driver, MobileElement element, int duration){
            final Point location = element.getCoordinates().inViewPort();
            final Dimension size = element.getSize();
            final int centerY = location.getY() + (size.getHeight() / 2);
            driver.swipe(location.getX() + size.getWidth() - 1, centerY, location.getX(), centerY, duration);
        }
    },
    /**
     * To the right from the center of the leftmost
     */
    RIGHT{
        @Override
        void swipe(AppiumDriver driver, MobileElement element, int duration){
            final Point location = element.getCoordinates().inViewPort();
            final Dimension size = element.getSize();
            final int centerY = location.getY() + (size.getHeight() / 2);
            driver.swipe(location.getX(), centerY, location.getX()+ size.getWidth() - 1, centerY, duration);
        }
    };

    void swipe(AppiumDriver driver, MobileElement element, int duration){}
}
