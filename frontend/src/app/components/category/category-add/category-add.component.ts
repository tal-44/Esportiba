import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../../../services/category.service';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from '../../../common/category';

@Component({
  selector: 'app-category-add',
  standalone: false,
  templateUrl: './category-add.component.html',
  styleUrl: './category-add.component.css'
})
export class CategoryAddComponent implements OnInit {

  id:number =0;
  name:string = '';

  constructor(private categoryService:CategoryService, private toastr:ToastrService, private router:Router, private activatedRoute: ActivatedRoute) {
    
  }

  ngOnInit(): void {
    this.getCategoryById();
  }
  
  addCategory(){
    console.log(this.name);
    const category = new Category(this.id === 0 ? 0 : this.id, this.name.trim());
  //  let category = new Category(this.id, this.name.trim());
    this.categoryService.createCategory(category).subscribe(
     res=>{
      this.toastr.success('Categoria registrada correctemente', 'Categorias');
      this.router.navigate(['admin/category']);
     }
    )
  }

  getCategoryById(){
    this.activatedRoute.params.subscribe(
      category =>{
        let id = category['id'];
        if(id){
          console.log('Valor id: '+id)
          this.categoryService.getCategoryById(id).subscribe(
            data=>{
              this.id = data.id;
              this.name = data.name;
            }
          )
        }
      }
    )
  }

}
/*

const formData = new FormData();
formData.append('id', this.id.toString());
formData.append('code', this.code);
formData.append('name', this.name);
formData.append('description', this.description);
formData.append('price', this.price.toString());
formData.append('image', this.selectFile)
formData.append('urlImage', this.imageUrl);
formData.append('userId', this.UserId);
formData.append('categoryId', this.categoryId);
console.log(formData);

this.productService.createProduct(formData).subscribe(
  data => {
    console.log(data);

    if (this.id == 0) {
      this.toastr.success('Producto registrado correctamente', 'Productos') 
    } else {
      this.toastr.success('Producto modificado correctamente', 'Productos') 
    }

    this.router.navigate(['/admin/product']);
  }
);
}

*/