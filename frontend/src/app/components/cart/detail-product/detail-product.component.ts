import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from '../../../services/product.service';
import { CartService } from '../../../services/cart.service';
import { NumberFormatStyle } from '@angular/common';
import { ItemCart } from '../../../common/item-cart';
import { ToastrService } from 'ngx-toastr';
import { HomeService } from '../../../services/home.service';

@Component({
  selector: 'app-detail-product',
  standalone: false,
  templateUrl: './detail-product.component.html',
  styleUrl: './detail-product.component.css'
})
export class DetailProductComponent implements OnInit {
  
  id: number = 0;
  name: string = '';
  description: string = '';
  price: number = 0;
  urlImage: string = '';
  quantity: number = 1; // Cambia de 0 a 1

  constructor(private activatedRoute: ActivatedRoute, private homeService: HomeService, private cartService: CartService, private toastr: ToastrService) {}

  ngOnInit(): void {
    this.getProductById();
    /*
    
    this.activatedRoute.params.subscribe(params => {
      this.id = +params['id'];
      this.getProductById();
    });
    */

  }

  getProductById() {
    this.activatedRoute.params.subscribe(
      p => {
        let id = p['id'];
        if (id) {
          this.homeService.getProductById(id).subscribe(
            data =>{
              this.id = data.id;
              this.name = data.name;
              this.description = data.description;
              this.price = data.price;
              this.urlImage = data.urlImage;
            
          })
          ;
        }
      }
    );
  }

  addToCart(id:number){
    console.log('Producto agregado al carrito:', id);
    console.log('Nombre:', this.name);
    console.log('Precio:', this.price);
    console.log('Cantidad:', this.quantity);

    let item = new ItemCart(id, this.name, this.price, this.quantity);

    this.cartService.addItemCart(item);

    console.log('Total carrito:');
    console.log(this.cartService.totalCart());

    this.toastr.success('Producto agregado al carrito', 'Carrito de compras');

  }

}
