package ma.org.comfybackend.security.Services;

import ma.org.comfybackend.security.Entities.Caddy;

public interface CaddyService {
    Caddy createCaddy(String customerEmail);
}
