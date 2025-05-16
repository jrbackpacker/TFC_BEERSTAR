import { createRouter, createWebHistory } from 'vue-router';
import Login from '../views/Login.vue'; // Asumo que Login está en views
import RegistroArticulosLayout from '../views/RegistroArticulos.vue'; // Usamos este como layout
import Productos from '../components/Productos.vue'; // Importamos el nuevo componente Productos
import Pedidos from '../components/Pedidos.vue';     // Importamos Pedidos

const routes = [
    { path: '/', name: 'Login', component: Login },
    {
        // Ruta padre para la gestión del proveedor
        path: '/gestion',
        name: 'GestionProveedor',
        component: RegistroArticulosLayout, // Este componente contiene la barra de navegación y router-view
        children: [
            {
                // Ruta hija para Productos (será la vista por defecto en /gestion)
                path: '', // Ruta vacía significa la ruta hija por defecto
                name: 'GestionProductos',
                component: Productos
            },
            {
                // Ruta hija para Pedidos
                path: 'pedidos', // La ruta completa será /gestion/pedidos
                name: 'GestionPedidos',
                component: Pedidos
            },
            // Puedes añadir más rutas hijas aquí (ej. '/gestion/ventas')
        ]
    },
    // Si necesitas mantener la ruta /registro por compatibilidad, puedes redirigirla
    { path: '/registro', redirect: '/gestion' },
    // La ruta /pedidos a nivel superior ya no se necesita si siempre se accede desde /gestion
    // Si la necesitas por alguna razón (ej. acceso directo), ajústala, pero la estructura anidada es preferible
    // { path: '/pedidos', name: 'PedidosStandalone', component: Pedidos },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;