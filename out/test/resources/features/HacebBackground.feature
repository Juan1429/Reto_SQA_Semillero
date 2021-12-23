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
