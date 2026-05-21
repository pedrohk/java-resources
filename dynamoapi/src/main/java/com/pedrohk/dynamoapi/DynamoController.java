package com.pedrohk.dynamoapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class DynamoController {

    private final DynamoService dynamoService;

    public DynamoController(DynamoService dynamoService) {
        this.dynamoService = dynamoService;
    }

    @PostMapping
    public ResponseEntity<String> criarUsuario(@RequestBody Usuario usuario) {
        try {
            dynamoService.salvarUsuario(usuario);
            return ResponseEntity.ok("Registo salvo com sucesso no DynamoDB!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao salvar no DynamoDB: " + e.getMessage());
        }
    }
}
