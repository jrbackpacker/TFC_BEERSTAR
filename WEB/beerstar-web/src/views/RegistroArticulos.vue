<template>
  <div class="registro-articulos">
    <nav class="menu-bar">
      <ul>
        <li>
          <router-link to="/gestion" active-class="active">Productos</router-link>
        </li>
        <li>
          <router-link to="/gestion/pedidos" active-class="active">Pedidos</router-link>
        </li>
      </ul>
    </nav>

    <router-view></router-view>

  </div>
</template>

<script>
// Ya no necesitas importar los componentes hijos aquí
export default {
  name: 'RegistroArticulosLayout', // Buen nombre para un componente de layout
  // No necesitas 'components' ni 'data' para este propósito
}
</script>

<style scoped>
/* Mantén tus estilos para .registro-articulos y .menu-bar */
.registro-articulos {
  background-color: gold;
  min-height: 100vh;
  padding-top: 100px; /* Espacio para la barra fija */
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-bottom: 50px; /* Añade espacio al final si el contenido es largo */
  width: 100%; /* Asegura que ocupe el ancho completo para centrar los paneles hijos */
}
.menu-bar {
  position: fixed;
  top: 20px;
  left: 50%;
  transform: translateX(-50%);
  background-color: #333;
  color: white;
  padding: 1rem 3rem;
  border-radius: 30px;
  box-shadow: 0 6px 30px rgba(0,0,0,0.35);
  z-index: 1000;
  font-size: 1.2rem;
}
.menu-bar ul {
  list-style: none;
  display: flex;
  justify-content: center;
  gap: 3rem;
  margin: 0;
  padding: 0;
}
.menu-bar li {
  cursor: pointer;
  font-weight: bold;
}

.menu-bar a {
  color: white;
  text-decoration: none;
}

.menu-bar a.active {
  text-decoration: underline;
  color: #ffd700;
}

/* ELIMINADOS los estilos .form-panel de aquí */
/* Estos estilos ahora solo deben aplicarse en los componentes de vista (Productos.vue, Pedidos.vue) */
</style>