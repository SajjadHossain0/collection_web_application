package com.collection_web_application.ServiceImplements;

import com.collection_web_application.Entities.CommentItem;
import com.collection_web_application.Entities.UserCollectionItems;
import com.collection_web_application.Repository.CommentItemRepository;
import com.collection_web_application.Service.CommentItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentItemServiceImpl implements CommentItemService {

    @Autowired
    private CommentItemRepository commentItemRepository;

    @Override
    public List<CommentItem> getCommentsByItem(UserCollectionItems item) {
        return commentItemRepository.findByItem(item);
    }

    @Override
    public void saveComment(CommentItem commentItem) {
        commentItemRepository.save(commentItem);
    }
}
