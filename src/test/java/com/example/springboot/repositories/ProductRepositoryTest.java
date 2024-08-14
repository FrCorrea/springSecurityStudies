package com.example.springboot.repositories;

import com.example.springboot.dtos.ProductRecordDto;
import com.example.springboot.models.ProductModel;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;


@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get Product sucessfly from db")
    void findProductByIdSucess(){
        ProductRecordDto productRecordDto = new ProductRecordDto("Produto 1", new BigDecimal("16000.00"));
        ProductModel productModel = this.createProduct(productRecordDto);

        Optional<ProductModel> founded = this.productRepository.findById(productModel.getIdProduct());

        assertThat(founded.isPresent()).isTrue();
    }

    private ProductModel createProduct(ProductRecordDto dto){
        var productModels = new ProductModel();
        BeanUtils.copyProperties(dto, productModels);
        this.entityManager.persist(productModels);
        return productModels;
    }
}