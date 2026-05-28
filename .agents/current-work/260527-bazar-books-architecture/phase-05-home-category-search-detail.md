# Phase 05: Home, Category, Search, Detail

Status: Pending  
Priority: P1  
Effort: 14h

## Overview

Implement the Figma-grounded browsing experience from sections `4. Home` and `5. Category`. Phase 05 is the implementation source for Home, Location entry from Home, Vendors, Authors, Author Detail, Book Detail, Category, Search, recent searches, and wishlist data hooks. The full Favorites screen remains owned by Phase 07.

UI follows Figma layout and copy direction with native Material 3 Compose and the core design system. Do not use pixel-perfect absolute positioning, and do not recreate system status bars, bottom/home indicators, or keyboard UI.

## Figma Anchors

- `4. Home`
- `4.1 Home - Set Location`
- `4.2 Home - Set Location`
- `4.3 Home - Vendors`
- `4.3 Detail Menu`
- `4.3 Home - Authors`
- `4.3 Home - Authors - Inner page`
- `5 Menu`
- `5.1 Menu-Search`
- `9.3 Your Favorites` as a wishlist data consumer only; the screen implementation belongs to Phase 07.

## Implementation Steps

1. Implement Home flow from `4. Home`:
   - Top app bar title `Home`.
   - Special offer banner with `Special Offer`, `Discount 25%`, and `Order Now`.
   - `Top of Week` horizontal book section with `See all`.
   - `Best Vendors` preview with `See all`.
   - `Authors` preview with `See all`.
   - Bottom shell remains owned by Phase 02 and exposes only Home, Category, Cart, and Profile destinations.
2. Implement Category and Search from `5 Menu` and `5.1 Menu-Search`:
   - Category screen title `Category`.
   - Category tabs: `All`, `Novels`, `Self Love`, `Science`, `Romantic`.
   - Product cards use Figma-style book title, cover, and price rhythm.
   - Search screen title `Search`.
   - Search field placeholder `Search`.
   - `Recent Searches` section with persisted recent terms, results, and empty state.
3. Implement Vendor flow from `4.3 Home - Vendors`:
   - Use app copy `Vendors`, correcting the Figma typo `Vendords`.
   - Vendor tabs: `All`, `Books`, `Poems`, `Special for you`, `Stationary`, `Our Vendors`.
   - Seed/display Figma-style vendors: `Wattpad`, `Kuromi`, `Crane & Co`, `GooDay`, `Warehouse`, `Peppa Pig`, `Jstor`, `Peloton`, `Haymarket`.
4. Implement Author flow from `4.3 Home - Authors` and `4.3 Home - Authors - Inner page`:
   - Authors screen title `Authors`.
   - Author tabs: `All`, `Poets`, `Playwrights`, `Novelists`, `Journalists`.
   - Author list shows author name and short bio.
   - Author detail shows name, role, rating, `About`, and `Products`.
   - Normalize duplicate/lowercase Figma category labels.
5. Implement Book Detail from `4.3 Detail Menu`:
   - Required UI/data: cover, title, description, review/rating, quantity stepper, price, wishlist state, `Continue shopping`, and `View cart`.
   - `Continue shopping` returns to the previous browsing surface.
   - `View cart` navigates through `core:navigation`; Phase 05 must not depend directly on the cart feature implementation.
6. Implement Location entry from Home using `4.1 Home - Set Location` and `4.2 Home - Set Location`:
   - Address detail screen shows selected address detail and `Confirmation`.
   - Address form uses Kuwait-style fields: name, phone, governorate, city, block, street, building, optional floor, optional flat, optional avenue.
   - Persist address through the shared address repository/model planned in Phase 03 so checkout and profile can reuse it later.
7. Define MVI contracts at behavior level:
   - Home state: offers, top-of-week books, best vendors, authors, selected location, loading/error/offline.
   - Category state: selected tab, paged products, filter/search entry, loading/error/empty.
   - Search state: query, recent searches, results, no-results, loading/error.
   - Book detail state: book, rating/reviews, stock, quantity, wishlist, cart action state.
   - Vendor state: selected vendor tab, vendors, loading/error/empty.
   - Author state: selected author tab, authors, author detail, author products.
   - Location state: address form, validation errors, save/confirmation event.
8. Define data contracts expected from Phase 03:
   - Catalog repository supports home feed, category products, search request with query/category/author/vendor/page/pageSize/sort, book detail, related products, vendor list, author list, author detail, and author products.
   - Wishlist repository supports observe/toggle for book cards and detail.
   - Recent search repository persists search terms offline.
   - Address repository stores the selected Home/checkout/profile address.

## Acceptance Criteria

- Home matches Figma section order and critical copy from `4. Home`.
- Category tabs match `All`, `Novels`, `Self Love`, `Science`, and `Romantic`.
- Search displays `Recent Searches`, persists recent terms offline, and shows results and empty state.
- Vendor and Author list/detail flows are reachable from Home.
- Book Detail can update quantity, toggle wishlist, continue shopping, and navigate to cart through `core:navigation`.
- Location entry validates and persists the shared Kuwait-style address model.
- Wishlist persistence is implemented for cards and detail so Phase 07 Favorites can consume the same data.
- Feature modules do not depend on each other directly.

## Test Plan

- Unit test Home ViewModel section loading, section actions, location confirmation, offline/error states.
- Unit test Category tab selection, pagination trigger, empty state, and product click navigation effect.
- Unit test Search query submission, recent-search persistence, recent-search click, clear behavior, and no-results state.
- Unit test Book Detail quantity changes, wishlist toggle, continue-shopping event, and view-cart navigation effect.
- Unit test Vendors and Authors tab filtering plus author-detail product loading.
- Unit test Location form validation and persisted address save.
- Compose UI tests verify Figma-critical copy is visible for Home, Category, Search, Vendors, Authors, Author Detail, Book Detail, and Location screens.

## Validation

```powershell
.\gradlew.bat :feature:home:testDebugUnitTest
.\gradlew.bat :feature:home:compileDebugKotlin
.\gradlew.bat :core:data:testDebugUnitTest
.\gradlew.bat :app:assembleDebug
```
