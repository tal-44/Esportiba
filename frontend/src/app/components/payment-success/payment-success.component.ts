import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../services/order.service';
import { SessionStorageService } from '../../services/session-storage.service';
import { OrderState } from '../../common/order-state';

@Component({
  selector: 'app-payment-success',
  standalone: false,
  templateUrl: './payment-success.component.html',
  styleUrl: './payment-success.component.css'
})
export class PaymentSuccessComponent implements OnInit {

  constructor(
    private orderService: OrderService,
    private sessionStorage: SessionStorageService
  ) { }

  ngOnInit(): void {
    // Aquí puedes agregar lógica adicional si es necesario

    console.log(this.sessionStorage.getItem('order'));
    let order = this.sessionStorage.getItem('order');

    let formData = new FormData();

    formData.append('id', order.id);
    formData.append('state', OrderState.CONFIRMED.toString());

    this.orderService.updateOrder(formData).subscribe(

      data => {
        
        console.log(data);

        console.log('logoutComponent: ' + this.sessionStorage.getItem('token'));
        this.sessionStorage.removeItem('token');
        console.log('logoutComponent: ' + this.sessionStorage.getItem('token'));

      }
    )

  }

}

