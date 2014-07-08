package org.personal.mason.fl.utils.convert;

import org.personal.mason.fl.domain.model.Comment;
import org.personal.mason.fl.web.pojo.PoComment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mason on 6/23/14.
 */
public class CommentConverter {

    public static PoComment fromModel(Comment model){
        return fromModel(model, ConvertType.FULL);
    }
    public static PoComment fromModel(Comment model, ConvertType convertType) {
        PoComment poComment = null;

        if (model != null) {
            poComment = new PoComment();
            if(convertType.getPriority() >= ConvertType.KEY_ONLY.getPriority()){
                poComment.setId(model.getId());
            }
            if(convertType.getPriority() >= ConvertType.FLAT.getPriority()){
                poComment.setContent(model.getContent());
                poComment.setPoster(model.getPoster());
            }
            if(convertType.getPriority() >= ConvertType.FULL.getPriority()){
                poComment.setPoMerchant(
                    MerchantConverter.fromModel(model.getMerchant(), ConvertType.KEY_ONLY)
                );
            }
        }

        return poComment;
    }

    public static List<PoComment> fromModel(List<Comment> models){
        return fromModel(models, ConvertType.FULL);
    }

    public static List<PoComment> fromModel(List<Comment> models, ConvertType convertType) {
        List<PoComment> poComments = new ArrayList<>();
        if (models != null) {
            for (Comment model : models) {
                PoComment poComment = fromModel(model, convertType);

                if (poComment != null) {
                    poComments.add(poComment);
                }
            }
        }
        return poComments;
    }

    public static Comment toModel(PoComment poComment){
        return toModel(poComment, ConvertType.FULL);
    }

    public static Comment toModel(PoComment poComment, ConvertType convertType) {
        Comment model = null;

        if (poComment != null) {
            model = new Comment(poComment.getId());
            if(convertType.getPriority() >= ConvertType.FLAT.getPriority()){
                model.setContent(poComment.getContent());
                model.setPoster(poComment.getPoster());
            }
            if(convertType.getPriority() >= ConvertType.FULL.getPriority()) {
                model.setMerchant(
                    MerchantConverter.toModel(poComment.getPoMerchant(), ConvertType.KEY_ONLY)
                );
            }
        }

        return model;
    }

    public static List<Comment> toModel(List<PoComment> poComments){
        return toModel(poComments, ConvertType.FULL);
    }

    public static List<Comment> toModel(List<PoComment> poComments, ConvertType convertType) {
        List<Comment> models = new ArrayList<>();
        if (poComments != null) {
            for (PoComment poComment : poComments) {
                Comment model = toModel(poComment, convertType);
                if (model != null) {
                    models.add(model);
                }
            }
        }
        return models;
    }
}
