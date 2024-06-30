package com.hutech.demo.controller;

import com.hutech.demo.model.Brand;
import com.hutech.demo.model.Product;
import com.hutech.demo.service.BrandService;
import com.hutech.demo.service.CategoryService;
import com.hutech.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.io.File;



import org.springframework.util.StringUtils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private  ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @GetMapping
    public String showProductList(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Product> products;
        if (keyword != null && !keyword.isEmpty()) {
            products = productService.searchProducts(keyword);
        } else {
            products = productService.getAllProducts();
        }

        // Set default brand for products with null brand
        for (Product product : products) {
            if (product.getBrand() == null) {
                Brand defaultBrand = new Brand();
                defaultBrand.setName("No Brand");
                product.setBrand(defaultBrand);
            }
        }

        model.addAttribute("products", products);
        return "products/products-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("brands", brandService.getAllBrand());
        return "products/add-product";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute("product") Product product, BindingResult result, @RequestParam("image") MultipartFile imageFile) {
        if (result.hasErrors()) {
            return "products/add-product";
        }
        if (!imageFile.isEmpty()) {
            try {
                String imageName = saveImageStatic(imageFile);
                product.setThumnail(imageName); // Lưu tên ảnh vào cơ sở dữ liệu
            } catch (IOException e) {
                e.printStackTrace(); // Xử lý ngoại lệ một cách thích hợp
            }
        }
        //luu nhieu hinh anh cua products
        productService.addProduct(product);
        return "redirect:/products";
    }

    private String saveImageStatic(MultipartFile image) throws IOException {
        // Đường dẫn tới thư mục lưu trữ hình ảnh trong thư mục static/images

        Path dirImages = Paths.get("target/classes/static/img");
        // Tạo thư mục nếu chưa tồn tại
        if (!Files.exists(dirImages)) {
            Files.createDirectories(dirImages);
        }

        // Tạo tên tệp duy nhất cho hình ảnh
        String imageName = UUID.randomUUID().toString() + "." + StringUtils.getFilenameExtension(image.getOriginalFilename());

        // Lưu hình ảnh vào thư mục lưu trữ

        Path pathFileUpload = dirImages.resolve(imageName);
        Files.copy(image.getInputStream(), pathFileUpload, StandardCopyOption.REPLACE_EXISTING);

        return imageName;
    }
    //----------------------------------------------------------
    // Edit 1 sản phẩm danh mục
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/products/update-product";
    }
    // cập nhật danh sách sản phẩm
    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @Valid Product product, BindingResult result, @RequestParam("image") MultipartFile imageFile) {
        if (result.hasErrors()) {
            product.setId(id);
            return "/products/update-product";
        }

        // Xóa ảnh cũ nếu có
        if (!imageFile.isEmpty()) {
            deleteOldImage(product.getThumnail());
            try {
                String imageName = saveImageStatic(imageFile);
                product.setThumnail(imageName); // Lưu tên ảnh mới vào cơ sở dữ liệu
            } catch (IOException e) {
                e.printStackTrace(); // Xử lý ngoại lệ một cách thích hợp
            }
        }

        productService.updateProduct(product);
        return "redirect:/products";
    }

    // Phương thức để xóa ảnh cũ
    private void deleteOldImage(String imageName) {
        if (imageName != null && !imageName.isEmpty()) {
            Path dirImages = Paths.get("target/classes/static/images");
            Path imagePath = dirImages.resolve(imageName); // Sử dụng resolve thay vì Paths.get
            try {
                Files.deleteIfExists(imagePath);
            } catch (IOException e) {
                e.printStackTrace(); // Xử lý ngoại lệ một cách thích hợp
            }
        }
    }
    // xóa 1 sản phẩm trong danh sách sản phẩm
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return "redirect:/products";
    }

}
