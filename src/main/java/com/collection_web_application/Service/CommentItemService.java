package com.collection_web_application.Service;

import com.collection_web_application.Entities.CommentItem;
import com.collection_web_application.Entities.UserCollectionItems;

import java.util.List;

public interface CommentItemService {

    List<CommentItem> getCommentsByItem(UserCollectionItems item);
    public void saveComment(CommentItem commentItem);
}
