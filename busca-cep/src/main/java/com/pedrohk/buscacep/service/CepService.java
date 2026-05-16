package com.pedrohk.buscacep.service;

import com.pedrohk.buscacep.dto.CepResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CepService {

    private final RestClient restClient;

    public CepService() {
        this.restClient = RestClient.create();
    }

    public CepResponse buscarCep(String cep) {
        String cepLimpo = cep.replaceAll("\\s+", "").replaceAll("\\D", "");

        if (cepLimpo.length() != 8) {
            return null;
        }

        String urlFinal = "https://viacep.com.br/ws/" + cepLimpo + "/json/";

        try {
            return restClient.get()
                    .uri(urlFinal)
                    .header(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .body(CepResponse.class);
        } catch (Exception e) {
            System.err.println("URL requisitada: " + urlFinal);
            System.err.println("Falha na requisicao ViaCEP: " + e.getMessage());
            return null;
        }
    }
}
