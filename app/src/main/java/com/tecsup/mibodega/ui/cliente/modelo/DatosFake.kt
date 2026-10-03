package com.tecsup.mibodega.ui.cliente.modelo

val listaCategorias = listOf("Todos", "Bebidas", "Abarrotes", "Snacks")

val listaProductosFake = listOf(
    Producto(
        id = 1,
        nombre = "Arroz Costeño",
        descripcion = "Arroz extra, grano largo, ideal para el día a día.",
        precio = 4.50,
        categoria = "Abarrotes",
        imagenUrl = "https://images.unsplash.com/photo-1586201375761-83865001e31c?w=500&q=80",
        peso = "1 kg"
    ),
    Producto(
        id = 2,
        nombre = "Aceite Primor",
        descripcion = "Aceite vegetal 1 L, alto en vitamina E.",
        precio = 8.90,
        categoria = "Abarrotes",
        imagenUrl = "https://images.unsplash.com/photo-1474979266404-7eaacbcd87c5?w=500&q=80",
        peso = "1 L"
    ),
    Producto(
        id = 3,
        nombre = "Leche Gloria",
        descripcion = "Leche evaporada entera 1 L.",
        precio = 5.20,
        categoria = "Abarrotes",
        imagenUrl = "https://images.unsplash.com/photo-1563636619-e9143da7973b?w=500&q=80",
        peso = "1 L"
    ),
    Producto(
        id = 4,
        nombre = "Galleta Oreo",
        descripcion = "Galletas de chocolate rellenas 126 g.",
        precio = 3.50,
        categoria = "Snacks",
        imagenUrl = "https://images.unsplash.com/photo-1558961363-fa8fdf82db35?w=500&q=80",
        peso = "126 g"
    ),
    Producto(
        id = 5,
        nombre = "Coca-Cola Original",
        descripcion = "Bebida gaseosa sabor cola. Ideal para compartir en familia.",
        precio = 6.50,
        categoria = "Bebidas",
        imagenUrl = "https://images.unsplash.com/photo-1622483767028-3f66f32aef97?w=500&q=80",
        peso = "1.5 L"
    )
)