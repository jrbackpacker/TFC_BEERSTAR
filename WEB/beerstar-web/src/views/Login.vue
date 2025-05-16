// views/Login.vue
<template>
  <div class="login-container">
    <div class="login-box">
      <h2>Login</h2>
      <form @submit.prevent="login">
        <input v-model="email" type="email" placeholder="Email" required />
        <input v-model="password" type="password" placeholder="Contraseña" required />
        <button type="submit">Entrar</button>
      </form>
    </div>
  </div>
</template>

<script>
import { loginUser } from '@/services/api.js';

export default {
  name: 'Inicie sesión',
  data() {
    return {
      email: '',
      password: '',
    };
  },
  methods: {
    async login() {
      try {
        const data = await loginUser(this.email, this.password);

        // Guardar lo que sí usas
        localStorage.setItem('token', data.token);
        localStorage.setItem('tipoUsuario', data.tipoUsuario);
        localStorage.setItem('email', data.email);

        // Redirigir al panel principal
        this.$router.push('/gestion');
      } catch (error) {
        alert('Credenciales incorrectas');
        console.error(error);
      }
    }
  }
};
</script>


<style scoped>
.login-container {
  background-image: url('@/assets/wall.jpg');
  background-position: center;
  background-size: cover;
  background-repeat: no-repeat;
  background-attachment:fixed;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}
.login-box {
  background: transparent;
  padding: 2rem;
  border-radius: 12px;
 text-align: center;
  width: 100%;
  max-width: 400px;
}
input, button {
  width: 100%;
  margin: 0.5rem 0;
  padding: 0.75rem;
  border: 1px solid #ccc;
  border-radius: 8px;
}
button {
  background-color: #333;
  color: white;

  cursor: pointer;
  font-weight: bold;
}
button:hover {
  background-color: #555;
}
</style>