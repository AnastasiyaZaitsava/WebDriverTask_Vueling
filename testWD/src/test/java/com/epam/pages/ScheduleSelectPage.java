package com.epam.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;


public class ScheduleSelectPage extends AbstractPage {
    private final String BASE_URL = "http://www.vueling.com/en";


    @FindBy(id = "ControlGroupScheduleSelectView_AvailabilityInputScheduleSelectView_RadioButtonMkt1Fare1Label")
    private WebElement basicOutboundButton;

    @FindBy(id = "ControlGroupScheduleSelectView_AvailabilityInputScheduleSelectView_RadioButtonMkt1Fare2Label")
    private WebElement optimaOutboundButton;

    @FindBy(id = "ControlGroupScheduleSelectView_AvailabilityInputScheduleSelectView_RadioButtonMkt1Fare3Label")
    private WebElement excellenceOutboundButton;

    @FindBy(id = "ControlGroupScheduleSelectView_AvailabilityInputScheduleSelectView_RadioButtonMkt2Fare1Label")
    private WebElement basicReturnButton;

    @FindBy(id = "ControlGroupScheduleSelectView_AvailabilityInputScheduleSelectView_RadioButtonMkt2Fare2Label")
    private WebElement optimaReturnButton;

    @FindBy(id = "ControlGroupScheduleSelectView_AvailabilityInputScheduleSelectView_RadioButtonMkt2Fare3Label")
    private WebElement excellenceReturnButton;

    @FindBy(id = "ControlGroupScheduleSelectView_AvailabilityInputScheduleSelectView_LinkButtonSubmit")
    private WebElement continueButton;

    @FindBy(xpath = "//dl[contains(@class,'travelInfo_listHeader')]//span[contains(@class, 'wrapper_currency')]")
    private WebElement totalPrice;

    public ScheduleSelectPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    public void openPage() {
        driver.navigate().to(BASE_URL);
    }

    public boolean chooseFlightTwoWays()
    {
        basicOutboundButton.click();
        basicReturnButton.click();
        driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
        WebElement element = driver.findElement(By.xpath("//div[@class='travelInfo_list']"));
        if (element.isDisplayed()) {
            continueButton.click();
            return true;
        } else {
            return false;
        }
    }

    public void chooseFlightOneWays ()
    {
        basicOutboundButton.click();
    }

    public double getPriceForOnePassenger(WebElement element)
    {
        String labelID = element.getAttribute("id");
        String tag = element.getTagName();
        String price1 = driver.findElement(By.xpath("//"+ tag +"[@id='"+labelID+"']/span")).getText();
        return Double.parseDouble(price1);
    }

    public double getTotalPrice ()
    {
        WebDriverWait wait = new WebDriverWait(driver, 50);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//dl[contains(@class,'travelInfo_listHeader')]//span[contains(@class, 'wrapper_currency')]")));
        String price1 = totalPrice.getText();
        String  price = price1.substring(0,(price1.length()-4));
        return Double.parseDouble(price);
    }

    public WebElement getWebElementToCheckPrice()
    {
        return basicOutboundButton;
    }

}





