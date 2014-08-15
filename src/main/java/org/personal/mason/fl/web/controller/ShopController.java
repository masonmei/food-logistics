package org.personal.mason.fl.web.controller;

import org.personal.mason.fl.domain.model.Item;
import org.personal.mason.fl.domain.model.Shop;
import org.personal.mason.fl.domain.model.User;
import org.personal.mason.fl.domain.repository.ShopRepository;
import org.personal.mason.fl.domain.repository.ItemRepository;
import org.personal.mason.fl.utils.convert.CommentConverter;
import org.personal.mason.fl.utils.convert.MerchantConverter;
import org.personal.mason.fl.utils.convert.ProductConverter;
import org.personal.mason.fl.web.pojo.PoComment;
import org.personal.mason.fl.web.pojo.PoMerchant;
import org.personal.mason.fl.web.pojo.PoProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mason on 6/23/14.
 */
@Controller
public class ShopController extends AbstractController {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping(method = RequestMethod.GET,
            value = {"merchant"}
    )
    public ResponseEntity<List<PoMerchant>> findAll() {
        List<Shop> shops = shopRepository.findAll();

        List<PoMerchant> poMerchants = MerchantConverter.fromModel(shops);
        if (poMerchants.isEmpty()) {
            return new ResponseEntity<List<PoMerchant>>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<PoMerchant>>(poMerchants, HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.GET,
            value = {"merchant/restaurant"}
    )
    public ResponseEntity<List<PoMerchant>> findAllMerchantRestaurant() {
        User loginUser = getLoginUser();
        List<Shop> shops = shopRepository.findByUser(loginUser);

        List<PoMerchant> poMerchants = MerchantConverter.fromModel(shops);
        if (poMerchants.isEmpty()) {
            return new ResponseEntity<List<PoMerchant>>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<PoMerchant>>(poMerchants, HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.GET,
            value = {"merchant/{id}"}
    )
    public ResponseEntity<PoMerchant> findById(@PathVariable Long id) {
        PoMerchant poMerchant = MerchantConverter.fromModel(shopRepository.findOne(id));
        if (poMerchant == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(poMerchant, HttpStatus.OK);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = {RequestMethod.POST},
            value = {"merchant"}
    )
    public ResponseEntity<PoMerchant> createRole(@RequestBody PoMerchant poMerchant, UriComponentsBuilder builder) {

        PoMerchant result = MerchantConverter.fromModel(shopRepository.save(MerchantConverter.toModel(poMerchant)));

        HttpHeaders headers = new HttpHeaders();
        if (result != null) {
            headers.setLocation(builder.path("merchant/" + result.getId()).build().toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT')")
    @RequestMapping(method = {RequestMethod.PUT},
            value = {"merchant/{id}"}
    )
    public ResponseEntity<PoMerchant> updateMerchant(@PathVariable Long id, @RequestBody PoMerchant poMerchant, UriComponentsBuilder builder) {
        Shop shop = MerchantConverter.toModel(poMerchant);
        Shop model = shopRepository.findOne(id);
        updateModel(model, shop);
        Shop result = shopRepository.saveAndFlush(model);

        HttpHeaders headers = new HttpHeaders();
        if (result != null) {
            headers.setLocation(builder.path("merchant/" + result.getId()).build().toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT')")
    @RequestMapping(method = {RequestMethod.PATCH},
            value = {"merchant/{id}"}
    )
    public ResponseEntity partialUpdateMerchant(@PathVariable Long id, @RequestBody PoMerchant poMerchant, UriComponentsBuilder builder) {
        Shop shop = MerchantConverter.toModel(poMerchant);
        Shop model = shopRepository.findOne(id);
        mergeModel(model, shop);
        Shop result = shopRepository.saveAndFlush(model);

        HttpHeaders headers = new HttpHeaders();
        if (result != null) {
            headers.setLocation(builder.path("merchant/" + result.getId()).build().toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT')")
    @RequestMapping(method = RequestMethod.DELETE,
            value = {"merchant/{id}"}
    )
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            shopRepository.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(method = RequestMethod.GET,
            value = {"merchant/{id}/product"}
    )
    public ResponseEntity<List<PoProduct>> findProducts(@PathVariable Long id) {
        List<PoProduct> poProducts = ProductConverter.fromModel(new ArrayList(shopRepository.findOne(id).getItems()));
        if (poProducts == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(poProducts, HttpStatus.OK);
        }
    }

//    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT')")
    @RequestMapping(method = RequestMethod.POST,
            value = {"merchant/{id}/product"}
    )
    public ResponseEntity createProduct(@PathVariable Long id, @RequestBody PoProduct merchantProduct, UriComponentsBuilder builder) {
        Shop shop = shopRepository.findOne(id);
        Item item = ProductConverter.toModel(merchantProduct);
        if(item != null){
            shop.addMerchantProduct(item);

            item = itemRepository.saveAndFlush(item);
            shopRepository.save(shop);
        }

        HttpHeaders headers = new HttpHeaders();
        if (item != null) {
            headers.setLocation(builder.path("merchant/" + id + "/product").build().toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT')")
    @RequestMapping(method = RequestMethod.PUT,
            value = {"merchant/{id}/product/{pid}"}
    )
    public ResponseEntity updateProduct(@PathVariable Long id, @PathVariable Long pid, @RequestBody PoProduct merchantProduct, UriComponentsBuilder builder) {
        try{
            Shop shop = shopRepository.findOne(id);
            Item model = itemRepository.findOne(pid);
            if(!model.getShop().getId().equals(id)){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            Item item = ProductConverter.toModel(merchantProduct);
            updateModel(model, item);
            model.setShop(shop);
            itemRepository.saveAndFlush(model);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("merchant/" + id + "/product").build().toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT')")
    @RequestMapping(method = RequestMethod.PATCH,
            value = {"merchant/{id}/product/{pid}"}
    )
    public ResponseEntity<Void> partialUpdateProduct(@PathVariable Long id, @PathVariable Long pid, @RequestBody PoProduct merchantProduct, UriComponentsBuilder builder) {
        try{
            Shop shop = shopRepository.findOne(id);
            Item model = itemRepository.findOne(pid);
            if(!model.getShop().getId().equals(id)){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            Item item = ProductConverter.toModel(merchantProduct);
            mergeModel(model, item);
            model.setShop(shop);
            itemRepository.saveAndFlush(model);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("merchant/" + id + "/product").build().toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT')")
    @RequestMapping(method = RequestMethod.DELETE,
            value = {"merchant/{id}/product/{pid}"}
    )
    public ResponseEntity<Void> createProduct(@PathVariable Long id, @PathVariable Long pid, UriComponentsBuilder builder) {
        try{
            Shop shop = shopRepository.findOne(id);
            Item model = itemRepository.findOne(pid);

            shop.removeMerchantProduct(model);
            shopRepository.saveAndFlush(shop);
            itemRepository.delete(model);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("merchant/"+ id + "/product").build().toUri());
            return new ResponseEntity<>(headers, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
    }

    // ~ Comment ======
    @PreAuthorize("permitAll")
    @RequestMapping(
            method = RequestMethod.GET,
            value = {"merchant/{id}/comment"}
    )
    public ResponseEntity<List<PoComment>> findComments(@PathVariable Long id) {
        Shop shop = shopRepository.findOne(id);
        List<PoComment> poComments = CommentConverter.fromModel(new ArrayList(shop.getComments()));
        if (poComments == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(poComments, HttpStatus.OK);
        }
    }
}