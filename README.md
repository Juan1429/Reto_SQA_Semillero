## Reto Final

Se realizó tres diferentes pruebas automatizadas con ScreenPlay en la página de https://www.haceb.com las cuales son:

- Realizar una busqueda de 5 productos diferentes por medio de un scenario outline
- Realizar una busqueda con dos scenarios los cuales tendrían una precondición utilizando un Background
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


}

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()// https://mvnrepository.com/artifact/io.cucumber/cucumber-jvm-deps




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
// https://mvnrepository.com/artifact/cglib/cglib-nodep
    implementation group: 'cglib', name: 'cglib-nodep', version: '3.3.0'

// https://mvnrepository.com/artifact/com.thoughtworks.xstream/xstream
    implementation group: 'com.thoughtworks.xstream', name: 'xstream', version: '1.4.18'
    implementation 'info.cukes:cucumber-jvm-deps:1.0.5'




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

    public static  GoogleChromeDriver chromeHisBrowserWeb() {


        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--disable-infobars");

        driver = new ChromeDriver(options);
        return new GoogleChromeDriver();

    }

    public  WebDriver on(String url) {
        driver.get(url);
        return driver;
    }

}

```

#### Paquete uis

El paquete uis contiene la clase HacebPages la cual nos ayuda a definir nuestros Xpath, estos Xpath son la dirección de los elementos que se encuentran en la página, los cuales necesitamos para realizar la prueba; también encontraremos nuestros set y get, quienes nos ayudan a llevar y traer nuestros Xpath.

```java
package uis;

import net.serenitybdd.screenplay.targets.Target;


public class HacebPages{

 public static final Target TXT_BUSCADOR = Target.the("").locatedBy("//input[@placeholder='¿Buscas un producto en especial?']");
    public static final Target BTN_BUSCADOR = Target.the("").locatedBy("//button[@class='btn btn-search']");
    public static final Target BTN_ELEMENTO_BUSQUEDA = Target.the("").locatedBy("//div[@class='shelve__item' and @data-name='{0}']");
    public static final Target TXT_ELEMENTO_BUSQUEDA = Target.the("").locatedBy("//div[@class='column']//h1[contains(text(),'{0}')]");
}

```

#### Paquete task

El paquete task posee la clase HacebSteps, esta clase contiene los métodos que se van a ejecutar dentro del código para que la automatización funcione de manera correcta.

```java
package task;


import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import uis.HacebPages;

public class HacebBuscar implements Task {

    private  String producto;

    public HacebBuscar(String producto) {
        this.producto = producto;
    }
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Enter.theValue(producto).into(HacebPages.TXT_BUSCADOR),
                Click.on(HacebPages.BTN_BUSCADOR),
                Click.on(HacebPages.BTN_ELEMENTO_BUSQUEDA.of(producto))

        );
    }

    public static HacebBuscar EnHaceb (String producto){

        return Instrumented.instanceOf(HacebBuscar.class).withProperties(producto);
    }
}

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

Cada clase contiene un ***Actor*** el cual ayuda a que la simulación de las pruebas se acerque mas a las acciones manuales de una persona.

Actor actor = new Actor("Juan");

- HacebBackgroundStepDefinition
  Contiene los paso a paso del archivo ***HacebBackground.feature***.

```java
package stepsDefinitions;


import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import drivers.GoogleChromeDriver;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.GivenWhenThen;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.questions.WebElementQuestion;
import task.HacebBuscar;
import uis.HacebPages;

import java.util.List;


public class HacebBackgroundStepDefinition {



    Actor actor = new Actor("Juan");

    @Before
    public void before(){
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("^que el usuario ingreso a la pagina$")
    public void queElUsuarioIngresoALaPagina() {
        actor.can(BrowseTheWeb.with(GoogleChromeDriver.chromeHisBrowserWeb().on("https://www.haceb.com/")));
    }


    @When("^ecuentre el producto$")
    public void ecuentreElProducto(List<String>Productos) {
        actor.attemptsTo(HacebBuscar.EnHaceb(Productos.get(0)));

    }

    @Then("^podre mirar (.*) en pantalla$")
    public void podreMirarEnPantalla(List<String>Productos) {
        actor.should(GivenWhenThen.seeThat(WebElementQuestion.the(HacebPages.TXT_ELEMENTO_BUSQUEDA.of(Productos.get(0))), WebElementStateMatchers.containsText(Productos.get(0))));
    }

}


```

- HacebFallidoExitosoStepDefinition

Contiene los paso a paso del archivo ***HacebFallidoExitoso.feature***.

```java
package stepsDefinitions;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import drivers.GoogleChromeDriver;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.GivenWhenThen;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.questions.WebElementQuestion;
import task.HacebBuscar;
import uis.HacebPages;

import java.util.List;

public class HacebFallidoExitosoStepDefinition {

    Actor actor = new Actor("Juan");

    @Before
    public void before(){
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("^que navegue en la pagina de Haceb$")
    public void queNavegueEnLaPaginaDeHaceb() { actor.can(BrowseTheWeb.with(GoogleChromeDriver.chromeHisBrowserWeb().on("https://www.haceb.com/")));}


    @When("^halle los productos$")
    public void halleLosProductos(List<String>Productos) { actor.attemptsTo(HacebBuscar.EnHaceb(Productos.get(0)));}

    @Then("^podre observar en pantalla$")
    public void podreObservarEnPantalla(List<String>Productos) {
        actor.should(GivenWhenThen.seeThat(WebElementQuestion.the(HacebPages.TXT_ELEMENTO_BUSQUEDA.of(Productos.get(0))), WebElementStateMatchers.containsText(Productos.get(0))));
    }

}


```

- HacebStepDefinition

Contiene los paso a paso del archivo ***Haceb.feature***.

```java
package stepsDefinitions;

import cucumber.api.java.Before;
import cucumber.api.java.en.*;
import drivers.GoogleChromeDriver;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.GivenWhenThen;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.questions.WebElementQuestion;
import task.HacebBuscar;
import uis.HacebPages;

public class HacebStepDefinition {


    Actor actor = new Actor("Juan");


    @Before
    public void before(){
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("^que me encuentro en la pagina de Haceb$")
    public void queMeEncuentroEnLaPaginaDeHaceb() {actor.can(BrowseTheWeb.with(GoogleChromeDriver.chromeHisBrowserWeb().on("https://www.haceb.com/")));}

    @When("^busque el producto (.*)$")
    public void busqueElProductoNeveraHimalayaNoFrost404LitrosPanelDigitalTouchInox(String producto) {
        actor.attemptsTo(HacebBuscar.EnHaceb(producto));
    }

    @Then("^podre ver (.*) en pantalla$")
    public void podreVerNeveraHimalayaNoFrost404LitrosPanelDigitalTouchInoxEnPantalla(String producto) {
        actor.should(GivenWhenThen.seeThat(WebElementQuestion.the(HacebPages.TXT_ELEMENTO_BUSQUEDA.of(producto)), WebElementStateMatchers.containsText(producto)));

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
Feature: HU-002 Buscador HacebBackground
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
Feature: HU-003 Buscador Haceb
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

#### Imagenes del reporte

![Image text](https://github.com/Juan1429/Reto_SQA_Semillero/blob/main/src/main/resources/Image%202021-12-23%20at%2012.23.29%20PM.jpeg)
![Image text](https://github.com/Juan1429/Reto_SQA_Semillero/blob/main/src/main/resources/Image%202021-12-23%20at%2012.24.02%20PM.jpeg)

