<template>
  <div class="admin-productos-single-layout">
    <div class="producto-form-panel">
      <h2>{{ productoSeleccionado ? 'Editar Artículo' : 'Registrar Artículo' }}</h2>

      <form @submit.prevent="productoSeleccionado ? actualizarArticulo() : registrarArticulo()">
        <input v-model="nombre" placeholder="Nombre del artículo" required />
        <input v-model.number="precio" type="number" step="0.01" placeholder="Precio" required />
        <select v-model.number="idCategoria" required>
          <option disabled value="">Selecciona una categoría</option>
          <option value="1">Bebidas</option>
          <option value="2">Ropa</option>
          <option value="3">Electrónica</option>
          <option value="4">Alimentos</option>
          <option value="5">Accesorios</option>
        </select>
        <input v-model.number="stock" type="number" placeholder="Stock disponible" required />
        <textarea v-model="descripcion" placeholder="Descripción" required></textarea>
        <input v-model="url" placeholder="URL de la imagen" />
        <input v-model.number="graduacion" type="number" step="0.1" placeholder="Graduación (%)" />

        <button type="submit">{{ productoSeleccionado ? 'Actualizar' : 'Registrar' }}</button>
        <button v-if="productoSeleccionado" type="button" @click="cancelarEdicion">Cancelar</button>
      </form>
    </div>

    <div class="producto-list-panel">
      <h2>Listado de Artículos</h2>
      <table class="productos-table">
        <thead>
        <tr>
          <th>Imagen</th>
          <th>Nombre</th>
          <th>Precio</th>
          <th>Categoría</th>
          <th>Stock</th>
          <th>Descripción</th>
          <th>Graduación</th>
          <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="producto in paginatedProductos" :key="producto.idArticulo">
          <td>
            <img v-if="producto.url" :src="producto.url" :alt="producto.nombre" class="imagen-miniatura" />
            <span v-else>Sin imagen</span>
          </td>
          <td>{{ producto.nombre }}</td>
          <td>{{ producto.precio }} €</td>
          <td>{{ getCategoryName(producto.idCategoria) }}</td>
          <td>{{ producto.stock }}</td>
          <td>{{ producto.descripcion }}</td>
          <td>{{ producto.graduacion !== null ? producto.graduacion + ' %' : 'N/A' }}</td>
          <td class="actions-cell">
            <button @click="showDetail(producto)" class="btn-view">Vista</button>
            <button @click="editarArticulo(producto)" class="btn-edit">Editar</button>
            <button @click="borrarArticulo(producto.idArticulo)" class="btn-delete">Eliminar</button>
          </td>
        </tr>
        <tr v-if="paginatedProductos.length === 0 && productos.length > 0">
          <td colspan="8" class="text-center">Página vacía. Ajusta la página.</td>
        </tr>
        <tr v-if="productos.length === 0">
          <td colspan="8" class="text-center">No hay artículos registrados.</td>
        </tr>
        </tbody>
      </table>

      <div v-if="totalPages > 1" class="pagination-controls">
        <button @click="prevPage" :disabled="currentPage === 1" class="btn-pagination">
          &laquo; Página Anterior
        </button>
        <span>Página {{ currentPage }} de {{ totalPages }}</span>
        <button @click="nextPage" :disabled="currentPage >= totalPages" class="btn-pagination">
          Página Siguiente &raquo;
        </button>
      </div>

    </div>

    <div v-if="showDetailedView && productForDetail" class="modal-overlay" @click.self="closeDetail">
      <div class="modal-content">
        <button class="modal-close" @click="closeDetail">&times;</button>
        <h3>{{ productForDetail.nombre }}</h3>

        <div class="detail-body">
          <img v-if="productForDetail.url" :src="productForDetail.url" :alt="productForDetail.nombre" class="detail-image" />
          <span v-else class="no-image-placeholder">Sin imagen disponible</span>

          <div class="detail-info">
            <p><strong>Precio:</strong> {{ productForDetail.precio }} €</p>
            <p><strong>Categoría:</strong> {{ getCategoryName(productForDetail.idCategoria) }}</p>
            <p><strong>Stock:</strong> {{ productForDetail.stock }}</p>
            <p><strong>Descripción:</strong> {{ productForDetail.descripcion }}</p>
            <p v-if="productForDetail.graduacion !== null"><strong>Graduación:</strong> {{ productForDetail.graduacion }} %</p>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<script>

import { getArticulos, registrarArticulo as crearArticulo, actualizarArticulo as modificarArticulo, eliminarArticulo } from '@/services/api';

