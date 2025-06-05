



import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SessionStorageService {

  constructor() { }

  setItem(key: string, value: any) {
    if (typeof window !== 'undefined' && window.sessionStorage) {
      sessionStorage.setItem(key, JSON.stringify(value));
    }
  }

  getItem(key: string){
    if (typeof window !== 'undefined' && window.sessionStorage) {
      const item = sessionStorage.getItem(key);
      return item ? JSON.parse(item) : null;
    }
    return null;
  }

  removeItem(key: string) {
    if (typeof window !== 'undefined' && window.sessionStorage) {
      sessionStorage.removeItem(key);
    }
  }

  clear() {
    if (typeof window !== 'undefined' && window.sessionStorage) {
      sessionStorage.clear();
    }
  }

}
