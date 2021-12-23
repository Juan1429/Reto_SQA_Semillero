package uis;

import net.serenitybdd.screenplay.targets.Target;


public class HacebPages{

    public static final Target TXT_BUSCADOR = Target.the("").locatedBy("//input[@placeholder='¿Buscas un producto en especial?']");
    public static final Target BTN_BUSCADOR = Target.the("").locatedBy("//button[@class='btn btn-search']");
    public static final Target BTN_ELEMENTO_BUSQUEDA = Target.the("").locatedBy("//div[@class='shelve__item' and @data-name='{0}']");
    public static final Target TXT_ELEMENTO_BUSQUEDA = Target.the("").locatedBy("//div[@class='column']//h1[contains(text(),'{0}')]");
}
