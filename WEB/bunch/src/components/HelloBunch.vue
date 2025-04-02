<template>
  <div class="container">
    <h2>Registrar Usuario</h2>
    <form @submit.prevent="insertarUsuario">
      <input type="email" v-model="email" placeholder="Email" required />
      <input type="password" v-model="password" placeholder="Contraseña" required />
      <button type="submit">Insertar</button>
    </form>
    <div v-if="success" class="success-message">
      HELLO BUNCH!!
    </div>
  </div>
</template>

<script>
import axios from "axios"; // Importamos Axios para hacer peticiones HTTP
import { API_URL } from "@/config/api.js"; // Importamos la URL de la API

export default {
  data() {
    return {
      email: "",
      password: "",
      success: false,
    };
  },
  methods: {
    async insertarUsuario() {
      try {
        console.log("Enviando datos a la API:", {
          email: this.email,
          password: this.password
        });

        const response = await axios.post(`${API_URL}/beerstar/usuarios/registro`, {
          email: this.email,
          password: this.password,
        }, {
          headers: { "Content-Type": "application/json"},
          withCredentials: true
        });

        console.log("Respuesta de la API:", response);

        // Verificar si la respuesta tiene datos en formato JSON
        if (response.status === 200 && response.data) {
          this.success = true; // Mostramos el mensaje HELLO BUNCH!!
        } else {
          console.error("La respuesta no es válida:", response);
        }
      } catch (error) {
        console.error("Error al insertar usuario:", error);

        // Si el error tiene una respuesta, la mostramos
        if (error.response) {
          console.error("Detalles del error:", error.response.data);
          alert(`Error: ${error.response.data.message || "No se pudo insertar el usuario."}`);
        } else {
          alert("Error desconocido al intentar registrar.");
        }
      }
    },
  },
};
</script>

<style scoped>
.container {
  text-align: center;
  margin-top: 50px;
}
input {
  display: block;
  margin: 10px auto;
  padding: 8px;
}
button {
  padding: 10px 20px;
  background-color: #42b983;
  color: white;
  border: none;
  cursor: pointer;
}
.success-message {
  margin-top: 20px;
  font-size: 24px;
  color: #42b983;
}
</style>
