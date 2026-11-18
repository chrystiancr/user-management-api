package com.chrystiancr.usermanagement.user;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserResponse> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(UserResponse::from)
                .toList();
    }

    public UserResponse findById(Long id) {
        return UserResponse.from(getUser(id));
    }

    public UserResponse create(UserRequest request) {
        validateEmailAvailable(request.email(), null);

        User user = new User(request.name().trim(), normalizeEmail(request.email()));
        return UserResponse.from(repository.save(user));
    }

    public UserResponse update(Long id, UserRequest request) {
        User user = getUser(id);
        validateEmailAvailable(request.email(), id);

        user.setName(request.name().trim());
        user.setEmail(normalizeEmail(request.email()));
        return UserResponse.from(repository.save(user));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        repository.deleteById(id);
    }

    private User getUser(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private void validateEmailAvailable(String email, Long ignoredId) {
        String normalizedEmail = normalizeEmail(email);

        repository.findByEmailIgnoreCase(normalizedEmail).ifPresent(existing -> {
            if (!existing.getId().equals(ignoredId)) {
                throw new EmailAlreadyExistsException(normalizedEmail);
            }
        });
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }
}
