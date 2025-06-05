import { Component, OnInit } from '@angular/core';
import { Category } from '../../../common/category';
import { CategoryService } from '../../../services/category.service';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';
import { Product } from '../../../common/product';
import { ProductService } from '../../../services/product.service';

@Component({
  selector: 'app-category-list',
  standalone: false,
  templateUrl: './category-list.component.html',
  styleUrl: './category-list.component.css'
})
export class CategoryListComponent implements OnInit {

  categories: Category[] = [];

  constructor(private categoryService: CategoryService, private productService: ProductService, private toastr: ToastrService) { }

  ngOnInit(): void {
    this.listCategories();
  }

  listCategories() {
    this.categoryService.getCategoryList().subscribe(
      data => this.categories = data
    )
  }

  deleteCategoryById(id: number) {
    console.log('id de la categoria abtes de eliminar: ' + id);

    this.productService.getProducts().subscribe((
      productos: Product[]) => {

      for (let i = 0; i < productos.length; i++) {
        console.log('id de la categoria de un producto: ' + productos[i].categoryId);
        if (Number(productos[i].categoryId) === id) {
          Swal.fire({
            title: "No se puede eliminar la categoria",
            text: "Existen productos asociados a esta categoria.",
            icon: "error",
            confirmButtonText: "Aceptar"
          });
          return;

        }
      }

    }
    );

    Swal.fire({
      title: "Esta seguro de elminar este registro?",
      text: "",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Eliminar",
      cancelButtonText: "Cancelar"
    }).then((result) => {
      if (result.isConfirmed) {

        this.categoryService.deleteCategoryById(id).subscribe(
          () => this.listCategories()
        );

        Swal.fire({
          title: "Categorias",
          text: "Categoria eliminada correctamente.",
          icon: "success"
        });
      }
    });
  }

}
