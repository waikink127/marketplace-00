package com.myexample.demofullstack.service;

import com.myexample.demofullstack.exception.GeneralNotFoundException;
import com.myexample.demofullstack.model.AppUser;
import com.myexample.demofullstack.model.Product;
import com.myexample.demofullstack.repository.ProductRepository;
import com.myexample.demofullstack.s3.S3BucketName;
import com.myexample.demofullstack.s3.S3FileStore;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private S3FileStore fileStore;


    public Product createProduct(Product product, MultipartFile file, String username){
        product.setIdentifier(generateId());
        product.setOwner(userService.getUser(username));
        product.setOwnerActive(true);
        product.setOwnerName(username);
        if(file != null)uploadProductImage(product, file);
        return productRepository.save(product);
    }

    public List<Product> findByCategory(String category){
        List<Product> ps = productRepository.findAllByCategoryAndOwnerActive(category,true).orElseThrow(() -> new RuntimeException(("Not found")));
        return ps.stream().map(this::setOwnerName).collect(Collectors.toList());
    }


    public Product findByIdentifier(String id){
        Product p = productRepository.findByIdentifierAndOwnerActive(id, true)
                .orElseThrow(() -> new GeneralNotFoundException(String.format("Product not found: %s", id)));
        return setOwnerName(p);
    }

    // TODO create exception
    public Product updateProduct(String identifier, Product product, MultipartFile file, String username){
        Product toBeUpdate = findByIdentifier(identifier);
        if(!toBeUpdate.getOwnerName().equals(username)) throw new RuntimeException("Not eligible");
        toBeUpdate.setName(product.getName());
        toBeUpdate.setCategory(product.getCategory());
        toBeUpdate.setDescription(product.getDescription());
        if(file != null)uploadProductImage(toBeUpdate, file);
        return productRepository.save(toBeUpdate);
    }

    public String deleteProduct(String identifier, String username){
        Product toBeDelete = findByIdentifier(identifier);
        if(!toBeDelete.getOwnerName().equals(username)) throw new RuntimeException("no auth to delete this");
        productRepository.delete(toBeDelete);
        return String.format("Deleted product: %s", toBeDelete.getName());
    }

    public Product setBuyProduct(String productId, String username){
        Product product = findByIdentifier(productId);
        AppUser user = userService.getUser(username);
        product.addBuyer(user);
        return productRepository.save(product);
    }

    public List<Product> findAllAvailableProduct(){
        List<Product> products = productRepository.findAllByOwnerActive(true)
                .orElseThrow(() -> new GeneralNotFoundException("No product available"));
        return products.stream().map(this::setOwnerName).collect(Collectors.toList());
    }

    private String generateId(){
        String id = RandomString.make(8);
        try{
            Product p = findByIdentifier(id);
            if(p != null) return generateId();
            return id;
        } catch (Exception e){
            return id;
        }
    }
    private Product setOwnerName(Product p){
        p.setOwnerName(p.getOwner().getUsername());
        return p;
    }

    private void uploadProductImage(Product product, MultipartFile file) {
        Map<String, String> metadata = getFileMetadata(file);

        String path = String.format("%s/%s/%s", S3BucketName.PROFILE_IMAGE.getBucketName(), product.getOwnerName(), product.getIdentifier());
        String fileName = file.getOriginalFilename();
        String savedPath = String.format("%s/%s/%s", product.getOwnerName(), product.getIdentifier(),fileName);
        try {
            fileStore.save(path, fileName, Optional.of(metadata), file.getInputStream());
            product.setImageUrl(String.format("https://%s.s3-ap-southeast-1.amazonaws.com/%s", S3BucketName.PROFILE_IMAGE.getBucketName(), savedPath));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
    private Map<String, String> getFileMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }
}
