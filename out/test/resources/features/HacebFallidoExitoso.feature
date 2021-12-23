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
      |Nevera Himalaya No Frost 700 Litros - Titanio|
    Then podre observar en pantalla
      |Nevera Himalaya No Frost 700 Litros - Titanio|