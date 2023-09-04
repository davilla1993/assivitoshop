package com.gbossoufolly.assivitoshopbackend.repository;

import com.gbossoufolly.assivitoshopbackend.models.Product;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ListCrudRepository<Product, Long> {
}
