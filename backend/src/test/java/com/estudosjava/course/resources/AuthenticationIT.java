package com.estudosjava.course.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthenticationIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String loginJson;
    private String invalidLoginJson;

    @BeforeEach
    void setUp() {
        loginJson = "{\"email\":\"john.doe@email.com\", \"password\":\"password\"}";
        invalidLoginJson = "{\"email\":\"john.doe@email.com\", \"password\":\"wrongpassword\"}";
    }

    @Test
    public void loginShouldReturnTokenWhenCredentialsAreValid() throws Exception {
        mockMvc.perform(post("/auth/login")
                .content(loginJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists()); // Verifica se o campo token existe na resposta
    }

    @Test
    public void loginShouldReturnUnauthorizedWhenCredentialsAreInvalid() throws Exception {
        mockMvc.perform(post("/auth/login")
                .content(invalidLoginJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized()) // Deve retornar 401
                .andExpect(jsonPath("$.message").value("E-mail ou senha inválidos"));
    }

    @Test
    public void protectedRouteShouldReturnForbiddenWhenNoTokenIsProvided() throws Exception {
        mockMvc.perform(get("/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()); // Spring Security bloqueia sem token
    }

    @Test
    public void fullWorkflowShouldWorkCorrectly() throws Exception {
        // 1. Realiza o login para obter o token
        MvcResult result = mockMvc.perform(post("/auth/login")
                .content(loginJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseString = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseString);
        String token = jsonNode.get("token").asText();

        // 2. Usa o token para acessar uma rota protegida
        mockMvc.perform(get("/users")
                .header("Authorization", "Bearer " + token) // Insere o token no cabeçalho
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray()); // Verifica se retornou a lista de usuários
    }

    @Test
    public void insertUserShouldReturnCreatedWhenDataIsValid() throws Exception {
        String newUserJson = "{\"name\":\"Testador\", \"email\":\"testador@empresa.com\", \"phone\":\"123456789\", \"password\":\"senha123\"}";

        mockMvc.perform(post("/users")
                .content(newUserJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()) // UserResource retorna 201 Created
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Testador"));
    }

    @Test
    public void insertUserShouldReturnBadRequestWhenDataIsInvalid() throws Exception {
        // Cenário: Nome vazio e email mal formatado
        String invalidUserJson = "{\"name\":\"\", \"email\":\"email-invalido\", \"phone\":\"123\", \"password\":\"1\"}";

        mockMvc.perform(post("/users")
                .content(invalidUserJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()) 
                .andExpect(jsonPath("$.errors").isArray())
                // Verifica se o seu ResourceExceptionHandler mapeou os campos corretamente
                .andExpect(jsonPath("$.errors[?(@.fieldName=='email')]").exists())
                .andExpect(jsonPath("$.errors[?(@.fieldName=='name')]").exists());
    }

    @Test
    public void insertUserShouldReturnBadRequestWhenPasswordIsTooShort() throws Exception {
        // Cenário: Senha curta
        String shortPasswordJson = "{\"name\":\"Teste\", \"email\":\"testes@gmail.com\", \"phone\":\"123\", \"password\":\"123\"}";

        mockMvc.perform(post("/users")
                .content(shortPasswordJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[?(@.fieldName=='password')]").exists());
    }
}