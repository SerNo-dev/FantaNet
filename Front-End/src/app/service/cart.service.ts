import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartItemCount = new BehaviorSubject<number>(0);
  cartItemCount$ = this.cartItemCount.asObservable();

  updateCartItemCount(count: number) {
    this.cartItemCount.next(count);
  }

  resetCartItemCount(): void {
    this.cartItemCount.next(0);
  }
}
