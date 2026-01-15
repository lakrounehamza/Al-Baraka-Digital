import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {PofileService} from '../../../services/client/pofile';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {hidden} from '@angular/forms/signals';

@Component({
  selector: 'app-profile',
  imports: [FormsModule, CommonModule],
  templateUrl: './profile.html',
  styleUrl: './profile.css',
})
export class Profile implements OnInit {

  username: string = "";
  numerAccount: string = "K ";
  balance: number = 0;
  role: string = "";
  active: boolean = false;
  createdAt: string = "";
  operations: any[] = [];
  burgaremune= false;


  constructor(private profileservice: PofileService, private cd: ChangeDetectorRef) {
  }

  ngOnInit(): void {
    this.setData();
  }

  setData() {
    this.profileservice.getAccount().subscribe({
      next: (res: any) => {
        console.log(res);
        this.username = res.onwer.username;
        this.numerAccount = res.numer;
        console.log(this.username);
        this.username = res.onwer.username;
        this.numerAccount = res.numer;
        this.balance = res.balance;
        this.role = res.onwer.role;
        this.active = res.onwer.active;
        this.createdAt = res.onwer.created_at;
        this.operations = res.operations;
        this.cd.detectChanges();
      }
    })
  }

  getTypeBadge(type: string): string {
    let index: 1 | 2 | 3 | 0 = 0;

    if (type === "RETRAIT") index = 1;
    if (type === "VERSEMENT") index = 2;
    if (type === "VIREMENT") index = 3;

    const config: Record<number, { class: string; icon: string }> = {
      1: {class: 'bg-red-100 text-red-800', icon: '↓'},
      2: {class: 'bg-green-100 text-green-800', icon: '↑'},
      3: {class: 'bg-blue-100 text-blue-800', icon: '→'}
    };

    if (!config[index]) return type; // sécurité

    const c = config[index];

    return `
    <span class="px-3 py-1 text-sm font-semibold rounded-full ${c.class}">
      ${c.icon} ${type}
    </span>
  `;
  }


  getStatusBadge(status: string): string {
    let index: 1 | 2 | 3 | 0 = 1;
    if(status==="PENDING") index=1;
    if(status==="VALIDATED") index=2;
    if(status==="REJECTED") index=3;
    const config = {
      1: {class: 'bg-yellow-100 text-yellow-800', label: 'En attente'},
      2: {class: 'bg-green-100 text-green-800', label: 'Validé'},
      3: {class: 'bg-red-100 text-red-800', label: 'Rejeté'}
    };

    const c = config[index];

    return `<span class="px-2 py-1 text-xs font-semibold rounded-full ${c.class}">${c.label}</span>`;
  }


  formatAccountNumber(numer: string): string {
    return numer.match(/.{1,4}/g)?.join(' ') || numer;

  }

  toggleMobile(){
    console.log("hello");
    this.burgaremune = !this.burgaremune;
  }

  showModal = false;
  operationType: 'VIREMENT' | 'DOPOT' | 'RETRAIT' = 'DOPOT';

  operation: any = {
    type: '',
    amount: '',
    accountDestination_id: ''
  };

  openModal(type: 'VIREMENT' | 'DOPOT' | 'RETRAIT') {
    this.operationType = type;
    this.operation = {
      type: type,
      amount: '',
      accountDestination_id: ''
    };
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }

  submitOperation() {
    console.log(this.operation);

    /*
    EXEMPLES envoyés au backend :

    VIREMENT:
    {
      type: "VIREMENT",
      amount: "9000",
      accountDestination_id: "8546029418615930"
    }

    DOPOT:
    {
      type: "DOPOT",
      amount: "9000"
    }

    RETRAIT:
    {
      type: "RETRAIT",
      amount: "9000"
    }
    */

    this.closeModal();
  }


  protected readonly hidden = hidden;
}

