package com.pedrohk.dynamoapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Service
public class DynamoService {

    private final DynamoDbTable<Usuario> usuarioTable;

    public DynamoService(DynamoDbEnhancedClient enhancedClient,
                         @Value("${aws.dynamodb.table-name}") String tableName) {
        this.usuarioTable = enhancedClient.table(tableName, TableSchema.fromBean(Usuario.class));
    }

    public void salvarUsuario(Usuario usuario) {
        usuarioTable.putItem(usuario);
    }
}
