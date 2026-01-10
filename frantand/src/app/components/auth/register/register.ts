import {Component} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Router} from '@angular/router';
import {RegisterService} from '../../../services/register';
import Swal from 'sweetalert2';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-register',
  imports: [FormsModule,CommonModule],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
  username = '';
  email = '';
  password = '';
  message ='';
  constructor(private router: Router,private registerService:RegisterService) {
  }

  redirectLogin() {
    this.router.navigate(['']);
  }

  onsubmit() {
    console.log(this.username)
    this.registerService
      .register(this.username, this.password, this.email)
      .subscribe({
        next: (res: any) => {
          if (res?.id) {
            Swal.fire('Succès', 'Inscription réussie', 'success')
              .then(() => this.redirectLogin());
          } else {
            Swal.fire('Conflit', res?.message || 'Erreur', 'error');
          }
        },
        error: (err) => {
          this.message ="Erreur serveur";
        }
      });
  }
}
