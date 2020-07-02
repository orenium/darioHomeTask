package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static utils.TestRunner.printToLog;

public class CartInfoPage extends BaseWebPage {
    @FindAll({
            @FindBy(css = "strong")})
    private List<WebElement> summaryText;

    public CartInfoPage(WebDriver driver) {
        super(driver);
    }

    public void printCartSummaryText() {
        if (summaryText.size()> 0) {
            printToLog(summaryText.get(2).getText());
        }
    }


}
