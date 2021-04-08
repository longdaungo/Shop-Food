package paypal;


import java.util.*;

import com.paypal.api.payments.*;
import com.paypal.base.rest.*;
import cart.ItemOrder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author truon
 */
public class PaymentServices {

    private static final String CLIENT_ID = "Aancaf4uph1PF9uPHJC4DsDd0MYScXQ7M88xXNRjVXE0Zq3vXAHpl8elrhH_Z6BfjM1TYdF2_C3tbz2D";
    private static final String CLIENT_SECRET = "EFwYYIUGnH1K06D4vt5lNpVH9vOz79Jq9rQknvEPsEFnZY-SmlQt2NjN1smrlyK-AjI5opTEnA8LLXdS";
    private static final String MODE = "sandbox";

    public String authorizePayment(List<ItemOrder> listItem, String total)
            throws PayPalRESTException {

        Payer payer = getPayerInformation();
        RedirectUrls redirectUrls = getRedirectURLs();
        List<Transaction> listTransaction = getTransactionInformation(listItem,total); 
        Payment requestPayment = new Payment();
        requestPayment.setTransactions(listTransaction);
        requestPayment.setRedirectUrls(redirectUrls);
        requestPayment.setPayer(payer);
        requestPayment.setIntent("authorize");
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        Payment approvedPayment = requestPayment.create(apiContext);
        return getApprovalLink(approvedPayment);
    }
    private Payer getPayerInformation() {
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");
        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName("William")
                .setLastName("Peterson")
                .setEmail("william.peterson@company.com");
        payer.setPayerInfo(payerInfo);
        return payer;
    }

    private RedirectUrls getRedirectURLs() {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8080/J3.L.P0013/cancel.jsp");
        redirectUrls.setReturnUrl("http://localhost:8080/J3.L.P0013/review_payment");

        return redirectUrls;
    }

    private List<Transaction> getTransactionInformation(List<ItemOrder> listItemorder, String total) {

        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(total);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
     

        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();
        for (ItemOrder itemorder : listItemorder) {
            Item item = new Item();
            item.setCurrency("USD");
            item.setName(itemorder.getName());
            item.setPrice(String.valueOf(itemorder.getPrice()));
            item.setPrice(String.valueOf(itemorder.getPrice()));
            item.setQuantity(String.valueOf(itemorder.getQuantity()));
            items.add(item);
        }  
        itemList.setItems(items);
        transaction.setItemList(itemList);

        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);

        return listTransaction;
    }

    private String getApprovalLink(Payment approvedPayment) {
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;

        for (Links link : links) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                approvalLink = link.getHref();
                break;
            }
        }

        return approvalLink;
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        Payment payment = new Payment().setId(paymentId);

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

        return payment.execute(apiContext, paymentExecution);
    }

    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        return Payment.get(apiContext, paymentId);
    }

}
