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
      |Nevera Himalaya No Frost 404 Litros - Panel Digital Touch - Inox|
      |Estufa de empotrar Cristal negro 60X50 cm Hierro fundido gas natural|
      |Lavadora Digital Atlas 18 Kg Panel Trasero ONIX|
      |Horno mixto Masala 60 cm negro 120V gas natural Haceb|
      |Freidora de Aire sin Aceite Haceb Negra|