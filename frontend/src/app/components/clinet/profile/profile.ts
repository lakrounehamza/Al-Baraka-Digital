import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {PofileService} from '../../../services/client/pofile';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {OperationService} from '../../../services/client/operation';

@Component({
  selector: 'app-profile',
  imports: [FormsModule, CommonModule],
  templateUrl: './profile.html',
  styleUrl: './profile.css',
})
export class Profile implements OnInit {

  username: string = "";
  numerAccount: string = "";
  balance: number = 0;
  role: string = "";
  active: boolean = false;
  createdAt: string = "";
  operations: any[] = [];
  burgaremune = false;

  showModal = false;
  operationType: 'VIREMENT' | 'DEPOT' | 'RETRAIT' = 'DEPOT';
  operation: any = {
    type: '',
    amount: '',
    accountDestination_id: ''
  };

  showDetailsModal = false;
  selectedOperation: any = null;

  // File upload properties
  selectedFile: File | null = null;
  filePreviewUrl: string | null = null;
  isUploading: boolean = false;

  constructor(
    private profileservice: PofileService,
    private cd: ChangeDetectorRef,
    private operationService: OperationService
  ) {}

  ngOnInit(): void {
    this.setData();
  }

  setData() {
    this.profileservice.getAccount().subscribe({
      next: (res: any) => {
        console.log(res);
        this.username = res.onwer.username;
        this.numerAccount = res.numer;
        this.balance = res.balance;
        this.role = res.onwer.role;
        this.active = res.onwer.active;
        this.createdAt = res.onwer.created_at;
        this.operations = res.operations;
        this.cd.detectChanges();
      },
      error: (err) => {
        console.error("Erreur lors du chargement du profil:", err);
      }
    });
  }

  getTypeBadge(type: string): string {
    let index: 1 | 2 | 3 | 0 = 0;

    if (type === "RETRAIT") index = 1;
    if (type === "VERSEMENT" || type === "DEPOT") index = 2;
    if (type === "VIREMENT") index = 3;

    const config: Record<number, { class: string; icon: string }> = {
      1: {class: 'bg-red-100 text-red-800', icon: '↓'},
      2: {class: 'bg-green-100 text-green-800', icon: '↑'},
      3: {class: 'bg-blue-100 text-blue-800', icon: '→'}
    };

    if (!config[index]) return type;

    const c = config[index];

    return `
    <span class="px-3 py-1 text-sm font-semibold rounded-full ${c.class}">
      ${c.icon} ${type}
    </span>
  `;
  }

  getStatusBadge(status: string): string {
    let index: 1 | 2 | 3 | 0 = 1;

    if (status === "PENDING") index = 1;
    if (status === "VALIDATED" || status === "EXECUTED") index = 2; // Ajout de EXECUTED
    if (status === "REJECTED") index = 3;

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

  toggleMobile() {
    this.burgaremune = !this.burgaremune;
  }

  openModal(type: 'VIREMENT' | 'DEPOT' | 'RETRAIT') {
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
    // Validation
    if (!this.operation.amount || this.operation.amount <= 0) {
      console.error("Montant invalide");
      return;
    }

    if (this.operationType === 'VIREMENT' && !this.operation.accountDestination_id) {
      console.error("Compte destination requis pour un virement");
      return;
    }

    const accountDest = this.operation.accountDestination_id || undefined;

    this.operationService.createOperation(
      this.operation.type,
      this.operation.amount,
      accountDest
    ).subscribe({
      next: (res: any) => {
        console.log("Opération créée avec succès:", res.id);
        this.closeModal();
        this.setData(); // Recharger les données
      },
      error: (err) => {
        console.error("Erreur lors de la création de l'opération:", err);
        alert("Erreur lors de la création de l'opération");
      }
    });
  }

  openDetailsModal(op: any) {
    this.selectedOperation = op;
    this.showDetailsModal = true;
  }

  closeDetailsModal() {
    this.showDetailsModal = false;
    this.selectedOperation = null;
    this.selectedFile = null;
    this.filePreviewUrl = null;
  }

  // File upload methods
  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.processFile(file);
    }
  }

  onDragOver(event: DragEvent) {
    event.preventDefault();
    event.stopPropagation();
    const target = event.target as HTMLElement;
    target.classList.add('bg-blue-25', 'border-blue-500');
  }

  onDragLeave(event: DragEvent) {
    event.preventDefault();
    event.stopPropagation();
    const target = event.target as HTMLElement;
    target.classList.remove('bg-blue-25', 'border-blue-500');
  }

  onDrop(event: DragEvent) {
    event.preventDefault();
    event.stopPropagation();
    const target = event.target as HTMLElement;
    target.classList.remove('bg-blue-25', 'border-blue-500');

    const files = event.dataTransfer?.files;
    if (files && files.length > 0) {
      this.processFile(files[0]);
    }
  }

  processFile(file: File) {
    // Check file size (10MB limit)
    if (file.size > 10 * 1024 * 1024) {
      alert('Le fichier est trop volumineux. Taille maximale : 10MB');
      return;
    }

    // Check file type
    if (!this.isValidFileType(file)) {
      alert('Type de fichier non supporté. Utilisez PNG, JPG, PDF, DOC ou DOCX.');
      return;
    }

    this.selectedFile = file;

    // Create preview for images
    if (this.isImageFile(file)) {
      const reader = new FileReader();
      reader.onload = (e) => {
        this.filePreviewUrl = e.target?.result as string;
        this.cd.detectChanges();
      };
      reader.readAsDataURL(file);
    }
  }

  removeFile() {
    this.selectedFile = null;
    this.filePreviewUrl = null;
    // Reset file input
    const fileInput = document.getElementById('file-upload') as HTMLInputElement;
    if (fileInput) {
      fileInput.value = '';
    }
  }

  isImageFile(file: File): boolean {
    return file.type.startsWith('image/');
  }

  isValidFileType(file: File): boolean {
    const validTypes = [
      'image/png',
      'image/jpeg',
      'image/jpg',
      'application/pdf',
      'application/msword',
      'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
    ];
    return validTypes.includes(file.type);
  }

  uploadDocument() {
    if (!this.selectedFile || !this.selectedOperation) {
      return;
    }

    this.isUploading = true;

    const formData = new FormData();
    formData.append('file', this.selectedFile);

    // Upload document to the server
    this.operationService.uploadDocument(this.selectedOperation.id, formData).subscribe({
      next: (res: any) => {
        console.log('Document uploaded successfully:', res);
        this.isUploading = false;
        this.selectedFile = null;
        this.filePreviewUrl = null;
        // Refresh operations data
        this.setData();
        alert('Document téléchargé avec succès !');
      },
      error: (err: any) => {
        console.error('Error uploading document:', err);
        this.isUploading = false;
        alert('Erreur lors du téléchargement du document');
      }
    });
  }
}
