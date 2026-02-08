package com.istlc.backend.productos_api_jbam.producto.repository;

import com.istlc.backend.productos_api_jbam.producto.entity.producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<producto, Long>{
}
