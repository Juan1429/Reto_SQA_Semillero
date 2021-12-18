package steps;

import drivers.GoogleChromeDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import pages.HacebPages;

public class HacebSteps {

    HacebPages hacebpages = new HacebPages();

    public void abrirPagina(){GoogleChromeDriver.chromeWebDriver("https://www.haceb.com/");}

    public void buscarElementoEnHaceb(String producto){
        GoogleChromeDriver.driver.findElement(hacebpages.getTxtBuscador()).sendKeys(producto);
        GoogleChromeDriver.driver.findElement(hacebpages.getBtnBuscador()).click();
        hacebpages.setBtnElementoBusqueda(producto);
        GoogleChromeDriver.driver.findElement(hacebpages.getBtnElementoBusqueda()).click();
    }

    public void buscarElementoEnHacebs(String productos)  {
        try {
            escribirEnTexto(hacebpages.getTxtBuscador(), productos);
            Thread.sleep(1000);
            clicEnElemento(hacebpages.getBtnBuscador());
            Thread.sleep(1000);
            hacebpages.setBtnElementoBusqueda(productos);
            Thread.sleep(1000);
            clicEnElemento(hacebpages.getBtnElementoBusqueda());
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



    public void validarElementoEnPantalla(String producto){
        hacebpages.setTxtElementoBusqueda(producto);
        Assert.assertEquals(producto.replace("  "," "),GoogleChromeDriver.driver.findElement(hacebpages.getTxtElementoBusqueda()).getText());
        GoogleChromeDriver.driver.quit();
    }



    public void escribirEnTexto(By elemento, String texto) {GoogleChromeDriver.driver.findElement(elemento).sendKeys(texto);}

    public void clicEnElemento(By elemento) {GoogleChromeDriver.driver.findElement(elemento).click();}


}
