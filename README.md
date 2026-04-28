Developing a secure web application for digital wallet management, enabling seamless user-to-user money transfers and account funding via credit/debit cards

---

# STATUS: Currently in process.

# Latest updates:
```
Log #11:
- Removed the list of wallets and cards from UserLongResponse, due to security reasons.

- Added an identifier to ExchangeRateRespone.

- Changed the counterparty property to a direction that indicates whether the money was received or sent.
```
```
Log #10:
- Update the whole money flow & currency movement.

- Add Exchange rates which are updated by external api (https://app.exchangerate-api.com),
  with automatic recall on every 24 hours or manual call by admins in the new ExchangeRateController.
  
- Wallet Resolution logic now makes it possible to choose the ideal money 
  flow path even if currency is used.

- Transactions now have separate information for sent and received money, 
  with their currencies and exchange rate.

- Transactions now can be searched by label as well with their new fields that were added.
```
```
Log #9:
- Fix api response to be suitasble for android client.

- Add label to transaction models.

- Update transaction search for the new label field.
 
- Add wallet delete endpoind.

- Update wallet create and delete rules. Wallets count have to be between 1 and 5 now. 
```
```
Log #8:
- Add CurrencyController and CurrencyService to be able to retrieve currency data from the api.
```
```
Log #7:
- Fix ApiResponse. Every response from backend now returns a uniform structure. 

- Fix pay and transfer method namings in the controllers.  
```
``` 
Log #6:
- The project now supports multiple cards and wallets.

- All validations are centralized by type, and all validation messages are stored 
  in a single place (ValidationMessages).

- Each entity has both short and long (detailed) response.

- Services are now properly separated by responsibility.

- New endpoints were created, and each role now has clearly defined responsibilities.

- The database schema was updated: users, cards, and wallets now include 
  an isDeleted field for future soft-delete handling. This is currently implemented only for cards.

- Security was improved to better handle authentication and authorization.

- Repositories were enhanced with more useful and specialized queries.
```
``` 
Log #5:
- Add JWT implementation
```
``` 
Log #4:
- Fix sender to be nullable.

- Add Global Exception Handler.

- Add ApiErrorResponse DTO.
```
``` 
Log #3:
- Add pay endpoint to transaction controller.

- Fix search endpoints.

- Remove unused service methods.
```
``` 
Log #2:
- Add mappers.

- Update service abstraction.

- Fix card information to be more secure.
```
``` 
Log #1:
- Add all models with their repositories.

- Add basic business logic.

- Add Controllers and test if workflow is correct.
```
---

For further information, questions, or feedback, feel free to get in touch:

| Name           | Email                        | GitHub                           |
|----------------|------------------------------|----------------------------------|
| Todor Krushkov | todorkrushkov.1304@gmail.com | https://github.com/todorkrushkov |

---

Built with clean code, security, and scalability in mind.
