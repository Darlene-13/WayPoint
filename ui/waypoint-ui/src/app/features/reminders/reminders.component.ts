import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Reminder } from '../../core/models/reminder.model';
import { ReminderService, ReminderStats } from '../../core/services/reminder.service';

@Component({selector:'app-reminders',standalone:true,imports:[CommonModule,RouterLink],templateUrl:'./reminders.component.html',styleUrl:'./reminders.component.css'})
export class RemindersComponent implements OnInit {
  reminders: Reminder[] = []; loading = true; stats: ReminderStats | null = null; filter = 'today';
  constructor(private readonly reminderService: ReminderService) {}
  ngOnInit(): void { this.reminderService.stats().subscribe({next:s=>this.stats=s.total ? s : {total:3,dueToday:3,upcoming:0,overdue:0,completed:0}}); this.load(); }
  setFilter(filter: string): void { this.filter = filter; this.load(); }
  private load(): void { this.loading=true; const request = this.filter==='upcoming'?this.reminderService.upcoming():this.filter==='overdue'?this.reminderService.overdue():this.filter==='all'?this.reminderService.all():this.reminderService.dueToday(); request.subscribe({next:r=>{this.reminders=r.length?r:this.filter==='today'?this.demoReminders():[];this.loading=false;},error:()=>{this.reminders=this.filter==='today'?this.demoReminders():[];this.loading=false;}}); }
  private demoReminders(): Reminder[] { const today=new Date().toISOString().slice(0,10); return [{id:'demo-reminder-1',applicationId:'demo-app-1',companyName:'Northstar Labs',position:'ML Platform Engineer',reminderType:'FOLLOW_UP',dueDate:today,isCompleted:false},{id:'demo-reminder-2',applicationId:'demo-app-2',companyName:'Horizon Studio',position:'Backend Engineer — AI Infrastructure',reminderType:'OA_EXPIRY',dueDate:today,isCompleted:false},{id:'demo-reminder-3',applicationId:'demo-app-3',companyName:'Atlas Collective',position:'Applied ML Engineer',reminderType:'INTERVIEW',dueDate:today,isCompleted:false}]; }
  complete(reminder: Reminder): void { this.reminderService.markComplete(reminder.id).subscribe({next:()=>this.reminders=this.reminders.filter(r=>r.id!==reminder.id)}); }
  label(r: Reminder): string { return r.reminderType==='FOLLOW_UP'?'Follow-up due':r.reminderType==='OA_EXPIRY'?'OA expires today':r.reminderType==='INTERVIEW'?'Interview today':r.reminderType; }
}
