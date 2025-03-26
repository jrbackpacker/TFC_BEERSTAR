package com.tfc.beerstar.repository;

import com.tfc.beerstar.model.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
        // Buscar usuario por email
        Optional<Usuario> findByEmail(String email);

        // Verificar si existe un usuario por email
        boolean existsByEmail(String email);

        // Buscar usuarios por rol
        List<Usuario> findByRol(Usuario.RolUsuario rol);
    
        // Buscar usuarios por tipo de usuario
        List<Usuario> findByTipoUsuario(Usuario.TipoUsuario tipoUsuario);
}
