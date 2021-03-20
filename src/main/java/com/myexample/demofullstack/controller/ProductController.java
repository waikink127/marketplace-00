package com.myexample.demofullstack.controller;

import com.myexample.demofullstack.dto.EditProductRequestDto;
import com.myexample.demofullstack.model.Product;
import com.myexample.demofullstack.service.FormValidationService;
import com.myexample.demofullstack.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private FormValidationService formValidationService;


    @GetMapping("/p/{identifier}")
    public ResponseEntity<?> findProductByIdentifier(@PathVariable String identifier) {
        Product p = productService.findByIdentifier(identifier);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @GetMapping("/category/{cat}")
    public ResponseEntity<?> findProductByCategory(@PathVariable String cat) {
        List<Product> products = productService.findByCategory(cat);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAllProducts() {
        List<Product> products = productService.findAllAvailableProduct();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    // TODO set auth Principal
    @DeleteMapping("/{identifier}")
    public ResponseEntity<String> deleteProductByIdentity(@PathVariable String identifier, Principal principal) {
        String res = productService.deleteProduct(identifier, principal.getName());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/p/{identifier}")
    public ResponseEntity<?> updateProduct(@PathVariable("identifier") String identifier,
                                           @RequestParam("name") @NotBlank @Size(min = 1, max = 50) String name,
                                           @RequestParam("description") @NotBlank @Size(min = 10, max = 100) String description,
                                           @RequestParam("price") @NotNull @Min(value = 1) int price,
                                           @RequestParam("category") String category,
                                           @RequestParam(value = "image", required = false) MultipartFile file, Principal principal) {
        Product product = new Product(name, description, price, category);
        Product updatedProduct = productService.updateProduct(identifier, product, file, principal.getName());
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }


    @PatchMapping("/p/{identifier}")
    public ResponseEntity<?> buyProduct(@PathVariable String identifier, Principal principal) {
        Product product = productService.setBuyProduct(identifier, principal.getName());
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> createProduct(@RequestParam("name") @NotBlank @Size(min = 1, max = 50) String name,
                                           @RequestParam("description") @NotBlank @Size(min = 10, max = 100) String description,
                                           @RequestParam("price") @NotNull @Min(value = 1) int price,
                                           @RequestParam("category") String category,
                                           @RequestParam(value = "image", required = false) MultipartFile file, Principal principal) {
        Product product = new Product(name, description, price, category);
        Product created = productService.createProduct(product, file, principal.getName());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }


}
