package com.example.ecommerce.service.cart;

import com.example.ecommerce.dto.cart.CartDto;
import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.exception.InsufficientStockException;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.mapper.CartMapper;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    @Override
    public CartDto addToCart(Long userId, Long productId, Integer quantity) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
        if (product.getQuantity() < quantity) {
            throw new InsufficientStockException("Not enough available products");
        }
        Cart cart = cartRepository.findByUserId(userId).orElse(new Cart(null, user, new ArrayList<>()));
        Optional<CartItem> existingCartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId)).findFirst();
        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }else {
            CartItem cartItem=new CartItem(null,cart,product,quantity);
            cart.getItems().add(cartItem);
        }
        Cart savedCart = cartRepository.save(cart);
        return cartMapper.toDto(savedCart);
    }

    @Override
    public CartDto getCart(Long userId) {
        Cart cart =cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart Not Found"));
        return cartMapper.toDto(cart);
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart=cartRepository.findByUserId(userId).orElseThrow(
                ()->new ResourceNotFoundException("Cart Not Found"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public void removeCartItem(Long userId, Long productId) {
      Cart cart =cartRepository.findByUserId(userId)
              .orElseThrow(()->new ResourceNotFoundException("Cart Not Found"));
      cart.getItems().removeIf(item->item.getProduct().getId().equals(productId));
      cartRepository.save(cart);
    }

}
