<template>
  <div class="form-container">
    <h2>Insertar Usuario</h2>
    <form @submit.prevent="insertarUsuario">
      <label for="email">Email:</label>
      <input id="email" v-model="email" type="email" required />

      <label for="password">Contraseña:</label>
      <input id="password" v-model="password" type="password" required />

      <button type="submit">Insertar</button>
    </form>
  </div>
</template>

<script>
import axios from "axios";
import confetti from "canvas-confetti";
import { API_URL } from "@/config/api.js"; // Usamos la URL centralizada

export default {
  data() {
    return {
      email: "",
      password: ""
    };
  },
  methods: {
    async insertarUsuario() {
      try {
        console.log("Enviando solicitud a:", API_URL);
        console.log("Datos enviados:", { email: this.email, password: this.password });

        const respuesta = await axios.post(`${API_URL}/beerstar/usuarios/registro`, {
          email: this.email,
          password: this.password
        }, {
          headers: { "Content-Type": "application/json" },
          withCredentials: true
        });

        console.log("Respuesta completa de la API:", respuesta);

        if (respuesta.status === 200) {
          alert("HELLO BUNCH!!");
          confetti({
            particleCount: 150,
            spread: 90,
            origin: { y: 0.6 },
            colors: ["#bb0000", "#ffffff"]
          });
        }
      } catch (error) {
        console.error("Error al insertar usuario:", error);
        console.error("Detalles del error del servidor:", error.response?.data || "No se recibió respuesta JSON");
        alert(`Error: ${error.response?.data?.message || "No se pudo insertar el usuario."}`);
      }
    }
  }
};
</script>

<style scoped>
.form-container {
  max-width: 300px;
  margin: auto;
  padding: 20px;
  border: 1px solid #ffffff;
  border-radius: 10px;
  box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
}
label {
  display: block;
  margin-top: 10px;
}
input {
  width: 100%;
  padding: 8px;
  margin-top: 5px;
  border: 1px solid #ffffff;
  border-radius: 5px;
}
button {
  margin-top: 15px;
  padding: 10px;
  width: 100%;
  background-color: #42b983;
  color: #0c0c0c;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
button:hover {
  background-color: #369d7a;
}
</style>