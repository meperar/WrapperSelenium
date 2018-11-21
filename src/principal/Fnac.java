package principal;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Fnac {

    private static final String URL = "https://www.fnac.es/";

    private final WebDriver driver;
    private final FluentWait<WebDriver> wait;
    private final NumberFormat formatter;

    public Fnac(WebDriver driver) {
        this.driver = driver;
        this.formatter = NumberFormat.getInstance(Locale.getDefault());
        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(org.openqa.selenium.NoSuchElementException.class);
    }

    public List<Book> buscarLibro(String titulo, String autor) {
        driver.get(URL);
        WebElement categoriaMenu = wait.pollingEvery(Duration.ofSeconds(3)).until(webDriver -> webDriver.findElement(By.cssSelector("form#QuickSearchForm div.Header__aisle a")));
        categoriaMenu.click();
        List<WebElement> libroCategoria = wait.pollingEvery(Duration.ofSeconds(3)).until(webDriver -> webDriver.findElements(By.cssSelector("div.select ul.select-options li")));
        for (WebElement option : libroCategoria) {
            wait.until(driver -> option.isDisplayed());
            if (option.getText().equals("Libros")) {
                option.click();
                break;
            }
        }
        String busqueda = "";
        if (titulo != null && !titulo.isEmpty()) {
            busqueda += titulo;
        }
        if (autor != null && !autor.isEmpty()) {
            busqueda += " " + autor;
        }
        busqueda = busqueda.trim();

        WebElement inputSearch = wait.until(webDriver -> webDriver.findElement(By.id("Fnac_Search")));
        inputSearch.sendKeys(busqueda);
        inputSearch.sendKeys(Keys.ENTER);

        WebElement list = wait.pollingEvery(Duration.ofSeconds(3)).until(webDriver -> webDriver.findElement(By.cssSelector("ul.Article-list")));
        List<WebElement> articles = list.findElements(By.cssSelector("li.Article-item"));
        return articlesToBooks(articles)
                .filter(libro -> matchAutor(autor, libro))
                .collect(Collectors.toList());
    }

    private static boolean matchAutor(String autor, Book libro) {
        if (autor == null || autor.isEmpty()) return true;
        final String autorLibro = libro.getAutor();
        return Arrays.stream(autor.split(" ")).anyMatch(autorLibro::contains);
    }

    private Stream<Book> articlesToBooks(List<WebElement> listElements) {
        return listElements.parallelStream()
                .map(this::articleToBook)
                .filter(libro -> libro.getNewPrice() != 0);
    }

    private Book articleToBook(WebElement element) {
        WebElement descriptionElement = wait.until(driver -> element.findElement(By.cssSelector("p.Article-desc")));
        String title = descriptionElement.getText();
        final String autor = parseAutor(element);

        WebElement priceElement = wait.until(driver -> element.findElement(By.cssSelector("div.Article-price")));
        final double oldPrice = parseOldPrice(priceElement);

        List<WebElement> maybeUserPrice = wait.until(driver -> priceElement.findElements(By.cssSelector("a.userPrice")));
        double newPrice = 0;
        if (maybeUserPrice.size() > 0) {
            WebElement userPriceElement = maybeUserPrice.get(0);
            title = userPriceElement.getAttribute("title");
            String newPriceString = userPriceElement.getText();
            newPrice = parse(newPriceString.substring(0, newPriceString.length() - 1));
        }

        final double descuento = parseDescuento(element);
        return new Book("Fnac", title, autor, oldPrice, newPrice, descuento);
    }

    private String parseAutor(WebElement element) {
        WebElement subDescriptionElement = wait.until(driver -> element.findElement(By.cssSelector("p.Article-descSub")));
        String autor = "";
        if (subDescriptionElement.getText().contains("Autor")) {
            autor = subDescriptionElement.findElement(By.cssSelector("a[href]")).getText();
        }
        return autor;
    }

    private double parseOldPrice(WebElement priceElement) {
        List<WebElement> maybeOldPrice = wait.until(driver -> priceElement.findElements(By.cssSelector("span.oldPrice")));
        double oldPrice = 0;
        if (maybeOldPrice.size() > 0) {
            String oldPriceString = maybeOldPrice.get(0).getText();
            oldPrice = parse(oldPriceString.substring(0, oldPriceString.length() - 1));
        }
        return oldPrice;
    }

    private double parseDescuento(WebElement element) {
        List<WebElement> maybeDiscount = wait.until(driver -> element.findElements(By.cssSelector("strong.stimuliOPC-flyer.stimuliOPC-flyer--GoodDeal")));
        double descuento = 0;
        if (maybeDiscount.size() > 0) {
            WebElement descuentoElement = maybeDiscount.get(0);
            String descuentoString = descuentoElement.getText();
            descuento = parse(descuentoString.substring(1, descuentoString.length() - 1));
        }
        return descuento;
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
