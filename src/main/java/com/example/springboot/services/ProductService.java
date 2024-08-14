package com.example.springboot.services;


import com.example.springboot.controllers.ProductController;
import com.example.springboot.dtos.ProductRecordDto;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductModel saveProduct (ProductRecordDto productRecordDto){
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return productRepository.save(productModel);
    }

    public List<ProductModel> listAllProducts(){
        List<ProductModel> productList = productRepository.findAll();
        if(!productList.isEmpty()){
            for(ProductModel productModel: productList){
                productModel.add(linkTo(methodOn(ProductController.class)
                        .getProduct(productModel.getIdProduct())).withSelfRel());
            }
        }
        return productList;
    }

    public Object getOneProduct (Long id) {
        Optional<ProductModel> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not exists");
        }
        return product;
    }

    public Object updateProduct(Long id, ProductRecordDto productRecordDto){
        Optional<ProductModel> productModel0 = productRepository.findById(id);
        if(productModel0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        var productModel = productModel0.get();
        BeanUtils.copyProperties(productRecordDto,productModel);
        return productModel;
    }

    public ResponseEntity<String> deleteProduct(Long id){
        Optional<ProductModel> productModel0 = productRepository.findById(id);
        if(productModel0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        productRepository.delete(productModel0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
    }
}
