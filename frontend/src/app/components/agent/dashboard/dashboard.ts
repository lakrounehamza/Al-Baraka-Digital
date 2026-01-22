import { Component, OnInit } from '@angular/core';
import { Operations } from '../../../services/agent/operations';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {

  operations: any[] = [];
  showDetailsModal = false;
  selectedOperation: any = null;
  isLoading = false;
  lastUpdated: Date | null = null;
  errorMessage: string | null = null;
  successMessage: string | null = null;

  // Statistiques des montants
  get totalDeposits(): number {
    return this.operations
      .filter(op => op.type === 'DEPOT')
      .reduce((sum, op) => sum + op.amount, 0);
  }

  get totalWithdrawals(): number {
    return this.operations
      .filter(op => op.type === 'RETRAIT')
      .reduce((sum, op) => sum + op.amount, 0);
  }

  // Statistiques calculées
  get totalDepositsCount(): number {
    return this.operations.filter(op => op.type === 'DEPOT').length;
  }

  get totalWithdrawalsCount(): number {
    return this.operations.filter(op => op.type === 'RETRAIT').length;
  }

  get pendingOperationsCount(): number {
    return this.operations.filter(op => op.status === 'PENDING').length;
  }

  constructor(private operationsService: Operations) {}

  ngOnInit(): void {
    this.loadOperations();
  }

  loadOperations(): void {
    this.isLoading = true;
    this.errorMessage = null;

    this.operationsService.getPendingOperations().subscribe({
      next: (response: any) => {
        this.operations = response.content || [];
        this.lastUpdated = new Date();
        this.isLoading = false;
        console.log('Opérations chargées:', this.operations.length);
      },
      error: (error) => {
        console.error('Erreur lors du chargement des opérations:', error);
        this.errorMessage = 'Erreur lors du chargement des opérations. Veuillez réessayer.';
        this.isLoading = false;
      }
    });
  }

  refreshData(): void {
    console.log('Actualisation des données...');
    this.isLoading = true;
    this.errorMessage = null;
    this.successMessage = null;

    this.operationsService.getPendingOperations().subscribe({
      next: (response: any) => {
        this.operations = response.content || [];
        this.lastUpdated = new Date();
        this.isLoading = false;
        this.successMessage = 'Données mises à jour avec succès';

        // Effacer le message de succès après 3 secondes
        setTimeout(() => {
          this.successMessage = null;
        }, 3000);
      },
      error: (error) => {
        console.error('Erreur lors du rechargement des données:', error);
        this.errorMessage = 'Erreur lors du rechargement des données. Veuillez réessayer.';
        this.isLoading = false;
      }
    });
  }

  openDetailsModal(operation: any): void {
    this.selectedOperation = operation;
    this.showDetailsModal = true;
  }

  closeDetailsModal(): void {
    this.showDetailsModal = false;
    this.selectedOperation = null;
  }

  approveOperation(operationId: string): void {
    if (confirm('Êtes-vous sûr de vouloir approuver cette opération ?')) {
      this.operationsService.approveOperation(operationId).subscribe({
        next: (response) => {
          console.log('Opération approuvée:', response);
          this.loadOperations(); // Recharger la liste
          this.closeDetailsModal();
          alert('Opération approuvée avec succès');
        },
        error: (error) => {
          console.error('Erreur lors de l\'approbation:', error);
          alert('Erreur lors de l\'approbation de l\'opération');
        }
      });
    }
  }

  rejectOperation(operationId: string): void {
    if (confirm('Êtes-vous sûr de vouloir rejeter cette opération ?')) {
      this.operationsService.rejectOperation(operationId).subscribe({
        next: (response) => {
          console.log('Opération rejetée:', response);
          this.loadOperations(); // Recharger la liste
          this.closeDetailsModal();
          alert('Opération rejetée avec succès');
        },
        error: (error) => {
          console.error('Erreur lors du rejet:', error);
          alert('Erreur lors du rejet de l\'opération');
        }
      });
    }
  }

  getTypeBadge(type: string): string {
    let index: 1 | 2 | 3 | 0 = 0;

    if (type === "RETRAIT") index = 1;
    if (type === "DEPOT") index = 2;
    if (type === "VIREMENT") index = 3;

    const config: Record<number, { class: string; icon: string; label: string }> = {
      1: { class: 'bg-red-100 text-red-800', icon: '↑', label: 'Retrait' },
      2: { class: 'bg-green-100 text-green-800', icon: '↓', label: 'Dépôt' },
      3: { class: 'bg-blue-100 text-blue-800', icon: '→', label: 'Virement' }
    };

    if (!config[index]) return type;

    const c = config[index];

    return `
    <span class="px-3 py-1 text-sm font-semibold rounded-full ${c.class}">
      ${c.icon} ${c.label}
    </span>
  `;
  }

  getStatusBadge(status: string): string {
    let index: 1 | 2 | 3 | 0 = 1;

    if (status === "PENDING") index = 1;
    if (status === "APPROVED" || status === "EXECUTED") index = 2;
    if (status === "REJECTED") index = 3;

    const config = {
      1: { class: 'bg-yellow-100 text-yellow-800', label: 'En attente' },
      2: { class: 'bg-green-100 text-green-800', label: 'Approuvé' },
      3: { class: 'bg-red-100 text-red-800', label: 'Rejeté' }
    };

    const c = config[index];

    return `<span class="px-2 py-1 text-xs font-semibold rounded-full ${c.class}">${c.label}</span>`;
  }

  formatAccountNumber(numer: string): string {
    return numer.match(/.{1,4}/g)?.join(' ') || numer;
  }
}
