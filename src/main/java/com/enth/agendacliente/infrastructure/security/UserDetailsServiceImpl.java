package com.enth.agendacliente.infrastructure.security;


import com.enth.agendacliente.business.dto.out.ClienteResponseDTO;
import com.enth.agendacliente.infrastructure.security.client.ClienteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl {

    @Autowired
    private ClienteClient client;
    public UserDetails carregaDadosUsuario(String email, String token){

        ClienteResponseDTO clienteDTO = client.buscarPorEmail(email, token);
        return User
                .withUsername(clienteDTO.getEmail()) // Define o nome de usuário como o e-mail
                .password(clienteDTO.getSenha()) // Define a senha do usuário
                .build();
    }
}
