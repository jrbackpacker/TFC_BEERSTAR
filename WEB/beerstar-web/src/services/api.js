import axios from 'axios';

// Crear instancia de Axios
const api = axios.create({
    baseURL: 'https://java-railway-backend-beerstar-production.up.railway.app', // URL base del backend
    headers: {
        'Content-Type': 'application/json',
    },
});

// Obtener todos los artículos
export const getArticulos = async () => {
    try {
        const response = await api.get('/beerstar/articulos');
        return response.data;
    } catch (error) {
        console.error('Error al obtener artículos:', error);
        throw error;
    }
};

// Iniciar sesión
export const loginUser = async (email, password) => {
    try {
        // Usar la instancia de axios configurada para hacer el POST
        const response = await api.post('/beerstar/auth/login', {
            email,
            password
        });
        return response.data; // Contiene token, tipoUsuario, etc.
    } catch (error) {
        console.error('Error al hacer login:', error);
        throw error;
    }
};

// Registrar un nuevo artículo
export const registrarArticulo = async (articulo) => {
    try {
        const response = await api.post('/beerstar/articulos', articulo);
        return response.data;
    } catch (error) {
        console.error('Error al registrar artículo:', error);
        throw error;
    }
};

// Eliminar un artículo
export const eliminarArticulo = async (id) => {
    try {
        const response = await api.delete(`/beerstar/articulos/${id}`);
        return response.data;
    } catch (error) {
        console.error('Error al eliminar artículo:', error);
        throw error;
    }
};

// Actualizar un artículo
export const actualizarArticulo = async (id, articulo) => {
    try {
        const response = await api.put(`/beerstar/articulos/${id}`, articulo);
        return response.data;
    } catch (error) {
        console.error('Error al actualizar artículo:', error);
        throw error;
    }
};

export default api;
