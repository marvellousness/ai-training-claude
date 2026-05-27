# Phase 06: Cart, Checkout, Orders

Status: Pending  
Priority: P1  
Effort: 12h

## Overview

Implement cart, Kuwait-style address/location, simulated payment, delivery slot selection, order summary, payment details, order status, and rating feedback.

## Figma Anchors

- `6.1 Cart - Confirm Order`
- `6.1 Cart - Confirm Order visa added`
- `6.2 Cart - Confirm Order - See details`
- `6.3 Cart - Confirm Order - Payment Method`
- `Cart Empty`
- `7 Order Status - Order Success Waiting Shipper`
- `7.3 Order Status - Order Received & Rating`

## Implementation Steps

1. Implement Cart MVI:
   - cart items
   - quantity changes
   - remove item
   - empty cart
   - subtotal/shipping/total
2. Implement address/location MVI with fields:
   - name
   - phone
   - governorate
   - city
   - block
   - street name/number
   - building name/number
   - optional floor
   - optional flat
   - optional avenue
   - save label: home/offices
3. Implement checkout draft:
   - selected address
   - payment method
   - delivery date
   - delivery window
   - order summary
4. Implement payment options:
   - KNET
   - credit card demo
   - cash on delivery
5. Implement order details breakdown:
   - item lines
   - price
   - shipping
   - total payment
6. Implement deterministic order status state machine:
   - pending
   - paid
   - processing
   - shipping
   - delivered
   - cancelled
   - refunded
7. Implement waiting shipper and received/rating screens.
8. Persist every checkout/order transition in Room.

## Acceptance Criteria

- Cart works offline and survives restart.
- Checkout cannot submit invalid address/payment/date data.
- Order is persisted before simulated processing completes.
- Order status transitions are valid and testable.
- Rating feedback is saved locally.

## Validation

```powershell
.\gradlew.bat :feature:cart_checkout:testDebugUnitTest
.\gradlew.bat :feature:orders:testDebugUnitTest
.\gradlew.bat :core:data:testDebugUnitTest
.\gradlew.bat :app:assembleDebug
```
