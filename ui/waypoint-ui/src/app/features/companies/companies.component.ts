import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Company } from '../../core/models/company.model';
import { CompanyService } from '../../core/services/company.service';
import { JobApplicationService } from '../../core/services/job-application.service';
import { JobApplication } from '../../core/models/job-application.model';

@Component({ selector: 'app-companies', standalone: true, imports: [CommonModule, FormsModule], templateUrl: './companies.component.html', styleUrl: './companies.component.css' })
export class CompaniesComponent implements OnInit {
  companies: Company[] = [];
  isDemo = false;
  applications: JobApplication[] = [];
  search = ''; showingForm = false; loading = true; error = '';
  draft = { name: '', website: '', industry: '', notes: '' };
  dream = new Set<string>();
  constructor(private readonly companyService: CompanyService, private readonly applicationService: JobApplicationService) {}
  ngOnInit(): void {
    if (typeof localStorage !== 'undefined') this.dream = new Set(JSON.parse(localStorage.getItem('waypoint-dream-companies') || '[]'));
    this.companyService.list().subscribe({ next: c => { this.companies = c; this.isDemo = c.length === 0; if (this.isDemo) this.companies = this.demoCompanies(); this.loading = false; }, error: () => { this.error = 'Could not load companies right now.'; this.loading = false; } });
    this.applicationService.list().subscribe({ next: a => this.applications = a, error: () => this.applications = [] });
  }
  get filtered(): Company[] { const q = this.search.trim().toLowerCase(); return this.companies.filter(c => !q || [c.name, c.industry, c.website].some(v => v?.toLowerCase().includes(q))); }
  count(company: Company): number { return this.applications.filter(a => a.companyId === company.id || a.companyName?.toLowerCase() === company.name.toLowerCase()).length; }
  isDream(c: Company): boolean { return this.dream.has(c.id); }
  toggleDream(c: Company): void { this.isDream(c) ? this.dream.delete(c.id) : this.dream.add(c.id); if (typeof localStorage !== 'undefined') localStorage.setItem('waypoint-dream-companies', JSON.stringify([...this.dream])); }
  add(): void { if (!this.draft.name.trim()) return; this.companyService.create({ ...this.draft, name: this.draft.name.trim() }).subscribe({ next: c => { this.companies = [c, ...this.companies]; this.draft = { name: '', website: '', industry: '', notes: '' }; this.showingForm = false; }, error: () => this.error = 'Company could not be saved.' }); }
  private demoCompanies(): Company[] { return [
    { id: 'demo-northstar', name: 'Northstar Labs', industry: 'Product & technology', website: 'https://northstar.example', notes: '', createdAt: '', updatedAt: '' },
    { id: 'demo-horizon', name: 'Horizon Studio', industry: 'Design & media', website: 'https://horizon.example', notes: '', createdAt: '', updatedAt: '' },
    { id: 'demo-atlas', name: 'Atlas Collective', industry: 'Data & impact', website: 'https://atlas.example', notes: '', createdAt: '', updatedAt: '' }
  ]; }
}
