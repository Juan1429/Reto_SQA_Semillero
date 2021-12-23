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
