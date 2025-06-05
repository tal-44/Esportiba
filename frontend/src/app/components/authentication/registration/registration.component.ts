import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../../services/authentication.service';
import { User } from '../../../common/user';
import { UserType } from '../../../common/user-type';
import { Router } from '@angular/router';
import { Toast, ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-registration',
  standalone: false,
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.css'
})
export class RegistrationComponent implements OnInit{

  username: string = '';
  name: string = '';
  surname: string = '';
  email: string = '';
  address: string = '';
  cellphone: string = '';
  password: string = '';
  userType: string = '';

  ngOnInit(): void {
  }

  constructor(private authentication: AuthenticationService, private router: Router, private toastr: ToastrService) {}

  register() {
    this.username = this.email;
    this.userType = UserType.USER; // Default user type
    let user = new User(
      null,
      this.username,
      this.name,
      this.surname,
      this.email,
      this.address,
      this.cellphone,
      this.password,
      this.userType
    )

    this.authentication.register(user).subscribe(
      res => {
        this.toastr.success('Usuario registrado correctamente', 'Usuario');
        console.log(res);
      },
      err => {
        this.toastr.error('Error al registrar usuario', 'Usuario');
      }

      
    )

    console.log(user);

    this.router.navigate(['/user/login']);

  }

}
