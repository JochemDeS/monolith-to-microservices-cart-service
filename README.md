# Cart Service

De Cart Service is een microservice die verantwoordelijk is voor het beheer van winkelmanden binnen de webshop. Deze service biedt API-endpoints voor het aanmaken, beheren en opvragen van winkelmanden, evenals het toevoegen en verwijderen van producten in een winkelmand.

## Functionaliteiten

- **Winkelmand aanmaken**
- **Producten toevoegen aan een winkelmand**
- **Producten verwijderen uit een winkelmand**
- **Winkelmand opvragen**
- **Winkelmand legen**

## API Spec

De API-specificatie is te bekijken via de Swagger UI. Start de applicatie en navigeer 
naar [http://localhost:8083/swagger-ui/index.html](http://localhost:8083/swagger-ui/index.html).

Voor het gebruik van de endpoints is authenticatie vereist (Bearer token). Je kunt een bearer token bemachtigen door een 
gebruiker te registreren via de User Service en daarna in te loggen.

## Implementeren microservice

De implementatie van deze microservice volgt dezelfde stappen als bij de Product en User Service.

- **Kopiëren bestaande cart functionaliteiten**
- **Applicatie configuratie**: deze applicatie draait op poort `8083`.
- **Database configuratie**: database draait op poort `5435`.
- **Proxy/API-Gateway configuratie**

### Database configuratie

De Cart Service maakt gebruik van een eigen database. Zorg ervoor dat deze database op een andere poort draait dan de databases van andere services.

1. **Database aanmaken:** Kopieer het `docker-compose.yml` bestand van de andere services en pas de poort aan naar **5435**.
2. **Configuratie:** Zorg ervoor dat de databaseconfiguratie in het `application.yml` bestand overeenkomt met de nieuwe database-instellingen.
3. **Docker:** Start Docker Desktop en voer het volgende commando uit in de root van het project:
    ```shell
    docker compose up -d
    ```
4. **Database schema's migreren:** Kopieer de Flyway-migratiebestanden vanuit de monolithische applicatie naar de `src/main/resources/db/migration` map van de Cart Service.
5. **Schema's toepassen:** Start de applicatie om de schema's automatisch aan de nieuwe database toe te voegen.

### Inter-servicecommunicatie opzetten

#### User service: winkelmand aanmaken

De Cart Service moet kunnen communiceren met de User Service om winkelmanden te koppelen aan gebruikers.

Hiervoor heb ik een extra endpoint toegevoegd aan de Cart Service om een winkelmand aan te maken voor een gebruiker. 
Dit kan worden aangeroepen door de User Service op het moment dat een nieuwe gebruiker zich registreert.

De User Service stuurt een POST-request naar `/api/cart` met een gebruikers-ID als parameter. Dit zorgt ervoor dat er 
automatisch een nieuwe winkelmand wordt aangemaakt voor de gebruiker.

In de Cart Service hebben we een endpoint toegevoegd dat een nieuwe winkelmand creëert voor een gebruiker. Hieronder is een 
voorbeeld van hoe dit is geïmplementeerd in `CartController.java`:

```java
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/create")
    public ResponseEntity<CartId> createCart(@RequestBody CreateCartRequest request) {
        CartId cartId = cartService.createCart(request.getUserId());
        return new ResponseEntity<>(cartId, HttpStatus.CREATED);
    }
}
```

#### Commmuniceren met Product service

De Cart Service moet ook kunnen communiceren met de Product Service om productinformatie op te halen en toe te voegen aan een winkelmand.

Deze code kun je vinden in `src/main/java/com/example/cartservice/application/product/ProductService.java`:

```java
    @Override
    public List<Product> byIdsIn(List<ProductId> ids) {
        final var idsQueryParam = ids.stream()
                .map(id -> String.valueOf(id.value()))
                .collect(Collectors.joining(","));

        final var response = restTemplate.getForEntity(
                String.format("%s/products?ids=%s", productServiceUrl, idsQueryParam),
                ProductResponse[].class);

        return Arrays.stream(Objects.requireNonNull(response.getBody()))
                .map(this::toProduct)
                .collect(Collectors.toList());
    }
```

#### Authenticatie

De Cart Service moet ook kunnen communiceren met de User Service om bearer tokens te valideren. Alle cart endpoints vereisen een 
geldige bearer token.

Dit kan via het endpoint http://localhost:8082/api/auth/me waarbij we een GET-request sturen met een Bearer token in de header. 
Deze code vind je terug in src/main/java/com/example/cartservice/application/user/UserService.java.

Vervolgens moeten we de JWTRequestFilter updaten om bij ieder request naar de Cart Service de bearer token te valideren:

```java
@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    private final GetUser getUser;

    public JWTRequestFilter(GetUser getUser) {
        this.getUser = getUser;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7);
            getUser.byToken(token).ifPresent(user -> {
                var authentication = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            });
        }
        filterChain.doFilter(request, response);
    }
}
```

### Microservice code refactoren

De Cart Service beheert nu zelfstandig de winkelmanden voor gebruikers, dus het is belangrijk om de logica rondom winkelmanden 
volledig te scheiden van andere services, zoals de User Service. Verwijzingen naar winkelmanden vanuit de User Service moeten worden verwijderd.
