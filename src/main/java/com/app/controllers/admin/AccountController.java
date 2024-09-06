package com.app.controllers.admin;

import com.app.dtos.UserInfoDTO;
import com.app.dtos.UserRegistrationDTO;
import com.app.models.User;
import com.app.services.UserService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    @Autowired
    UserService userService;

    @GetMapping
    public String index(Model model,
                            @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<User> userPage = userService.findPaginatedUsers(pageable);
        int totalPages = userPage.getTotalPages();
        model.addAttribute("userPage", userPage);
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "admin/users/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("userDTO", new UserRegistrationDTO());
        return "admin/users/add-user";
    }

    @PostMapping("/")
    public String saveUser(@ModelAttribute("userDTO") UserRegistrationDTO userDTO, RedirectAttributes redirectAttributes) {
        userService.saveUser(userDTO);
        redirectAttributes.addFlashAttribute("message", "User created successfully!");
        return "redirect:/admin/users";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("userDTO", userService.convertToDTO(user));
        return "admin/users/edit-user";
    }

    @PatchMapping("/{id}/update")
    public String updateUser(@PathVariable("id") Integer id, @ModelAttribute("userDTO") UserInfoDTO userDTO, RedirectAttributes redirectAttributes) {
        userService.updateUser(id, userDTO);
        redirectAttributes.addFlashAttribute("message", "User updated successfully!");
        return "redirect:/admin/users";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        userService.softDeleteUser(id);
        redirectAttributes.addFlashAttribute("message", "User deleted successfully!");
        return "redirect:/admin/users";
    }
}
