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
