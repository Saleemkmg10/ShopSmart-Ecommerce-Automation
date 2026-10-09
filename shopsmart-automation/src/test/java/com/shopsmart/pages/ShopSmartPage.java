
package com.shopsmart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ShopSmartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By products = By.cssSelector(".product");
    private final By searchInput = By.id("searchInput");
    private final By cartCount = By.id("cartCount");
    private final By cartTotal = By.id("cartTotal");

    public ShopSmartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(15)
        );
    }

    public void open() {
        driver.get("http://localhost:8080");

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        products
                )
        );
    }

    public void searchProduct(String productName) {
        WebElement search = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        searchInput
                )
        );

        search.clear();
        search.sendKeys(productName);
    }

    public boolean isProductDisplayed(String productName) {
        By productLocator = By.xpath(
                "//article[contains(@class,'product')]"
                        + "[.//h3[normalize-space()='" + productName + "']]"
        );

        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            productLocator
                    )
            ).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException exception) {
            return false;
        }
    }

    public String getFirstProductPrice() {
        WebElement product = getFirstProduct();

        return product.findElement(
                By.cssSelector(".price")
        ).getText();
    }

    public void addFirstProductToCart() {
        WebElement product = getFirstProduct();

        WebElement addButton = product.findElement(
                By.cssSelector(".add-to-cart")
        );

        if (!addButton.isEnabled()) {
            throw new IllegalStateException(
                    "First product is out of stock"
            );
        }

        addButton.click();
    }

    public void increaseFirstCartItemQuantity() {
        By increaseButton = By.cssSelector(
                "#cartItems button[aria-label^='Increase']"
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        increaseButton
                )
        ).click();
    }

    public void removeFirstCartItem() {
        By removeButton = By.cssSelector(
                "#cartItems .remove-button"
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        removeButton
                )
        ).click();
    }

    public String getCartCount() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        cartCount
                )
        ).getText();
    }

    public String getCartTotal() {
        return driver.findElement(cartTotal).getText();
    }

    public boolean isCartEmpty() {
        By emptyMessage = By.cssSelector(
                "#cartItems .empty-cart"
        );

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        emptyMessage
                )
        ).getText().contains("cart is empty");
    }

    public void waitForCartCount(String expectedCount) {
        wait.until(
                ExpectedConditions.textToBe(
                        cartCount,
                        expectedCount
                )
        );
    }

    private WebElement getFirstProduct() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        products
                )
        );
    }
}