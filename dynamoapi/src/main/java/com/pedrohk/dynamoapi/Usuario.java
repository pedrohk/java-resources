package com.pedrohk.dynamoapi;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;

@DynamoDbBean
public class Usuario {

    private String aula7;
    private String name;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("aula7")
    public String getAula7() {
        return aula7;
    }

    public void setAula7(String aula7) {
        this.aula7 = aula7;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
