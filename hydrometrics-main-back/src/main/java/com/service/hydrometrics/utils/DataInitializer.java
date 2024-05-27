package com.service.hydrometrics.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.hydrometrics.client.DataProcessor;
import com.service.hydrometrics.models.DB.entity.Station;
import com.service.hydrometrics.models.DB.entity.User;
import com.service.hydrometrics.models.enums.Role;
import com.service.hydrometrics.repository.StationRepository;
import com.service.hydrometrics.services.Impl.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.InputStream;
import java.time.Duration;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    @Value("${init.data.enabled}")
    private boolean initDataEnabled;
    @Value("${init.stations.enabled}")
    private boolean initStationsEnabled;
    @Value("${init.users.enabled}")
    private boolean initUsersEnabled;
    @Value("${init.random.data}")
    private boolean randomEnable;
    @Value("${data.processor.url}")
    private String processorUrl;

    private final StationRepository stationRepo;
    private final UserService userService;
    private final DataProcessor dataProcessor;
    private final WebClient.Builder webClientBuilder;

    String[] stationNames = {"Apto_Guaymaral", "Bolivia", "Bosa", "Carvajal_Sevillana", "Centro_de_Alto_Rendimiento", "Ciudad_Bolivar", "Colina", "Doña_Juana", "Fontibon", "Guaymaral", "Jazmin", "Kennedy", "Las_Ferias", "Min_Ambiente", "Movil_7ma", "Movil_Fontibon", "Puente_Aranda", "Ramada", "San_Cristobal", "Suba", "Tunal", "Usaquen", "Usme"};


    public void initUsers() {
        User admin = new User("admin", "admin", "admin", "admin", "admin@hydrometrics.com", Role.ADMIN, true);
        if (userService.getUser(admin.getUsername()) == null) {
            userService.saveUser(admin);
        }
        User auditor = new User("auditor", "auditor", "auditor", "auditor", "auditor@hydrometrics.com", Role.AUDITOR, true);
        if (userService.getUser(auditor.getUsername()) == null) {
            userService.saveUser(auditor);
        }
        User user = new User("user", "user", "user", "user", "user@hydrometrics.com", Role.USER, true);
        if (userService.getUser(user.getUsername()) == null) {
            userService.saveUser(user);
        }
    }

    public void initStations() {
        for (String stationName : stationNames) {
            if (stationRepo.findByName(stationName).isEmpty()) {
                Station station = new Station(stationName);
                stationRepo.save(station);
            }
        }
    }


    public boolean validateConnection() {
        WebClient client = webClientBuilder.build();
        try {
            client.get().uri(uriBuilder -> uriBuilder.path("/health").build())  // Cambia a tu endpoint de validación o salud
                    .retrieve().toBodilessEntity().block(Duration.ofSeconds(5)); // Tiempo de espera
            return true; // Conexión exitosa
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                System.out.println("Endpoint no encontrado");
            } else {
                System.out.println("Error en la respuesta: " + e.getStatusCode());
            }
        } catch (WebClientRequestException e) {
            System.out.println(processorUrl);
            System.out.println("Error en la conexión: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    @PostConstruct
    public void init() {
        if (initUsersEnabled) {
            initUsers();
        }
        if (initStationsEnabled) {
            initStations();
        }
        String json = "JSON/datos_unificados_short.json";
        if (randomEnable) {
            json = "JSON/weather_data_random.json";
        }


        if (initDataEnabled) {
            System.out.println("Initializing data of WeatherData...");
            ObjectMapper objectMapper = new ObjectMapper();
            try (InputStream inputStream = new ClassPathResource(json).getInputStream()) {
                List<WeatherDataMapper> weatherDataMappers = objectMapper.readValue(inputStream, new TypeReference<List<WeatherDataMapper>>() {
                });
                boolean conectado = false;
                while (!conectado) {
                    conectado = validateConnection();
                }
                dataProcessor.sendWeatherDataMappers(weatherDataMappers, "application");
                System.out.println("End data Initialization of WeatherData...");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}