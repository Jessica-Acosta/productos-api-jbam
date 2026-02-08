package com.istlc.backend.productos_api_jbam.producto.service;

import com.istlc.backend.productos_api_jbam.categoria.entity.Categoria;
import com.istlc.backend.productos_api_jbam.categoria.repository.CategoriaRepository;
import com.istlc.backend.productos_api_jbam.producto.dto.ProductoCreateDto;
import com.istlc.backend.productos_api_jbam.producto.dto.ProductoDto;
import com.istlc.backend.productos_api_jbam.producto.entity.producto;
import com.istlc.backend.productos_api_jbam.producto.exception.ResourceNotFoundException;
import com.istlc.backend.productos_api_jbam.producto.repository.ProductoRepository;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private final ProductoRepository repository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository repository, CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public ProductoDto crear(ProductoCreateDto dto) {
        producto p = new producto();
        p.setNombre(dto.getNombre());
        p.setDescripcion(dto.getDescripcion());
        p.setPrecio(dto.getPrecio());
        p.setActivo(dto.getActivo());

        if (dto.getCategorias() != null) {
            Set<Categoria> categorias = dto.getCategorias().stream()
                    .map(nombre -> categoriaRepository.findByNombre(nombre)
                            .orElseGet(() -> categoriaRepository.save(new Categoria(nombre))))
                    .collect(Collectors.toSet());
            p.setCategorias(categorias);
        }

        return toDto(repository.save(p));
    }

    public List<ProductoDto> listarTodo() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ProductoDto obtenerPorId(Long id) {
        return repository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("El producto con id " + id + " no existe!!"));
    }

    public ProductoDto toDto(producto p) {
        if (p == null) return null;
        ProductoDto dto = new ProductoDto();
        dto.setId(p.getId());
        dto.setNombre(p.getNombre());
        dto.setDescripcion(p.getDescripcion());
        dto.setPrecio(p.getPrecio());
        dto.setActivo(p.getActivo());

        Set<String> nombresCategorias = p.getCategorias().stream()
                .map(Categoria::getNombre)
                .collect(Collectors.toSet());
        dto.setCategorias(nombresCategorias);

        return dto;
    }
}
