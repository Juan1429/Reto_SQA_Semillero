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
