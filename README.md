## Reto Final

Se realizó tres diferentes pruebas automatizadas en la página de https://www.haceb.com las cuales son:

- Realizar una busqueda de 5 productos diferentes pormedio de un scenario outline
- Realizar una busqueda con dos scenarios los cuales tendrian una precondición utilizando un Background
- Realizar una prueba fallida y una exitosa.


##### Para realizar dichas pruebas se debe tener encuenta los siguientes aspectos:

#### build.gradle
El archivo build.gradle lo podemos encontrar en la raíz del proyecto, este archivo nos define configuraciones que se aplican a todos los módulos del proyecto.



```
apply plugin: 'java-library'
apply plugin: 'net.serenity-bdd.aggregator'
apply plugin: 'eclipse'

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()

}

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()

    }
    dependencies {
        classpath("net.serenity-bdd:serenity-gradle-plugin:2.0.80")
    }
}

dependencies {
    implementation 'net.serenity-bdd:serenity-junit:2.0.80'
    implementation 'net.serenity-bdd:serenity-cucumber:1.9.45'
    implementation 'net.serenity-bdd:serenity-core:2.0.80'
    implementation 'org.slf4j:slf4j-simple:1.7.7'
    implementation group: 'org.apache.poi', name: 'poi', version: '3.17'
    implementation group: 'org.apache.poi', name: 'poi-ooxml', version: '3.17'
}

test {
    ignoreFailures = true
}
gradle.startParameter.continueOnFailure = true
```

#### Paquete drivers

En el paquete de los drivers encontramos la clases llamada GoogleChromeDirver , esta clase nos ayuda a levantar el navegador en el cual relizaremos la búsqueda de la página a la que queremos probar de forma automatizada.

```java
package drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class GoogleChromeDriver {

    public static WebDriver driver;

    public static void chromeWebDriver(String url){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--disable-infobars");
        driver = new ChromeDriver(options);
        driver.get(url);
    }
}
```

#### Paquete pages

El paquete pages contiene la clase HacebPages la cual nos ayuda a definir nuestros Xpath, estos Xpath son la dirección de los elementos que se encuentran en la página, los cuales necesitamos para realizar la prueba; también encontraremos nuestros set y get, quienes nos ayudan a llevar y traer nuestros Xpath.

```java
package pages;

import org.openqa.selenium.By;

public class HacebPages {


    By txtBuscador = By.xpath("//input[@placeholder='¿Buscas un producto en especial?']");
    By btnBuscador = By.xpath("//button[@class='btn btn-search']");
    By btnElementoBusqueda;
    By txtElementoBusqueda;


    public By getTxtBuscador() {
        return txtBuscador;
    }

    public By getBtnBuscador() {
        return btnBuscador;
    }

    public By getBtnElementoBusqueda() {
        return btnElementoBusqueda;
    }

    public By getTxtElementoBusqueda() {
        return txtElementoBusqueda;
    }

    public void setBtnElementoBusqueda(String producto) {
        this.btnElementoBusqueda = By.xpath("//div[@class='shelve__item' and @data-name='"+producto+"']");
    }


    public void setTxtElementoBusqueda(String producto) {
        this.txtElementoBusqueda = By.xpath("//div[@class='column']//h1[contains(text(),'"+producto+"')]");
    }


}
```

#### Paquete steps

El paquete steps posee la clase HacebSteps, esta clase contiene los métodos que se van a ejecutar dentro del código para que la automatización funcione de manera correcta.

```java
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

```

