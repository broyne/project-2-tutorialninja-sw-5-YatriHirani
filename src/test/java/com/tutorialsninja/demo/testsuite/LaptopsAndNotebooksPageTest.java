package com.tutorialsninja.demo.testsuite;

import com.tutorialsninja.demo.customlisteners.CustomListeners;
import com.tutorialsninja.demo.pages.*;
import com.tutorialsninja.demo.testbase.TestBase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Collections;
import java.util.List;

/**
 *Create the class LaptopsAndNotebooksTest
 * Write the following test
 * 1. Test name verifyProductsPriceDisplayHighToLowSuccessfully()
 * 1.1 Mouse hover on Laptops & Notebooks Tab.and click
 * 1.2 Click on “Show All Laptops & Notebooks”
 * 1.3 Select Sort By "Price (High > Low)"
 * 1.4 Verify the Product price will arrange in High to Low order.
 * 2. Test name verifyThatUserPlaceOrderSuccessfully()
 * 2.1 Mouse hover on Laptops & Notebooks Tab and click
 * 2.2 Click on “Show All Laptops & Notebooks”
 * 2.3 Select Sort By "Price (High > Low)"
 * 2.4 Select Product “MacBook”
 * 2.5 Verify the text “MacBook”
 * 2.6 Click on ‘Add To Cart’ button
 * 2.7 Verify the message “Success: You have added MacBook to your shopping cart!”
 * 2.8 Click on link “shopping cart” display into success message
 * 2.9 Verify the text "Shopping Cart"
 * 2.10 Verify the Product name "MacBook"
 * 2.11 Change Quantity "2"
 * 2.12 Click on “Update” Tab
 * 2.13 Verify the message “Success: You have modified your shopping cart!”
 * 2.14 Verify the Total £737.45
 * 2.15 Click on “Checkout” button
 * 2.16 Verify the text “Checkout”
 * 2.17 Verify the Text “New Customer”
 * 2.18 Click on “Guest Checkout” radio button
 * 2.19 Click on “Continue” tab
 * 2.20 Fill the mandatory fields
 * 2.21 Click on “Continue” Button
 * 2.22 Add Comments About your order into text area
 * 2.23 Check the Terms & Conditions check box
 * 2.24 Click on “Continue” button
 * 2.25 Verify the message “Warning: Payment method required!
 */
@Listeners(CustomListeners.class)
public class LaptopsAndNotebooksPageTest extends TestBase {
    HomePage homePage;
    DesktopPage desktopPage;
    ShoppingCartPage cartPage;
    LaptopsAndNotebooksPage laptopsAndNotebooksPage;
    ProductPage productPage;
    SoftAssert softAssert;
    @BeforeMethod(alwaysRun = true)
    public void inIt(){
        homePage = new HomePage();
        desktopPage = new DesktopPage();
        cartPage = new ShoppingCartPage();
        productPage = new ProductPage();
        laptopsAndNotebooksPage = new LaptopsAndNotebooksPage();

        softAssert = new SoftAssert();
    }

    @Test(groups = {"sanity","smoke","regression"})
    public void verifyProductsPriceDisplayHighToLowSuccessfully() throws InterruptedException {
        homePage.clickOnElement(desktopPage.currencyDropdown);
        homePage.clickOnElement(desktopPage.poundSterling);
        Thread.sleep(2000);
        //1.1 Mouse hover on Laptops & Notebooks Tab.and click
        homePage.mouseHoverOnLaptopsAndNotebooksLinkAndClick();
        //1.2 Click on “Show All Laptops & Notebooks”
        homePage.selectMenu("Show AllLaptops & Notebooks");
        Thread.sleep(2000);
        // 1.3 Select Sort By "Price (High > Low)"
        laptopsAndNotebooksPage.selectSortByOption("Price (High > Low)");
        Thread.sleep(2000);
        //1.4 Verify the Product price will arrange in High to Low order.
        //Get all the products price and stored into array list
        List<Double> originalProductsPrice = laptopsAndNotebooksPage.getProductsPriceList();
        // Sort By Reverse order
        Collections.sort(originalProductsPrice, Collections.reverseOrder());
        //System.out.println(originalProductsPrice);
        // After filter Price (High > Low) Get all the products name and stored into array list
        List<Double> afterSortByPrice = laptopsAndNotebooksPage.getProductsPriceList();
        //System.out.println(afterSortByPrice);
        softAssert.assertEquals(originalProductsPrice,afterSortByPrice, "Product not sorted by price High to Low");
    }

