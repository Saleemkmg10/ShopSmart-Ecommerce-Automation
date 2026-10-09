package com.shopsmart.tests;

import com.shopsmart.pages.ShopSmartPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.math.BigDecimal;

@Listeners(ShopSmartTestListener.class)
public class ShopSmartUITest {

    private WebDriver driver;
    private ShopSmartPage shopSmartPage;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        shopSmartPage = new ShopSmartPage(driver);
        shopSmartPage.open();
    }

    // Used by the listener to capture failure screenshots
    public WebDriver getDriver() {
        return driver;
    }

    @Test
    public void searchProductTest() {
        shopSmartPage.searchProduct("Automation Keyboard");

        Assert.assertTrue(
                shopSmartPage.isProductDisplayed(
                        "Automation Keyboard"
                ),
                "Searched product should be displayed"
        );

        System.out.println("Product search test passed");
    }

    @Test
    public void addProductToCartTest() {
        shopSmartPage.addFirstProductToCart();
        shopSmartPage.waitForCartCount("1");

        Assert.assertEquals(
                shopSmartPage.getCartCount(),
                "1",
                "Cart count should be 1"
        );

        System.out.println("Add to cart test passed");
    }

    @Test
    public void updateQuantityAndVerifyTotalTest() {
        String priceText =
                shopSmartPage.getFirstProductPrice();

        BigDecimal price = new BigDecimal(
                priceText.replace("₹", "")
                        .replace(",", "")
                        .trim()
        );

        shopSmartPage.addFirstProductToCart();
        shopSmartPage.waitForCartCount("1");

        shopSmartPage.increaseFirstCartItemQuantity();
        shopSmartPage.waitForCartCount("2");

        BigDecimal expectedTotal = price.multiply(
                BigDecimal.valueOf(2)
        );

        String expectedText = "₹" + expectedTotal
                .setScale(2)
                .toPlainString();

        Assert.assertEquals(
                shopSmartPage.getCartTotal(),
                expectedText,
                "Cart total should reflect two items"
        );

        System.out.println(
                "Quantity and total test passed"
        );
    }

    @Test
    public void removeProductFromCartTest() {
        shopSmartPage.addFirstProductToCart();
        shopSmartPage.waitForCartCount("1");

        shopSmartPage.removeFirstCartItem();
        shopSmartPage.waitForCartCount("0");

        Assert.assertTrue(
                shopSmartPage.isCartEmpty(),
                "Cart should be empty after removing the product"
        );

        Assert.assertEquals(
                shopSmartPage.getCartTotal(),
                "₹0.00",
                "Cart total should be zero"
        );

        System.out.println(
                "Remove from cart test passed"
        );
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
