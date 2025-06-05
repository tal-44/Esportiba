import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from '../../../services/authentication.service';
import { Userdto } from '../../../common/userdto';
import { Router } from '@angular/router';
import { SessionStorageService } from '../../../services/session-storage.service';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{

  username: string = '';
  password: string = '';

  ngOnInit(): void {
  }

  constructor(private authentication: AuthenticationService, private router: Router, private toastr: ToastrService, private sessionStorage: SessionStorageService) {}

  login() {

   let userDto = new Userdto(this.username, this.password);
    console.log(userDto);

    this.authentication.login(userDto).subscribe(
      token => {
        console.log(token);
        this.sessionStorage.setItem('token', token);

        if (token.type === 'ADMIN') {
          this.router.navigate(['/admin/product']);
        } else {
          this.router.navigate(['/']);
        }

      }
    );
    console.log(userDto);
  }

}