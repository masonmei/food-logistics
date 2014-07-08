package org.personal.mason.fl.web.controller;

import org.personal.mason.fl.domain.model.ShoppingCar;
import org.personal.mason.fl.domain.model.ShoppingCarItem;
import org.personal.mason.fl.domain.repository.ProductRepository;
import org.personal.mason.fl.domain.repository.ShoppingCarItemRepository;
import org.personal.mason.fl.domain.repository.ShoppingCarRepository;
import org.personal.mason.fl.utils.convert.ShoppingCarConverter;
import org.personal.mason.fl.utils.convert.ShoppingCarItemConverter;
import org.personal.mason.fl.web.pojo.PoShoppingCar;
import org.personal.mason.fl.web.pojo.PoShoppingCarItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collections;
import java.util.Set;

/**
 * Created by mason on 7/3/14.
 */
@Controller
public class ShoppingCarController extends AbstractController {

    @Autowired
    private ShoppingCarRepository shoppingCarRepository;

    @Autowired
    private ShoppingCarItemRepository shoppingCarItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"sc"}, method = RequestMethod.GET)
    public ResponseEntity getMyCar(){
        try{
            ShoppingCar shoppingCar = getMyShoppingCar();
            if(shoppingCar != null){
                PoShoppingCar poShoppingCar = ShoppingCarConverter.fromModel(shoppingCar);
                return new ResponseEntity(poShoppingCar, HttpStatus.OK);
            }
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"sc"}, method = RequestMethod.POST)
    public ResponseEntity addItem(@RequestBody PoShoppingCarItem poShoppingCarItem){
        try{
            ShoppingCar shoppingCar = getMyShoppingCar();
            if(shoppingCar != null){
                ShoppingCarItem shoppingCarItem = ShoppingCarItemConverter.toModel(poShoppingCarItem);
                if(shoppingCarItem != null) {
                    Set<ShoppingCarItem> shoppingCarItems = shoppingCar.getShoppingCarItems();
                    boolean found = false;
                    ShoppingCarItem founded = null;
                    for (ShoppingCarItem item : shoppingCarItems){
                        if(item.getProduct().getId().equals(shoppingCarItem.getProduct().getId())){
                            found = true;
                            founded = item;
                            break;
                        }
                    }
                    if(found){
                        founded.setNumber(founded.getNumber() + shoppingCarItem.getNumber());
                        if(founded.getNumber() <= 0){
                            shoppingCar.removeShoppingCarItem(founded);
                            shoppingCarItemRepository.delete(founded);
                            shoppingCarRepository.saveAndFlush(shoppingCar);
                        } else {
                            shoppingCarItemRepository.saveAndFlush(founded);
                        }
                    } else {
                        ShoppingCarItem item = new ShoppingCarItem();
                        item.setNumber(shoppingCarItem.getNumber());
                        item.setProduct(productRepository.findOne(shoppingCarItem.getProduct().getId()));
                        shoppingCar.addShoppingCarItem(item);
                        shoppingCarItemRepository.save(item);
                        shoppingCar = shoppingCarRepository.saveAndFlush(shoppingCar);
                    }
                }
                return new ResponseEntity(HttpStatus.OK);
            }
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"sc/{id}"}, method = RequestMethod.DELETE)
    public ResponseEntity removeItem(@PathVariable Long id){
        try{
            ShoppingCar shoppingCar = getMyShoppingCar();
            if(shoppingCar != null){
                ShoppingCarItem shoppingCarItem = shoppingCarItemRepository.findOne(id);
                if(shoppingCarItem != null) {
                    shoppingCar.removeShoppingCarItem(shoppingCarItem);
                    shoppingCarRepository.saveAndFlush(shoppingCar);
                }
                return new ResponseEntity(HttpStatus.OK);
            }
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"sc"}, method = RequestMethod.DELETE)
    public ResponseEntity clear(){
        try{
            ShoppingCar shoppingCar = getMyShoppingCar();
            if(shoppingCar != null){
                shoppingCarItemRepository.delete(shoppingCar.getShoppingCarItems());
                shoppingCar.setShoppingCarItems(Collections.emptySet());
                shoppingCarRepository.save(shoppingCar);
            }
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }
}
