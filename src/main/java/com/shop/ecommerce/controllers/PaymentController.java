package com.shop.ecommerce.controllers;

import com.shop.ecommerce.entities.Purchase;
import com.shop.ecommerce.entities.User;
import com.shop.ecommerce.services.PaymentService;
import com.shop.ecommerce.support.ResponseMessage;
import com.shop.ecommerce.support.exceptions.DateWrongRangeException;
import com.shop.ecommerce.support.exceptions.QuantityProductUnavailableException;
import com.shop.ecommerce.support.exceptions.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PaymentController {
    @Autowired
    private PaymentService purchasingService;

    @PreAuthorize("hasRole('client_user')")
    @PostMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity makePurchase(@RequestBody @Valid Purchase purchase) { // è buona prassi ritornare l'oggetto inserito
        try {
            return new ResponseEntity<>(purchasingService.makePurchase(purchase), HttpStatus.OK);
        } catch (QuantityProductUnavailableException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product quantity unavailable!", e); // realmente il messaggio dovrebbe essrere più esplicativo (es. specificare il prodotto di cui non vi è disponibilità)
        }
    }

    @PreAuthorize("hasRole('client_admin')")
    @GetMapping
    public List<Purchase> getAllPurchases() {
        return purchasingService.getAllPurchases();
    }



    @PreAuthorize("hasRole('client_user') or hasRole('client_admin')")
    @PostMapping("/{user}")
    public List<Purchase> getPurchases(@RequestBody @Valid User user) {
        try {
            return purchasingService.getPurchasesByUser(user);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!", e);
        }
    }

    @PreAuthorize("hasRole('client_user') or hasRole('client_admin')")
    @GetMapping("/{user}/{startDate}/{endDate}")
    public ResponseEntity getPurchasesInPeriod(@Valid @PathVariable("user") User user, @PathVariable("startDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date start, @PathVariable("endDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date end) {
        try {
            List<Purchase> result = purchasingService.getPurchasesByUserInPeriod(user, start, end);
            if ( result.size() <= 0 ) {
                return new ResponseEntity<>(new ResponseMessage("No results!"), HttpStatus.OK);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found XXX!", e);
        } catch (DateWrongRangeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date must be previous end date XXX!", e);
        }
    }


}
