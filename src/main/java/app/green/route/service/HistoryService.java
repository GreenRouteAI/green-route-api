package app.green.route.service;

import app.green.route.endpoint.security.AuthProvider;
import app.green.route.model.History;
import app.green.route.repository.HistoryRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HistoryService {
  private final HistoryRepository repository;

  public List<History> getHistory() {
    var authId = AuthProvider.getUser().getId();
    return repository.findByUserId(authId);
  }
}
