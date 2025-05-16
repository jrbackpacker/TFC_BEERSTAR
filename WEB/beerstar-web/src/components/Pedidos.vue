<template>
  <div class="form-panel">
    <h2 class="text-2xl font-semibold mb-4">Gestión de Pedidos</h2>

    <div v-if="loading" class="text-center text-gray-500 mb-4">Cargando pedidos...</div>
    <div v-if="error" class="text-center text-red-500 mb-4">Error al cargar los pedidos: {{ error.message }}</div>

    <table class="min-w-full bg-white border border-gray-200 rounded-lg shadow-md mt-4">
      <thead class="bg-gray-100">
      <tr>
        <th class="py-2 px-4 text-left text-gray-600 font-semibold">ID Pedido</th>
        <th class="py-2 px-4 text-left text-gray-600 font-semibold">Cliente</th>
        <th class="py-2 px-4 text-left text-gray-600 font-semibold">Fecha</th>
        <th class="py-2 px-4 text-left text-gray-600 font-semibold">Estado</th>
        <th class="py-2 px-4 text-center text-gray-600 font-semibold">Total</th>
        <th class="py-2 px-4 text-center text-gray-600 font-semibold">Terminado</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="pedido in pedidos" :key="pedido.idPedido" class="border-t hover:bg-gray-50">
        <td class="py-2 px-4">{{ pedido.idPedido }}</td>
        <td class="py-2 px-4">{{ pedido.cliente ? pedido.cliente.nombre : 'N/A' }}</td>
        <td class="py-2 px-4">{{ formatDate(pedido.fechaPedido) }}</td>
        <td class="py-2 px-4">{{ pedido.estado }}</td>
        <td class="py-2 px-4 text-center">{{ formatCurrency(pedido.totalPedido) }}</td>
        <td class="py-2 px-4 text-center">
          <input
              type="checkbox"
              :checked="pedido.estado === 'Terminado'"
              @change="toggleTerminadoStatus(pedido)"
              class="form-checkbox h-5 w-5 text-blue-600"
          />
        </td>
      </tr>
      <tr v-if="pedidos.length === 0 && !loading && !error">
        <td colspan="6" class="py-4 px-4 text-center text-gray-500">No hay pedidos para mostrar.</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from 'axios';

// --- Servicio API para Pedidos ---
const api = axios.create({
  baseURL: 'https://java-railway-backend-beerstar-production.up.railway.app',
  headers: { 'Content-Type': 'application/json' },
});

const getPedidos = async () => {
  try {
    // ¡¡¡VERIFICA Y CORRIGE ESTA URL!!! Este es el endpoint que te da 404
    const response = await api.get('/beerstar/pedidos/listarPedidos');
    return response.data;
  } catch (error) {
    console.error('Error al obtener pedidos:', error);
    // NO lanzamos el error de nuevo para que no detenga la ejecución
    throw error; // Mantenemos throw para que el error se capture en fetchPedidos
  }
};

const updatePedidoStatus = async (id, nuevoEstado) => {
  try {
    const response = await api.put(`/beerstar/pedidos/actualizarEstadoPedido/${id}`, { estado: nuevoEstado }); // Verifica este endpoint
    return response.data;
  } catch (error) {
    console.error(`Error al actualizar estado del pedido ${id}:`, error);
    throw error;
  }
};
// --- Fin Servicio API para Pedidos ---

export default {
  name: 'Pedidos',
  data() {
    return {
      pedidos: [],
      loading: false,
      error: null,
    };
  },
  methods: {
    async fetchPedidos() {
      this.loading = true;
      this.error = null; // Reinicia el error antes de una nueva carga
      try {
        const data = await getPedidos();
        this.pedidos = data;
      } catch (err) {
        this.error = err; // Captura el error para mostrar el mensaje
        console.error('Detalle del error de fetch:', err.response || err.message); // Log para depuración
      } finally {
        this.loading = false;
      }
    },
    async toggleTerminadoStatus(pedido) {
      const nuevoEstado = pedido.estado === 'Terminado' ? 'En preparación' : 'Terminado';

      try {
        await updatePedidoStatus(pedido.idPedido, nuevoEstado);
        pedido.estado = nuevoEstado; // Actualiza localmente
        console.log(`Pedido ${pedido.idPedido} actualizado a estado: ${nuevoEstado}`);
      } catch (err) {
        console.error('Error actualizando estado del pedido:', err);
        alert('Hubo un error al actualizar el estado del pedido.');
      }
    },
    formatDate(dateString) {
      if (!dateString) return '';
      try {
        const date = new Date(dateString);
        if (isNaN(date.getTime())) {
          console.error("Fecha inválida para formatear:", dateString);
          return dateString;
        }
        const options = { year: 'numeric', month: 'long', day: 'numeric' };
        return date.toLocaleDateString(undefined, options);
      } catch (e) {
        console.error("Error al parsear o formatear fecha:", dateString, e);
        return dateString;
      }
    },
    formatCurrency(amount) {
      if (amount === null || amount === undefined) return '';
      const num = parseFloat(amount);
      if (isNaN(num)) {
        console.error("Valor inválido para formatear como moneda:", amount);
        return amount;
      }
      return `${num.toFixed(2)} €`;
    }
  },
  mounted() {
    this.fetchPedidos(); // Cargar pedidos al montar
  },
};
</script>

<style scoped>
/* Estilos para el contenedor del panel */
.form-panel {
  background: white;
  padding: 3rem;
  border-radius: 20px;
  box-shadow: 0 6px 30px rgba(0,0,0,0.35);
  width: 100%;
  max-width: 1000px;
  margin-top: 0;
  margin-left: auto;
  margin-right: auto;
  font-size: 1.1rem;
  padding-bottom: 2rem;
}

/* Estilos de tabla */
table {
  width: 100%;
  margin-top: 2rem;
  border-collapse: collapse;
}
th, td {
  border: 1px solid #ccc;
  padding: 1rem;
  text-align: left;
}
th {
  background-color: #eee;
}
td:nth-child(5), td:nth-child(6) { /* Centrar Total y Terminado */
  text-align: center;
}
</style>