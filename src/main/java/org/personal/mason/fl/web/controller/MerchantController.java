package org.personal.mason.fl.web.controller;

import org.personal.mason.fl.domain.model.Merchant;
import org.personal.mason.fl.domain.model.Product;
import org.personal.mason.fl.domain.model.User;
import org.personal.mason.fl.domain.repository.MerchantRepository;
import org.personal.mason.fl.domain.repository.ProductRepository;
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
public class MerchantController extends AbstractController {

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(method = RequestMethod.GET,
            value = {"merchant"}
    )
    public ResponseEntity<List<PoMerchant>> findAll() {
        List<Merchant> merchants = merchantRepository.findAll();

        List<PoMerchant> poMerchants = MerchantConverter.fromModel(merchants);
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
        List<Merchant> merchants = merchantRepository.findByUser(loginUser);

        List<PoMerchant> poMerchants = MerchantConverter.fromModel(merchants);
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
        PoMerchant poMerchant = MerchantConverter.fromModel(merchantRepository.findOne(id));
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

        PoMerchant result = MerchantConverter.fromModel(merchantRepository.save(MerchantConverter.toModel(poMerchant)));

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
        Merchant merchant = MerchantConverter.toModel(poMerchant);
        Merchant model = merchantRepository.findOne(id);
        updateModel(model, merchant);
        Merchant result = merchantRepository.saveAndFlush(model);

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
        Merchant merchant = MerchantConverter.toModel(poMerchant);
        Merchant model = merchantRepository.findOne(id);
        mergeModel(model, merchant);
        Merchant result = merchantRepository.saveAndFlush(model);

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
            merchantRepository.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(method = RequestMethod.GET,
            value = {"merchant/{id}/product"}
    )
    public ResponseEntity<List<PoProduct>> findProducts(@PathVariable Long id) {
        List<PoProduct> poProducts = ProductConverter.fromModel(new ArrayList(merchantRepository.findOne(id).getProducts()));
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
        Merchant merchant = merchantRepository.findOne(id);
        Product product = ProductConverter.toModel(merchantProduct);
        if(product != null){
            merchant.addMerchantProduct(product);

            product = productRepository.saveAndFlush(product);
            merchantRepository.save(merchant);
        }

        HttpHeaders headers = new HttpHeaders();
        if (product != null) {
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
            Merchant merchant = merchantRepository.findOne(id);
            Product model = productRepository.findOne(pid);
            if(!model.getMerchant().getId().equals(id)){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            Product product = ProductConverter.toModel(merchantProduct);
            updateModel(model, product);
            model.setMerchant(merchant);
            productRepository.saveAndFlush(model);
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
            Merchant merchant = merchantRepository.findOne(id);
            Product model = productRepository.findOne(pid);
            if(!model.getMerchant().getId().equals(id)){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            Product product = ProductConverter.toModel(merchantProduct);
            mergeModel(model, product);
            model.setMerchant(merchant);
            productRepository.saveAndFlush(model);
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
            Merchant merchant = merchantRepository.findOne(id);
            Product model = productRepository.findOne(pid);

            merchant.removeMerchantProduct(model);
            merchantRepository.saveAndFlush(merchant);
            productRepository.delete(model);

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
        Merchant merchant = merchantRepository.findOne(id);
        List<PoComment> poComments = CommentConverter.fromModel(new ArrayList(merchant.getComments()));
        if (poComments == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(poComments, HttpStatus.OK);
        }
    }
}