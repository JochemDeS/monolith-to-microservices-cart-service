package com.example.cartservice.application.cart;

import com.example.cartservice.application.common.UseCase;
import com.example.cartservice.domain.CartId;
import com.example.cartservice.domain.UserId;
import org.springframework.stereotype.Service;

@Service
public class CreateCartForUserUseCase implements UseCase<UserId, CartId> {
    private final SaveCartPort saveCartPort;

    public CreateCartForUserUseCase(SaveCartPort saveCartPort) {
        this.saveCartPort = saveCartPort;
    }

    @Override
    public CartId handle(UserId request) {
            return saveCartPort.save(request);
    }
}
