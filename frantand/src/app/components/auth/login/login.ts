import { Component } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {LoginService} from '../../../services/login';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  username:string = "";
  password:  string = "";

  constructor( private loginService: LoginService,
               private router: Router,
                 private route: ActivatedRoute) {
  }
  redirectRegister(){
    this.router.navigate(['register'])
  }

  onsubmit(){
    console.log("username " + this.username)
    this.loginService.login(this.username,this.password).subscribe(
      {
        next: (res:any)=>{
      localStorage.setItem('token', res.token);
      localStorage.setItem('role', res.user.role);
    }
      }
    );
  }
}
