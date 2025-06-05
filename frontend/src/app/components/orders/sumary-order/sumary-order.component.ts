import { Component, OnInit } from '@angular/core';
import { ItemCart } from '../../../common/item-cart';
import { CartService } from '../../../services/cart.service';
import { UserService } from '../../../services/user.service';
import { OrderProduct } from '../../../common/order-product';
import { OrderState } from '../../../common/order-state';
import { Order } from '../../../common/order';
import { OrderService } from '../../../services/order.service';
import { PaymentService } from '../../../services/payment.service';
import { DataPayment } from '../../../common/data-payment';
import { url } from 'inspector';
import { SessionStorageService } from '../../../services/session-storage.service';

@Component({
  selector: 'app-sumary-order',
  standalone: false,
  templateUrl: './sumary-order.component.html',
  styleUrl: './sumary-order.component.css'
})
export class SumaryOrderComponent implements OnInit {
 
  items: ItemCart [] = [];
  totalCart: number = 0;
  firstName: string = '';
  lastName: string = '';
  email: string = '';
  address: string = '';
  orderProducts: OrderProduct[] = [];
  userId:number = 1; 

  constructor(
    private cartService: CartService,
    private userService: UserService, 
    private orderService: OrderService, 
    private paymentService: PaymentService, 
    private sessionStorage: SessionStorageService
  ) {}

  ngOnInit(): void {
    this.items = this.cartService.convertToListFromMap();
    this.totalCart = this.cartService.totalCart();
    this.userId = this.sessionStorage.getItem('token').id; 
    this.getUserById(this.userId);
    setTimeout(() => {
      this.sessionStorage.removeItem('token');
    }, 600000
  );
      
  }

  generateOrder() {
    this.items.forEach(
    
      item=> {
        let orderProduct = new OrderProduct(null, item.productId, item.price, item.quantity);
        this.orderProducts.push(orderProduct);
      }

    );

    let order = new Order(null, new Date(), this.orderProducts, this.userId, OrderState.CANCELLED);
    console.log("Orden generada: ", order.orderState);
    this.orderService.createOrder(order).subscribe(
      data => {
        console.log("Order creada con id: ", data.id);
        this.sessionStorage.setItem('order', data);
      }
    );



    // Redireccionar al usuario a la página de pago de paypal

    let urlPayment;
    let dataPayment = new DataPayment(
      'PAYPAL',
      this.totalCart.toString(),
      'USD',
      'COMPRA'
    );

    console.log("Datos de pago: ", dataPayment);

    this.paymentService.getUrlPayment(dataPayment).subscribe(
    
      data => {

        urlPayment = data.url;
        console.log("respuesta exitosa: ", urlPayment);
        window.location.href = urlPayment;

      }

    )

  }

  deleteItemCart(productId: number) {
    this.cartService.deleteItemCart(productId);
    this.items = this.cartService.convertToListFromMap();
    this.totalCart = this.cartService.totalCart();
  }

  getUserById(id: number) {
    this.userService.getUserById(id).subscribe(
    
      data => {

        this.firstName = data.firstName;
        this.lastName = data.lastName;
        this.email = data.email;
        this.address = data.address;

      }
    );
  }

}