    @Test(groups = {"smoke","regression"})
    public void verifyThatUserPlaceOrderSuccessfully() throws InterruptedException {
        homePage.clickOnElement(desktopPage.currencyDropdown);
        homePage.clickOnElement(desktopPage.poundSterling);
        Thread.sleep(2000);
        //2.1 Mouse hover on Laptops & Notebooks Tab and click
        homePage.mouseHoverOnLaptopsAndNotebooksLinkAndClick();
        //2.2 Click on “Show All Laptops & Notebooks”
        homePage.selectMenu("Show AllLaptops & Notebooks");
        //2.3 Select Sort By "Price (High > Low)"
        laptopsAndNotebooksPage.selectSortByOption("Price (High > Low)");
        //2.4 Select Product “MacBook”
        laptopsAndNotebooksPage.selectProductByName("HP LP3065");
        //2.5 Verify the text “MacBook”
        homePage.softAssert(productPage.getProductText(), "HP LP3065", "MacBook Product not display");
        //2.6 Click on ‘Add To Cart’ button
        productPage.clickOnAddToCartButton();
        //2.7 Verify the message “Success: You have added MacBook to your shopping cart!”
        softAssert.assertTrue(productPage.getProductAddedSuccessMessage().contains("Success: You have added MacBook to your shopping cart!"),
                "Product not added to cart");
        //2.8 Click on link “shopping cart” display into success message
        productPage.clickOnShoppingCartLinkIntoMessage();
        //2.9 Verify the text "Shopping Cart"
        softAssert.assertTrue(cartPage.getShoppingCartText().contains("Shopping Cart"), "Shopping Cart not matched");
        //2.10 Verify the Product name "MacBook"
        String actualProductName = desktopPage.getVerificationText(cartPage.productName);
        softAssert.assertEquals(actualProductName, "HP LP3065", "Product name not matched");
        //2.11 Change Quantity "2"
        cartPage.changeQuantity("2");
        //2.12 Click on “Update” Tab
        cartPage.clickOnQtyUpdateButton();
        //2.13 Verify the message “Success: You have modified your shopping cart!”
        softAssert.assertTrue(cartPage.getSuccessModifiedMessage().contains("Success: You have modified your shopping cart!"));
        //2.14 Verify the Total £737.45
        softAssert.assertEquals(cartPage.getTotal(), "£149.45", "Total not matched");
        //2.15 Click on “Checkout” button
        homePage.clickOnElement(laptopsAndNotebooksPage.clickOnCheckout);
        //2.16 Verify the text “Checkout”
        homePage.getTextFromElement(laptopsAndNotebooksPage.verifyTheTextCheckout);
        //2.17 Verify the Text “New Customer”
        homePage.getTextFromElement(laptopsAndNotebooksPage.verifyTheTxtNewCustomer);

        //2.18 Click on “Guest Checkout” radio button
        homePage.clickOnElement(laptopsAndNotebooksPage.clickOnGuestCheckout);
        //2.19 Click on “Continue” tab
        homePage.clickOnElement(laptopsAndNotebooksPage.ClickOnContinue);
        //2.20 Fill the mandatory fields
        laptopsAndNotebooksPage.enterTheMandatoryField(laptopsAndNotebooksPage.firstName, "Yatri");
        laptopsAndNotebooksPage.enterTheMandatoryField(laptopsAndNotebooksPage.lastName, "Hirani" );
        laptopsAndNotebooksPage.enterTheMandatoryField(laptopsAndNotebooksPage.eMail, "yatri+123@gmail.com");
        laptopsAndNotebooksPage.enterTheMandatoryField(laptopsAndNotebooksPage.telephone, "07438939393993");
        laptopsAndNotebooksPage.enterTheMandatoryField(laptopsAndNotebooksPage.Address1,"Sydney");
        laptopsAndNotebooksPage.enterTheMandatoryField(laptopsAndNotebooksPage.city, "Australia");
        laptopsAndNotebooksPage.enterTheMandatoryField(laptopsAndNotebooksPage.postcode, "2146");
        laptopsAndNotebooksPage.enterState();

        //2.21 Click on “Continue” Button
        laptopsAndNotebooksPage.clickOnContinueButton();
//laptopsAndNotebooksPage.clickOnCheckoutOptionContinueButton();
//2.22 Add Comments About your order into text area
        laptopsAndNotebooksPage.enterComment();
        laptopsAndNotebooksPage.clickOnContinueAfterComment();
        Thread.sleep(1000);
//2.23 Check the Terms & Conditions check box
        laptopsAndNotebooksPage.clickOnAgreeTermsConditionCheckbox();
        Thread.sleep(1000);
//2.24 Click on “Continue” button
//2.25 Verify the message “Warning: Payment method required!”
        laptopsAndNotebooksPage.clickOnPaymentMethodCont();
        softAssert.assertAll();
    }

}