package tfg.backend.infrastructure.rest;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import tfg.backend.domain.model.DataPayment;
import tfg.backend.domain.model.URLPayPalResponse;
import tfg.backend.infrastructure.Service.PayPalService;


@RestController
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8085"})
@RequestMapping("/api/v1/payments")
public class PayPalController {

    private final PayPalService payPalService;
    private final String SUCCESS_URL = "http://localhost:8085/api/v1/payments/success";
    private final String CANCEL_URL = "http://localhost:8085/api/v1/payments/cancel";

    @PostMapping
    public URLPayPalResponse CreatePayment(@RequestBody DataPayment dataPayment) {

        try {
            Payment payment = payPalService.createPayment(
                    Double.valueOf(dataPayment.getAmount()),
                    dataPayment.getCurrency(),
                    dataPayment.getMethod(),
                    "SALE",
                    dataPayment.getDescription(),
                    CANCEL_URL,
                    SUCCESS_URL
            );

            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return new URLPayPalResponse(links.getHref());
                }
            }

        } catch (PayPalRESTException e) {
            throw new RuntimeException(e);
        }

        return new URLPayPalResponse("http://localhost:8085");
//        return new URLPayPalResponse("http://localhost:4200");
    }

    @GetMapping("/success")
    public RedirectView paymentSuccess(
            @RequestParam("paymentId")  String paymentId,
            @RequestParam("PayerID") String payerId
    ) {
        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);

            if (payment.getState().equals("approved")) {
                return new RedirectView("http://localhost:8085/payment/success");
//                return new RedirectView("http://localhost:4200/payment/success");
//                return new RedirectView("http://localhost:4200");
            } else {
                return new RedirectView("http://localhost:8085");
//                return new RedirectView("http://localhost:4200");
            }

        } catch (PayPalRESTException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/cancel")
    public RedirectView paymentCancel() {
        return new RedirectView("http://localhost:8085");
        //return new RedirectView("http://localhost:4200"

    }

}