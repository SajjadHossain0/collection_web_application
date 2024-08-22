package com.collection_web_application.Repository;

import com.collection_web_application.Entities.CommentItem;
import com.collection_web_application.Entities.UserCollectionItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public interface CommentItemRepository extends JpaRepository<CommentItem, Long> {

    List<CommentItem> findByItem(UserCollectionItems item);

}
