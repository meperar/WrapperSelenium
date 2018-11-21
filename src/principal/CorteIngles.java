package principal;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class CorteIngles {

    private static final String URL = "https://www.elcorteingles.es/libros/?level=1";

    private final WebDriver driver;
    private final FluentWait<WebDriver> wait;
    private NumberFormat formatter;

    public CorteIngles(WebDriver driver) {
        this.driver = driver;
        this.formatter = NumberFormat.getInstance(Locale.getDefault());
        this.wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);
    }

    public List<Book> buscarLibro(String titulo, String autor) {
        driver.get(URL);
        wait.until(driver -> driver.findElement(By.id("cookies-agree"))).click();
        String busqueda = "";
        if (titulo != null && !titulo.isEmpty()) {
            busqueda += titulo;
        }
        if (autor != null && !autor.isEmpty()) {
            busqueda += " " + autor;
        }
        busqueda = busqueda.trim();
        wait.until(driver -> driver.findElement(By.cssSelector("input#search-box"))).sendKeys(busqueda, Keys.ENTER);
        List<WebElement> elements = wait.until(driver -> driver.findElement(By.cssSelector("ul.product-list"))).findElements(By.tagName("li"));

        return articlesToBooks(elements);
    }

    private List<Book> articlesToBooks(List<WebElement> elements) {
        return elements.parallelStream().map(this::articleToLibro).collect(Collectors.toList());
    }

    private Book articleToLibro(WebElement element) {
        WebElement titleElement = wait.until(driver -> element.findElement(By.cssSelector("a[data-event=\"product_click\"][title]")));
        String title = titleElement.getAttribute("title");
        WebElement autorElement = wait.until(driver -> element.findElement(By.cssSelector("h4.brand")));
        String autor = autorElement.getText();
        WebElement price = wait.until(driver -> element.findElement(By.cssSelector("div.product-price")));
        WebElement currentPrice = wait.until(driver -> price.findElement(By.cssSelector("span.current")));
        String newPriceString = currentPrice.getText();
        double newPrice = parse(newPriceString.substring(0, newPriceString.length() - 1));
        WebElement formerPrice = wait.until(driver -> price.findElement(By.cssSelector("span.former")));
        String oldPriceString = formerPrice.getText();
        double oldPrice = parse(oldPriceString.substring(0, oldPriceString.length() - 1));
        WebElement discountElement = wait.until(driver -> price.findElement(By.cssSelector("span.discount")));
        String discountString = discountElement.getText();
        double discount = parse(discountString.substring(1, discountString.length() - 1));
        return new Book("El Corte Ingles", title, autor, oldPrice, newPrice, discount);
    }

    private double parse(String number) {
        try {
            return formatter.parse(number).doubleValue();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() throws Exception {
        driver.close();
    }
}