En esta clase  ***HacebSteps***  utilice un hilo para dejar que la pagina se tomara un segundo de espera y pueda terminar de cargar la información.
```

```java
 public void buscarElementoEnHacebs(String productos)  {
        try {
            escribirEnTexto(hacebPages.getTxtBuscador(), productos);
            Thread.sleep(1000);
            clicEnElemento(hacebPages.getBtnBuscador());
            Thread.sleep(1000);
            hacebPages.setBtnElementoBusqueda(productos);
            Thread.sleep(1000);
            clicEnElemento(hacebPages.getBtnElementoBusqueda());
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

```
```

#### Paquete runners

En el paquete llamado runners utilizamos tres clases diferentes llamadas:

- HacebBackgroundRunner
- HacebFallidoExitosoRunner
- HacebRunner

Estas clases nos ayudan que el código ejecute los parámetros del Cucumber, el cual está compuesto por la clase que contiene el paquete de los stepsDefinitions y por el archivo que contiene el directorio llamado features.


- HacebBackgroundRunner

Ejecuta las sentencias que me se encuentran en la clase ***HacebBackground.feature***.

```java
package runners;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src\\test\\resources\\features\\HacebBackground.feature",
        glue = "stepsDefinitions",
        snippets = SnippetType.CAMELCASE
)
public class HacbeBackgroundRunner {
}

```

- HacebFallidoExitosoRunner

Ejecuta las sentencias que me se encuentran en la clase ***HacebFallidoExitoso.feature***.

```java
package runners;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src\\test\\resources\\features\\HacebFallidoExitoso.feature",
        glue = "stepsDefinitions",
        snippets = SnippetType.CAMELCASE
)
public class HacebFallidoExitosoRunner {
}
```

- HacebRunner

Ejecuta las sentencias que me se encuentran en la clase ***Haceb.feature***.

```java
package runners;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src\\test\\resources\\features\\Haceb.feature",
        glue = "stepsDefinitions",
        snippets = SnippetType.CAMELCASE
)

public class HacebRunner {
}
```

#### Paquete stepDefinitios
En el paquete llamado stepDefinitios utilizamos tres clases diferentes llamadas:

- HacebBackgroundStepDefinition
- HacebFallidoExitosoStepDefinition
- HacebStepDefinition

Estas clases definen el paso a paso de lo que se va a hacer dentro de la automatización en la página web con ayuda de las diferentes clases, las cuales contienen los parametros ***Given, When y Then***  y estos tambien se pueden encontrar en un lenguaje mucho más sencillo en el directorio llamado feature.

A estas clases se les hace una extension de la clase ***HacebSteps*** para poder utilizar los metodos que se encunetran en esta clase y asi ayudar al funcionamiento de la automatización.


***HacebSteps hacebsteps = new HacebSteps();***


- HacebBackgroundStepDefinition
  Contiene los paso a paso del archivo ***HacebBackground.feature***.

```java
package stepsDefinitions;


import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.HacebSteps;

import java.util.List;


public class HacebBackgroundStepDefinition {



    HacebSteps hacebsteps = new HacebSteps();

    @Given("^que el usuario ingreso a la pagina$")
    public void queElUsuarioIngresoALaPagina() {
       hacebsteps.abrirPagina();
    }


    @When("^ecuentre el producto$")
    public void ecuentreElProducto(List<String>Productos) {
      hacebsteps.buscarElementoEnHacebs(Productos.get(0));

    }

    @Then("^podre mirar (.*) en pantalla$")
    public void podreMirarEnPantalla(List<String>Productos) {
    hacebsteps.validarElementoEnPantalla(Productos.get(0));
    }

}

```

- HacebFallidoExitosoStepDefinition

Contiene los paso a paso del archivo ***HacebFallidoExitoso.feature***.
```java
package stepsDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.HacebSteps;

import java.util.List;

public class HacebFallidoExitosoStepDefinition {


    HacebSteps hacebsteps = new HacebSteps();

    @Given("^que navegue en la pagina de Haceb$")
    public void queNavegueEnLaPaginaDeHaceb() {hacebsteps.abrirPagina();}


    @When("^halle los productos$")
    public void halleLosProductos(List<String>Productos) {hacebsteps.buscarElementoEnHacebs(Productos.get(0));}

    @Then("^podre observar en pantalla$")
    public void podreObservarEnPantalla(List<String>Productos) {hacebsteps.validarElementoEnPantalla(Productos.get(0));}

}

```

- HacebStepDefinition

Contiene los paso a paso del archivo ***Haceb.feature***.
```java
package stepsDefinitions;

import cucumber.api.java.en.*;
import steps.HacebSteps;

public class HacebStepDefinition {

    HacebSteps hacebsteps = new HacebSteps();

    @Given("^que me encuentro en la pagina de Haceb$")
    public void queMeEncuentroEnLaPaginaDeHaceb() {hacebsteps.abrirPagina();}

    @When("^busque el producto (.*)$")
    public void busqueElProductoNeveraHimalayaNoFrost404LitrosPanelDigitalTouchInox(String producto) {
        hacebsteps.buscarElementoEnHacebs(producto);
    }

    @Then("^podre ver (.*) en pantalla$")
    public void podreVerNeveraHimalayaNoFrost404LitrosPanelDigitalTouchInoxEnPantalla(String producto) {
        hacebsteps.validarElementoEnPantalla(producto);

    }

}

```


#### Directorio feature
El directorio features posee tres archivos:

- Haceb.feature
- HacebBackground.feature
- HacebFallidoExitoso.feature

Los cuaes contienen el lenguaje ordinario llamado Gherkin, este lenguaje nos ayuda a comprender el comportamiento de las pruebas en un idioma lógico para que se pueda entender con mayor claridad.

- scenario simple: Nos permite agregar datos de manera independiente.
- scenario outline: Nos facilita agregar diversos datos para un solo scenario.
- Background: Se utiliza para ahorrar codigo en precondiciones que tienen en comun diferentes scenarios.


- Haceb.feature

Por medio de este archivo ***.feature***  realizamos la prueba de la busqueda de los cinco productos diferentes y con scenario outline.

```
Feature: HU-001 Buscador Haceb
   Yo como usuario de Haceb
   Quiero buscar un producto en la plataforma
   Para ver el nombre del producto en pantalla

  Scenario Outline: Buscar producto
    Given que me encuentro en la pagina de Haceb
    When busque el producto <NombreProducto>
    Then podre ver <NombreProducto> en pantalla
    Examples:
      |NombreProducto|
      |Nevera Himalaya No Frost 404 Litros - Panel Digital Touch - Inox|
      |Estufa de empotrar Cristal negro 60X50 cm Hierro fundido gas natural|
      |Lavadora Digital Atlas 18 Kg Panel Trasero ONIX|
      |Horno mixto Masala 60 cm negro 120V gas natural Haceb|
      |Freidora de Aire sin Aceite Haceb Negra|
```


- HacebBackground.feature

Por medio de este archivo ***.feature***  realizamos la prueba de la busqueda de los dos productos diferentes con scenario outline y utilizando background.

```
Feature: HU-001 Buscador HacebBackground
  Yo como usuario de Haceb
  Quiero buscar un producto en la plataforma
  Para ver el nombre del producto en pantalla


  Background: Usuario ingreso a la pagina
    Given que el usuario ingreso a la pagina



  Scenario Outline:Encontrar producto
    When ecuentre el producto
      |<Producto>|
    Then podre mirar <Producto> en pantalla
    Examples:
      |Producto|
      |Calentador de agua 10 galones Haceb de acumulación eléctrico blanco|


  Scenario Outline:Encontrar producto
    When ecuentre el producto
      |<Producto>|
    Then podre mirar <Producto> en pantalla
    Examples:
      |Producto|
      |Aire Acondicionado Inverter Tayrona Haceb 12.000 BTU 110 v|
```


- HacebFallidoExitoso.feature

Por medio de este archivo ***.feature***  realizamos la prueba de la busqueda de los dos productos diferentes con scenario simples.

```
Feature: HU-001 Buscador Haceb
  Yo como usuario de Haceb
  Quiero buscar un producto en la plataforma
  Para ver el nombre del producto en pantalla



  Scenario: Busqueda Exitosa
    Given que navegue en la pagina de Haceb
    When halle los productos
      |Calentador de agua 10 galones Haceb de acumulación eléctrico blanco|
    Then podre observar en pantalla
      |Calentador de agua 10 galones Haceb de acumulación eléctrico blanco|

  Scenario: Busqueda Fallida
    Given que navegue en la pagina de Haceb
    When halle los productos
      |Nevera Himalaya No Frost 448 Litros - Titanio|
    Then podre observar en pantalla
      |Nevera Himalaya No Frost 448 Litros - Titanio|
```