export default {
  name: 'ProductosAdminPaginatedSingle', // Unique name
  data() {
    return {
      productos: [], // Holds ALL products
      // Form fields
      nombre: '',
      precio: null,
      idCategoria: '',
      descripcion: '',
      url: '',
      graduacion: null,
      stock: null,
      // State for editing
      productoSeleccionado: null,
      // State for pagination
      currentPage: 1,
      itemsPerPage: 10, // Number of items per page
      // Example category mapping
      categories: {
        1: 'Bebidas',
        2: 'Ropa',
        3: 'Electrónica',
        4: 'Alimentos',
        5: 'Accesorios'
      }
      // State for detailed view
      , showDetailedView: false
      , productForDetail: null // Holds the product object for the detailed view
    };
  },
  computed: {
    // Calculates the total number of pages
    totalPages() {
      return Math.ceil(this.productos.length / this.itemsPerPage);
    },
    // Returns the subset of products for the current page
    paginatedProductos() {
      const startIndex = (this.currentPage - 1) * this.itemsPerPage;
      const endIndex = startIndex + this.itemsPerPage;
      return this.productos.slice(startIndex, endIndex);
    }
  },
  created() {
    this.cargarArticulos();
  },
  methods: {
    getCategoryName(id) {
      return this.categories[id] || 'Desconocida';
    },
    async cargarArticulos() {
      try {
        const articulos = await getArticulos();
        this.productos = articulos; // Update the full list
        // After loading, ensure the current page is valid
        const newTotalPages = Math.ceil(this.productos.length / this.itemsPerPage);
        if (this.currentPage > newTotalPages) {
          // If current page is out of bounds, go to the new last page (or page 1 if list is empty)
          this.currentPage = newTotalPages > 0 ? newTotalPages : 1;
        }
        // If the list became empty, explicitly go to page 1
        if (this.productos.length === 0) {
          this.currentPage = 1;
        }

      } catch (error) {
        console.error('Error cargando productos:', error);
        // Handle error visually
      }
    },
    async registrarArticulo() {
      if (!this.nombre || this.precio === null || this.idCategoria === '' || !this.descripcion || this.stock === null) {
        alert('Por favor, completa todos los campos obligatorios.');
        return;
      }
      try {
        const nuevoArticulo = {
          nombre: this.nombre,
          precio: parseFloat(this.precio),
          idCategoria: parseInt(this.idCategoria),
          descripcion: this.descripcion,
          url: this.url || null,
          graduacion: this.graduacion !== null ? parseFloat(this.graduacion) : null,
          stock: parseInt(this.stock)
        };
        console.log('Enviando nuevo artículo:', nuevoArticulo);
        await crearArticulo(nuevoArticulo);
        alert('Artículo registrado exitosamente');
        this.resetFormulario();
        this.cargarArticulos(); // Recargar la lista (will adjust page if needed)
      } catch (error) {
        console.error('Error registrando artículo:', error);
        alert('Hubo un error al registrar el artículo.');
      }
    },
    editarArticulo(producto) {
      // Before setting productForDetail for editing, ensure detail view is closed
      this.closeDetail();
      this.productoSeleccionado = producto;
      // Populate form fields from selected product
      this.nombre = producto.nombre;
      this.precio = producto.precio;
      this.idCategoria = producto.idCategoria;
      this.descripcion = producto.descripcion;
      this.url = producto.url;
      this.graduacion = producto.graduacion;
      this.stock = producto.stock;
    },
    async actualizarArticulo() {
      if (!this.productoSeleccionado) return;

      if (!this.nombre || this.precio === null || this.idCategoria === '' || !this.descripcion || this.stock === null) {
        alert('Por favor, completa todos los campos obligatorios.');
        return;
      }

      try {
        const articuloActualizado = {
          nombre: this.nombre,
          precio: parseFloat(this.precio),
          idCategoria: parseInt(this.idCategoria),
          descripcion: this.descripcion,
          url: this.url || null,
          graduacion: this.graduacion !== null ? parseFloat(this.graduacion) : null,
          stock: parseInt(this.stock)
        };
        console.log('Enviando artículo actualizado:', articuloActualizado);
        await modificarArticulo(this.productoSeleccionado.idArticulo, articuloActualizado);
        alert('Artículo actualizado exitosamente');
        this.resetFormulario();
        this.cargarArticulos(); // Recargar la lista (will adjust page if needed)
      } catch (error) {
        console.error('Error actualizando artículo:', error);
        alert('Hubo un error al actualizar el artículo.');
      }
    },
    async borrarArticulo(id) {
      if (confirm('¿Estás seguro de eliminar este artículo?')) {
        try {
          // Before deleting, ensure detail view is closed if this product is being viewed
          if (this.productForDetail && this.productForDetail.idArticulo === id) {
            this.closeDetail();
          }
          // Before deleting, ensure edit form is reset if this product is being edited
          if (this.productoSeleccionado && this.productoSeleccionado.idArticulo === id) {
            this.resetFormulario();
          }

          await eliminarArticulo(id);
          alert('Artículo eliminado');
          this.cargarArticulos(); // Recargar la lista (will adjust page if needed)

        } catch (error) {
          console.error('Error eliminando artículo:', error);
          alert('Hubo un error al eliminar el artículo.');
        }
      }
    },
    cancelarEdicion() {
      this.resetFormulario();
    },
    resetFormulario() {
      this.nombre = '';
      this.precio = null;
      this.idCategoria = '';
      this.descripcion = '';
      this.url = '';
      this.graduacion = null;
      this.stock = null;
      this.productoSeleccionado = null; // Clear selected product
    },
    // Pagination Methods
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
        // Reset selected product when changing page to avoid editing item no longer visible
        this.resetFormulario();
      }
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
        // Reset selected product when changing page
        this.resetFormulario();
      }
    },
    // Detailed View Methods
    showDetail(producto) {
      // Close edit form if open
      this.resetFormulario();
      this.productForDetail = producto;
      this.showDetailedView = true;
    },
    closeDetail() {
      this.showDetailedView = false;
      this.productForDetail = null; // Clear the product data
    }
  }
};
</script>

