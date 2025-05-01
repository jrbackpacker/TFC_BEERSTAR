package com.tfc.beerstar.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfc.beerstar.dto.request.ArticulosRequestDTO;
import com.tfc.beerstar.dto.response.ArticulosResponseDTO;
import com.tfc.beerstar.exception.ResourceNotFoundException;
import com.tfc.beerstar.model.Articulos;
import com.tfc.beerstar.model.Categorias;
import com.tfc.beerstar.repository.ArticulosRepository;
import com.tfc.beerstar.repository.CategoriasRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Servicio que encapsula la lógica de negocio para la gestión de artículos.
 *
 * <p>
 * Permite crear, obtener, actualizar y eliminar artículos, así como mapear
 * entre entidades JPA y DTOs de petición/respuesta.</p>
 *
 * <p>
 * Utiliza repositorios para acceder a la base de datos y lanza excepciones
 * específicas cuando no se encuentran recursos.</p>
 *
 * @author TuNombre
 */
@Slf4j
@Service
public class ArticuloService {

    @Autowired
    private ArticulosRepository articuloRepository;

    @Autowired
    private CategoriasRepository categoriasRepository;

    /**
     * Crea un nuevo artículo en la base de datos.
     * 
     * @param dto DTO con los datos de petición para crear un artículo.
     * @return DTO de respuesta con los datos del artículo recién creado.
     * @throws ResourceNotFoundException si la categoría indicada no existe.
     */
    public ArticulosResponseDTO crearArticulos(ArticulosRequestDTO dto) {
        log.info("Creando artículo: {}", dto.getNombre());

        // Mapear DTO a entidad
        Articulos articulo = new Articulos();
        articulo.setNombre(dto.getNombre());
        articulo.setDescripcion(dto.getDescripcion());
        articulo.setPrecio(dto.getPrecio());
        articulo.setStock(dto.getStock());
        articulo.setGraduacion(dto.getGraduacion());
        articulo.setUrl(dto.getUrl());

        // Buscar y asociar categoría
        Categorias categoria = categoriasRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> {
                    log.error("Categoría no encontrada para el id: {}", dto.getIdCategoria());
                    return new ResourceNotFoundException("Categoría no encontrada");
                });

        articulo.setCategoria(categoria);

        // Guardar y mapear a DTO de respuesta
        Articulos guardado = articuloRepository.save(articulo);
        log.info("Artículo creado con éxito, id: {}", guardado.getIdArticulo());
        return mapearResponseDTO(guardado);
    }


    /**
     * Obtiene un artículo por su id.
     * 
     * @param id ID del artículo a buscar.
     * @return DTO de respuesta con los datos del artículo encontrado.
     * @throws ResourceNotFoundException si no existe un artículo con ese ID.
     */
    public ArticulosResponseDTO obtenerArticuloPorId(Long id) {
        log.info("Obteniendo artículo por id: {}", id);
        Articulos articulo = articuloRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Artículo no encontrado para el id: {}", id);
                    return new ResourceNotFoundException("Artículo no encontrado");
                });
        return mapearResponseDTO(articulo);
    }

    /**
     * Obtiene todos los artículos almacenados.
     * 
     * @return Lista de DTOs de respuesta con todos los artículos.
     */
    public List<ArticulosResponseDTO> obtenerTodosLosArticulos() {
        log.info("Obteniendo todos los artículos");
        List<Articulos> articulos = articuloRepository.findAll();
        log.debug("Cantidad de artículos encontrados: {}", articulos.size());
        return articulos.stream()
                .map(this::mapearResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Actualiza un artículo existente.
     * 
     * @param id  ID del artículo a actualizar.
     * @param dto DTO con los nuevos datos del artículo.
     * @return DTO de respuesta con los datos actualizados.
     * @throws ResourceNotFoundException si el artículo o la categoría no existen.
     */
    public ArticulosResponseDTO actualizarArticulo(Long id, ArticulosRequestDTO dto) {
        log.info("Actualizando artículo id: {}", id);

        // Buscar artículo por ID
        Articulos articulo = articuloRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Artículo no encontrado para actualizar, id: {}", id);
                    return new ResourceNotFoundException("Artículo no encontrado");
                });

        // Actualizar campos del artículo
        articulo.setNombre(dto.getNombre());
        articulo.setDescripcion(dto.getDescripcion());
        articulo.setPrecio(dto.getPrecio());
        articulo.setStock(dto.getStock());
        articulo.setGraduacion(dto.getGraduacion());
        articulo.setUrl(dto.getUrl());

        // Buscar y asociar categoría
        Categorias categoria = categoriasRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        articulo.setCategoria(categoria);

        // Guardar artículo actualizado y mapear a DTO de respuesta
        Articulos actualizado = articuloRepository.save(articulo);
        return mapearResponseDTO(actualizado);
    }

    /**
     * Elimina un artículo por su ID.
     * 
     * @param id ID del artículo a eliminar.
     * @throws ResourceNotFoundException si no existe un artículo con ese ID.
     */
    public void eliminarArticulo(Long id) {
        log.info("Eliminando artículo id: {}", id);
        Articulos articulo = articuloRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Artículo no encontrado para eliminar, id: {}", id);
                    return new ResourceNotFoundException("Artículo no encontrado");
                });
        articuloRepository.delete(articulo);
        log.info("Artículo eliminado, id: {}", id);
    }

    /**
     * Mapea una entidad {@link Articulos} a su correspondiente {@link ArticulosResponseDTO}.
     * 
     * @param articulo Entidad JPA de artículo.
     * @return DTO de respuesta con los datos mapeados.
     */
    private ArticulosResponseDTO mapearResponseDTO(Articulos articulo) {
        ArticulosResponseDTO dto = new ArticulosResponseDTO();
        dto.setIdArticulo(articulo.getIdArticulo());
        dto.setNombre(articulo.getNombre());
        dto.setDescripcion(articulo.getDescripcion());
        dto.setPrecio(articulo.getPrecio());
        dto.setStock(articulo.getStock());
        dto.setGraduacion(articulo.getGraduacion());
        dto.setUrl(articulo.getUrl());
        if (articulo.getCategoria() != null) {
            dto.setIdCategoria(articulo.getCategoria().getIdCategoria());
            dto.setNombreCategoria(articulo.getCategoria().getNombre());
            dto.setDescripcion(articulo.getCategoria().getDescripcion());
        }
        return dto;
    }

}
