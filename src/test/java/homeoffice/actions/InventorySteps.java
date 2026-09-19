package homeoffice.actions;

import homeoffice.PageObject.SauceDemoInventory;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.steps.UIInteractionSteps;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InventorySteps extends UIInteractionSteps {

    @Step("Verify the user is on the inventory page")
    public void verifyOnInventoryPage() {
        waitFor(ExpectedConditions.urlContains("inventory.html"));
        assertTrue(
            "Expected to be on the inventory page but was at: " + getDriver().getCurrentUrl(),
            getDriver().getCurrentUrl().contains("inventory.html")
        );
    }

    @Step("Sort inventory by '{0}'")
    public void sortInventoryBy(String sortOption) {
        Select select = new Select(find(SauceDemoInventory.SORT_DROPDOWN));
        switch (sortOption.toLowerCase()) {
            case "name a to z":
                select.selectByValue("az");
                break;
            case "name z to a":
                select.selectByValue("za");
                break;
            case "price low to high":
                select.selectByValue("lohi");
                break;
            case "price high to low":
                select.selectByValue("hilo");
                break;
            default:
                throw new IllegalArgumentException("Unknown sort option: " + sortOption);
        }
    }

    @Step("Verify the first inventory item is '{0}' priced at '{1}'")
    public void verifyFirstItem(String expectedName, String expectedPrice) {
        String actualName  = find(SauceDemoInventory.INVENTORY_ITEM_LABEL).getText();
        String actualPrice = find(SauceDemoInventory.INVENTORY_ITEM_PRICE).getText();
        assertEquals("First item name mismatch",  expectedName,  actualName);
        assertEquals("First item price mismatch", expectedPrice, actualPrice);
    }

    @Step("Add item '{0}' to the cart")
    public void addItemToCart(String itemSlug) {
        find(SauceDemoInventory.addToCartButtonFor(itemSlug)).click();
    }

    @Step("Verify the cart badge shows '{0}'")
    public void verifyCartBadge(String expectedCount) {
        waitFor(ExpectedConditions.textToBe(SauceDemoInventory.SHOPPING_CART_BADGE, expectedCount));
        assertEquals(
            "Cart badge count mismatch",
            expectedCount,
            find(SauceDemoInventory.SHOPPING_CART_BADGE).getText()
        );
    }
}