<style scoped>
/* Overall layout container */
.admin-productos-single-layout {
  display: flex; /* Arrange children side-by-side */
  gap: 1cm; /* 1 cm separation between panels */
  padding: 20px 2cm; /* 20px top/bottom padding, ~2cm margin effect on left/right */
  /* Removed max-width: 1200px; to allow it to be wider */
  margin: 0 auto; /* Center the container horizontally */
  flex-wrap: wrap; /* Allow wrapping on smaller screens */
  /* Optional: Add box-sizing for predictable padding behavior */
  box-sizing: border-box;
  /* Needed for modal absolute positioning context if modal is inside this layout */
  position: relative;
}

/* Styles for the Form Panel */
.producto-form-panel {
  background: white;
  padding: 2rem;
  border-radius: 10px;
  box-shadow: 0 4px 20px rgba(0,0,0,0.2);
  width: 100%; /* Base width */
  font-size: 1rem;
  flex: 1; /* Allows form to grow and take up available space */
  min-width: 300px; /* Minimum width before wrapping */
}

.producto-form-panel h2 {
  margin-top: 0;
  margin-bottom: 1.5rem;
  color: #333;
}

.producto-form-panel form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.producto-form-panel input,
.producto-form-panel select,
.producto-form-panel textarea {
  padding: 0.75rem;
  border-radius: 8px;
  border: 1px solid #ccc;
  font-size: 1rem;
  width: 100%;
  box-sizing: border-box;
}

.producto-form-panel textarea {
  min-height: 120px;
  resize: vertical;
}

.producto-form-panel button {
  padding: 0.75rem 1.5rem;
  border-radius: 8px;
  border: none;
  background-color: #007bff;
  color: white;
  cursor: pointer;
  font-weight: bold;
  transition: background-color 0.3s ease;
  width: auto;
  align-self: flex-start;
  margin-right: 0.5rem;
}

.producto-form-panel button[type="submit"] {
  background-color: #28a745;
}

.producto-form-panel button:hover {
  opacity: 0.9;
}

.producto-form-panel button:last-child {
  margin-right: 0;
}

.producto-form-panel button[type="button"] { /* Cancel button */
  background-color: #6c757d;
}

.producto-form-panel button[type="button"]:hover {
  background-color: #5a6268;
}


/* Styles for the List Panel */
.producto-list-panel {
  background: white;
  padding: 2rem;
  border-radius: 10px;
  text-align: center;
  box-shadow: 0 4px 20px rgba(0,0,0,0.2);
  width: 100%; /* Base width */
  font-size: 1rem;
  flex: 2; /* Allows list to grow more than form (ratio 2:1) */
  min-width: 400px; /* Minimum width before wrapping */
  display: flex; /* Use flexbox for internal layout */
  flex-direction: column; /* Stack contents vertically */
}

.producto-list-panel h2 {
  margin-top: 0;
  margin-bottom: 1.5rem;
  color: #333;
}

.productos-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 1rem;
  border-radius: 8px;
  overflow: hidden;
  margin-bottom: 1rem; /* Space below table, before pagination */
}

.productos-table th,
.productos-table td {
  border: 1px solid #ddd;
  padding: 0.75rem;
  text-align: left;
  vertical-align: middle; /* Vertically align content in cells */
}

.productos-table th {
  background-color: #f2f2f2;
  font-weight: bold;
  color: #555;
}

.productos-table tbody tr:nth-child(even) {
  background-color: #f9f9f9;
}

