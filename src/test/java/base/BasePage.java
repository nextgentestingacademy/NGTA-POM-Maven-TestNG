package base;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	protected WebDriver driver;
	Actions actions;
	WebDriverWait wait;

	// ========== Wait methods ==========
	public BasePage(WebDriver driver) {
		this.driver = driver;
		this.actions = new Actions(driver);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	}

	public WebElement waitForVisible(WebElement elm) {
		try {
			return wait.until(ExpectedConditions.visibilityOf(elm));
		} catch (Exception e) {
	        return null;
	    }
	}

	public WebElement waitForClickable(WebElement elm) {
		return wait.until(ExpectedConditions.elementToBeClickable(elm));
	}

	public boolean waitForInvisibility(WebElement elm) {
		return wait.until(ExpectedConditions.invisibilityOf(elm));
	}

	public void waitForText(WebElement elm, String text) {
		wait.until(ExpectedConditions.textToBePresentInElement(elm, text));
	}

	// ========== Action methods ==========
	public void click(WebElement elm) {
		if (isPresent(elm)) {
			waitForClickable(elm).click();
		}
	}

	public boolean type(WebElement elm, String text) {
		try {
			waitForVisible(elm).sendKeys(text);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String getText(WebElement elm) {
		try {
			return waitForVisible(elm).getText();
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isDisplayed(WebElement elm) {
		try {
			return waitForVisible(elm).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isEnabled(WebElement elm) {
		try {
			return waitForVisible(elm).isEnabled();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isSelected(WebElement elm) {
		try {
			return waitForVisible(elm).isSelected();
		} catch (Exception e) {
			return false;
		}
	}

	// -------------------------
	// PAGE NAVIGATION
	// -------------------------

	public void open(String url) {
		driver.get(url);
	}

	public String getPageTitle() {
		return driver.getTitle();
	}

	// -------------------------
	// DROPDOWN
	// -------------------------

	public boolean selectByVisibleText(WebElement elm, String text) {
		try {
			Select sel = new Select(waitForVisible(elm));
			sel.selectByVisibleText(text);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean selectByValue(WebElement elm, String value) {
		try {
			Select sel = new Select(waitForVisible(elm));
			sel.selectByValue(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean selectByIndex(WebElement elm, int index) {
		try {
			Select sel = new Select(waitForVisible(elm));
			sel.selectByIndex(index);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean selectFromAutoSuggest(WebElement inputBox, List<WebElement> suggestions, String textToType,
			String valueToSelect) throws InterruptedException {

		// Type in input box
		wait.until(ExpectedConditions.visibilityOf(inputBox));
		wait.until(ExpectedConditions.elementToBeClickable(inputBox));
		jsClick(inputBox);
		type(inputBox,textToType);

		// Wait until suggestions are visible
		wait.until(ExpectedConditions.visibilityOfAllElements(suggestions));
		Thread.sleep(3000);
		for (WebElement option : suggestions) {
			try {
				if (option.getText().contains(valueToSelect)) {
					wait.until(ExpectedConditions.elementToBeClickable(option));
					jsClick(option);
					return true;
				}
			} catch (StaleElementReferenceException e) {
				return false;
				// PageFactory list refresh handled implicitly on next interaction
			}
		}

		return false;
	}

	// -------------------------
	// SCROLLING
	// -------------------------

	public void scrollTo(WebElement elm) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elm);
	}

	public void scrollBy(int x, int y) {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(arguments[0], arguments[1]);", x, y);
	}

	// -------------------------
	// JS ACTIONS
	// -------------------------

	public void jsClick(WebElement elm) {
		waitForVisible(elm);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", elm);
	}

	public void jsSetValue(WebElement elm, String value) {
		waitForVisible(elm);
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + value + "'", elm);
	}
	
	public void jsSetValueCharByChar(WebElement elm, String value) {
	    waitForVisible(elm);

	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    // Clear existing value
	    js.executeScript("arguments[0].value='';", elm);

	    for (char ch : value.toCharArray()) {
	        js.executeScript(
	            "arguments[0].value = arguments[0].value + arguments[1];" +
	            "arguments[0].dispatchEvent(new Event('input'));" +
	            "arguments[0].dispatchEvent(new Event('change'));",
	            elm,
	            String.valueOf(ch)
	        );
	    }
	}


	public String jsGetText(WebElement elm) {
		waitForVisible(elm);
		return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].textContent;", elm);
	}

	// -------------------------
	// ACTIONS CLASS UTILITIES
	// -------------------------

	public void hover(WebElement elm) {
		actions.moveToElement(waitForVisible(elm)).perform();
	}

	public void rightClick(WebElement elm) {
		actions.contextClick(waitForVisible(elm)).perform();
	}

	public void doubleClick(WebElement elm) {
		actions.doubleClick(waitForVisible(elm)).perform();
	}

	public void dragAndDrop(WebElement source, WebElement target) {
		actions.dragAndDrop(waitForVisible(source), waitForVisible(target)).perform();
	}

	public void dragAndDropBy(WebElement elm, int x, int y) {
		actions.dragAndDropBy(waitForVisible(elm), x, y).perform();
	}

	public void typeUsingActions(WebElement elm, String text) {
		actions.moveToElement(waitForVisible(elm)).click().sendKeys(text).perform();
	}

	// -------------------------
	// ALERTS
	// -------------------------

	public void acceptAlert() {
		wait.until(ExpectedConditions.alertIsPresent()).accept();
	}

	public void dismissAlert() {
		wait.until(ExpectedConditions.alertIsPresent()).dismiss();
	}

	public String getAlertText() {
		return wait.until(ExpectedConditions.alertIsPresent()).getText();
	}

	public void sendKeysToAlert(String text) {
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.sendKeys(text);
		alert.accept();
	}

	// -------------------------
	// FRAME & WINDOW HANDLING
	// -------------------------

	public void switchToFrame(WebElement frameElement) {
		waitForVisible(frameElement);
		driver.switchTo().frame(frameElement);
	}

	public void switchToFrameByIndex(int index) {
		driver.switchTo().frame(index);
	}

	public void switchToDefault() {
		driver.switchTo().defaultContent();
	}

	public void switchToWindow(String handle) {
		driver.switchTo().window(handle);
	}

	// -------------------------
	// WAIT FOR PAGE LOAD
	// -------------------------

	public void waitForPageLoad() {
		new WebDriverWait(driver, Duration.ofSeconds(15)).until(webDriver -> ((JavascriptExecutor) driver)
				.executeScript("return document.readyState").equals("complete"));
	}

	// -------------------------
	// RETRY CLICK (robust)
	// -------------------------

	public void safeClick(WebElement elm) {
		int attempts = 0;
		while (attempts < 3) {
			try {
				click(elm);
				return;
			} catch (Exception e) {
				attempts++;
			}
		}
	}

	// -------------------------
	// EXISTENCE CHECKS
	// -------------------------

	public boolean isPresent(WebElement elm) {
		try {
			waitForVisible(elm);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// -------------------------
	// CUSTOM WAIT FOR TEXT CHANGE
	// -------------------------

	public void waitForTextChange(WebElement elm, String expectedText) {
		wait.until(driver -> elm.getText().contains(expectedText));
	}
}