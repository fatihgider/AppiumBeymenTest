package methods;

import com.thoughtworks.gauge.Step;
import driver.BaseTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class Method{
    AppiumDriver appiumDriver;
    FluentWait<AppiumDriver> wait;

    static Logger log = LogManager.getLogger(Method.class);


    public Method(){
        appiumDriver = BaseTest.appiumDriver;
        wait = new FluentWait<>(appiumDriver);
        wait.withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(NoSuchElementException.class);
    }

    @Step("<x> saniye bekle")
    public void waitForSecods(int x) throws InterruptedException {
        Thread.sleep(1000*x);
    }

    public MobileElement findElement(By by){
        return (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    @Step("<id> id'ye tıkla")
    public void click(String id){
        findElement(By.id(id)).click();

    }

    @Step("<id> elementine <text> yaz")
    public void sendKeys(String id, String text){
        findElement(By.id(id)).sendKeys(text);
    }

    @Step("<xpath> xpath'e tıkla")
    public void clickXPath(String xpath){
        findElement(By.xpath(xpath)).click();
    }

    @Step("Enter'a bas")
    public void sendEnter(){
        Actions action = new Actions(appiumDriver);
        action.sendKeys(Keys.ENTER).build().perform();
    }


    @Step("<id> elementinin textinin <text> olduğunu kontrol et")
    public void elementVisible(String id,String text){
        MobileElement elemText = findElement(By.id(id));
        Assert.assertTrue("Assertion Fail",elemText.getText().equals(text));

        if (elemText.isDisplayed()){
            log.info(elemText.getText()+" Elementi Görünür.");
        }
        else {
            log.info("Element Görünmüyor.");
        }

    }

    @Step("Sayfa aşağıya kaydırılır")
    public void swipeScreen() {
        Dimension dim = appiumDriver.manage().window().getSize();
        int startY = (int) (dim.width * 0.5);
        int startX = (int) (dim.width * 0.8);

        int endY = (int) (dim.width * 0.2);
        int endX = (int) (dim.width * 0.2);

        TouchAction touch = new TouchAction(appiumDriver);
        touch.press(PointOption.point(startX,startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                .moveTo(PointOption.point(endX,endY)).release().perform();
    }

    @Step("<urunler>'den random <urun> seçilir")
    public void randomUrun(String urunler, String urun){
        MobileElement urunlerElem = findElement(By.id(urunler));
        List<MobileElement> e = urunlerElem.findElements(By.id(urun));
        Random r=new Random();
        e.get(r.nextInt(e.size())).click();
    }

    @Step("<id> ürününün <fiyat> kontrol edilir")
    public void fiyatAl(String id, String fiyat){
        MobileElement elem = findElement(By.id(id));
        Assert.assertTrue("Fiyatlar Eşit Değil",elem.getText().equals(fiyat));
    }
}
