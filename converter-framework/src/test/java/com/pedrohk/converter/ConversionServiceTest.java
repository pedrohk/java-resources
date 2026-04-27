package com.pedrohk.converter;

import com.pedrohk.converter.core.ConversionService;
import com.pedrohk.converter.core.TypeConverter;
import com.pedrohk.converter.example.UserConverter;
import com.pedrohk.converter.example.UserDTO;
import com.pedrohk.converter.example.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConversionServiceTest {
    private ConversionService service;

    @BeforeEach
    void setup() {
        service = new ConversionService();
        service.register(new UserConverter());
    }

    @Test
    void shouldConvertUserEntityToDTO() {
        UserEntity entity = new UserEntity(1L, "Pedro", "Henrique", "pedro@test.com");

        UserDTO dto = service.convert(entity, UserDTO.class);

        assertNotNull(dto);
        assertEquals("Pedro Henrique", dto.fullName());
        assertEquals("pedro@test.com", dto.contact());
    }

    @Test
    void shouldThrowExceptionWhenNoConverterExists() {
        assertThrows(IllegalArgumentException.class, () ->
                service.convert("Raw String", Integer.class)
        );
    }

    @Test
    void shouldReturnNullOnNullInput() {
        assertNull(service.convert(null, UserDTO.class));
    }

    @Test
    void shouldHandleCustomComplexConversion() {
        service.register(new TypeConverter<String, String[]>() {
            @Override
            public String[] convert(String source) {
                return source.split(",");
            }

            @Override
            public Class<String> getSourceType() {
                return String.class;
            }

            @Override
            public Class<String[]> getTargetType() {
                return String[].class;
            }
        });

        String[] result = service.convert("a,b,c", String[].class);
        assertEquals(3, result.length);
        assertEquals("b", result[1]);
    }
}
