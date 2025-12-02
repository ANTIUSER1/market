package pn.market.services;

import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

public interface ModelService {

    Model createModel(int page, int pageSize, String search, String sorted, Model model);

    Pageable createPageble(int page, int pageSize, String sorted);
}
