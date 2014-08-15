package org.personal.mason.fl.web.controller;

import org.personal.mason.fl.domain.model.*;
import org.personal.mason.fl.domain.repository.*;
import org.personal.mason.fl.utils.Constrains;
import org.personal.mason.fl.utils.UserNumberUtils;
import org.personal.mason.fl.utils.convert.ContactConverter;
import org.personal.mason.fl.utils.convert.OrderConverter;
import org.personal.mason.fl.utils.convert.PurchaseItemConverter;
import org.personal.mason.fl.web.pojo.PoCheckout;
import org.personal.mason.fl.web.pojo.PoContact;
import org.personal.mason.fl.web.pojo.PoOrder;
import org.personal.mason.fl.web.pojo.PoPurchaseItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by mason on 6/25/14.
 */
@Controller
public class OrderController extends AbstractController {

//    2014-07-06T02:24:54.000Z
    @InitBinder
    private void dateBinder(WebDataBinder binder){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        CustomDateEditor editor = new CustomDateEditor(dateFormat, false);
        //Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);
    }

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private PurchaseItemRepository purchaseItemRepository;

    @Autowired
    private ShoppingCarRepository shoppingCarRepository;

    @Autowired
    private ShoppingCarItemRepository shoppingCarItemRepository;

    @RequestMapping(method = RequestMethod.GET,
            value = {"order/status"}
    )
    public ResponseEntity allOrderStatus() {
        return new ResponseEntity(Constrains.ORDER_STATUS, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT', 'CAVALIER', 'USER')")
    @RequestMapping(method = RequestMethod.GET,
            value = {"order/{id}"}
    )
    public ResponseEntity<PoOrder> findById(@PathVariable Long id) {
        PoOrder poOrder = OrderConverter.fromModel(orderRepository.findOne(id));
        if (poOrder == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(poOrder, HttpStatus.OK);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"order/checkout"}
    )
    public ResponseEntity checkOutFromShoppingCar(@RequestBody PoCheckout poCheckout, UriComponentsBuilder builder) {
        try {
            User user = getLoginUser();

            Contact contact;
            PoContact poContact = poCheckout.getPoContact();
            if(poContact.getId() == null){
                contact = ContactConverter.toModel(poContact);
                contact.setProfile(user.getProfile());
                contact = contactRepository.saveAndFlush(contact);
            } else {
                contact = contactRepository.findOne(poContact.getId());
            }

            Set<ShoppingCarItem> shoppingCarItems = user.getShoppingCar().getShoppingCarItems();

            Map<Shop, List<ShoppingCarItem>> maps = new HashMap<>();
            for (ShoppingCarItem item : shoppingCarItems){
                Shop shop = item.getItem().getShop();
                if(!maps.containsKey(shop)){
                    maps.put(shop, new ArrayList<>());
                }
                maps.get(shop).add(item);
            }

            long count = orderRepository.count();
            List<Order> orders = new ArrayList<>();
            for(Shop shop : maps.keySet()){
                Order order = new Order();
                List<ShoppingCarItem> carItems = maps.get(shop);
                BigDecimal total = new BigDecimal(0);
                for (ShoppingCarItem carItem : carItems){
                    PurchaseItem purchaseItem = new PurchaseItem();
                    purchaseItem.setItem(carItem.getItem());
                    purchaseItem.setPurchaseNumber(carItem.getNumber());
                    order.addPurchaseItem(purchaseItem);
                    total = total.add(carItem.getItem().getPrice().multiply(new BigDecimal(carItem.getNumber())));
                }
                order.setStatus(Constrains.ORDER_STATUS[0]);
                order.setUser(user);
                order.setContact(contact);
                order.setShop(shop);
                order.setSubmitTime(new Date());
                order.setOrderNumber(UserNumberUtils.createNumber("NUM_", ++count));
                order.setDeliveryFee(shop.getDeliveryFee());
                order.setTotal(total);
                order.setMeetTime(poCheckout.getMeetTime());
                order.setAdditionInfo(poCheckout.getAdditionInfo());
                orders.add(order);
            }

            orderRepository.save(orders);

            ShoppingCar shoppingCar = user.getShoppingCar();
            shoppingCar.getShoppingCarItems().clear();
            shoppingCarRepository.saveAndFlush(shoppingCar);

            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"order"}
    )
    public ResponseEntity<PoOrder> createOrder(@RequestBody PoOrder poOrder, UriComponentsBuilder builder) {
        try {

            Order model = OrderConverter.toModel(poOrder);
            User user = getLoginUser();
            model.setUser(user);
            model.setStatus(Constrains.ORDER_STATUS[0]);
            model = orderRepository.save(model);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("order/" + model.getId()).build().toUri());
            return new ResponseEntity<PoOrder>(headers, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<PoOrder>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT', 'USER')")
    @RequestMapping(method = {RequestMethod.PUT},
            value = {"order/{id}"}
    )
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody PoOrder poOrder, UriComponentsBuilder builder) {
        Order order = OrderConverter.toModel(poOrder);
        Order model = orderRepository.findOne(id);
        updateModel(model, order);
        Order result = orderRepository.saveAndFlush(model);

        HttpHeaders headers = new HttpHeaders();
        if (result != null) {
            headers.setLocation(builder.path("order/" + result.getId()).build().toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT', 'USER', 'CAVALIER')")
    @RequestMapping(method = {RequestMethod.PATCH},
            value = {"order/{id}"}
    )
    public ResponseEntity<?> partialUpdateOrder(@PathVariable Long id, @RequestBody PoOrder poOrder, UriComponentsBuilder builder) {
        Order order = OrderConverter.toModel(poOrder);
        Order model = orderRepository.findOne(id);
        mergeModel(model, order);
        Order result = orderRepository.saveAndFlush(model);

        HttpHeaders headers = new HttpHeaders();
        if (result != null) {
            headers.setLocation(builder.path("order/" + result.getId()).build().toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT', 'USER')")
    @RequestMapping(method = RequestMethod.DELETE,
            value = {"order/{id}"}
    )
    public ResponseEntity<PoOrder> delete(@PathVariable Long id) {
        try {
            orderRepository.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT', 'USER', 'CAVALIER')")
    @RequestMapping(method = RequestMethod.GET,
            value = {"order/{id}/purchase"}
    )
    public ResponseEntity<List<PoPurchaseItem>> findPurchases(@PathVariable Long id) {
        List<PoPurchaseItem> poOrderProducts = PurchaseItemConverter.fromModel(new ArrayList<PurchaseItem>(orderRepository.findOne(id).getPurchaseItems()));
        if (poOrderProducts == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(poOrderProducts, HttpStatus.OK);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @RequestMapping(method = RequestMethod.POST,
            value = {"order/{id}/purchase"}
    )
    public ResponseEntity<Void> createPurchase(@PathVariable Long id, @RequestBody PoPurchaseItem poPurchaseItem, UriComponentsBuilder builder) {
        Order order = orderRepository.findOne(id);
        PurchaseItem item = PurchaseItemConverter.toModel(poPurchaseItem);

        if (item != null) {
            order.addPurchaseItem(item);
            purchaseItemRepository.save(item);
            orderRepository.save(order);
        }

        HttpHeaders headers = new HttpHeaders();
        if (item != null) {
            headers.setLocation(builder.path("order/" + id + "/purchase/" + item.getId()).build().toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT', 'USER')")
    @RequestMapping(
            method = {RequestMethod.PUT, RequestMethod.PATCH},
            value = {"order/{id}/purchase/{pid}"}
    )
    public ResponseEntity<PoPurchaseItem> updatePurchase(@PathVariable Long id, @PathVariable Long pid, @RequestBody PoPurchaseItem poPurchaseItem, UriComponentsBuilder builder) {
        try {
            Order order = orderRepository.findOne(id);
            PurchaseItem model = purchaseItemRepository.findOne(pid);

            if (!model.getOrder().getId().equals(id)) {
                return new ResponseEntity<PoPurchaseItem>(HttpStatus.NOT_MODIFIED);
            }

            PurchaseItem item = PurchaseItemConverter.toModel(poPurchaseItem);

            model.setPurchaseNumber(item.getPurchaseNumber());

            if (model.getPurchaseNumber() <= 0) {
                order.removePurchaseItem(item);
                purchaseItemRepository.delete(item);
                orderRepository.saveAndFlush(order);
                return new ResponseEntity<PoPurchaseItem>(HttpStatus.OK);
            } else {
                purchaseItemRepository.saveAndFlush(model);
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(builder.path("order/" + id + "/purchase/" + item.getId()).build().toUri());

                return new ResponseEntity<PoPurchaseItem>(PurchaseItemConverter.fromModel(model), headers, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @RequestMapping(method = RequestMethod.DELETE,
            value = {"order/{id}/purchase/{pid}"}
    )
    public ResponseEntity<Void> deletePurchase(@PathVariable Long id, @PathVariable Long pid, UriComponentsBuilder builder) {
        try {
            Order order = orderRepository.findOne(id);
            PurchaseItem item = purchaseItemRepository.findOne(pid);

            order.removePurchaseItem(item);
            purchaseItemRepository.delete(item);
            orderRepository.saveAndFlush(order);

            HttpHeaders headers = new HttpHeaders();

            headers.setLocation(builder.path("order/" + id + "/purchase").build().toUri());
            return new ResponseEntity<>(headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT')")
    @RequestMapping(method = RequestMethod.GET,
            value = {"order/merchant/{id}"}
    )
    public ResponseEntity<List<PoOrder>> queryMerchantOrders(@PathVariable Long id,
                                                             @RequestParam(required = false) String status,
                                                             @RequestParam(required = false) Date from,
                                                             @RequestParam(required = false) Date to) {
        try {
            Shop shop = shopRepository.findOne(id);

            List<Order> models = new ArrayList<>();

            from = toStartOfDay(from);
            to = toEndOfDay(to);

            if (status == null) {
                models = orderRepository.findByMerchantAndSubmitTimeBetween(shop, from, to);
            } else {
                Collection statusCollect = splitToArray(status);
                if (from == null && to == null) {
//                    models = orderRepository.findByStatusAndMerchant(status, merchant);
                    models = orderRepository.findByMerchantAndStatusIn(shop, statusCollect);
                } else if (to == null) {
//                    models = orderRepository.findByStatusAndMerchantAndSubmitTimeAfter(status, merchant, from);
                    models = orderRepository.findByStatusInAndMerchantAndSubmitTimeAfter(statusCollect, shop, from);
                } else {
//                    models = orderRepository.findByStatusAndMerchantAndSubmitTimeBetween(status, merchant, from, to);
                    models = orderRepository.findByStatusInAndMerchantAndSubmitTimeBetween(statusCollect, shop, from, to);
                }

            }

            if (models.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<PoOrder> orders = OrderConverter.fromModel(new ArrayList<Order>(models));

            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(method = RequestMethod.GET,
            value = {"order/user"}
    )
    public ResponseEntity<List<PoOrder>> queryUserOrders(@RequestParam(required = false) String status,
                                                         @RequestParam(required = false) Date from,
                                                         @RequestParam(required = false) Date to) {
        try {
            User user = getLoginUser();

            List<Order> models = new ArrayList<>();

            from = toStartOfDay(from);
            to = toEndOfDay(to);

            if (status == null) {
                models = orderRepository.findByUserAndSubmitTimeBetween(user, from, to);
            } else {
                if (from == null && to == null) {
                    models = orderRepository.findByStatusAndUser(status, user);
                } else if (to == null) {
                    models = orderRepository.findByStatusAndUserAndSubmitTimeAfter(status, user, from);
                } else {
                    models = orderRepository.findByStatusAndUserAndSubmitTimeBetween(status, user, from, to);
                }

            }

            if (models.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<PoOrder> orders = OrderConverter.fromModel(new ArrayList<Order>(models));

            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('CAVALIER')")
    @RequestMapping(method = RequestMethod.GET,
            value = {"order/cavalier"}
    )
    public ResponseEntity<List<PoOrder>> queryCavalierOrders(@RequestParam(required = false) String status,
                                                             @RequestParam(required = false) Date from,
                                                             @RequestParam(required = false) Date to) {
        try {
            User user = getLoginUser();

            from = toStartOfDay(from);
            to = toEndOfDay(to);

            List<Order> models = new ArrayList<>();

            if (status == null) {
                models = orderRepository.findByCavalierAndSubmitTimeBetween(user, from, to);
            } else {
                if (from == null && to == null) {
                    models = orderRepository.findByStatusAndCavalier(status, user);
                } else if (to == null) {
                    models = orderRepository.findByStatusAndCavalierAndSubmitTimeAfter(status, user, from);
                } else if (from != null) {
                    models = orderRepository.findByStatusAndCavalierAndSubmitTimeBetween(status, user, from, to);
                }
            }

            if (models.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<PoOrder> orders = OrderConverter.fromModel(new ArrayList<Order>(models));

            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET,
            value = {"order/user/{id}"}
    )
    public ResponseEntity<List<PoOrder>> queryUserOrders(@PathVariable Long id,
                                                         @RequestParam(required = false) String status,
                                                         @RequestParam(required = false) Date from,
                                                         @RequestParam(required = false) Date to) {
        try {
            User user = userRepository.findOne(id);

            from = toStartOfDay(from);
            to = toEndOfDay(to);

            List<Order> models = new ArrayList<>();

            if (status == null) {
                models = orderRepository.findByUserAndSubmitTimeBetween(user, from, to);
            } else {
                if (from == null && to == null) {
                    models = orderRepository.findByStatusAndUser(status, user);
                } else if (to == null) {
                    models = orderRepository.findByStatusAndUserAndSubmitTimeAfter(status, user, from);
                } else {
                    models = orderRepository.findByStatusAndUserAndSubmitTimeBetween(status, user, from, to);
                }

            }

            if (models.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<PoOrder> orders = OrderConverter.fromModel(new ArrayList<Order>(models));

            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET,
            value = {"order/cavalier/{id}"}
    )
    public ResponseEntity<List<PoOrder>> queryCavalierOrders(@PathVariable Long id,
                                                             @RequestParam(required = false) String status,
                                                             @RequestParam(required = false) Date from,
                                                             @RequestParam(required = false) Date to) {
        try {
            User user = userRepository.findOne(id);

            from = toStartOfDay(from);
            to = toEndOfDay(to);

            List<Order> models = new ArrayList<>();

            if (status == null) {
                models = orderRepository.findByCavalierAndSubmitTimeBetween(user, from, to);
            } else {
                if (from == null && to == null) {
                    models = orderRepository.findByStatusAndCavalier(status, user);
                } else if (to == null) {
                    models = orderRepository.findByStatusAndCavalierAndSubmitTimeAfter(status, user, from);
                } else if (from != null) {
                    models = orderRepository.findByStatusAndCavalierAndSubmitTimeBetween(status, user, from, to);
                }
            }

            if (models.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<PoOrder> orders = OrderConverter.fromModel(new ArrayList<Order>(models));

            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET,
            value = {"order"}
    )
    public ResponseEntity<List<PoOrder>> queryOrders(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Date from,
            @RequestParam(required = false) Date to) {

        try {
            List<Order> models = new ArrayList<>();

            from = toStartOfDay(from);
            to = toEndOfDay(to);

            if (status == null) {
                if (from == null && to == null) {
                    models = orderRepository.findAll();
                } else if (to == null) {
                    models = orderRepository.findBySubmitTimeAfter(from);
                } else if (from != null) {
                    models = orderRepository.findBySubmitTimeBetween(from, to);
                }
                models = orderRepository.findBySubmitTimeBetween(from, to);
            } else {
                if (from == null && to == null) {
                    models = orderRepository.findByStatus(status);
                } else if (to == null) {
                    models = orderRepository.findByStatusAndSubmitTimeAfter(status, from);
                } else if (from != null) {
                    models = orderRepository.findByStatusAndSubmitTimeBetween(status, from, to);
                }
            }

            if (models.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<PoOrder> orders = OrderConverter.fromModel(new ArrayList<Order>(models));

            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(
            method = RequestMethod.GET,
            value = {"order/left"}
    )
    public ResponseEntity<List<PoOrder>> queryOrdersNotScheduled() {

        try {
            List<Order> models = orderRepository.findByCavalierIsNull();

            if (models.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<PoOrder> orders = OrderConverter.fromModel(new ArrayList<Order>(models));

            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
