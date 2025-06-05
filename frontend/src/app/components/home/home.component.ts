import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Product } from '../../common/product';
import { ProductService } from '../../services/product.service';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { HeaderUserComponent } from '../header-user/header-user.component';
import { HomeService } from '../../services/home.service';

@Component({
  standalone: true,
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  imports: [CommonModule, RouterModule, HeaderUserComponent]
})

export class HomeComponent implements OnInit {
  products: Product[] = [];
  

    trackById(index: number, item: Product): number {
      return item.id;
    }

  constructor(private homeService: HomeService) {}

  ngOnInit(): void {
    this.homeService.getProducts().subscribe({
      next: (data: Product[]) => {
        this.products = data;
      },
      error: (error) => {
        console.error('Error al cargar los productos:', error);
      }
    });
  }
}