.productos-table tbody tr:hover {
  background-color: #e9e9e9;
}

/* Style for the actions cell to potentially use flex or control button layout */
.actions-cell {
  white-space: nowrap; /* Prevent buttons from wrapping */
}

/* Image miniature style */
.imagen-miniatura {
  height: 50px; /* Keep a consistent height for the row */
  width: auto; /* Allow width to adjust proportionally based on height */
  object-fit: contain; /* Scales the image down to fit within the dimensions without cropping */
  display: block; /* Treat as block element */
  margin: 0 auto; /* Horizontally center the block element */
  border-radius: 6px;
}


/* Styles for buttons inside table cells */
.productos-table td button {
  display: inline-block;
  width: auto;
  margin: 0 5px 0 0;
  padding: 0.4rem 0.8rem;
  font-size: 0.85rem;

  border-radius: 5px;
  cursor: pointer;
  border: none;
  color: white;
  transition: background-color 0.3s ease;
}

.productos-table td button:last-child {
  margin-right: 0;
}

/* Specific styles for the new "Vista" button */
.productos-table .btn-view {
  background-color: #17a2b8; /* Bootstrap info color */
}
.productos-table .btn-view:hover {
  background-color: #138496;
}

.productos-table .btn-edit {
  background-color: #ffc107;
}

.productos-table .btn-edit:hover {
  background-color: #e0a800;
}

.productos-table .btn-delete {
  background-color: #dc3545;
}

.productos-table .btn-delete:hover {
  background-color: #c82333;
}

.text-center {
  text-align: center;
}


/* Pagination Styles */
.pagination-controls {
  display: flex;
  justify-content: center; /* Center controls horizontally */
  align-items: center;
  margin-top: 1.5rem; /* Space above controls */
  gap: 1rem; /* Space between buttons and text */
}

.btn-pagination {
  padding: 0.75rem 1.5rem;
  border-radius: 8px;
  border: 1px solid #ccc;
  background-color: #f8f9fa;
  color: #333;
  cursor: pointer;
  transition: background-color 0.3s ease, border-color 0.3s ease;
  font-size: 1rem;
}

.btn-pagination:hover:not(:disabled) {
  background-color: #e9ecef;
  border-color: #bbb;
}

.btn-pagination:disabled {
  opacity: 0.5;
  cursor: not-allowed;
  background-color: #f1f1f1;
}

.pagination-controls span {
  font-weight: bold;
  color: #555;
  white-space: nowrap;
}

.modal-overlay {
  position: fixed; /* Fixed position relative to the viewport */
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.7); /* Semi-transparent dark background */
  display: flex;
  justify-content: center; /* Center content horizontally */
  align-items: center; /* Center content vertically */
  z-index: 1000; /* Ensure it's on top of other content */
}

/* The modal content box */
.modal-content {
  background: white;
  padding: 2rem;
  border-radius: 10px;
  box-shadow: 0 8px 40px rgba(0, 0, 0, 0.4);
  position: relative; /* Needed for positioning the close button */
  max-width: 500px; /* Limit the width */
  max-height: 90vh; /* Limit the height to prevent overflow, allow scrolling */
  overflow-y: auto; /* Add scroll if content exceeds max-height */
  width: 90%; /* Take up 90% of the available width */
}

.modal-content h3 {
  margin-top: 0;
  margin-bottom: 1rem;
  color: #333;
  text-align: center; /* Center the title */
}

/* Close button for the modal */
.modal-close {
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  padding: 5px;
  line-height: 1;
  color: #aaa;
  transition: color 0.2s ease;
}

.modal-close:hover {
  color: #666;
}

.detail-body {
  display: flex;
  flex-direction: column; /* Stack image and info vertically */
  align-items: center; /* Center items horizontally within the body */
  gap: 1.5rem; /* Space between image and info */
}

/* Image within the detailed view */
.detail-image {
  max-width: 100%; /* Ensure image doesn't overflow container */
  max-height: 250px; /* Limit image height */
  height: auto; /* Maintain aspect ratio */
  display: block; /* Treat as block for centering */
  margin: 0 auto; /* Center the image horizontally */
  object-fit: contain; /* Ensure entire image is visible */
  border-radius: 8px; /* Match panel styling */
  border: 1px solid #eee; /* Subtle border */
}

.no-image-placeholder {
  display: block;
  text-align: center;
  font-style: italic;
  color: #777;
  margin: 1rem 0;
}

/* Detailed information text block */
.detail-info {
  width: 100%; /* Take full width below image */
}

.detail-info p {
  margin-bottom: 0.75rem; /* Space between detail lines */
  line-height: 1.5;
  color: #555;
}

.detail-info p:last-child {
  margin-bottom: 0;
}

.detail-info strong {
  color: #333;
}


</style>