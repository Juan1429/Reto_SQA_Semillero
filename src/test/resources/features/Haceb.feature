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
      |Estufa Romero Vidrio Haceb 50 cms Gas Natural Negra|
      |Estufa de empotrar Cristal negro 60X50 cm Hierro fundido gas natural|
      |Calentador de agua Bambú 10 litros Haceb de paso a gas natural tiro forzado blanco|
      |Horno mixto Masala 60 cm negro 120V gas natural Haceb|
      |Freidora de Aire sin Aceite Haceb Negra|
