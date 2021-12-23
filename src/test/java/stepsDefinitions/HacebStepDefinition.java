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
