package com.shop.ecommerce.services;

import com.shop.ecommerce.entities.Product;
import com.shop.ecommerce.entities.ProductInPurchase;
import com.shop.ecommerce.entities.Purchase;
import com.shop.ecommerce.entities.User;
import com.shop.ecommerce.repositories.ProductInPurchaseRepository;
import com.shop.ecommerce.repositories.PurchaseRepository;
import com.shop.ecommerce.repositories.UserRepository;
import com.shop.ecommerce.support.exceptions.DateWrongRangeException;
import com.shop.ecommerce.support.exceptions.QuantityProductUnavailableException;
import com.shop.ecommerce.support.exceptions.UserNotFoundException;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private ProductInPurchaseRepository productInPurchaseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;


    @Transactional(readOnly = false)
    public Purchase makePurchase(Purchase purchase) throws QuantityProductUnavailableException {
        Purchase result = purchaseRepository.save(purchase);
        for ( ProductInPurchase pip : result.getProductsInPurchase() ) {
            pip.setPurchase(result);
            ProductInPurchase justAdded = productInPurchaseRepository.save(pip);
            entityManager.refresh(justAdded);
            Product product = justAdded.getProduct();
            int newQuantity = product.getQuantity() - pip.getQuantity();
            if ( newQuantity < 0 ) {
                throw new QuantityProductUnavailableException();
            }
            product.setQuantity(newQuantity);
            entityManager.refresh(pip);
        }
        entityManager.refresh(result);
        return result;
    }

    @Transactional(readOnly = true)
    public List<Purchase> getPurchasesByUser(User user) throws UserNotFoundException {
        if ( !userRepository.existsById(user.getId()) ) {
            throw new UserNotFoundException();
        }
        return purchaseRepository.findByBuyer(user);
    }

    @Transactional(readOnly = true)
    public List<Purchase> getPurchasesByUserInPeriod(User user, Date startDate, Date endDate) throws UserNotFoundException, DateWrongRangeException {
        if ( !userRepository.existsById(user.getId()) ) {
            throw new UserNotFoundException();
        }
        if ( startDate.compareTo(endDate) >= 0 ) {
            throw new DateWrongRangeException();
        }
        return purchaseRepository.findByBuyerInPeriod(startDate, endDate, user);
    }

    @Transactional(readOnly = true)
    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }


}
