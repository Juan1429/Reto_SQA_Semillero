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
