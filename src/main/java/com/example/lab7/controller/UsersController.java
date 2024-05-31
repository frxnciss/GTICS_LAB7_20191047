package com.example.lab7.controller;

import com.example.lab7.entity.Resources;
import com.example.lab7.entity.Users;
import com.example.lab7.repository.ResourcesRepository;
import com.example.lab7.repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UsersController {

    final UsersRepository usersRepository;

    public UsersController(UsersRepository usersRepository, ResourcesRepository resourcesRepository) {
        this.usersRepository = usersRepository;
        this.resourcesRepository = resourcesRepository;
    }
    final ResourcesRepository resourcesRepository;

    //CREAR
    @PostMapping("")
    public ResponseEntity<HashMap<String, Object>> guardaUser(
            @RequestBody Users users,
            @RequestParam(value = "fetchId", required = false) boolean fetchId) {

        HashMap<String, Object> responseJson = new HashMap<>();

        usersRepository.save(users);
        if(fetchId){
            responseJson.put("id",users.getId());
        }
        responseJson.put("estado","creado");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
    }

    @GetMapping(value = {"/list", ""})
    public List<Users> listaUsers() {
        return usersRepository.findAll();
    }


    //BORRAR
    @DeleteMapping(value = "/product/{id}")
    public ResponseEntity<HashMap<String, Object>> borrarUser(@PathVariable("id") String idStr) {

        HashMap<String, Object> responseMap = new HashMap<>();

        try {
            int id = Integer.parseInt(idStr);
            if (usersRepository.existsById(id)) {
                usersRepository.deleteById(id);
                responseMap.put("estado", "borrado exitoso");
                return ResponseEntity.ok(responseMap);
            } else {
                responseMap.put("estado", "error");
                responseMap.put("msg", "no se encontró el usuario con id: " + id);
                return ResponseEntity.badRequest().body(responseMap);
            }
        } catch (NumberFormatException ex) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "El ID debe ser un número");
            return ResponseEntity.badRequest().body(responseMap);
        }
    }



    //ACTUALIZAR

    //ERROR
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String, String>> gestionException(HttpServletRequest request) {
        HashMap<String, String> responseMap = new HashMap<>();
        if (request.getMethod().equals("POST") || request.getMethod().equals("PUT")) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "Debe enviar datos");
        }
        return ResponseEntity.badRequest().body(responseMap);
    }

}

