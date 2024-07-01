package com.hutech.demo.controller;

import com.hutech.demo.model.User;
import com.hutech.demo.model.UserRegistrationDto;
import com.hutech.demo.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "users/login";
    }

    @GetMapping("/register")
    public String register(@NotNull Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "users/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserRegistrationDto userDto,
        BindingResult bindingResult,
        @RequestParam("avatar") MultipartFile avatarFile,
        Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "users/register";
        }
        if (!avatarFile.isEmpty()) {
            try {
                String avatarName = saveImage(avatarFile);
                userDto.setAvatar(avatarFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        User user = new User();
        // Copy data from DTO to User entity
        BeanUtils.copyProperties(userDto, user);
        // Set avatar field in User
        user.setAvatar(userDto.getAvatar().getOriginalFilename());
        userService.save(user);
        userService.setDefaultRole(user.getUsername());
        return "redirect:/login";
    }

    private String saveImage(MultipartFile image) throws IOException {
        Path dirImages = Paths.get("target/classes/static/images");
        if (!Files.exists(dirImages)) {
            Files.createDirectories(dirImages);
        }
        String imageName = UUID.randomUUID().toString() + "." + image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf(".") + 1);
        Path pathFileUpload = dirImages.resolve(imageName);
        Files.copy(image.getInputStream(), pathFileUpload, StandardCopyOption.REPLACE_EXISTING);
        return imageName;
    }

    @GetMapping("/profile")
    public String getProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userService.findByUsername(username);
        String avatarUrl = "/images/" + user.get().getAvatar();

        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);

        return "profile";
    }

    @GetMapping("/user-info")
    public String getUserInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userService.findByUsername(username);
        String avatarUrl = "/images/" + user.get().getAvatar();

        model.addAttribute("username", user.get().getUsername());
        model.addAttribute("avatarUrl", avatarUrl);
        model.addAttribute("email", user.get().getEmail());

        return "user-info";
    }
}