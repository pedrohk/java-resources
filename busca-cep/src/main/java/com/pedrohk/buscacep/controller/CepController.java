package com.pedrohk.buscacep.controller;

import com.pedrohk.buscacep.dto.CepResponse;
import com.pedrohk.buscacep.service.CepService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ceps")
public class CepController {

    private final CepService cepService;

    public CepController(CepService cepService) {
        this.cepService = cepService;
    }

    @GetMapping("/{cep}")
    public ResponseEntity<?> consultarCep(@PathVariable String cep) {
        try {
            CepResponse response = cepService.buscarCep(cep);

            if (response == null || response.cep() == null) {
                return ResponseEntity.status(404).body("Erro: O CEP informado nao foi encontrado no ViaCEP.");
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno na aplicacao: " + e.getMessage());
        }
    }
}
