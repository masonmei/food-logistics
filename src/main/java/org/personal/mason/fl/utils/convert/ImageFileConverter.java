package org.personal.mason.fl.utils.convert;

import org.personal.mason.fl.domain.model.ImageFile;
import org.personal.mason.fl.web.pojo.PoImageFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mason on 6/25/14.
 */
public class ImageFileConverter {

    public static PoImageFile fromModel(ImageFile model){
        return fromModel(model, ConvertType.NO_BINARY);
    }

    public static PoImageFile fromModel(ImageFile model, ConvertType convertType) {
        PoImageFile poImageFile = null;

        if (model != null) {
            poImageFile = new PoImageFile();
            if(convertType.getPriority() >= ConvertType.KEY_ONLY.getPriority()){
                poImageFile.setId(model.getId());
            }

            if(convertType.getPriority() >= ConvertType.NO_BINARY.getPriority()){
                poImageFile.setName(model.getName());
                poImageFile.setSize(model.getSize());
                poImageFile.setType(model.getType());
            }

            if(convertType.getPriority() >= ConvertType.FLAT.getPriority()){
                poImageFile.setContent(model.getContent());
            }
        }

        return poImageFile;
    }

    public static List<PoImageFile> fromModel(List<ImageFile> models){
        return fromModel(models, ConvertType.NO_BINARY);
    }

    public static List<PoImageFile> fromModel(List<ImageFile> models, ConvertType convertType) {
        List<PoImageFile> poImageFiles = new ArrayList<>();
        if (models != null) {
            for (ImageFile model : models) {
                PoImageFile poImageFile = fromModel(model, convertType);

                if (poImageFile != null) {
                    poImageFiles.add(poImageFile);
                }
            }
        }
        return poImageFiles;
    }

    public static ImageFile toModel(PoImageFile poImageFile){
        return toModel(poImageFile, ConvertType.NO_BINARY);
    }

    public static ImageFile toModel(PoImageFile poImageFile, ConvertType convertType) {
        ImageFile model = null;

        if (poImageFile != null) {
            model = new ImageFile(poImageFile.getId());

            if(convertType.getPriority() >= ConvertType.NO_BINARY.getPriority()){
                model.setName(poImageFile.getName());
                model.setType(poImageFile.getType());
                model.setSize(poImageFile.getSize());
            }

            if(convertType.getPriority() >= ConvertType.FLAT.getPriority()){
                model.setContent(poImageFile.getContent());
            }
        }

        return model;
    }

    public static List<ImageFile> toModel(List<PoImageFile> poImageFiles){
        return toModel(poImageFiles, ConvertType.NO_BINARY);
    }

    public static List<ImageFile> toModel(List<PoImageFile> poImageFiles, ConvertType convertType) {
        List<ImageFile> models = new ArrayList<>();
        if (poImageFiles != null) {
            for (PoImageFile poImageFile : poImageFiles) {
                ImageFile model = toModel(poImageFile, convertType);
                if (model != null) {
                    models.add(model);
                }
            }
        }
        return models;
    }
}
