package com.punith.ecom.backend.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.punith.ecom.backend.model.Product;
import com.punith.ecom.backend.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;
    
    public List<Product> getAllProducts(){
        return repository.findAll();
    }

    public Product getProductById(int prodId){
        return repository.findById(prodId).orElse(new Product());
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException{
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return repository.save(product);
    }

    public Product updateProduct(int prodId, Product product, MultipartFile imageFile) throws IOException{
        Product existingProduct = repository.findById(prodId).orElse(null);
        if(existingProduct == null ) return null;
        existingProduct.setProdName(product.getProdName());
        existingProduct.setBrand(product.getBrand());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setAvailable(product.isAvailable());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setReleaseDate(product.getReleaseDate());

         if (imageFile != null && !imageFile.isEmpty()) {
            existingProduct.setImageData(imageFile.getBytes());
            existingProduct.setImageName(imageFile.getOriginalFilename());
            existingProduct.setImageType(imageFile.getContentType());
        }

        return repository.save(existingProduct);
    }

    public void deleteProduct(int prodId){
        repository.deleteById(prodId);
    }

    public List<Product> searchProducts(String keyword){
        return repository.searchProducts(keyword);
    }
}
