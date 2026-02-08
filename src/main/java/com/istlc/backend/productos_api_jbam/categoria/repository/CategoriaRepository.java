package com.istlc.backend.productos_api_jbam.categoria.repository;

import com.istlc.backend.productos_api_jbam.categoria.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByNombre(String nombre);
}
