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
